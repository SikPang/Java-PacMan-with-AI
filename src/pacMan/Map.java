package pacMan;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import javax.swing.ImageIcon;

public class Map extends Thread {
	public Image empty = new ImageIcon(Main.class.getResource("../images/empty.png")).getImage();
	public Image wall = new ImageIcon(Main.class.getResource("../images/wall.png")).getImage();
	public Image feed = new ImageIcon(Main.class.getResource("../images/feed.png")).getImage();
	public Image superFeed = new ImageIcon(Main.class.getResource("../images/superFeed.png")).getImage();
	public Image ghost1 = new ImageIcon(Main.class.getResource("../images/ghost1.png")).getImage();
	public Image ghost2 = new ImageIcon(Main.class.getResource("../images/ghost2.png")).getImage();
	public Image ghost3 = new ImageIcon(Main.class.getResource("../images/ghost3.png")).getImage();
	public Image ghost4 = new ImageIcon(Main.class.getResource("../images/ghost4.png")).getImage();
	public Image player = new ImageIcon(Main.class.getResource("../images/playerL.png")).getImage();
	public Image teleporter = new ImageIcon(Main.class.getResource("../images/empty.png")).getImage();
	
	public short playerX = 23; //23
	public short playerY = 14; //14
	public short recent = 0;
	public short gRecent[] = {0,0,0,0};
	public static short score = 0;
	public static short life = 3;
	public boolean isSuperMode[] = {false, false, false, false};
	public boolean isNormalMode[] = {true, true, true, true};
	public boolean isFinished = false;
	public short finishCnt = 0;
	public boolean isRight = false;
	public boolean isLeft = false;
	public boolean isUp = false;
	public boolean isDown = false;
	public short direction = 0;	// 한번 누른 방향을 기억하여 자동으로 벽을 만날 때 까지 가게 도와주는 함수
	public short cnt = 0;	// 유령이 플레이어가 시작하기도 전에 움직이는 것을 방지, 플레이어가 움직이면 cnt가 올라가고 cnt가 0보다 클 시 유령 움직임에 사용되는 변수
	public short superCnt = 0; // 슈퍼모드의 끝
	public short level = 1; // 14, 12
	public short ghostX[] = {14, 14, 14, 14};
	public short ghostY[] = {12, 13 ,14, 15};
	Ghost ghost = new Ghost(this);
	
	
	public static String showScore = "00000";
	public static String showLife = "3";

