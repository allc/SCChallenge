import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
public class EdgeDetector {
	
	private BufferedImage img; // grey scale image
	private int width;
	private int height;
	private int pixelNumber;

	/**
	 * Constructs an <code>EdgeDetector</code> detect the edge of an given 
	 * image.
	 * @param img <code>BufferedImage</code> to find the edge
	 */
	public EdgeDetector(BufferedImage img) {
		this.img = ImageProcess.greyscale(img);
		width = img.getWidth();
		height = img.getHeight();
		pixelNumber = width * height;
	}
	
	/**
	 * Constructs an <code>EdgeDetector</code> detect the edge of an given 
	 * image.
	 * @param imgPath path to a image file
	 * @throws IOException
	 */
	public EdgeDetector(String imgPath) throws IOException {
		this(ImageIO.read(new File(imgPath)));
	}
	
	public BufferedImage edgeDetect() {
		BufferedImage edges = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
		
		int[] verticalMask = {
				-1, 0, 1,
				-2, 0, 2,
				-1, 0, 1
		};
		int[] horizontalMask = {
				-1, -2, -1,
				 0,  0,  0,
				 1,  2,  1
		};
		
		int[][] imgBlue = new int[width][height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				imgBlue[x][y] = ColorTool.getBlue(img.getRGB(x, y));
			}
		}
		
		int[][] gradientScale = new int[width][height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int horizontalGradient = 0;
				int verticalGradient = 0;
				if (x != 0 && x != width - 1 && y != 0 && y != height - 1) { // fill in the edge
					int[][] smallMatrixCoordinates = {
							{x - 1, y - 1}, {x, y - 1}, {x + 1, y - 1},
							{x - 1, y    }, {x, y    }, {x + 1, y    },
							{x - 1, y + 1}, {x, y + 1}, {x + 1, y + 1}
					};
					for (int i = 0; i < 9; i++) {
						horizontalGradient += imgBlue[smallMatrixCoordinates[i][0]][smallMatrixCoordinates[i][1]] * horizontalMask[i];
						verticalGradient += imgBlue[smallMatrixCoordinates[i][0]][smallMatrixCoordinates[i][1]] * verticalMask[i];
					}
					gradientScale[x][y] = (int) Math.sqrt(horizontalGradient * horizontalGradient + verticalGradient * verticalGradient);
				}
			}
		}
		int th = 150; // 150 bingmayong
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (gradientScale[i][j] > th) {
					edges.setRGB(i, j, new Color(255, 255, 255).getRGB());
					//System.out.print("++");
				} else {
					//System.out.print("  ");
				}
				System.out.print(gradientScale[i][j] + " ");
			}
			System.out.println();
		}
		
		return edges;
	}
	
}
