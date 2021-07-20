package pacMan;

import java.util.Random;

import javax.swing.ImageIcon;

/*
 *  할 것
 *  1. MM 코드 수정 갈림길
 *  2. 공격 AI 유령 개개인화
 *  2. 텍스쳐 업데이트
 *  
 *  ?. 레벨 디자인
 */
public class Ghost {
	Map map;
	int status[] = {0, 0, 0, 0};	// 초반 유령 방을 빠져나오게 하기 위한 상태 배열
	int gDirection[] = {0, 0, 0, 0}; // 유령의 이동방향을 랜덤하게 정하게 하기 위한 배열
	boolean isDead[] = {false, false, false, false};
	private short startCnt = 0;	// 시작 카운트, 시간이 지날수록 유령이 차례로 움직이기 시작함
	private short cnt[] = {1, 1, 1, 1}; // 슈퍼모드일 때 움직임을 느리게 하기 위해서 홀수에만 움직이게 하는데 쓰이는 변수
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
	private int aX[]= {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; // 21개
	private int aY[]= {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; // 21개
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
	public static int[][][] distance = {	// 탐색 알고리즘에 필요한 배열 최대 6x6
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
	
	public void alphaGhost() {	// 알파고    스트
		for(int i =0; i<4; i++) {		// 유령이 4마리라서 4번 반복
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
			if(map.isSuperMode[i] && cnt[i] % 2 == 0) {	// 변수를 이용하여 2의 배수일 때는 한템포 쉬어서 슈퍼모드 때는 유령이 느리게 이동하게끔 구현
				cnt[i]++;
				continue;
			}
			else if(map.isSuperMode[i] && cnt[i] % 2 == 1) {
				cnt[i]++;
			}
			
			if (status[i]<5) {
				if(i==0) {								// 5번 유령
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
				else if(i==1 && startCnt>=30) {			// 6번 유령
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
				else if(i==2 && startCnt>=60) {				// 7번 유령
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
				else if(i==3 && startCnt>=90) {				// 8번 유령
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
				if(isAttackMode[i]==false || map.isSuperMode[i] == true){ // 4방향
				
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
			System.out.println("1없애기");
			break;
		case 2:
			rand[0]=0;
			System.out.println("2없애기");
			break;
		case 3:
			rand[3]=0;
			System.out.println("3없애기");
			break;
		case 4:
			rand[2]=0;
			System.out.println("4없애기");
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
				//유령 잡아먹기
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
				// 플레이어, 유령 위치 초기화
				if(map.life == 0) {
					//게임오버
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
	
	public void checkAttack(int n) { // 유령의 반경 5x5에 플레이어가 들어오면 어택모드 
		// 따라가는 것이 아닌, 한 번 움직일 때마다 본인 위치에서 플레이어의 위치 까지 경로를 "탐색"하여 더 빠른 길을 찾음
		int gX = map.ghostX[n];	// 고스트 n의 x,y좌표 (x,y) 
		int gY = map.ghostY[n];
		int pX = map.playerX;
		int pY = map.playerY;
		
		// gx 와 px의 차이가 5이하다. gx=5, px=10 ok, gx +5 = px /         gx - 5 = px / gy + 2 = py
		// 상수가 5 이하면 가능. 5, 4, 3, 2, 1 ... 
		for(int j=5; j>-1; j--) {
			for(int i=0; i<6; i++) {
				if ((pX-j == gX || pX+j == gX) && (pY+i == gY || pY-i == gY)) {
					isAttackMode[n] = true;
					gapX = j;
					gapY = i;
					attack(n);
					//System.out.println(n + " : attack!");
					
					if (pX-j == gX && pY-i == gY) {			//	(+,+)	
						// +,+ 구간임을 나타내는 변수
						//System.out.println("go PP"); // PLUS PLUS (x,y순)
						if(j==0)					// ZP   (유령이 플레이어보다 일직선상 왼쪽에 있음) 
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
						else if (j!=0 && i!=0) {	// PP	(유령이 플레이어보다 왼쪽 위에 있음)
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
						if(i==0) {					//PZ	(유령이 플레이어보다 일직선상 위에 있음)
							isPP = false;		
							isPM = false;
							isMP = false;
							isMM = false;
							isPZ = true;
							isZP = false;
							isMZ = false;
							isZM = false;
						}
						else if (j!=0 && i!=0) {	//PM	(유령이 플레이어보다  오른쪽 위에 있음)
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
						if(i==0) {					//MZ	(유령이 플레이어보다 일직선상 아래에 있음)
							isPP = false;		
							isPM = false;
							isMP = false;
							isMM = false;
							isPZ = false;
							isZP = false;
							isMZ = true;
							isZM = false;
						}
						else if (j!=0 && i!=0) {	//MP	(유령이 플레이어보다 왼쪽 아래에 있음)
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
						if(j==0) {					//ZM	(유령이 플레이어보다 일직선상 오른쪽에 있음)
							isPP = false;
							isPM = false;
							isMP = false;
							isMM = false;
							isPZ = false;
							isZP = false;
							isMZ = false;
							isZM = true;
						}
						else if (j!=0 && i!=0) {	//MM	(유령이 플레이어보다 오른쪽 아래에 있음)
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
	
	//	-5,-5	-5,-4	-5,-3	-5,-2	-5,-1	-5,0	-5,1	-5,2	-5,3	-5,4	-5,5			(세로, 가로)
	
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
	
	// 유령의 현재 위치 에서 부터 해당 길 까지의 거리 = a, 해당 길에서 부터 플레이어의 현재 위치 까지의 남은 거리 = b , a+b가 가장 낮은 길로 이동 (a*)
	public void attack(int n) {	// 플레이어를 따라가기, 최적의 경로 탐색
		// 1. 해당 방향으로 인접한 3개의 블록이 이동 가능한 블록인지 확인
		// 2. 이동 가능한 블록이면 인접한 3개의 블록에 거리 입력
		int gX = map.ghostX[n];	// 고스트 n의 x,y좌표 (x,y) 
		int gY = map.ghostY[n];
		int pX = map.playerX;
		int pY = map.playerY;
		int gap = gapX + gapY - 1;
		
		for(int i=0; i<6; i++) {
			for(int j=0; j<6; j++) {
				distance[n][i][j]=10;		// distance 초기화
			}
		}
		//최대 5x5의 타일을 수치화 시켜야함
		//+인지 -인지 0인지 확인 후 gap을 더하여 계산
		//gx+gap=px
		
		//System.out.println(n + " : ok!!!!!!!!!!!!!!!");
		//System.out.println(isPP + " " + isPM + " " + isMP + " " + isMM);
		//System.out.println(isAttackMode[0] + " " + isAttackMode[1] + " " + isAttackMode[2] + " " + isAttackMode[3]);
		
		// 목적지까지의 거리만 계산하자!!!!!!!
		// 장애물 상관 없이 넣은 후에 나중에 장애물 if문 계산
		// distance[][]
		// 목표 : pX,pY
		// 현위치 : gX,gY
		// px,py-2		px,py-1			px,py  
		// px-1,py-2	px-1,py-1		px-1,py
		// px-2,py-2	px-2,py-1		px-2,py
		// gx,gy		px-3,py-1		px-3,py		gx=px-3, gy=py-2
		// gap은 필요 없는듯 어느 구역(1/4)인지에 따라 더할지 뺄지만 정하고, 계속 연산하다가 gx나 gy랑 같아지면 그만 가기
		// gap은 for문 도착점에 넣기 o
		//if(isPP || isPZ || isZP) {
			for(int x=0; x<6; x++) {	// distance[] 셋팅 함수
				for(int y=0; y<6; y++) {	// 목적지 : (gapX, gapY)
					// ** 이 코드는 대각선으로 모든 값이 같기 때문에 px(도착지)의 x좌표와 y좌표보다 크면 return 시켜야함  ** (for문의 끝점을 gapX,gapY로 해서 해결)
					if(gX+x>=31 || gY+y>=28) {
						continue;
					}
					
					switch(x+y) {				// x+y는 시작점에서 해당 지점까지의 거리를 뜻함
					case 0: // gapX=2, gapY=3 일 때, gap=4
						distance[n][x][y] = gap+1;
						break;
					case 1: 
						if(map.map[gX+x][gY+y]!=1 && map.map[gX+x][gY+y]!=5 && map.map[gX+x][gY+y]!=6 && map.map[gX+x][gY+y]!=7 && map.map[gX+x][gY+y]!=8) {
							if(map.map[gX+x][gY+y] == 4)
								distance[n][x][y] = 0;
							else
								distance[n][x][y] = gap;	// gap은 도착지점 까지의 거리를 뜻함 (gapX+gapY-1)
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
			}// distance[][]에 거리를 나타내는 수 입력 완료
			
			// 길이 막혔을 경우 돌아가야 하기 때문에 전체 맵을 distance[][] 크기를 확장해야하나? 상황이 너무 한정적이라 굳이 필요 없을듯.
			// near[][]은 별 쓸모 없을듯.
			// distance[][] 내에서 계산한 후 setghost 등으로 옮기기만 하면 될거같아서 음수,양수는 상관없을듯.
			
			//distance[][]가 더 작은 곳으로 유령 이동
			//distance[][] 상으로는 어차피 오른쪽 아니면 윗쪽으로 밖에 안 감, 하지만  map 상에선 distance가 4방향으로 돌아가기 때문에 distance의 방향과는 다른 개념
			for(int i=0; i<6; i++) {
				for(int j=0; j<6; j++) {
					System.out.print(distance[n][i][j] + " ");
				}
				System.out.println();
			}
			boolean goToDown = false; 
			boolean goToUp = false; 
			// 경로를 만들자
	
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
					fork1=false;	// 갈림길 체크 변수
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
						if(distance[n][x][y+1]!=10 && distance[n][x][y] > distance[n][x][y+1] && goToDown==false) {		// 우측 검사
							aX[aCnt]=x;
							aY[aCnt]=y+1;
							fork1=true;
							System.out.println("Right");
						}
					}
					if(x!=5) {
						if(distance[n][x+1][y]!=10 && distance[n][x][y] > distance[n][x+1][y]) {		// 하단 검사
							if(fork1==false && goToUp==false) {	// 갈림길 검사만을 위해 구동, 우측 검사에서 해당되지 않았을 경우에만 경로에 삽입
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
						if(distance[n][x-1][y]!=10 && distance[n][x][y] > distance[n][x-1][y]) {	// 상단 검사
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
						if(distance[n][x][y-1]!=10 && distance[n][x][y] > distance[n][x][y-1]) {		// 좌측 검사
							if(fork1==false && fork2==false && fork3==false) {
								aX[aCnt]=x;
								aY[aCnt]=y-1;
								System.out.println("Left");
								if(goToDown) goToDown=false;
							}
							fork4=true;
						}
					}
					
					if((fork1==true && fork2==true) || (fork1==true && fork4==true) || (fork2==true && fork4==true)) {	// 둘 다 해당되면 갈림길임을 인지 (체크포인트)
						forkX = x;
						forkY = y;
						forkCnt = aCnt;
					}
					
					if((fork1==false && fork2==false && fork3==false && distance[n][x][y]!=0)) {	// 막다른 길
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
			
			else if(isMM||isMZ||isZM) {	// 왼쪽위, 왼쪽, 위
				x=gapX;
				y=gapY;
				while(true) {
					if(x==0 && y==0) break;
					if(goToDown) {
						x=forkX;
						y=forkY;
						aCnt=forkCnt;
					}
					fork1=false;	// 갈림길 체크 변수
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
						if(distance[n][x][y-1]!=10 && distance[n][x][y] > distance[n][x][y-1] && goToDown==false) {		// 좌측 검사
							aX[aCnt]=x;
							aY[aCnt]=y-1;
							fork1=true;
							System.out.println("Left");
						}
					}
					if(x!=0) {
						if(distance[n][x-1][y]!=10 && distance[n][x][y] > distance[n][x-1][y]) {		// 상단 검사
							if(fork1==false && goToUp==false) {	// 갈림길 검사만을 위해 구동, 우측 검사에서 해당되지 않았을 경우에만 경로에 삽입
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
						if(distance[n][x+1][y]!=10 && distance[n][x][y] > distance[n][x+1][y]) {	// 하단 검사
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
						if(distance[n][x][y+1]!=10 && distance[n][x][y] > distance[n][x][y+1]) {		// 우측 검사
							if(fork1==false && fork2==false && fork3==false) {
								aX[aCnt]=x;
								aY[aCnt]=y+1;
								System.out.println("Right");
								if(goToDown) goToDown=false;
							}
							fork4=true;
						}
					}
					
					if((fork1==true && fork2==true) || (fork1==true && fork4==true) || (fork2==true && fork4==true)) {	// 둘 다 해당되면 갈림길임을 인지 (체크포인트)
						forkX = x;
						forkY = y;
						forkCnt = aCnt;
					}
					
					if((fork1==false && fork2==false && fork3==false && distance[n][x][y]!=0)) {	// 막다른 길
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
		
		//회전변환 하자  하나씩 쓰자ㅏ
		// 큰 곳으로 향해
		
		
		/*
		if(isPP) {		// 오른쪽 or 아랫쪽    단순 숫자비교, 숫자가 
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
				System.out.println(n +" : 뭘 해야할 지 모르겠어요 PP");
			}
		}
		else if(isPM) {	// 왼쪽 or 아랫쪽
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
				System.out.println(n + " : 뭘 해야할 지 모르겠어요 PM");
			}
		}
		else if(isMP) {	// 오른쪽 or 윗쪽
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
				System.out.println(n + " : 뭘 해야할 지 모르겠어요 MP");
			}
		}
		else if(isMM) {	// 왼쪽 or 윗쪽
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
				System.out.println(n + " : 뭘 해야할 지 모르겠어요 MM");
			}
		}
		else if(isPZ) {		// 유령이 플레이어보다 일직선상 위에 있음
			if(map.map[gX+1][gY] != 1 && map.map[gX+1][gY] != 5 && map.map[gX+1][gY] != 6 && map.map[gX+1][gY] != 7 && map.map[gX+1][gY] != 8
					&& distance[1][0]!=9 && distance[1][0] <= distance[0][1]) {
				moveGhostD(n);
				System.out.println(n + " : PZ Down");
			}
		}
		else if(isZP) {		// 유령이 플레이어보다 일직선상 왼쪽에 있음
			if(map.map[gX][gY+1] != 1 && map.map[gX][gY+1] != 5 && map.map[gX][gY+1] != 6 && map.map[gX][gY+1] != 7 && map.map[gX][gY+1] != 8
					&& distance[0][1]!=9 && distance[0][1] <= distance[1][0]) {
				moveGhostR(n);
				System.out.println(n + " : ZP Right");
			}
		}
		else if(isMZ) {		// 유령이 플레이어보다 일직선상 아래에 있음
			if(map.map[gX-1][gY] != 1 && map.map[gX-1][gY] != 5 && map.map[gX-1][gY] != 6 && map.map[gX-1][gY] != 7 && map.map[gX-1][gY] != 8
					&& distance[1][0]!=9 && distance[1][0] <= distance[0][1]) {
				moveGhostU(n);
				System.out.println(n + " : MZ Up");
			}
		}
		else if(isZM) {		// 유령이 플레이어보다 일직선상 오른쪽에 있음
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
					if(i==j)					// 현재 위치에서 거리 계산, 거리는 인접한 블록만 계산  (0,0   0,1   1,0   1,1)
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
		}// 이거 + 목적지 까지 남은 거리 = 모든 블럭에 넣기
	
		// x나 y가 gapX와 gapY보다 큰지 안큰지만 확인하고 첫번째 줄만 비교 (1)
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
			map.map[map.ghostX[n]][map.ghostY[n]]=map.gRecent[n];	// 플레이어가 지나왔던 곳을 원래의 블록으로 채워넣음
			map.ghostX[n]-=1;
			setGhost(map.ghostX[n], map.ghostY[n], n);
			//System.out.println(map.ghostX + " " + n + " , " + map.ghostY + " ");
		}
	}
	
	public void moveGhostD(int n) {
		if(map.ghostX[n] < 30 && map.map[map.ghostX[n]+1][map.ghostY[n]] != 1) {
			map.map[map.ghostX[n]][map.ghostY[n]]=map.gRecent[n];	// 플레이어가 지나왔던 곳을 원래의 블록으로 채워넣음
			map.ghostX[n]+=1;
			setGhost(map.ghostX[n], map.ghostY[n], n);
			//System.out.println(map.ghostX + " " + n + " , " + map.ghostY + " ");
		}
	}
	
	public void moveGhostL(int n) {
		if(map.ghostY[n] > 0 && map.map[map.ghostX[n]][map.ghostY[n]-1] != 1) {
			map.map[map.ghostX[n]][map.ghostY[n]]=map.gRecent[n];	// 플레이어가 지나왔던 곳을 원래의 블록으로 채워넣음
			map.ghostY[n]-=1;
			setGhost(map.ghostX[n], map.ghostY[n], n);
			//System.out.println(map.ghostX + " " + n + " , " + map.ghostY + " ");
		}
	}
	
	public void moveGhostR(int n) {
		if(map.ghostY[n] < 27 && map.map[map.ghostX[n]][map.ghostY[n]+1] != 1) {
			map.map[map.ghostX[n]][map.ghostY[n]]=map.gRecent[n];	// 플레이어가 지나왔던 곳을 원래의 블록으로 채워넣음
			map.ghostY[n]+=1;
			setGhost(map.ghostX[n], map.ghostY[n], n);
			//System.out.println(map.ghostX + " " + n + " , " + map.ghostY + " ");
		}
	}
}
