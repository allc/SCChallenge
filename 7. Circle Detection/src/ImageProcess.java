import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class ImageProcess {
	
	/**
	 * Private constructor, prevent from being instantiated.
	 */
	private ImageProcess() {
	}

	/**
	 * Apply 3 x 3 average blur to a image and return <code>BufferedImage</code>.
	 * @param img <code>BufferedImage</code> to be processed
	 * @return average blur <code>BufferedImage</code>
	 */
	/*
	 * This implement ignores the edges
	 */
	public static BufferedImage averageBlur(BufferedImage img) {
		int[][] separateRGB = ImageTool.getSeparateImgRGB(img);
		int width = img.getWidth();
		int height = img.getHeight();
		int pixelNumber = width * height;
		int[] blurImgRGB = new int[pixelNumber]; // array of blur image RGB
		for (int i = 0; i < pixelNumber; i++) {
			Index2D coordinate = new Index2D(i, width);
			int x = coordinate.getX();
			int y = coordinate.getY();
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
					int pixelIndex = new Index2D(
							smallMatrixCoordinates[j][0], smallMatrixCoordinates[j][1], width).getIndex();
					red += separateRGB[pixelIndex][0];
					green += separateRGB[pixelIndex][1];
					blue += separateRGB[pixelIndex][2];
				}
				red /= 9;
				green /= 9;
				blue /= 9;
				int color = ColorTool.getSingleRGB(red, green, blue);
				blurImgRGB[i] = color;
			}
		}
		BufferedImage blurImg = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		blurImg.setRGB(0, 0, width, height, blurImgRGB, 0, width);
		return blurImg;
	}
	
	/**
	 * Returns grey scale <code>BufferedImage</code>.
	 * @param img <code>BufferedImage</code> to be processed
	 * @return grey scale <code>BufferedImage</code>
	 */
	public static BufferedImage greyscale(BufferedImage img) {
		BufferedImage greyImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		Graphics graphics = greyImg.getGraphics();
		graphics.drawImage(img, 0, 0, null);
		graphics.dispose();
		return greyImg;
	}
		
}
