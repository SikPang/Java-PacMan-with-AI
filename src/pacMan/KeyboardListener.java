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
		//if (TermProject.game == null)	// ���� �ΰ��� ȭ���� �ƴ� �� �ش� Ű���� �̺�Ʈ�� ����ȭ ��Ŵ
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
				SP_PacMan.map.direction = 1;	// �ش� ������ ������ �������� �� , �̸� ����صα�
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
