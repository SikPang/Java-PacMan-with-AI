package pacMan;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/*          할    것
 *  1. AI 레벨 디자인
 *  2. 인터페이스, 텍스쳐 디자인
 *  3. 마무리 버그 잡기
 */

public class SP_PacMan extends JFrame {

	private Image screenImage; // 더블 버퍼링을 위해 전체 화면에 대한 이미지를 담는 인스턴스
	private Graphics screenGraphic;
	
	private Image mainBackground = new ImageIcon(Main.class.getResource("../images/mainBackGround.png")).getImage();
	private JLabel menuBar = new JLabel(new ImageIcon(Main.class.getResource("../images/menuBar.png")));
	private ImageIcon exitButtonImage = new ImageIcon(Main.class.getResource("../images/exitButton.png"));
	private ImageIcon exitButtonOnImage = new ImageIcon(Main.class.getResource("../images/exitButtonOn.png"));
	private ImageIcon startButtonImage = new ImageIcon(Main.class.getResource("../images/startButton.png"));
	private ImageIcon startButtonOnImage = new ImageIcon(Main.class.getResource("../images/startButtonOn.png"));
	private ImageIcon quitButtonImage = new ImageIcon(Main.class.getResource("../images/quitButton.png"));
	private ImageIcon quitButtonOnImage = new ImageIcon(Main.class.getResource("../images/quitButtonOn.png"));
	
	private JButton exitButton = new JButton(exitButtonImage);
	private JButton startButton = new JButton(startButtonImage);
	private JButton quitButton = new JButton(quitButtonImage);
	
	private int mouseX, mouseY;						// 마우스의 좌표 읽기
	public boolean isGameScreen = false;
	public boolean isMainScreen = true;
	
	//static Map map = new Map();
	static Map map;
	static Ghost ghost = new Ghost(map);
	
	public SP_PacMan(){
		setUndecorated(true);
		setTitle("PacMac");
		setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);				
		setVisible(true);	
		setBackground(new Color(0, 0, 0, 0));	// 배경 없애려면 주석처리
		setLayout(null);
		
		addKeyListener(new KeyboardListener(ghost));

		// ------------- 메뉴 바 우측 x표 버튼 -------------
				exitButton.setBounds(Main.SCREEN_WIDTH-30, 0, 30, 30);
				exitButton.setBorderPainted(false);
				exitButton.setContentAreaFilled(false);
				exitButton.setFocusPainted(false);
				exitButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) { 		// 마우스 버튼을 눌렀을때 이벤트
						System.exit(0);								// 프로그램 종료
					}
					
					@Override
					public void mouseEntered(MouseEvent e) {		 // 마우스가 해당 버튼 위에 올라왔을때 이벤트
						exitButton.setIcon(exitButtonOnImage);  // 해당 이미지로 변경
					}
					
					@Override
					public void mouseExited(MouseEvent e) { 		 // 마우스가 해당 버튼 위에 없을때 이벤트
						exitButton.setIcon(exitButtonImage);		 // 해당 이미지로 변경
					}
				});
				add(exitButton);
				
				// ----------------- 메뉴 바 -----------------
				menuBar.setBounds(0, 0, Main.SCREEN_WIDTH, 30);
				menuBar.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) { 				// 마우스를 눌렀을때 마우스의 좌표를 기록
						mouseX = e.getX();					
						mouseY = e.getY();
					}
				});
				menuBar.addMouseMotionListener(new MouseMotionAdapter() {	// 마우스 드래그 할 때 마우스 좌표로 창이 같이 이동
					@Override
					public void mouseDragged(MouseEvent e) {
						int x = e.getXOnScreen();
						int y = e.getYOnScreen();
						setLocation(x-mouseX, y-mouseY);
					}
				});
				add(menuBar);		
				
				// ----------------- 혼자서 하기 버튼 -----------------
				startButton.setBounds(200, 355, 200, 45);
				startButton.setBorderPainted(false);
				startButton.setContentAreaFilled(false);
				startButton.setFocusPainted(false);
				startButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) { 		// 마우스 버튼을 눌렀을때 이벤트
						map = new Map();
						toGameScreen();
					}
					
					@Override
					public void mouseEntered(MouseEvent e) {		 // 마우스가 해당 버튼 위에 올라왔을때 이벤트
						startButton.setIcon(startButtonOnImage);  // 해당 이미지로 변경
					}
					
					@Override
					public void mouseExited(MouseEvent e) { 		 // 마우스가 해당 버튼 위에 없을때 이벤트
						startButton.setIcon(startButtonImage);		 // 해당 이미지로 변경
					}
				});
				add(startButton);
				
				// ----------------- 종료 버튼 -----------------
				quitButton.setBounds(200, 505, 200, 45);
				quitButton.setBorderPainted(false);
				quitButton.setContentAreaFilled(false);
				quitButton.setFocusPainted(false);
				quitButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) { 		// 마우스 버튼을 눌렀을때 이벤트
						System.exit(0);								// 프로그램 종료
					}
					
					@Override
					public void mouseEntered(MouseEvent e) {		 // 마우스가 해당 버튼 위에 올라왔을때 이벤트
						quitButton.setIcon(quitButtonOnImage);  // 해당 이미지로 변경
					}
					
					@Override
					public void mouseExited(MouseEvent e) { 		 // 마우스가 해당 버튼 위에 없을때 이벤트
						quitButton.setIcon(quitButtonImage);		 // 해당 이미지로 변경
					}
				});
				add(quitButton);


	}
	
	public void paint(Graphics g) { // JFrame을 상속받은 GUI 프로그램에서 화면을 그려주는 함수
		screenImage = createImage(Main.SCREEN_WIDTH,Main.SCREEN_HEIGHT);
		// Main.SCREEN_WIDTH,Main.SCREEN_HEIGHT 만큼의 크기인 이미지를 만들어 screenImage에 넣어줌
		screenGraphic = screenImage.getGraphics();
		// screenImage를 통해 그래픽 객체를 얻어옴
		
		screenDraw((Graphics2D)screenGraphic);	
		
		g.drawImage(screenImage, 0, 0, null); 		// screenImage를 0,0 위치에 그림
	}

	public void screenDraw(Graphics2D g) {
		g.drawImage(mainBackground, 0, 0, null);	// 역동적인 이미지들을 그려줌 (일반적)		배경 없애려면 주석처리
		
		if(isGameScreen) {
			if(map.isFinished) {
				toMainScreen();
			}
			else {
				map.screenDraw(g);
			}
		}
		
		paintComponents(g); 		// 고정된 이미지들을 그려줌 (add 함수가 사용된 이미지를 그림)
		
		try{
			Thread.sleep(5);
		}catch(Exception e){
			e.printStackTrace();
		}
		this.repaint();
	}

	public void toGameScreen() {
		map.cnt=0;
		map.isFinished=false;
		isGameScreen = true;
		isMainScreen = false;
		startButton.setVisible(false);
		quitButton.setVisible(false);
		map.start();
	}
	
	public void toMainScreen() {
		isMainScreen = true;
		isGameScreen = false;
		startButton.setVisible(true);
		quitButton.setVisible(true);
		map.close();
	}
}
