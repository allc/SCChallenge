import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		long time1 = System.currentTimeMillis();
		
		BufferedImage img8 = ImageFileTool.getBufferedImage("/Users/Allen/Documents/SampleImages/clock2.jpg");
		img8 = ImageProcess.averageBlur(img8);
		
		long time2 = System.currentTimeMillis();
		
		EdgeDetector ed = new EdgeDetector(img8);
		boolean[][] edges = ed.edgeDetect();
		
		BufferedImage edgeImg = new BufferedImage(img8.getWidth(), img8.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		for (int x = 0; x < img8.getWidth(); x++) {
			for (int y = 0; y < img8.getHeight(); y++) {
				if (edges[x][y]) {
					edgeImg.setRGB(x, y, new Color(255, 255, 255).getRGB());
				}
			}
		}
		ImageFileTool.saveImageFile(edgeImg, "/Users/Allen/Documents/SampleImages/edge.jpg");
		
		long time3 = System.currentTimeMillis();
		
		CircleDetector cd = new CircleDetector(edges);
		int[] circle = cd.circleDetect();
		
		long time4 = System.currentTimeMillis();
		
		for (int theta = 0; theta < 360; theta++) {
			double thetaRadius = theta * Math.PI / 180;
			int x = (int) (circle[0] + circle[2] * Math.sin(thetaRadius));
			int y = (int) (circle[1] - circle[2] * Math.cos(thetaRadius));
			img8.setRGB(x, y, new Color(255, 0, 0).getRGB());
		}
		ImageFileTool.saveImageFile(img8, "/Users/Allen/Documents/SampleImages/circle.jpg");
		
		System.out.println("circleDetect time: " + (time4 - time3) / 1000);
		
		int edgePix = 0;
		for (int i = 0; i < edges.length; i++) {
			for (int j = 0; j < edges[0].length; j++) {
				if (edges[i][j]) {
					edgePix++;
				}
			}
		}
		System.out.println(edgePix);
	}

}
