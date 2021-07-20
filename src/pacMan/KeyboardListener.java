package pacMan;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyboardListener extends KeyAdapter {
	
	Ghost ghost;
	
	public KeyboardListener(Ghost ghost) {
		this.ghost = ghost;
	}
	
	@Override
	public void keyPressed(KeyEvent e){
		//if (TermProject.game == null)	// 현재 인게임 화면이 아닐 시 해당 키보드 이벤트를 무력화 시킴
		//	return;
		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			SP_PacMan.map.cnt++;
			if(SP_PacMan.map.map[SP_PacMan.map.playerX][SP_PacMan.map.playerY-1] != 1) {
			SP_PacMan.map.isRight = false;
			SP_PacMan.map.isLeft = true;
			SP_PacMan.map.isUp = false;
			SP_PacMan.map.isDown = false;
			}
			else
				SP_PacMan.map.direction = 1;	// 해당 방향이 벽으로 막혀있을 시 , 미리 기억해두기
		}
		else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			SP_PacMan.map.cnt++;
			if(SP_PacMan.map.map[SP_PacMan.map.playerX][SP_PacMan.map.playerY+1] != 1) {
			SP_PacMan.map.isRight = true;
			SP_PacMan.map.isLeft = false;
			SP_PacMan.map.isUp = false;
			SP_PacMan.map.isDown = false;
			}
			else
				SP_PacMan.map.direction = 2;
		}
		else if(e.getKeyCode() == KeyEvent.VK_UP){
			SP_PacMan.map.cnt++;
			if(SP_PacMan.map.map[SP_PacMan.map.playerX-1][SP_PacMan.map.playerY] != 1) {
			SP_PacMan.map.isRight = false;
			SP_PacMan.map.isLeft = false;
			SP_PacMan.map.isUp = true;
			SP_PacMan.map.isDown = false;
			}
			else
				SP_PacMan.map.direction = 3;
		}
		else if(e.getKeyCode() == KeyEvent.VK_DOWN){
			SP_PacMan.map.cnt++;
			if(SP_PacMan.map.map[SP_PacMan.map.playerX+1][SP_PacMan.map.playerY] != 1) {
			SP_PacMan.map.isRight = false;
			SP_PacMan.map.isLeft = false;
			SP_PacMan.map.isUp = false;
			SP_PacMan.map.isDown = true;
			}
			else
				SP_PacMan.map.direction = 4;
		}
	}
}