	public static short[][] map = { // 28x31 (x=0~27, y=0~30)  *** 현재 x,y축 반전 *** map[31][28]  
			// 플레이어 : 23,14
			// 0=빈 공간, 1=벽, 2=먹이, 3=슈퍼먹이, 4=플레이어, 5,6,7,8=유령, 9,10=텔레포트				 //27
			{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
			{1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1 },
			{1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1,},
			{1, 3, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 3, 1,},
			{1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1,},
			{1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1 },
			{1, 2, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 2, 1,},
			{1, 2, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 2, 1,},
			{1, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 1 },
			{1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1 },
			{0, 0, 0, 0, 0, 1, 2, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 2, 1, 0, 0, 0, 0, 0 },
			{0, 0, 0, 0, 0, 1, 2, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 2, 1, 0, 0, 0, 0, 0 },
			{0, 0, 0, 0, 0, 1, 2, 1, 1, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 2, 1, 0, 0, 0, 0, 0 },
			{1, 1, 1, 1, 1, 1, 2, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 2, 1, 1, 1, 1, 1, 1 },
			{9, 0, 0, 0, 0, 0, 2, 0, 0, 0, 1, 0, 5, 6, 7, 8, 0, 1, 0, 0, 0, 2, 0, 0, 0, 0, 0, 10}, //14
			{1, 1, 1, 1, 1, 1, 2, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 2, 1, 1, 1, 1, 1, 1 },
			{0, 0, 0, 0, 0, 1, 2, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 2, 1, 0, 0, 0, 0, 0 },
			{0, 0, 0, 0, 0, 1, 2, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 2, 1, 0, 0, 0, 0, 0 },
			{0, 0, 0, 0, 0, 1, 2, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 2, 1, 0, 0, 0, 0, 0 },
			{1, 1, 1, 1, 1, 1, 2, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 2, 1, 1, 1, 1, 1, 1 },
			{1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1 },
			{1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1 },
			{1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1 },
			{1, 3, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 4, 2, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 3, 1 },
			{1, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 1,},
			{1, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 1,},
			{1, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 1 },
			{1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1 },
			{1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1 },
			{1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1 },
			{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 } //30
	};
	
	public static short[][] bMap = { // 28x31 (x=0~27, y=0~30)  *** 현재 x,y축 반전 *** map[31][28]  
			// 플레이어 : 23,14
			// 0=빈 공간, 1=벽, 2=먹이, 3=슈퍼먹이, 4=플레이어, 5,6,7,8=유령, 9,10=텔레포트				 //27
			{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
			{1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1 },
			{1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1,},
			{1, 3, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 3, 1,},
			{1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1,},
			{1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1 },
			{1, 2, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 2, 1,},
			{1, 2, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 2, 1,},
			{1, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 1 },
			{1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1 },
			{0, 0, 0, 0, 0, 1, 2, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 2, 1, 0, 0, 0, 0, 0 },
			{0, 0, 0, 0, 0, 1, 2, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 2, 1, 0, 0, 0, 0, 0 },
			{0, 0, 0, 0, 0, 1, 2, 1, 1, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 2, 1, 0, 0, 0, 0, 0 },
			{1, 1, 1, 1, 1, 1, 2, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 2, 1, 1, 1, 1, 1, 1 },
			{9, 0, 0, 0, 0, 0, 2, 0, 0, 0, 1, 0, 5, 6, 7, 8, 0, 1, 0, 0, 0, 2, 0, 0, 0, 0, 0, 10}, //14
			{1, 1, 1, 1, 1, 1, 2, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 2, 1, 1, 1, 1, 1, 1 },
			{0, 0, 0, 0, 0, 1, 2, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 2, 1, 0, 0, 0, 0, 0 },
			{0, 0, 0, 0, 0, 1, 2, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 2, 1, 0, 0, 0, 0, 0 },
			{0, 0, 0, 0, 0, 1, 2, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 2, 1, 0, 0, 0, 0, 0 },
			{1, 1, 1, 1, 1, 1, 2, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 2, 1, 1, 1, 1, 1, 1 },
			{1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1 },
			{1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1 },
			{1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1 },
			{1, 3, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 4, 2, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 3, 1 },
			{1, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 1,},
			{1, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 1,},
			{1, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 1 },
			{1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1 },
			{1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1 },
			{1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1 },
			{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 } //30
	};
	
	public static short[][] map1 = { // 28x31 (x=0~27, y=0~30)  *** 현재 x,y축 반전 *** map[31][28]
			// 플레이어 좌표 : 19, 14
			// 0=빈 공간, 1=벽, 2=먹이, 3=슈퍼먹이, 4=플레이어, 5,6,7,8=유령, 9,10=텔레포트				 //27
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0 },
			{0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0 },
			{0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0 },
			{0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0 },
			{0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0 },
			{0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0 },
			{0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0 },
			{0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0 },
			{0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0 },
			{0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0 },
			{0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0 },
			{0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 5, 6, 7, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0 },
			{0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0 },
			{0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0 },
			{0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0 },
			{0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0 },
			{0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0 },
			{0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0 },
			{0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0 },
			{0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0 },
			{0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0 },
			{0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0 },
			{0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0 },
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }
	};//30

