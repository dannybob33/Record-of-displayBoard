package DisplayBoardEmulation.actualPong;

public class Paddle {
	public int row;
	public int col;
	public int width;
	public int height;
	
	private final int maxRow;
	private final int maxHeight;
	
	public Paddle(int row,int col,int width,int height,int maxRow,int maxHeight) {
		this.maxRow = maxRow;
		this.maxHeight = maxHeight;
		this.row = row;
		this.col = col;
		this.width = width;
		this.height = height;
	}
}
