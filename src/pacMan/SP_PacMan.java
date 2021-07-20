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

/*          ��    ��
 *  1. AI ���� ������
 *  2. �������̽�, �ؽ��� ������
 *  3. ������ ���� ���
 */

public class SP_PacMan extends JFrame {

	private Image screenImage; // ���� ���۸��� ���� ��ü ȭ�鿡 ���� �̹����� ��� �ν��Ͻ�
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
	
	private int mouseX, mouseY;						// ���콺�� ��ǥ �б�
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
		setBackground(new Color(0, 0, 0, 0));	// ��� ���ַ��� �ּ�ó��
		setLayout(null);
		
		addKeyListener(new KeyboardListener(ghost));

		// ------------- �޴� �� ���� xǥ ��ư -------------
				exitButton.setBounds(Main.SCREEN_WIDTH-30, 0, 30, 30);
				exitButton.setBorderPainted(false);
				exitButton.setContentAreaFilled(false);
				exitButton.setFocusPainted(false);
				exitButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) { 		// ���콺 ��ư�� �������� �̺�Ʈ
						System.exit(0);								// ���α׷� ����
					}
					
					@Override
					public void mouseEntered(MouseEvent e) {		 // ���콺�� �ش� ��ư ���� �ö������ �̺�Ʈ
						exitButton.setIcon(exitButtonOnImage);  // �ش� �̹����� ����
					}
					
					@Override
					public void mouseExited(MouseEvent e) { 		 // ���콺�� �ش� ��ư ���� ������ �̺�Ʈ
						exitButton.setIcon(exitButtonImage);		 // �ش� �̹����� ����
					}
				});
				add(exitButton);
				
				// ----------------- �޴� �� -----------------
				menuBar.setBounds(0, 0, Main.SCREEN_WIDTH, 30);
				menuBar.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) { 				// ���콺�� �������� ���콺�� ��ǥ�� ���
						mouseX = e.getX();					
						mouseY = e.getY();
					}
				});
				menuBar.addMouseMotionListener(new MouseMotionAdapter() {	// ���콺 �巡�� �� �� ���콺 ��ǥ�� â�� ���� �̵�
					@Override
					public void mouseDragged(MouseEvent e) {
						int x = e.getXOnScreen();
						int y = e.getYOnScreen();
						setLocation(x-mouseX, y-mouseY);
					}
				});
				add(menuBar);		
				
				// ----------------- ȥ�ڼ� �ϱ� ��ư -----------------
				startButton.setBounds(200, 355, 200, 45);
				startButton.setBorderPainted(false);
				startButton.setContentAreaFilled(false);
				startButton.setFocusPainted(false);
				startButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) { 		// ���콺 ��ư�� �������� �̺�Ʈ
						map = new Map();
						toGameScreen();
					}
					
					@Override
					public void mouseEntered(MouseEvent e) {		 // ���콺�� �ش� ��ư ���� �ö������ �̺�Ʈ
						startButton.setIcon(startButtonOnImage);  // �ش� �̹����� ����
					}
					
					@Override
					public void mouseExited(MouseEvent e) { 		 // ���콺�� �ش� ��ư ���� ������ �̺�Ʈ
						startButton.setIcon(startButtonImage);		 // �ش� �̹����� ����
					}
				});
				add(startButton);
				
				// ----------------- ���� ��ư -----------------
				quitButton.setBounds(200, 505, 200, 45);
				quitButton.setBorderPainted(false);
				quitButton.setContentAreaFilled(false);
				quitButton.setFocusPainted(false);
				quitButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) { 		// ���콺 ��ư�� �������� �̺�Ʈ
						System.exit(0);								// ���α׷� ����
					}
					
					@Override
					public void mouseEntered(MouseEvent e) {		 // ���콺�� �ش� ��ư ���� �ö������ �̺�Ʈ
						quitButton.setIcon(quitButtonOnImage);  // �ش� �̹����� ����
					}
					
					@Override
					public void mouseExited(MouseEvent e) { 		 // ���콺�� �ش� ��ư ���� ������ �̺�Ʈ
						quitButton.setIcon(quitButtonImage);		 // �ش� �̹����� ����
					}
				});
				add(quitButton);


	}
	
	public void paint(Graphics g) { // JFrame�� ��ӹ��� GUI ���α׷����� ȭ���� �׷��ִ� �Լ�
		screenImage = createImage(Main.SCREEN_WIDTH,Main.SCREEN_HEIGHT);
		// Main.SCREEN_WIDTH,Main.SCREEN_HEIGHT ��ŭ�� ũ���� �̹����� ����� screenImage�� �־���
		screenGraphic = screenImage.getGraphics();
		// screenImage�� ���� �׷��� ��ü�� ����
		
		screenDraw((Graphics2D)screenGraphic);	
		
		g.drawImage(screenImage, 0, 0, null); 		// screenImage�� 0,0 ��ġ�� �׸�
	}

	public void screenDraw(Graphics2D g) {
		g.drawImage(mainBackground, 0, 0, null);	// �������� �̹������� �׷��� (�Ϲ���)		��� ���ַ��� �ּ�ó��
		
		if(isGameScreen) {
			if(map.isFinished) {
				toMainScreen();
			}
			else {
				map.screenDraw(g);
			}
		}
		
		paintComponents(g); 		// ������ �̹������� �׷��� (add �Լ��� ���� �̹����� �׸�)
		
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