	public static short[][] map0 = { // 28x31 (x=0~27, y=0~30)  *** 현재 x,y축 반전 *** map[31][28]
			// 플레이어 좌표 : 19, 14
			// 0=빈 공간, 1=벽, 2=먹이, 3=슈퍼먹이, 4=플레이어, 5,6,7,8=유령, 9,10=텔레포트				 //27
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0 },
			{0, 0, 0, 0, 0, 0, 1, 3, 2, 2, 1, 1, 2, 2, 2, 2, 1, 1, 2, 2, 3, 1, 0, 0, 0, 0, 0, 0 },
			{0, 0, 0, 0, 0, 0, 1, 2, 1, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 1, 2, 1, 0, 0, 0, 0, 0, 0 },
			{0, 0, 0, 0, 0, 0, 1, 2, 1, 2, 1, 1, 2, 2, 2, 2, 1, 1, 2, 1, 2, 1, 0, 0, 0, 0, 0, 0 },
			{0, 0, 0, 0, 0, 0, 1, 2, 1, 2, 1, 1, 0, 1, 1, 0, 1, 1, 2, 1, 2, 1, 0, 0, 0, 0, 0, 0 },
			{0, 0, 0, 0, 0, 0, 1, 2, 1, 2, 1, 0, 0, 0, 0, 0, 0, 1, 2, 1, 2, 1, 0, 0, 0, 0, 0, 0 },
			{0, 0, 0, 0, 0, 0, 1, 2, 2, 2, 1, 0, 1, 0, 0, 1, 0, 1, 2, 2, 2, 1, 0, 0, 0, 0, 0, 0 },
			{0, 0, 0, 0, 0, 0, 1, 1, 1, 2, 0, 0, 1, 5, 7, 1, 0, 0, 2, 1, 1, 1, 0, 0, 0, 0, 0, 0 },
			{0, 0, 0, 0, 0, 0, 1, 2, 2, 2, 1, 0, 1, 1, 1, 1, 0, 1, 2, 2, 2, 1, 0, 0, 0, 0, 0, 0 },
			{0, 0, 0, 0, 0, 0, 1, 2, 1, 2, 1, 0, 0, 0, 0, 0, 0, 1, 2, 1, 2, 1, 0, 0, 0, 0, 0, 0 },
			{0, 0, 0, 0, 0, 0, 1, 2, 1, 2, 1, 1, 0, 1, 1, 0, 1, 1, 2, 1, 2, 1, 0, 0, 0, 0, 0, 0 },
			{0, 0, 0, 0, 0, 0, 1, 2, 1, 2, 1, 1, 2, 2, 4, 2, 1, 1, 2, 1, 2, 1, 0, 0, 0, 0, 0, 0 },
			{0, 0, 0, 0, 0, 0, 1, 2, 1, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 1, 2, 1, 0, 0, 0, 0, 0, 0 },
			{0, 0, 0, 0, 0, 0, 1, 3, 2, 2, 1, 1, 2, 2, 2, 2, 1, 1, 2, 2, 3, 1, 0, 0, 0, 0, 0, 0 },
			{0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0 },
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }
	};//30
	
	public Map() {		// 맵 초기화
		for (int i=0; i<31; i++) {
			for (int j=0; j<28; j++) {
				map[i][j] = bMap[i][j];
			}
		}
		recent = 0;
		score = 0;
		life = 3;
		isFinished = false;
		finishCnt = 0;
		isRight = false;
		isLeft = false;
		isUp = false;
		isDown = false;
		direction = 0;
		cnt = 0;
		superCnt = 0;
		for(int i=0; i<4; i++) {
			gRecent[i] = 0;
			isSuperMode[i] = false;
			isNormalMode[i] = true;
		}
	}
	
	@Override
	public void run(){		// 플레이어와 유령을 동시에 돌리게 하기 위한 쓰레드 이용
		while(true) {		// 무한 루프, 키보드를 한 번 누르면 한 칸만 움직이는게 아닌, 자동으로 계속 움직이게 한다
			if(isSuperMode[0] || isSuperMode[1] || isSuperMode[2] || isSuperMode[3] )
				turnNormal();
			if(isRight) {
				movePlayerR();
			}
			else if(isLeft) {
				movePlayerL();
			}
			else if(isUp) {
				movePlayerU();
			}
			else if(isDown) {
				movePlayerD();
			}
			try {
				Thread.sleep(150);
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
			searchDirection();
			if(cnt>0) ghost.alphaGhost();
			if(isFinished) break;
		}
	}
	public void close(){	// 현재 곡 중지 메소드
		this.interrupt();	// 해당 쓰레드 중지상태로 만들기
	}
	
	public int getMap(int x, int y) {
		try{
			return map[x][y];
		}catch(Exception e){
			e.printStackTrace();
		}
		return map[x][y];
	}
	
	public void setPlayer(int x, int y) {
		switch(map[x][y]) {
		case 0: 
			map[x][y]=4;
			recent=0;
			break;
		case 1:
			break;
		case 2:
			map[x][y]=4;
			score += 100;
			update();
			recent=0;
			checkFinished();
			break;
		case 3:
			map[x][y]=4;
			score += 500;
			for(int i=0; i<4; i++)
				superMode(i);
			superCnt=0;
			update();
			recent=0;
			checkFinished();
			break;
		case 5:
			if(isSuperMode[0]) {
				//유령 잡아먹기
				ghost.removeGhost(0);
				//ghost.resetGhost(0);
				score += 1000;
				map[x][y]=4;
				update();
			}
			else if (isNormalMode[0]) {
				life -= 1;
				resetPlayer();
				update();
				// 플레이어, 유령 위치 초기화
				map[x][y]=5;
				if(life <= 0) {
					//게임오버
					close();
					isFinished=true;
				}
			}
			break;
		case 6:
			if(isSuperMode[1]) {
				//유령 잡아먹기
				ghost.removeGhost(1);
				//ghost.resetGhost(1);
				score += 1000;
				map[x][y]=4;
				update();
			}
			else if (isNormalMode[1]) {
				life -= 1;
				resetPlayer();
				update();
				// 플레이어, 유령 위치 초기화
				map[x][y]=6;
				if(life <= 0) {
					//게임오버
					close();
				}
			}
			break;
		case 7:
			if(isSuperMode[2]) {
				//유령 잡아먹기
				ghost.removeGhost(2);
				//ghost.resetGhost(2);
				score += 1000;
				map[x][y]=4;
				update();
			}
			else if (isNormalMode[2]) {
				life -= 1;
				resetPlayer();
				update();
				// 플레이어, 유령 위치 초기화
				map[x][y]=7;
				if(life <= 0) {
					//게임오버
					close();
				}
			}
			break;
		case 8:
			if(isSuperMode[3]) {
				//유령 잡아먹기
				ghost.removeGhost(3);
				//ghost.resetGhost(3);
				score += 1000;
				map[x][y]=4;
				update();
			}
			else if (isNormalMode[3]) {
				life -= 1;
				resetPlayer();
				update();
				// 플레이어, 유령 위치 초기화
				map[x][y]=8;
				if(life <= 0) {
					//게임오버
					close();
				}
			}
			break;
		case 9: // (0,14)
			map[14][27]=4;
			playerX=14;
			playerY=27;
			recent=10;
			break;
		case 10:// (27,0)
			map[14][0]=4;
			playerX=14;
			playerY=0;
			recent=9;
			break;
		}
	}
	
	public void movePlayerU() {
		player = new ImageIcon(Main.class.getResource("../images/playerU.png")).getImage();
		if(playerX > 0 && map[playerX-1][playerY] != 1) {
			map[playerX][playerY]=recent;	// 플레이어가 지나왔던 곳을 원래의 블록으로 채워넣음
			playerX-=1;
			setPlayer(playerX,playerY);
			//System.out.println(playerX + " , " + playerY);
		}
	}
	
	public void movePlayerD() { 
		player = new ImageIcon(Main.class.getResource("../images/playerD.png")).getImage();
		if(playerX < 30 && map[playerX+1][playerY] != 1) {
			map[playerX][playerY]=recent;
			playerX+=1;
			setPlayer(playerX,playerY);
			//System.out.println(playerX + " , " + playerY);
		}
	}
	
	
	public void movePlayerL() {
		player = new ImageIcon(Main.class.getResource("../images/playerL.png")).getImage();
		if(playerY > 0 && map[playerX][playerY-1] != 1) {
			map[playerX][playerY]=recent;
			playerY-=1;
			setPlayer(playerX,playerY);
			//System.out.println(playerX + " , " + playerY);
		}
	}
	
	public void movePlayerR() {
		player = new ImageIcon(Main.class.getResource("../images/playerR.png")).getImage();
		if(playerY < 27 && map[playerX][playerY+1] != 1) {
			map[playerX][playerY]=recent;
			playerY+=1;
			setPlayer(playerX,playerY);
			//System.out.println(playerX + " , " + playerY);
		}
	}
	
	public void searchDirection() {		// 갈림길 이전에 다른 방향을 누르면 기억해놨다가 갈림길 생길 시에 자동으로 이동하게 하여 더욱 편한 조작을 가능하게 하는 함수
		if (direction == 1 && map[playerX][playerY-1] != 1) {
			movePlayerL();
			direction = 0;
			isLeft=true;
			isRight=false;
			isUp=false;
			isDown=false;
			try {
				Thread.sleep(150);
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
			//System.out.println("left");
		}
		else if (direction == 2 && map[playerX][playerY+1] != 1) {
			movePlayerR();
			direction = 0;
			isLeft=false;
			isRight=true;
			isUp=false;
			isDown=false;
			try {
				Thread.sleep(150);
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
			//System.out.println("right");
		}
		else if (direction == 3 && map[playerX-1][playerY] != 1) {
			movePlayerU();
			direction = 0;
			isLeft=false;
			isRight=false;
			isUp=true;
			isDown=false;
			try {
				Thread.sleep(150);
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
			//System.out.println("up");
		}
		else if (direction == 4 && map[playerX+1][playerY] != 1) {
			movePlayerD();
			direction = 0;
			isLeft=false;
			isRight=false;
			isUp=false;
			isDown=true;
			try {
				Thread.sleep(150);
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
			//System.out.println("down");
		}
	}
	
	public void getPlayer() {
		for (short x=0; x<31; x++) {
			for (short y=0; y<28; y++) {
				if (map[x][y] == 4) {
					playerX = x;
					playerY = y;
				}
			}
		}
	}
	
	public void update() {		// 점수와 생명 갱신 함수
		if(score==0)
			showScore = ("00000");
		else if(score<100)		// 점수를 6자리로 유지하기 위함
			showScore = ("000").concat(Integer.toString(score));
		else if(score>=100 && score<1000)
			showScore = ("00").concat(Integer.toString(score));
		else if (score>=1000 && score<10000)
			showScore = ("0").concat(Integer.toString(score));
		else if (score >=10000)
			showScore = Integer.toString(score);
		
		showLife = Integer.toString(life);
	}
	
	public void resetPlayer() {
		playerX=23;
		playerY=14;
		map[23][14]=4;
		recent=0;
		direction=0;
		player = new ImageIcon(Main.class.getResource("../images/playerL.png")).getImage();
		isRight=false;
		isLeft=false;
		isUp=false;
		isDown=false;
	}
	
	public void checkFinished() { // (맵 배열 : map[31][28]) , 먹이를 모두 먹었는가?
		finishCnt = 0 ;
		for (int x=0; x<31; x++) {
			for (int y=0; y<28; y++) {
				if (map[x][y] == 2 || map[x][y] == 3)
					finishCnt ++;
			}
		}
		if(finishCnt == 0) {
			System.out.println("끝");	// *** 다음 단계로 넘어가는 함수 구현 해야함 ***
			isFinished = true;
			close();
		}
	}
	
	public void superMode(int n) {			// 슈퍼먹이를 먹을 시 슈퍼모드로 바뀜
		isNormalMode[n] = false;
		isSuperMode[n] = true;
		if(ghost.isDead[0]==false)
			ghost1 = new ImageIcon(Main.class.getResource("../images/ghostS.png")).getImage();
		if(ghost.isDead[1]==false)
			ghost2 = new ImageIcon(Main.class.getResource("../images/ghostS.png")).getImage();
		if(ghost.isDead[2]==false)
			ghost3 = new ImageIcon(Main.class.getResource("../images/ghostS.png")).getImage();
		if(ghost.isDead[3]==false)
			ghost4 = new ImageIcon(Main.class.getResource("../images/ghostS.png")).getImage();
		
	}
	
	public void normalMode(int n) {			// 시간이 지난 후 노멀모드로 바뀜
		isNormalMode[n] = true;
		isSuperMode[n] = false;
		
		if(ghost.isDead[0]==false)
			ghost1 = new ImageIcon(Main.class.getResource("../images/ghost1.png")).getImage();
		if(ghost.isDead[1]==false)
			ghost2 = new ImageIcon(Main.class.getResource("../images/ghost2.png")).getImage();
		if(ghost.isDead[2]==false)
			ghost3 = new ImageIcon(Main.class.getResource("../images/ghost3.png")).getImage();
		if(ghost.isDead[3]==false)
			ghost4 = new ImageIcon(Main.class.getResource("../images/ghost4.png")).getImage();
		//안됨 2마리 먹으면 나머지 2마리 안돌아옴 ㅡㅡ
		System.out.println(isSuperMode[0] + " " +isSuperMode[1] + " " +isSuperMode[2] + " " +isSuperMode[3]);
	}
	
	public void turnNormal() {
		superCnt++;
		if(superCnt==50) {
			for(int i=0; i<4; i++)
				normalMode(i);
		}
	}
	public void screenDraw(Graphics2D g) {
		short x=30;
		short y=60;
		for(int i=0; i<31; i++) {
			for(int j=0; j<28; j++) {
				switch(getMap(i,j))
				{
				case 0:
					g.drawImage(empty, x, y, null);
					break;
				case 1:
					g.drawImage(wall, x, y, null);
					break;
				case 2:
					g.drawImage(feed, x, y, null);
					break;
				case 3:
					g.drawImage(superFeed, x, y, null);
					break;
				case 4:
					g.drawImage(player, x, y, null);
					//playerX=x;
					//playerY=y;
					break;
				case 5:
					g.drawImage(ghost1, x, y, null);
					break;
				case 6:
					g.drawImage(ghost2, x, y, null);
					break;
				case 7:
					g.drawImage(ghost3, x, y, null);
					break;
				case 8:
					g.drawImage(ghost4, x, y, null);
					break;
				case 9:
					g.drawImage(teleporter, x, y, null);
					break;
				}//switch-case
				x+=20;
			}//j
			x=30;
			y+=20;
		}//i
		x=30;
		y=60;
		
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);	
		
		g.setColor(Color.white);
		g.setFont(new Font("Elephant", Font.BOLD, 35));
		g.drawString("Score : " + showScore, 45, 720);				// 점수 문자열 그리기
		g.setFont(new Font("Elephant", Font.BOLD, 35));
		g.drawString("Lives : " + showLife, 400, 720);
	}
	
}
