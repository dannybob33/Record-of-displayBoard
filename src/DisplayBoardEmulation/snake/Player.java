package DisplayBoardEmulation.snake;

import java.awt.Color;
import java.util.TreeMap;

public class Player {
	public final Color PLAYER_COLOR;
	public final Color TRAIL_COLOR;
	private int row;
	private int col;
	private int direction;
	public boolean changedDir;
	public TreeMap<String, Integer> directions;
	
	public Player(Color playerColor,Color trailColor,int row,int col,int direction) {
		PLAYER_COLOR = playerColor;
		TRAIL_COLOR = trailColor;
		this.row = row;
		this.col = col;
		this.direction = direction;
		changedDir = false;
		directions = new TreeMap<String,Integer>();
	}
	
	public void addDirection(String key,int direction) {
		directions.put(key, direction);
	}
	
	public void changeDirection(String key) {
		if(!changedDir && directions.containsKey(key)) {
			int newDirection = directions.get(key);
			if(newDirection == 2 && direction == 8) {
				return;
			} else if (newDirection == 6 && direction == 4) {
				return;
			} else if (newDirection == 8 && direction == 2) {
				return;
			} else if (newDirection == 4 && direction == 6) {
				return;
			}
			direction = newDirection;
			changedDir = true;
		}
	}
	
	
	public int getRow() {
		return row;
	}
	
	public void setRow(int row) {
		this.row = row;
	}
	
	public int getCol() {
		return col;
	}
	
	public void setCol(int col) {
		this.col = col;
	}
	
	public int getDirection() {
		return direction;
	}
	
	public void setDirection(int dir) {
		direction = dir;
	}
}
