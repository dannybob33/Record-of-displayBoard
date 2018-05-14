package DisplayBoardEmulation.snake;

public class Location implements Comparable<Location>
{
	public int row;
	public int col;
	
	public Location (int r, int c)
	{
		row = r;
		col = c;
	}

	public int getRow()
	{
		return row;
	}

	public void setRow(int row)
	{
		this.row = row;
	}

	public int getCol()
	{
		return col;
	}

	public void setCol(int col)
	{
		this.col = col;
	}

	@Override
	public int compareTo(Location other)
	{
		if (row == other.row && col == other.col)
		{
			return 0;
		}
		else
		{
			return -1;
		}
	}
	
}
