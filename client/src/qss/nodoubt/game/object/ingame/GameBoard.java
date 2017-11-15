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
	
	public interface GameEndListener {
		void onGameEnd(int n);
	}
	
	public class State {
		public boolean isConflict = false;
		public int conflictPos = 0;
		public int conflictBike = 0;
		public boolean conflictBikes[] = new boolean[6];
		public State() {
			for(int i = 0; i < 6; i++) {
				conflictBikes[i] = false;
			}
		}
		public State setConflict(int pos, int bike, boolean[] bikes) {isConflict = true; conflictPos = pos; conflictBike = bike; for(int i = 0; i < 6; i++) conflictBikes[i] = bikes[i];return this;}
	}
	
	private int m_BikePoses[];
	
	private Cell m_Cells[][];
	
	private Bike m_Bikes[];
	
	private int m_MovingBikeIndex = 0;
	private int m_MovingGoal = 0;
	private int m_MovingDirection = 0;
	
	private boolean m_IsIdle = true;
	
	private State m_State;
	
	private GameEndListener m_Listener;
	
	public GameBoard(int playerNum, Bike bikes[], GameEndListener listener) {
		m_State = new State();
		m_BikePoses = new int[playerNum];
		m_Cells = new Cell[6][];
		m_Bikes = bikes;
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
			setBike(i, 0);
		}
		
		m_Listener = listener;
	}
	
	public void setBike(int n, int pos) {
		Bike bike = m_Bikes[n];
		if(bike == null) return;
		Cell c = m_Cells[s_RoadPos[pos].x][s_RoadPos[pos].y];
		if(c.bikes[n] == null) {
			c.bikeCount += 1;
			c.bikes[n] = bike;
			
			if(c.bikeCount > 1) {
				boolean bb[] = new boolean[6];
				for(int i = 0; i < 6; i++) {
					if(c.bikes[i] != null && i != n) {
						bb[i] = true;
					}else {
						bb[i] = false;
					}
				}
				m_State.setConflict(pos, n, bb);
			}else {
				m_State.isConflict = false;
			}
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
			a1 = 15; break;
		case 4:
		case 5:
			a1 = 30; break;
		case 6:
			a1 = 45; break;
		}
		
		for(int i = 0; i < c.bikes.length; i++)
		{
			if(c.bikes[i] != null) {
				c.bikes[i].setPosition(s_RoadPos[pos]);
				c.bikes[i].move(new Vector2f(0, a1 - a2 * 15));
				a2 += 1;
			}
		}
		
		if(pos == 35) m_Listener.onGameEnd(n);
	}
	
	public void moveBike(int n, int movingDistance) {
		int curPos = m_BikePoses[n];
		Cell c = m_Cells[s_RoadPos[curPos].x][s_RoadPos[curPos].y];
		Bike b = c.bikes[n];
		if(b == null) return;
		c.bikes[n] = null;
		c.bikeCount -= 1;
		m_MovingGoal = curPos + movingDistance;
		if(m_MovingGoal > 35) m_MovingGoal = 35;
		m_MovingDirection = movingDistance / movingDistance;
		b.go(s_RoadPos[curPos + movingDistance / movingDistance]);
		m_MovingBikeIndex = n;
		m_BikePoses[n] = curPos + m_MovingDirection;
		
		for(int i = 0; i < c.bikes.length; i++) {
			if(c.bikes[i] != null) {
				setBike(i, curPos);
				break;
			}
		}
		
		m_IsIdle = false;
	}
	
	private void moveEnd() {
		setBike(m_MovingBikeIndex, m_BikePoses[m_MovingBikeIndex]);
		m_IsIdle = true;
	}
	
	public void push(int n) {
		int curPos = m_BikePoses[n];
		Cell c = m_Cells[s_RoadPos[curPos].x][s_RoadPos[curPos].y];
		c.bikes[n] = null;
		c.bikeCount -= 1;
		setBike(n, c.recentCheckPoint);
		for(int i = 0; i < c.bikes.length; i++) {
			if(c.bikes[i] != null) {
				setBike(i, curPos);
				break;
			}
		}
	}
	
	public void update(float deltaTime) {
		if(m_IsIdle == false && m_Bikes[m_MovingBikeIndex].isIdle()) {
			if(m_MovingGoal == m_BikePoses[m_MovingBikeIndex]) {
				moveEnd();
			} else {
				m_Bikes[m_MovingBikeIndex].go(s_RoadPos[m_BikePoses[m_MovingBikeIndex] + m_MovingDirection]);
				m_BikePoses[m_MovingBikeIndex] += m_MovingDirection;
			}
		}
	}
	
	public State getState() {
		if(m_State.isConflict) {
			m_State.isConflict = false;
			return new State().setConflict(m_State.conflictPos, m_State.conflictBike, m_State.conflictBikes);
		}
		return m_State;
	}
	
	public boolean isIdle() {
		return m_IsIdle;
	}
}
