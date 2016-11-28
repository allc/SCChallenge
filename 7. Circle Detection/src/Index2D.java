
public class Index2D {
	
	private int index;
	
	private int x;
	private int y;

	public Index2D(int index, int width) {
		this.index = index;
		this.x = index % width; 
		this.y = index / width;
	}
	
	public Index2D(int x, int y, int width) {
		this.index = x + y * width;
	}
	
	public int getIndex() {
		return index;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
}
