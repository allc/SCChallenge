import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageProcess {
	
	private BufferedImage img;
	private int width;
	private int height;
	private int pixelNumber;
	
	/**
	 * Construct a <code>ImageProcess</code>.
	 * @param imgPath path to the image file
	 * @throws IOException
	 */
	public ImageProcess(String imgPath) throws IOException {
		img = ImageIO.read(new File(imgPath));
		width = img.getWidth();
		height = img.getHeight();
		pixelNumber = width * height;
	}
	
	/**
	 * Apply 3 x 3 average blur to the image and output the JPEG file with 
	 * given path and file name.
	 * @param outPath path and file name of the output file
	 * @throws IOException
	 */
	public void averageBlur(String outPath) throws IOException {
		int[][] separateRGB = getSeparateRGB();

		int[] blurImgRGB = new int[pixelNumber]; // array of blur image RGB
		for (int i = 0; i < pixelNumber; i++) {
			int[] coordinate = to2D(i);
			int x = coordinate[0];
			int y = coordinate[1];
			if (x == 0 || x == width - 1 || y == 0 || y == height - 1) { // fill in the edge
				blurImgRGB[i] = img.getRGB(x, y);
			} else { 
				int[][] smallMatrixCoordinates = {
						{x - 1, y - 1}, {x, y - 1}, {x + 1, y - 1},
						{x - 1, y    }, {x, y    }, {x + 1, y    },
						{x - 1, y + 1}, {x, y + 1}, {x + 1, y + 1}
				};
				int red = 0;
				int green = 0;
				int blue = 0;
				for (int j = 0; j < 9; j++) {
					int pixelIndex = to1D(smallMatrixCoordinates[j]);
					red += separateRGB[pixelIndex][0];
					green += separateRGB[pixelIndex][1];
					blue += separateRGB[pixelIndex][2];
				}
				red /= 9;
				green /= 9;
				blue /= 9;
				int color = separateToSingleRGB(red, green, blue);
				blurImgRGB[i] = color;
			}
		}
		BufferedImage blurImg = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		blurImg.setRGB(0, 0, width, height, blurImgRGB, 0, width);
		ImageIO.write(blurImg, "jpg", new File(outPath));
	}
	
	/**
	 * Output greyscaled image as JPEG file to given path and file name
	 * @param outPath path and file name of the output file
	 * @throws IOException
	 */
	public void greyscale(String outPath) throws IOException {
		BufferedImage grayImg = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
		Graphics graphics = grayImg.getGraphics();
		graphics.drawImage(img, 0, 0, null);
		graphics.dispose();
		ImageIO.write(grayImg, "jpg", new File(outPath));
	}
	
	/**
	 * Returns array of RGB values for the input image.
	 * @return array of RGB values for the input image
	 */
	private int[] getRGB() {
		return img.getRGB(0, 0, width, height, null, 0, width);
	}

	/**
	 * Returns separate value of RGB for the input image.
	 * @return separate value of RGB for the input image
	 */
	private int[][] getSeparateRGB() {
		int[][] separateRGB = new int[pixelNumber][3];
		int[] imgRGB = getRGB();
		for (int i = 0; i < pixelNumber; i++) {
			int color = imgRGB[i];
			int red = getRed(color);
			int green = getGreen(color);
			int blue = getBlue(color);
			separateRGB[i][0] = red;
			separateRGB[i][1] = green;
			separateRGB[i][2] = blue;
		}
		return separateRGB;
	}

	/**
	 * Return the red value for a given RGB value
	 * @param color a single RGB value
	 * @return red value
	 */
	private int getRed(int color) {
		return (color & 0xff0000) >> 16;
	}

	/**
	 * Return the green value for a given RGB value
	 * @param color a single RGB value
	 * @return green value
	 */
	private int getGreen(int color) {
		return (color & 0xff00) >> 8;
	}

	/**
	 * Return the blue value for a given RGB value
	 * @param color a single RGB value
	 * @return blue value
	 */
	private int getBlue(int color) {
		return color & 0xff;
	}

	/**
	 * Return a single RGB value for the given red, green, blue values
	 * @param red red value
	 * @param green green value
	 * @param blue blue value
	 * @return Return a single RGB value
	 */
	private int separateToSingleRGB(int red, int green, int blue) {
		return (red & 0xff) << 16 | (green & 0xff) << 8 | (blue & 0xff);
	}

	/**
	 * Convert array index to 2D coordinate.
	 * @param p array index
	 * @return 2D coordinate of {x, y}
	 */
	private int[] to2D(int p) {
		int width = img.getWidth();
		int x = p % width; 
		int y = p / width;
		int[] coordinate = {x, y};
		return coordinate;
	}

	/**
	 * Convert 2D coordinate to array index.
	 * @param 2D coordinate of {x, y}
	 * @return array index
	 */
	private int to1D(int[] coordinate) {
		int width = img.getWidth();
		return coordinate[0] + coordinate[1] * width;
	}

	public static void main(String args[]) throws IOException {
		
		long startTime = System.currentTimeMillis(); // running time measure
		
		ImageProcess cd = new ImageProcess("/Users/Allen/Documents/1.jpg");
		
		cd.averageBlur("/Users/Allen/Documents/blurImg.jpg");
		// running time measure
		long endTime   = System.currentTimeMillis();
		System.out.println((endTime - startTime) / 1000.0);
		
		cd.greyscale("/Users/Allen/Documents/greyscaleImg.jpg");
	}
	
}
