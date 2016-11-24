import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class CircleDetection {
	
	public static BufferedImage img;
	
	public static void main(String args[]) throws IOException {
		/** load image **/
		img = ImageIO.read(new File("/Users/Allen/Documents/4.jpg"));
		
		int imgHeight = img.getHeight();
		int imgWidth = img.getWidth();
		
		int[] imgRGB = img.getRGB(0, 0, imgWidth, imgHeight, null, 0, imgWidth); // get image pixel array
		
		/** average blur image **/
		int pixelNum = imgRGB.length;
		int[][] imgBlurInter = new int[pixelNum][3]; // array store RGB value for each pixel
		// array store RGB value divided by 9 for each pixel
		for (int i = 0; i < pixelNum; i++) {
			int color = imgRGB[i];
			int red = (color & 0xff0000) >> 16;
			int green = (color & 0xff00) >> 8;
			int blue = color & 0xff;
			imgBlurInter[i][0] = red / 9;
			imgBlurInter[i][1] = green / 9;
			imgBlurInter[i][2] = blue / 9;
		}
		int[] blurImgRGB = new int[pixelNum];
		for (int i = 0; i < pixelNum; i++) {
			int[] coordinate = to2D(i);
			int x = coordinate[0];
			int y = coordinate[1];
			if (x == 0 || x == imgWidth - 1 || y == 0 || y == imgHeight - 1) { // fill in the edge
				blurImgRGB[i] = imgRGB[i];
			} else { 
				int[][] smallMatrixIndexs = {
						{x - 1, y - 1}, {x, y - 1}, {x + 1, y - 1},
						{x - 1, y    }, {x, y    }, {x + 1, y    },
						{x - 1, y + 1}, {x, y + 1}, {x + 1, y + 1}
				};
				int red = 0;
				int green = 0;
				int blue = 0;
				for (int j = 0; j < 9; j++) {
					int pixelIndex = to1D(smallMatrixIndexs[j]);
					red += imgBlurInter[pixelIndex][0];
					green += imgBlurInter[pixelIndex][1];
					blue += imgBlurInter[pixelIndex][2];
				}
				int color = (red & 0xff) << 16 | (green & 0xff) << 8 | (blue & 0xff);
				blurImgRGB[i] = color;
			}
		}
		BufferedImage blurImg = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_3BYTE_BGR);
		blurImg.setRGB(0, 0, imgWidth, imgHeight, blurImgRGB, 0, imgWidth);
		ImageIO.write(blurImg, "jpg", new File("/Users/Allen/Documents/blurImg.jpg"));
		
		/** greyscale image **/
		BufferedImage grayImg;
		grayImg = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_BYTE_GRAY);
		Graphics graphics = grayImg.getGraphics();
		graphics.drawImage(img, 0, 0, null);
		graphics.dispose();
		ImageIO.write(grayImg, "jpg", new File("/Users/Allen/Documents/grayCircles.jpg"));
	}
	
	/**
	 * convert 1d to 2d coordinate
	 * @param p 1d array index
	 * @return 2d coordinate
	 */
	public static int[] to2D(int p) {
		int width = img.getWidth();
		int x = p % width; 
		int y = p / width;
		int[] coordinate = {x, y};
		return coordinate;
	}
	
	/**
	 * convert 2d coordinate to 1d
	 * @param coordinate
	 * @return
	 */
	public static int to1D(int[] coordinate) {
		int width = img.getWidth();
		return coordinate[0] + coordinate[1] * width;
	}
	
}
