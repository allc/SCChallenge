import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class ImageFileTool {

	/**
	 * Private constructor, prevent from being instantiated.
	 */
	private ImageFileTool() {
	}
	
	/**
	 * Returns <code>BufferedImage</code> with image from a file with given 
	 * path and file name.
	 * @param filePath path and filename of the input file
	 * @return <code>BufferedImage</code> image from file
	 * @throws IOException
	 */
	public static BufferedImage getBufferedImage(String filePath) throws IOException {
		return ImageIO.read(new File(filePath));
	}
	
	/**
	 * Save a <code>BufferedImage</code> as a JPEG file with given path and filename.
	 * @param image <code>BufferedImage</code> to save to file
	 * @param outPath path and filename of the output file
	 * @throws IOException
	 */
	public static void saveImageFile(BufferedImage image, String outPath) throws IOException {
		ImageIO.write(image, "jpg", new File(outPath));
	}
	
}
