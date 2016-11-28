import java.awt.image.BufferedImage;
import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedImage img = ImageFileTool.getBufferedImage("/Users/Allen/Documents/SampleImages/3.jpg");
		ImageFileTool.saveImageFile(ImageProcess.averageBlur(img), "/Users/Allen/Documents/SampleImages/blur.jpg");
		ImageFileTool.saveImageFile(ImageProcess.greyscale(img), "/Users/Allen/Documents/SampleImages/greyscale.jpg");
		
		BufferedImage img8 = ImageFileTool.getBufferedImage("/Users/Allen/Documents/SampleImages/8.jpg");
		img8 = ImageProcess.averageBlur(img8);
		EdgeDetector ed = new EdgeDetector(img8);
		ImageFileTool.saveImageFile(ed.edgeDetect(), "/Users/Allen/Documents/SampleImages/edge.jpg");
	}

}
