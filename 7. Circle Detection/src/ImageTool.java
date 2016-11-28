import java.awt.image.BufferedImage;

public class ImageTool {
	
	/**
	 * Private constructor, prevent from being instantiated.
	 */
	private ImageTool() {
	}
	
	/**
	 * Returns separate value of RGB for a image.
	 * @return separate value of RGB for a image
	 */
	public static int[][] getSeparateImgRGB(BufferedImage img) {
		int pixelNumber = img.getHeight() * img.getWidth();
		int[][] separateRGB = new int[pixelNumber][3];
		int[] imgRGB = getImgRGB(img);
		for (int i = 0; i < pixelNumber; i++) {
			int color = imgRGB[i];
			int red = ColorTool.getRed(color);
			int green = ColorTool.getGreen(color);
			int blue = ColorTool.getBlue(color);
			separateRGB[i][0] = red;
			separateRGB[i][1] = green;
			separateRGB[i][2] = blue;
		}
		return separateRGB;
	}
	
	/**
	 * Returns array of RGB values for the input image.
	 * @return array of RGB values for the input image
	 */
	private static int[] getImgRGB(BufferedImage img) {
		int width = img.getWidth();
		int height = img.getHeight();
		return img.getRGB(0, 0, width, height, null, 0, width);
	}
	
}
