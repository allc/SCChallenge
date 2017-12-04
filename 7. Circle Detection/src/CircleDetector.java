
public class CircleDetector {
	
	private boolean[][] edges;
	
	private int width;
	private int height;

	public CircleDetector(boolean[][] edges) {
		this.edges = edges;
		width = edges.length;
		height = edges[0].length;
	}
	
	public int[] circleDetect() {
		int centerX = 0;
		int centerY = 0;
		int circleRadius = 10;
		int[][][] accumulator = new int[width][height][300];
		for (int r = 10; r < 100; r++) {
			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {
					if (edges[i][j]) {
						for (int theta = 0; theta < 360; theta++) {
							double thetaRadius = theta * Math.PI / 180;
							int x = (int) (i + r * Math.sin(thetaRadius));
							int y = (int) (j - r * Math.cos(thetaRadius));
							if (x > 0 && x < width && y > 0 && y < height) {
								accumulator[x][y][r] += 1;
								if (accumulator[centerX][centerY][circleRadius] < accumulator[x][y][r]) {
									centerX = x;
									centerY = y;
									circleRadius = r;
								}
							}
						}
					}
				}
			}
		}
		System.out.println(centerX + " " + centerY + " " + circleRadius);
		int[] circle = {centerX, centerY, circleRadius};
		return circle;
	}
	
}
