package qss.nodoubt.game.object.ingame;

import org.joml.Vector2f;
import org.joml.Vector2i;

public class GameBoard {
	private static final Vector2i[] s_RoadPos = new Vector2i[]{
		new Vector2i(5, 5), new Vector2i(4, 5), new Vector2i(3, 5),
		new Vector2i(2, 5), new Vector2i(1, 5), new Vector2i(0, 5),
		new Vector2i(0, 4), new Vector2i(0, 3), new Vector2i(0, 2),
		new Vector2i(0, 1), new Vector2i(0, 0), new Vector2i(1, 0),
		new Vector2i(2, 0), new Vector2i(3, 0), new Vector2i(4, 0),
		new Vector2i(5, 0), new Vector2i(5, 1), new Vector2i(5, 2),
		new Vector2i(5, 3), new Vector2i(5, 4), new Vector2i(4, 4),
		new Vector2i(3, 4), new Vector2i(2, 4), new Vector2i(1, 4),
		new Vector2i(1, 3), new Vector2i(1, 2), new Vector2i(1, 1),
		new Vector2i(2, 1), new Vector2i(3, 1), new Vector2i(4, 1),
		new Vector2i(4, 2), new Vector2i(4, 3), new Vector2i(3, 3),
		new Vector2i(2, 3), new Vector2i(2, 2), new Vector2i(3, 2)
	};
	
	private class Cell {
		int bikeCount = 0;
		Bike bikes[];
		int recentCheckPoint;
		int currentPos;
	}
	
	private int m_BikePoses[];
	
	private Cell m_Cells[][];
	
	public GameBoard(int playerNum, Bike bikes[]) {
		m_BikePoses = new int[playerNum];
		m_Cells = new Cell[6][];
		for(int i = 0; i < 6; i++) {
			m_Cells[i] = new Cell[6];
			for(int j = 0; j < 6; j++) {
				m_Cells[i][j] = new Cell();
				m_Cells[i][j].bikes = new Bike[playerNum];
			}
		}
		
		for(int i = 0; i < s_RoadPos.length; i++) {
			Cell c = m_Cells[s_RoadPos[i].x][s_RoadPos[i].y];
			c.currentPos = i;
			c.recentCheckPoint = 0;
			if(i >= 11) c.recentCheckPoint = 11;
			if(i >= 21) c.recentCheckPoint = 21;
		}
		
		for(int i = 0; i < playerNum; i++) {
			setBike(bikes[i], i, 0);
		}
	}
	
	private void setBike(Bike bike, int n, int pos) {
		Cell c = m_Cells[s_RoadPos[pos].x][s_RoadPos[pos].y];
		if(c.bikes[n] == null) {
			c.bikeCount += 1;
			c.bikes[n] = bike;
		}
		
		m_BikePoses[n] = pos;
		
		int a1 = 0;
		int a2 = 0;
		switch(c.bikeCount)
		{
		case 1:
		case 2:
			a1 = 0; break;
		case 3:
		case 4:
			a1 = 15; break;
		case 5:
			a1 = 30; break;
		case 6:
			a1 = 45; break;
		}
		
		for(int i = 0; i < 6; i++)
		{
			if(c.bikes[i] != null) {
				c.bikes[i].setPosition(s_RoadPos[pos]);
				c.bikes[i].move(new Vector2f(0, a1 - a2 * 15));
				a2 += 1;
			}
		}
	}
	
	public void moveBike(int n, int movingDistance) {
		int curPos = m_BikePoses[n];
		Cell c = m_Cells[s_RoadPos[curPos].x][s_RoadPos[curPos].y];
		Bike b = c.bikes[n];
		c.bikes[n] = null;
		c.bikeCount -= 1;
		setBike(b, n, curPos + movingDistance);
		
		for(int i = 0; i < 6; i++) {
			if(c.bikes[i] != null) {
				setBike(c.bikes[i], i, curPos);
				break;
			}
		}
	}
}
