package pacMan;

import java.util.Random;

import javax.swing.ImageIcon;

/*
 *  �� ��
 *  1. MM �ڵ� ���� ������
 *  2. ���� AI ���� ������ȭ
 *  2. �ؽ��� ������Ʈ
 *  
 *  ?. ���� ������
 */
public class Ghost {
	Map map;
	int status[] = {0, 0, 0, 0};	// �ʹ� ���� ���� ���������� �ϱ� ���� ���� �迭
	int gDirection[] = {0, 0, 0, 0}; // ������ �̵������� �����ϰ� ���ϰ� �ϱ� ���� �迭
	boolean isDead[] = {false, false, false, false};
	private short startCnt = 0;	// ���� ī��Ʈ, �ð��� �������� ������ ���ʷ� �����̱� ������
	private short cnt[] = {1, 1, 1, 1}; // ���۸���� �� �������� ������ �ϱ� ���ؼ� Ȧ������ �����̰� �ϴµ� ���̴� ����
	private short deadCnt[] = {0, 0, 0, 0};
	public boolean isAttackMode[]= {false, false, false, false};
	private int gapX = 0;
	private int gapY = 0;
	private int forkX = 0;
	private int forkY = 0;
	private boolean fork1 = false;
	private boolean fork2 = false;
	private boolean fork3 = false;
	private boolean fork4 = false;
	private int aX[]= {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; // 21��
	private int aY[]= {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; // 21��
	private boolean isPP = false;
	private boolean isPM = false;
	private boolean isMP = false;
	private boolean isMM = false;
	private boolean isPZ = false;
	private boolean isZM = false;
	private boolean isZP = false;
	private boolean isMZ = false;
	private int aCnt = 0;
	private int forkCnt = 0;
	public static int[][][] distance = {	// Ž�� �˰��� �ʿ��� �迭 �ִ� 6x6
			{
				{10, 10, 10, 10, 10, 10},
				{10, 10, 10, 10, 10, 10},
				{10, 10, 10, 10, 10, 10},
				{10, 10, 10, 10, 10, 10},
				{10, 10, 10, 10, 10, 10},
				{10, 10, 10, 10, 10, 10}
			},
			{
				{10, 10, 10, 10, 10, 10},
				{10, 10, 10, 10, 10, 10},
				{10, 10, 10, 10, 10, 10},
				{10, 10, 10, 10, 10, 10},
				{10, 10, 10, 10, 10, 10},
				{10, 10, 10, 10, 10, 10}
			},
			{
				{10, 10, 10, 10, 10, 10},
				{10, 10, 10, 10, 10, 10},
				{10, 10, 10, 10, 10, 10},
				{10, 10, 10, 10, 10, 10},
				{10, 10, 10, 10, 10, 10},
				{10, 10, 10, 10, 10, 10}
			},
			{
				{10, 10, 10, 10, 10, 10},
				{10, 10, 10, 10, 10, 10},
				{10, 10, 10, 10, 10, 10},
				{10, 10, 10, 10, 10, 10},
				{10, 10, 10, 10, 10, 10},
				{10, 10, 10, 10, 10, 10}
			},
	};
	Random random = new Random();
	//ghostX[] = {14, 14, 14, 14};
	//ghostY[] = {12, 13 ,14, 15};
	
	public Ghost(Map map) {
		this.map = map;
	}
	
	public void alphaGhost() {	// ���İ�    ��Ʈ
		for(int i =0; i<4; i++) {		// ������ 4������ 4�� �ݺ�
			if(isDead[i]){
				if(deadCnt[i]>=30) {
					resetGhost(i);
					deadCnt[i]=0;
				}
				else {
					deadCnt[i]++;
					continue;
				}
			}
			if(map.isSuperMode[i] && cnt[i] % 2 == 0) {	// ������ �̿��Ͽ� 2�� ����� ���� ������ ��� ���۸�� ���� ������ ������ �̵��ϰԲ� ����
				cnt[i]++;
				continue;
			}
			else if(map.isSuperMode[i] && cnt[i] % 2 == 1) {
				cnt[i]++;
			}
			
			if (status[i]<5) {
				if(i==0) {								// 5�� ����
					switch(status[0]) {
					case 0:
					case 2:
					case 3:
						moveGhostU(0);
						status[0]++;
						break;
					case 1:
						moveGhostR(0);
						status[0]++;
						break;
					case 4:
						moveGhostL(0);
						status[0]++;
						gDirection[0]=3;
						break;
					}
				}
				else if(i==1 && startCnt>=30) {			// 6�� ����
					switch(status[1]) {
					case 0:
					case 1:
					case 2:
						moveGhostU(1);
						status[1]++;
						break;
					case 3:
					case 4:
						moveGhostL(1);
						status[1]++;
						gDirection[1] = 3;
						break;
					}
				}
				else if(i==2 && startCnt>=60) {				// 7�� ����
					switch(status[2]) {
					case 0:
					case 1:
					case 2:
						moveGhostU(2);
						status[2]++;
						break;
					case 3:
					case 4:
						moveGhostR(2);
						status[2]++;
						gDirection[2] = 4;
						break;
					}
				}
				else if(i==3 && startCnt>=90) {				// 8�� ����
					switch(status[3]) {
					case 0:
					case 2:
					case 3:
						moveGhostU(3);
						status[3]++;
						break;
					case 1:
						moveGhostL(3);
						status[3]++;
						break;
					case 4:
						moveGhostR(3);
						status[3]++;
						gDirection[3] = 4;
						break;
					}
				}
			}
			else {
				if(map.isSuperMode[i] == false) {	// attack check
					checkAttack(i);
				}
				//pSystem.out.println(isAttackMode[0] + " " + isAttackMode[1] + " " + isAttackMode[2] + " " + isAttackMode[3]);
				if(isAttackMode[i]==false || map.isSuperMode[i] == true){ // 4����
				
					if(gDirection[i]!=0) {
						switch(gDirection[i]) {
						case 1:
							if (map.map[map.ghostX[i]-1][map.ghostY[i]] != 1 && map.map[map.ghostX[i]-1][map.ghostY[i]] != 5
							&& map.map[map.ghostX[i]-1][map.ghostY[i]] != 6 && map.map[map.ghostX[i]-1][map.ghostY[i]] != 7
							&& map.map[map.ghostX[i]-1][map.ghostY[i]] != 8)
								moveGhostU(i);
							else {
								gDirection[i]=0;
								randMove(i);
							}
							break;
						case 2:
							if (map.map[map.ghostX[i]+1][map.ghostY[i]] != 1 && map.map[map.ghostX[i]+1][map.ghostY[i]] != 5
							&& map.map[map.ghostX[i]+1][map.ghostY[i]] != 6 && map.map[map.ghostX[i]+1][map.ghostY[i]] != 7
							&& map.map[map.ghostX[i]+1][map.ghostY[i]] != 8)
								moveGhostD(i);	
							else {
								gDirection[i]=0;
								randMove(i);
							}
							break;
						case 3:
							if (map.map[map.ghostX[i]][map.ghostY[i]-1] != 1 && map.map[map.ghostX[i]][map.ghostY[i]-1] != 5
							&& map.map[map.ghostX[i]][map.ghostY[i]-1] != 6 && map.map[map.ghostX[i]][map.ghostY[i]-1] != 7
							&& map.map[map.ghostX[i]][map.ghostY[i]-1] != 8) {
								moveGhostL(i);
							}
							else {
								gDirection[i]=0;
								randMove(i);
							}
							break;
						case 4:
							if (map.map[map.ghostX[i]][map.ghostY[i]+1] != 1 && map.map[map.ghostX[i]][map.ghostY[i]+1] != 5
							&& map.map[map.ghostX[i]][map.ghostY[i]+1] != 6 && map.map[map.ghostX[i]][map.ghostY[i]+1] != 7
							&& map.map[map.ghostX[i]][map.ghostY[i]+1] != 8)
								moveGhostR(i);
							else {
								gDirection[i]=0;
								randMove(i);
							}
							break;
						}
					}
					else {
						randMove(i);
					}
					
				}
			}
		}
		startCnt++;
	}
	
	public void randMove(int i) {
		int rand[] = {0, 0, 0, 0};
		int r = 0;
		if (map.map[map.ghostX[i]-1][map.ghostY[i]] != 1 && map.map[map.ghostX[i]-1][map.ghostY[i]] != 5
				&& map.map[map.ghostX[i]-1][map.ghostY[i]] != 6 && map.map[map.ghostX[i]-1][map.ghostY[i]] != 7
				&& map.map[map.ghostX[i]-1][map.ghostY[i]] != 8) {
			rand[0] = 1;
		}
		
		if (map.map[map.ghostX[i]+1][map.ghostY[i]] != 1 && map.map[map.ghostX[i]+1][map.ghostY[i]] != 5
				&& map.map[map.ghostX[i]+1][map.ghostY[i]] != 6 && map.map[map.ghostX[i]+1][map.ghostY[i]] != 7
				&& map.map[map.ghostX[i]+1][map.ghostY[i]] != 8) {
			rand[1] = 2;
		}
		
		if (map.map[map.ghostX[i]][map.ghostY[i]-1] != 1 && map.map[map.ghostX[i]][map.ghostY[i]-1] != 5
				&& map.map[map.ghostX[i]][map.ghostY[i]-1] != 6 && map.map[map.ghostX[i]][map.ghostY[i]-1] != 7
				&& map.map[map.ghostX[i]][map.ghostY[i]-1] != 8) {
			rand[2] = 3;
		}

		if (map.map[map.ghostX[i]][map.ghostY[i]+1] != 1 && map.map[map.ghostX[i]][map.ghostY[i]+1] != 5
				&& map.map[map.ghostX[i]][map.ghostY[i]+1] != 6 && map.map[map.ghostX[i]][map.ghostY[i]+1] != 7
				&& map.map[map.ghostX[i]][map.ghostY[i]+1] != 8) {
			rand[3] = 4;
		}
		/*
		switch(gDirection[i]) {
		case 1:
			rand[1]=0;
			System.out.println("1���ֱ�");
			break;
		case 2:
			rand[0]=0;
			System.out.println("2���ֱ�");
			break;
		case 3:
			rand[3]=0;
			System.out.println("3���ֱ�");
			break;
		case 4:
			rand[2]=0;
			System.out.println("4���ֱ�");
			break;
		}
		*/
		
		while(r==0) {
			r = random.nextInt(4)+1;
		}
		
		switch (r) {
		case 1:
			moveGhostU(i);
			gDirection[i] = 1;
			break;
		case 2:
			moveGhostD(i);
			gDirection[i] = 2;
			break;
		case 3:
			moveGhostL(i);
			gDirection[i] = 3;
			break;
		case 4:
			moveGhostR(i);
			gDirection[i] = 4;
			break;
		}
	}
	
	public void setGhost(int x, int y, int z) {
		switch(map.map[x][y]) {
		case 0: 
			map.map[x][y]=(short)(z+5);
			map.gRecent[z]=0;
			break;
		case 1:
			break;
		case 2:
			map.map[x][y]=(short)(z+5);
			map.update();
			map.gRecent[z]=2;
			break;
		case 3:
			map.map[x][y]=(short)(z+5);
			map.update();
			map.gRecent[z]=3;
			break;
		case 4:
			if(map.isSuperMode[z]) {
				//���� ��ƸԱ�
				map.gRecent[z]=0;
				removeGhost(z);
				map.map[x][y]=4;
				//resetGhost(z);
			}
			else if (map.isNormalMode[z]) {
				map.life-=1;
				map.resetPlayer();
				map.update();
				map.map[x][y]=(short)(z+5);
				// �÷��̾�, ���� ��ġ �ʱ�ȭ
				if(map.life == 0) {
					//���ӿ���
					map.isFinished=true;
					map.close();
				}
			}
			break;
		case 5:
		case 6:
		case 7:
		case 8:
			break;
		case 9: // (0,14)
			map.map[14][27]=(short)(z+5);
			map.ghostX[z]=14;
			map.ghostY[z]=27;
			map.gRecent[z]=10;
			break;
		case 10:// (27,0)
			map.map[14][0]=(short)(z+5);
			map.ghostX[z]=14;
			map.ghostY[z]=0;
			map.gRecent[z]=9;
			break;
		}
	}
	
	public void checkAttack(int n) { // ������ �ݰ� 5x5�� �÷��̾ ������ ���ø�� 
		// ���󰡴� ���� �ƴ�, �� �� ������ ������ ���� ��ġ���� �÷��̾��� ��ġ ���� ��θ� "Ž��"�Ͽ� �� ���� ���� ã��
		int gX = map.ghostX[n];	// ��Ʈ n�� x,y��ǥ (x,y) 
		int gY = map.ghostY[n];
		int pX = map.playerX;
		int pY = map.playerY;
		
		// gx �� px�� ���̰� 5���ϴ�. gx=5, px=10 ok, gx +5 = px /         gx - 5 = px / gy + 2 = py
		// ����� 5 ���ϸ� ����. 5, 4, 3, 2, 1 ... 
		for(int j=5; j>-1; j--) {
			for(int i=0; i<6; i++) {
				if ((pX-j == gX || pX+j == gX) && (pY+i == gY || pY-i == gY)) {
					isAttackMode[n] = true;
					gapX = j;
					gapY = i;
					attack(n);
					//System.out.println(n + " : attack!");
					
					if (pX-j == gX && pY-i == gY) {			//	(+,+)	
						// +,+ �������� ��Ÿ���� ����
						//System.out.println("go PP"); // PLUS PLUS (x,y��)
						if(j==0)					// ZP   (������ �÷��̾�� �������� ���ʿ� ����) 
						{
							isPP = false;		
							isPM = false;
							isMP = false;
							isMM = false;
							isPZ = false;
							isZP = true;
							isMZ = false;
							isZM = false;
						}
						else if (j!=0 && i!=0) {	// PP	(������ �÷��̾�� ���� ���� ����)
							isPP = true;		
							isPM = false;
							isMP = false;
							isMM = false;
							isPZ = false;
							isZP = false;
							isMZ = false;
							isZM = false;
						}
					}
					if (pX-j == gX && pY+i == gY) {	//	(+,-)
						//System.out.println("go PM"); // PLUS MINUS
						if(i==0) {					//PZ	(������ �÷��̾�� �������� ���� ����)
							isPP = false;		
							isPM = false;
							isMP = false;
							isMM = false;
							isPZ = true;
							isZP = false;
							isMZ = false;
							isZM = false;
						}
						else if (j!=0 && i!=0) {	//PM	(������ �÷��̾��  ������ ���� ����)
							isPP = false;	
							isPM = true;
							isMP = false;
							isMM = false;
							isPZ = false;
							isZP = false;
							isMZ = false;
							isZM = false;
						}
						//gapY=-gapY;
					}
					if (pX+j == gX && pY-i == gY) {	//	(-,+)
						//System.out.println("go MP");
						if(i==0) {					//MZ	(������ �÷��̾�� �������� �Ʒ��� ����)
							isPP = false;		
							isPM = false;
							isMP = false;
							isMM = false;
							isPZ = false;
							isZP = false;
							isMZ = true;
							isZM = false;
						}
						else if (j!=0 && i!=0) {	//MP	(������ �÷��̾�� ���� �Ʒ��� ����)
							isPP = false;		
							isPM = false;
							isMP = true;
							isMM = false;
							isPZ = false;
							isZP = false;
							isMZ = false;
							isZM = false;
						}
						//gapX=-gapX;
					}
					if (pX+j == gX && pY+i == gY) {	//	(-,-)
						//System.out.println("go MM");
						if(j==0) {					//ZM	(������ �÷��̾�� �������� �����ʿ� ����)
							isPP = false;
							isPM = false;
							isMP = false;
							isMM = false;
							isPZ = false;
							isZP = false;
							isMZ = false;
							isZM = true;
						}
						else if (j!=0 && i!=0) {	//MM	(������ �÷��̾�� ������ �Ʒ��� ����)
							isPP = false;		
							isPM = false;
							isMP = false;
							isMM = true;
							isPZ = false;
							isZP = false;
							isMZ = false;
							isZM = false;
						}
						//gapX=-gapX;
						//gapY=-gapY;
					}
				}
				else {
					isAttackMode[n] = false;
				}
				if(isAttackMode[n] == true)
					break;
			}
			if(isAttackMode[n] == true)
				break;
		}
	}
	
	//	-5,-5	-5,-4	-5,-3	-5,-2	-5,-1	-5,0	-5,1	-5,2	-5,3	-5,4	-5,5			(����, ����)
	
	//	-4,-5	-4,-4	-4,-3	-4,-2	-4,-1	-4,0	-4,1	-4,2	-4,3	-4,4	-4,5
	
	//	-3,-5	-3,-4	-3,-3	-3,-2	-3,-1	-3,0	-3,1	-3,2	-3,3	-3,4	-3,5
	
	//	-2,-5	-2,-4	-2,-3	-2,-2	-2,-1	-2,0	-2,1	-2,2	-2,3	-2,4	-2,5
	
	//	-1,-5	-1,-4	-1,-3	-1,-2	-1,-1	-1,0	-1,1	-1,2	-1,3	-1,4	-1,5
	
	//	 0,-5	 0,-4	 0,-3	 0,-2	 0,-1	 0,0	 0,1	 0,2	 0,3	 0,4	 0,5			// 0,0 = gX,gY
	
	//	 1,-5	 1,-4	 1,-3	 1,-2	 1,-1	 1,0	 1,1	 1,2	 1,3	 1,4	 1,5
	
	//	 2,-5	 2,-4	 2,-3	 2,-2	 2,-1	 2,0	 2,1	 2,2	 2,3	 2,4	 2,5
	
	//	 3,-5	 3,-4	 3,-3	 3,-2	 3,-1	 3,0	 3,1	 3,2	 3,3	 3,4	 3,5
	
	//	 4,-5	 4,-4	 4,-3	 4,-2	 4,-1	 4,0	 4,1	 4,2	 4,3	 4,4	 4,5
	
	//	 5,-5	 5,-4	 5,-3	 5,-2	 5,-1	 5,0	 5,1	 5,2	 5,3	 5,4	 5,5
	
	// ������ ���� ��ġ ���� ���� �ش� �� ������ �Ÿ� = a, �ش� �濡�� ���� �÷��̾��� ���� ��ġ ������ ���� �Ÿ� = b , a+b�� ���� ���� ��� �̵� (a*)
	public void attack(int n) {	// �÷��̾ ���󰡱�, ������ ��� Ž��
		// 1. �ش� �������� ������ 3���� ����� �̵� ������ ������� Ȯ��
		// 2. �̵� ������ ����̸� ������ 3���� ��Ͽ� �Ÿ� �Է�
		int gX = map.ghostX[n];	// ��Ʈ n�� x,y��ǥ (x,y) 
		int gY = map.ghostY[n];
		int pX = map.playerX;
		int pY = map.playerY;
		int gap = gapX + gapY - 1;
		
		for(int i=0; i<6; i++) {
			for(int j=0; j<6; j++) {
				distance[n][i][j]=10;		// distance �ʱ�ȭ
			}
		}
		//�ִ� 5x5�� Ÿ���� ��ġȭ ���Ѿ���
		//+���� -���� 0���� Ȯ�� �� gap�� ���Ͽ� ���
		//gx+gap=px
		
		//System.out.println(n + " : ok!!!!!!!!!!!!!!!");
		//System.out.println(isPP + " " + isPM + " " + isMP + " " + isMM);
		//System.out.println(isAttackMode[0] + " " + isAttackMode[1] + " " + isAttackMode[2] + " " + isAttackMode[3]);
		
		// ������������ �Ÿ��� �������!!!!!!!
		// ��ֹ� ��� ���� ���� �Ŀ� ���߿� ��ֹ� if�� ���
		// distance[][]
		// ��ǥ : pX,pY
		// ����ġ : gX,gY
		// px,py-2		px,py-1			px,py  
		// px-1,py-2	px-1,py-1		px-1,py
		// px-2,py-2	px-2,py-1		px-2,py
		// gx,gy		px-3,py-1		px-3,py		gx=px-3, gy=py-2
		// gap�� �ʿ� ���µ� ��� ����(1/4)������ ���� ������ ������ ���ϰ�, ��� �����ϴٰ� gx�� gy�� �������� �׸� ����
		// gap�� for�� �������� �ֱ� o
		//if(isPP || isPZ || isZP) {
			for(int x=0; x<6; x++) {	// distance[] ���� �Լ�
				for(int y=0; y<6; y++) {	// ������ : (gapX, gapY)
					// ** �� �ڵ�� �밢������ ��� ���� ���� ������ px(������)�� x��ǥ�� y��ǥ���� ũ�� return ���Ѿ���  ** (for���� ������ gapX,gapY�� �ؼ� �ذ�)
					if(gX+x>=31 || gY+y>=28) {
						continue;
					}
					
					switch(x+y) {				// x+y�� ���������� �ش� ���������� �Ÿ��� ����
					case 0: // gapX=2, gapY=3 �� ��, gap=4
						distance[n][x][y] = gap+1;
						break;
					case 1: 
						if(map.map[gX+x][gY+y]!=1 && map.map[gX+x][gY+y]!=5 && map.map[gX+x][gY+y]!=6 && map.map[gX+x][gY+y]!=7 && map.map[gX+x][gY+y]!=8) {
							if(map.map[gX+x][gY+y] == 4)
								distance[n][x][y] = 0;
							else
								distance[n][x][y] = gap;	// gap�� �������� ������ �Ÿ��� ���� (gapX+gapY-1)
						}
						else {distance[n][x][y] = 10;}
						break;
					case 2:
						if(map.map[gX+x][gY+y]!=1 && map.map[gX+x][gY+y]!=5 && map.map[gX+x][gY+y]!=6 && map.map[gX+x][gY+y]!=7 && map.map[gX+x][gY+y]!=8) {
							if(map.map[gX+x][gY+y] == 4)
								distance[n][x][y] = 0;
							else
								distance[n][x][y] = gap-1;	
						}
						else {distance[n][x][y] = 10;}
						break;
					case 3:
						if(map.map[gX+x][gY+y]!=1 && map.map[gX+x][gY+y]!=5 && map.map[gX+x][gY+y]!=6 && map.map[gX+x][gY+y]!=7 && map.map[gX+x][gY+y]!=8) {
							if(map.map[gX+x][gY+y] == 4)
								distance[n][x][y] = 0;
							else
								distance[n][x][y] = gap-2;	
						}
						else {distance[n][x][y] = 10;}
						break;
					case 4:
						if(map.map[gX+x][gY+y]!=1 && map.map[gX+x][gY+y]!=5 && map.map[gX+x][gY+y]!=6 && map.map[gX+x][gY+y]!=7 && map.map[gX+x][gY+y]!=8) {
							if(map.map[gX+x][gY+y] == 4)
								distance[n][x][y] = 0;
							else
								distance[n][x][y] = gap-3;	
						}
						else {distance[n][x][y] = 10;}
						break;
					case 5:
						if(map.map[gX+x][gY+y]!=1 && map.map[gX+x][gY+y]!=5 && map.map[gX+x][gY+y]!=6 && map.map[gX+x][gY+y]!=7 && map.map[gX+x][gY+y]!=8) {
							if(map.map[gX+x][gY+y] == 4)
								distance[n][x][y] = 0;
							else
								distance[n][x][y] = gap-4;	
						}
						else {distance[n][x][y] = 10;}
						break;
					case 6:
						if(map.map[gX+x][gY+y]!=1 && map.map[gX+x][gY+y]!=5 && map.map[gX+x][gY+y]!=6 && map.map[gX+x][gY+y]!=7 && map.map[gX+x][gY+y]!=8) {
							if(map.map[gX+x][gY+y] == 4)
								distance[n][x][y] = 0;
							else
								distance[n][x][y] = gap-5;	
						}
						else {distance[n][x][y] = 10;}
						break;
					case 7:
						if(map.map[gX+x][gY+y]!=1 && map.map[gX+x][gY+y]!=5 && map.map[gX+x][gY+y]!=6 && map.map[gX+x][gY+y]!=7 && map.map[gX+x][gY+y]!=8) {
							if(map.map[gX+x][gY+y] == 4)
								distance[n][x][y] = 0;
							else
								distance[n][x][y] = gap-6;	
						}
						else {distance[n][x][y] = 10;}
						break;
					case 8:
						if(map.map[gX+x][gY+y]!=1 && map.map[gX+x][gY+y]!=5 && map.map[gX+x][gY+y]!=6 && map.map[gX+x][gY+y]!=7 && map.map[gX+x][gY+y]!=8) {
							if(map.map[gX+x][gY+y] == 4)
								distance[n][x][y] = 0;
							else
								distance[n][x][y] = gap-7;	
						}
						else {distance[n][x][y] = 10;}
						break;
					case 9:
						if(map.map[gX+x][gY+y]!=1 && map.map[gX+x][gY+y]!=5 && map.map[gX+x][gY+y]!=6 && map.map[gX+x][gY+y]!=7 && map.map[gX+x][gY+y]!=8) {
							if(map.map[gX+x][gY+y] == 4)
								distance[n][x][y] = 0;
							else
								distance[n][x][y] = gap-8;	
						}
						else {distance[n][x][y] = 10;}
						break;
					case 10:
						if(map.map[gX+x][gY+y]!=1 && map.map[gX+x][gY+y]!=5 && map.map[gX+x][gY+y]!=6 && map.map[gX+x][gY+y]!=7 && map.map[gX+x][gY+y]!=8) {
							if(map.map[gX+x][gY+y] == 4)
								distance[n][x][y] = 0;
							else
								distance[n][x][y] = gap-9;	
						}
						else {distance[n][x][y] = 10;}
						break;
					}
				}
			}// distance[][]�� �Ÿ��� ��Ÿ���� �� �Է� �Ϸ�
			
			// ���� ������ ��� ���ư��� �ϱ� ������ ��ü ���� distance[][] ũ�⸦ Ȯ���ؾ��ϳ�? ��Ȳ�� �ʹ� �������̶� ���� �ʿ� ������.
			// near[][]�� �� ���� ������.
			// distance[][] ������ ����� �� setghost ������ �ű�⸸ �ϸ� �ɰŰ��Ƽ� ����,����� ���������.
			
			//distance[][]�� �� ���� ������ ���� �̵�
			//distance[][] �����δ� ������ ������ �ƴϸ� �������� �ۿ� �� ��, ������  map �󿡼� distance�� 4�������� ���ư��� ������ distance�� ������� �ٸ� ����
			for(int i=0; i<6; i++) {
				for(int j=0; j<6; j++) {
					System.out.print(distance[n][i][j] + " ");
				}
				System.out.println();
			}
			boolean goToDown = false; 
			boolean goToUp = false; 
			// ��θ� ������
	
			int x=0;
			int y=0;
			aCnt = 0;
			for(int i=0; i<21; i++) {
				aX[i]=0;
				aY[i]=0;
			}
			
			if(isPP || isPZ || isZP) {
				while(true) {
					if(x==gapX && y==gapY) break;
					if(goToDown) {
						x=forkX;
						y=forkY;
						aCnt=forkCnt;
					}
					fork1=false;	// ������ üũ ����
					fork2=false;
					fork3=false;
					fork4=false;
					///*
					for(int i=0; i<6; i++) {
						for(int j=0; j<6; j++) {
							System.out.print(distance[n][i][j] + " ");
						}
						System.out.println();
					}
					//*/
					if(y!=5) {
						if(distance[n][x][y+1]!=10 && distance[n][x][y] > distance[n][x][y+1] && goToDown==false) {		// ���� �˻�
							aX[aCnt]=x;
							aY[aCnt]=y+1;
							fork1=true;
							System.out.println("Right");
						}
					}
					if(x!=5) {
						if(distance[n][x+1][y]!=10 && distance[n][x][y] > distance[n][x+1][y]) {		// �ϴ� �˻�
							if(fork1==false && goToUp==false) {	// ������ �˻縸�� ���� ����, ���� �˻翡�� �ش���� �ʾ��� ��쿡�� ��ο� ����
								aX[aCnt]=x+1;
								aY[aCnt]=y;
								System.out.println("Down");
								if(goToDown) goToDown=false;
								goToUp = true;
							}
							fork2=true;
						}
					}
					if(x!=0) {
						if(distance[n][x-1][y]!=10 && distance[n][x][y] > distance[n][x-1][y]) {	// ��� �˻�
							if(fork1==false && fork2==false) {
								aX[aCnt]=x-1;
								aY[aCnt]=y;
								System.out.println("Up");
								if(goToDown) goToDown=false;
							}
							fork3=true;
						}
					}
					if(y!=0) {
						if(distance[n][x][y-1]!=10 && distance[n][x][y] > distance[n][x][y-1]) {		// ���� �˻�
							if(fork1==false && fork2==false && fork3==false) {
								aX[aCnt]=x;
								aY[aCnt]=y-1;
								System.out.println("Left");
								if(goToDown) goToDown=false;
							}
							fork4=true;
						}
					}
					
					if((fork1==true && fork2==true) || (fork1==true && fork4==true) || (fork2==true && fork4==true)) {	// �� �� �ش�Ǹ� ���������� ���� (üũ����Ʈ)
						forkX = x;
						forkY = y;
						forkCnt = aCnt;
					}
					
					if((fork1==false && fork2==false && fork3==false && distance[n][x][y]!=0)) {	// ���ٸ� ��
						if(goToDown==false && goToUp==false) {
							goToDown = true;
							System.out.println("fork");
						}
						else {
							goToDown = false;
							break;
						}
					}
				
				//		y++;
					System.out.println(x + " , " + y + " - fork1: " + fork1 + ", fork2: " + fork2 + ", fork3: " + fork3 + ", fork4: " + fork4);
					System.out.println();
					
					if(fork1) y++;
					else if(fork2) x++;
					else if(fork3) y--;
					else if(fork4) x--;
					
					if(distance[n][x][y]==0) {
						break;
					}
					else {
						aCnt++;
					}
				}
			}
			
			else if(isMM||isMZ||isZM) {	// ������, ����, ��
				x=gapX;
				y=gapY;
				while(true) {
					if(x==0 && y==0) break;
					if(goToDown) {
						x=forkX;
						y=forkY;
						aCnt=forkCnt;
					}
					fork1=false;	// ������ üũ ����
					fork2=false;
					fork3=false;
					fork4=false;
					///*
					for(int i=0; i<6; i++) {
						for(int j=0; j<6; j++) {
							System.out.print(distance[n][i][j] + " ");
						}
						System.out.println();
					}
					//*/
					if(y!=0) {
						if(distance[n][x][y-1]!=10 && distance[n][x][y] > distance[n][x][y-1] && goToDown==false) {		// ���� �˻�
							aX[aCnt]=x;
							aY[aCnt]=y-1;
							fork1=true;
							System.out.println("Left");
						}
					}
					if(x!=0) {
						if(distance[n][x-1][y]!=10 && distance[n][x][y] > distance[n][x-1][y]) {		// ��� �˻�
							if(fork1==false && goToUp==false) {	// ������ �˻縸�� ���� ����, ���� �˻翡�� �ش���� �ʾ��� ��쿡�� ��ο� ����
								aX[aCnt]=x-1;
								aY[aCnt]=y;
								System.out.println("Up");
								if(goToDown) goToDown=false;
								goToUp = true;
							}
							fork2=true;
						}
					}
					if(x!=0) {
						if(distance[n][x+1][y]!=10 && distance[n][x][y] > distance[n][x+1][y]) {	// �ϴ� �˻�
							if(fork1==false && fork2==false) {
								aX[aCnt]=x+1;
								aY[aCnt]=y;
								System.out.println("Down");
								if(goToDown) goToDown=false;
							}
							fork3=true;
						}
					}
					if(y!=0) {
						if(distance[n][x][y+1]!=10 && distance[n][x][y] > distance[n][x][y+1]) {		// ���� �˻�
							if(fork1==false && fork2==false && fork3==false) {
								aX[aCnt]=x;
								aY[aCnt]=y+1;
								System.out.println("Right");
								if(goToDown) goToDown=false;
							}
							fork4=true;
						}
					}
					
					if((fork1==true && fork2==true) || (fork1==true && fork4==true) || (fork2==true && fork4==true)) {	// �� �� �ش�Ǹ� ���������� ���� (üũ����Ʈ)
						forkX = x;
						forkY = y;
						forkCnt = aCnt;
					}
					
					if((fork1==false && fork2==false && fork3==false && distance[n][x][y]!=0)) {	// ���ٸ� ��
						if(goToDown==false && goToUp==false) {
							goToDown = true;
							System.out.println("fork");
						}
						else {
							goToDown = false;
							break;
						}
					}
				
				//		y++;
				System.out.println(x + " , " + y + " - fork1: " + fork1 + ", fork2: " + fork2 + ", fork3: " + fork3 + ", fork4: " + fork4);
				System.out.println();
				
				if(fork1) y++;
				else if(fork2) x++;
				else if(fork3) y--;
				else if(fork4) x--;
				
				if(distance[n][x][y]==0) {
					break;
				}
				else {
					aCnt++;
				}
			}
			}
			
			for(int i=0; i<=aCnt; i++) {
				System.out.println("[" + i +"] " + aX[i] + " , " + aY[i]);
			}
			
			if(isPP) System.out.println("PP");
			else if(isPZ) System.out.println("PZ");
			else if(isZP) System.out.println("ZP");
			else if(isMM) System.out.println("MM");
			else if(isMZ) System.out.println("MZ");
			else if(isZM) System.out.println("ZM");
			else if(isPM) System.out.println("PM");
			else if(isMP) System.out.println("MP");
		//}
		
		//ȸ����ȯ ����  �ϳ��� ���ڤ�
		// ū ������ ����
		
		
		/*
		if(isPP) {		// ������ or �Ʒ���    �ܼ� ���ں�, ���ڰ� 
			//System.out.println("PP");
			if(map.map[gX][gY+1] != 1 && map.map[gX][gY+1] != 5 && map.map[gX][gY+1] != 6 && map.map[gX][gY+1] != 7 && map.map[gX][gY+1] != 8
					&& distance[0][1]!=9 && distance[0][1] <= distance[1][0]) {
				moveGhostR(n);
				System.out.println(n + " : PP Right");
			}
			else if(map.map[gX+1][gY] != 1 && map.map[gX+1][gY] != 5 && map.map[gX+1][gY] != 6 && map.map[gX+1][gY] != 7 && map.map[gX+1][gY] != 8
					&& distance[1][0]!=9 && distance[1][0] <= distance[0][1]) {
				moveGhostD(n);
				System.out.println(n + " : PP Down");
			}
			else {
				System.out.println(n +" : �� �ؾ��� �� �𸣰ھ�� PP");
			}
		}
		else if(isPM) {	// ���� or �Ʒ���
			//System.out.println("PM");
			if(map.map[gX][gY-1] != 1 && map.map[gX][gY-1] != 5 && map.map[gX][gY-1] != 6 && map.map[gX][gY-1] != 7 && map.map[gX][gY-1] != 8
					&& distance[0][1]!=9 && distance[0][1] <= distance[1][0]) {
				moveGhostL(n);
				System.out.println(n + " : PM Left");
			}
			else if(map.map[gX+1][gY] != 1 && map.map[gX+1][gY] != 5 && map.map[gX+1][gY] != 6 && map.map[gX+1][gY] != 7 && map.map[gX+1][gY] != 8
					&& distance[1][0]!=9 && distance[1][0] <= distance[0][1]) {
				moveGhostD(n);
				System.out.println(n + " : PM Down");
			}
			else {
				System.out.println(n + " : �� �ؾ��� �� �𸣰ھ�� PM");
			}
		}
		else if(isMP) {	// ������ or ����
			//System.out.println("MP");
			if(map.map[gX][gY+1] != 1 && map.map[gX][gY+1] != 5 && map.map[gX][gY+1] != 6 && map.map[gX][gY+1] != 7 && map.map[gX][gY+1] != 8
					&& distance[0][1]!=9 && distance[0][1] <= distance[1][0]) {
				moveGhostR(n);
				System.out.println(n + " : MP Right");
			}
			else if(map.map[gX-1][gY] != 1 && map.map[gX-1][gY] != 5 && map.map[gX-1][gY] != 6 && map.map[gX-1][gY] != 7 && map.map[gX-1][gY] != 8
					&& distance[1][0]!=9 && distance[1][0] <= distance[0][1]) {
				moveGhostU(n);
				System.out.println(n + " : MP Up");
			}
			else {
				System.out.println(n + " : �� �ؾ��� �� �𸣰ھ�� MP");
			}
		}
		else if(isMM) {	// ���� or ����
			//System.out.println("MM");
			if(map.map[gX][gY-1] != 1 && map.map[gX][gY-1] != 5 && map.map[gX][gY-1] != 6 && map.map[gX][gY-1] != 7 && map.map[gX][gY-1] != 8
					&& distance[0][1]!=9 && distance[0][1] <= distance[1][0] ) {
				moveGhostL(n);
				System.out.println(n + " : MM Left");
			}
			else if(map.map[gX-1][gY] != 1 && map.map[gX-1][gY] != 5 && map.map[gX-1][gY] != 6 && map.map[gX-1][gY] != 7 && map.map[gX-1][gY] != 8
					&& distance[1][0]!=9 && distance[1][0] <= distance[0][1]) {
				moveGhostU(n);
				System.out.println(n + " : MM Up");
			}
			else {
				System.out.println(n + " : �� �ؾ��� �� �𸣰ھ�� MM");
			}
		}
		else if(isPZ) {		// ������ �÷��̾�� �������� ���� ����
			if(map.map[gX+1][gY] != 1 && map.map[gX+1][gY] != 5 && map.map[gX+1][gY] != 6 && map.map[gX+1][gY] != 7 && map.map[gX+1][gY] != 8
					&& distance[1][0]!=9 && distance[1][0] <= distance[0][1]) {
				moveGhostD(n);
				System.out.println(n + " : PZ Down");
			}
		}
		else if(isZP) {		// ������ �÷��̾�� �������� ���ʿ� ����
			if(map.map[gX][gY+1] != 1 && map.map[gX][gY+1] != 5 && map.map[gX][gY+1] != 6 && map.map[gX][gY+1] != 7 && map.map[gX][gY+1] != 8
					&& distance[0][1]!=9 && distance[0][1] <= distance[1][0]) {
				moveGhostR(n);
				System.out.println(n + " : ZP Right");
			}
		}
		else if(isMZ) {		// ������ �÷��̾�� �������� �Ʒ��� ����
			if(map.map[gX-1][gY] != 1 && map.map[gX-1][gY] != 5 && map.map[gX-1][gY] != 6 && map.map[gX-1][gY] != 7 && map.map[gX-1][gY] != 8
					&& distance[1][0]!=9 && distance[1][0] <= distance[0][1]) {
				moveGhostU(n);
				System.out.println(n + " : MZ Up");
			}
		}
		else if(isZM) {		// ������ �÷��̾�� �������� �����ʿ� ����
			if(map.map[gX][gY-1] != 1 && map.map[gX][gY-1] != 5 && map.map[gX][gY-1] != 6 && map.map[gX][gY-1] != 7 && map.map[gX][gY-1] != 8
					&& distance[0][1]!=9 && distance[0][1] <= distance[1][0]) {
				moveGhostL(n);
				System.out.println(n + " : ZM Left");
			}
		}
		*/
		//isAttackMode[n] = true;
		/*
		int i=0, j=1;
		while(true) {
			while(true) {
				if(map.map[gX+i][gY+j] != 1 && map.map[gX+i][gY+j] != 5 && map.map[gX+i][gY+j] != 6 && map.map[gX+i][gY+j] != 7 && map.map[gX+i][gY+j] != 8) {
					if(i==j)					// ���� ��ġ���� �Ÿ� ���, �Ÿ��� ������ ��ϸ� ���  (0,0   0,1   1,0   1,1)
						near[i][j]=(float)1.5;
					else
						near[i][j]=1;
				}
				if(i==1 && j==1) break;
				if(j<1)
					j++;
				else
					j=0;
			}
			if(i<1)
				i++;
		}// �̰� + ������ ���� ���� �Ÿ� = ��� ���� �ֱ�
	
		// x�� y�� gapX�� gapY���� ū�� ��ū���� Ȯ���ϰ� ù��° �ٸ� �� (1)
		/*
		if(isPP) {
			for(int i=0; i<gapX; i++) {
				for(int j=0; j<gapY; j++) {
					if(i==j)
						astar[i][j]=(float)(i+0.5);
					else
						astar[i][j]=i;
				}
			}
		}
		*/
	}
	
	public void removeGhost(int n) {
		switch(n) {
		case 0:
			map.ghost1 = new ImageIcon(Main.class.getResource("../images/empty.png")).getImage();
			break;
		case 1:
			map.ghost2 = new ImageIcon(Main.class.getResource("../images/empty.png")).getImage();
			break;
		case 2:
			map.ghost3 = new ImageIcon(Main.class.getResource("../images/empty.png")).getImage();
			break;
		case 3:
			map.ghost4 = new ImageIcon(Main.class.getResource("../images/empty.png")).getImage();
			break;
		}
		map.ghostX[n]=10;
		map.ghostY[n]=(short) (n);
		map.map[10][n]=(short) (n+5);
		gDirection[n]=0;
		map.gRecent[n]=0;
		isDead[n]= true;
	}
	
	public void resetGhost(int n) {
		map.ghostX[n]=(short)14;
		map.ghostY[n]=(short) (n+12);
		map.map[14][n+12]=(short) (n+5);
		map.map[10][n]=0;
		status[n]=0;
		gDirection[n]=0;
		isDead[n] = false;
		map.isSuperMode[n] = false;
		map.isNormalMode[n] = true;
		if(n==0)
			map.ghost1 = new ImageIcon(Main.class.getResource("../images/ghost1.png")).getImage();
		if(n==1)
			map.ghost2 = new ImageIcon(Main.class.getResource("../images/ghost2.png")).getImage();
		if(n==2)
			map.ghost3 = new ImageIcon(Main.class.getResource("../images/ghost3.png")).getImage();
		if(n==3)
			map.ghost4 = new ImageIcon(Main.class.getResource("../images/ghost4.png")).getImage();
		
	}
	
	public void moveGhostU(int n) {
		if(map.ghostX[n] > 0 && map.map[map.ghostX[n]-1][map.ghostY[n]] != 1) {
			map.map[map.ghostX[n]][map.ghostY[n]]=map.gRecent[n];	// �÷��̾ �����Դ� ���� ������ ������� ä������
			map.ghostX[n]-=1;
			setGhost(map.ghostX[n], map.ghostY[n], n);
			//System.out.println(map.ghostX + " " + n + " , " + map.ghostY + " ");
		}
	}
	
	public void moveGhostD(int n) {
		if(map.ghostX[n] < 30 && map.map[map.ghostX[n]+1][map.ghostY[n]] != 1) {
			map.map[map.ghostX[n]][map.ghostY[n]]=map.gRecent[n];	// �÷��̾ �����Դ� ���� ������ ������� ä������
			map.ghostX[n]+=1;
			setGhost(map.ghostX[n], map.ghostY[n], n);
			//System.out.println(map.ghostX + " " + n + " , " + map.ghostY + " ");
		}
	}
	
	public void moveGhostL(int n) {
		if(map.ghostY[n] > 0 && map.map[map.ghostX[n]][map.ghostY[n]-1] != 1) {
			map.map[map.ghostX[n]][map.ghostY[n]]=map.gRecent[n];	// �÷��̾ �����Դ� ���� ������ ������� ä������
			map.ghostY[n]-=1;
			setGhost(map.ghostX[n], map.ghostY[n], n);
			//System.out.println(map.ghostX + " " + n + " , " + map.ghostY + " ");
		}
	}
	
	public void moveGhostR(int n) {
		if(map.ghostY[n] < 27 && map.map[map.ghostX[n]][map.ghostY[n]+1] != 1) {
			map.map[map.ghostX[n]][map.ghostY[n]]=map.gRecent[n];	// �÷��̾ �����Դ� ���� ������ ������� ä������
			map.ghostY[n]+=1;
			setGhost(map.ghostX[n], map.ghostY[n], n);
			//System.out.println(map.ghostX + " " + n + " , " + map.ghostY + " ");
		}
	}
}
