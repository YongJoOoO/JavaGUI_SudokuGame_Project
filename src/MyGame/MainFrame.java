package MyGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainFrame extends JFrame{
	
	//필드 
	private JPanel windowPanel;
	private JPanel buttonSelectionPanel;
	private SudokuPanel sPanel;
	private JPanel bottomPanel; //랭킹버튼 넣을 패널 
	private JLabel label;
	private JButton RankButton;
	
	private Image screenImage;
	private Graphics screenGraphic;
    boolean isSelectScreen = false; // 현재 창이 게임선택screen 창인지 여부 
	
	private SudokuPuzzleType selectedPuzzle; //선택한 퍼즐 타입
	private int fontSize;
	
	//이미지 아이콘 생성 
	
	//시작하기 버튼 이미지(기본)
	private ImageIcon startButtonBasicImage = new ImageIcon(Main.class.getResource("/Images/startButton.png"));

	//종료하기 버튼 이미지(기본)
	private ImageIcon quitButtonBasicImage = new ImageIcon(Main.class.getResource("/Images/quitButton.png"));

	//introImage
	private Image introImage= new ImageIcon(Main.class.getResource("/Images/introImage.png")).getImage();

	//메뉴바(JLabel)
	private JLabel menuBar =  new JLabel(new ImageIcon(Main.class.getResource("/Images/menuBar.png")));

	//X 버튼 이미지(기본)
	private ImageIcon exitBasicImage = new ImageIcon(Main.class.getResource("/Images/exitButtonBasic.png"));

	//게임 선택창 
	private Image selectedImage;
	//left 이미지
	private ImageIcon leftButtonImage = new ImageIcon(Main.class.getResource("/Images/Left.png"));
	//right 이미지
	private ImageIcon rightButtonImage = new ImageIcon(Main.class.getResource("/Images/Right.png"));

	//선택하기 버튼 이미지 
	private ImageIcon selectButtonImage = new ImageIcon(Main.class.getResource("/Images/selectButton.png"));

	//back 버튼 이미지
	private ImageIcon backButtonImage = new ImageIcon(Main.class.getResource("/Images/backButton.png"));
	
	
	//ArrayList로 게임선택 Track 관리 
	ArrayList<Track> trackList = new ArrayList<Track>();
	
	private int nowSelected = 0; //선택 인덱스 기본 0초기화

	//J버튼에 각 이미지아이콘 넣어주기
	private JButton exitButton = new JButton(exitBasicImage); 
	private JButton startButton = new JButton(startButtonBasicImage);
	private JButton quitButton = new JButton(quitButtonBasicImage);
	private JButton leftButton = new JButton(leftButtonImage); 
	private JButton rightButton = new JButton(rightButtonImage);
	private JButton selectButton = new JButton(selectButtonImage);
	private JButton backButton = new JButton(backButtonImage);
	
	//마우스 좌표 변수 
	private int mouseX, mouseY;
	
	//생성자
	public MainFrame() {
		setUndecorated(true); //기본 메뉴바는 없애주기 (내가 설정한 메뉴바로 )
		setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT); //창 크기 
		setResizable(false);//한 번 만들어진 창은 사용자가 인위적으로 변형 금지시킴 
		setLocationRelativeTo(null); //실행 시 컴퓨터 정중앙에 뜸
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //게임창 종료시 프로그램 완전 종료
		setVisible(true); //게임창 화면에 정상 보여주기
		setBackground(new Color(0, 0, 0, 0));
		setLayout(null);
		
		//trackList(게임 선택창 화면) 
		trackList.add(new Track("level1.png")); //0번
		trackList.add(new Track("level2.png")); //1번
		trackList.add(new Track("level3.jpg")); //2번
		
		//exitButton 처리
		exitButton.setBounds(765, 0, 30, 30); //exit 버튼 위치(메뉴바 위의 오른쪽 위치로) 
		exitButton.setBorderPainted(false);
		exitButton.setContentAreaFilled(false);
		exitButton.setFocusPainted(false);
		exitButton.addMouseListener(new MouseAdapter() { //마우스 이벤트 처리 
			@Override
			public void mouseClicked(MouseEvent e) { //클릭 시 
				// TODO 자동 생성된 메소드 스텁
				super.mouseClicked(e);
				System.exit(0); //프로그램 종료 
			}
		});
		add(exitButton);	
		
		//메뉴바 처리
		menuBar.setBounds(0, 0, 800, 10); //메뉴바 위치
		menuBar.addMouseListener(new MouseAdapter() { //메뉴바 클릭이벤트
					public void mouseClicked(MouseEvent e) {
						mouseX = e.getX();
						mouseY = e.getY();
					}
				});
		menuBar.addMouseMotionListener(new MouseMotionAdapter() {
					//메뉴바 마우스 드레그 시, 마우스 좌표값 따라서 화면창도 움직임 
					@Override
					public void mouseDragged(MouseEvent e) {
						// TODO 자동 생성된 메소드 스텁
						super.mouseDragged(e);
						int x = e.getXOnScreen();	
						int y = e.getYOnScreen();
						setLocation(x - mouseX, y - mouseY);
					}
		});
		add(menuBar);

		//startButton 처리
		startButton.setBounds(180, 370, 200, 100); //위치
		startButton.setBorderPainted(false);
		startButton.setContentAreaFilled(false);
		startButton.setFocusPainted(false);
		startButton.addMouseListener(new MouseAdapter() { //마우스 이벤트 처리 
			@Override
			public void mouseClicked(MouseEvent e) { //클릭 시 -> 화면 전환 
				// TODO 자동 생성된 메소드 스텁
				super.mouseClicked(e);
				selectTrack(0); //시작버튼 눌렀을 때 전환되는 첫 트랙은 0인덱스
				startButton.setVisible(false); //화면에 안보이게
				quitButton.setVisible(false);
				leftButton.setVisible(true); //화면에 보이게 
				rightButton.setVisible(true);
				selectButton.setVisible(true);
				introImage = new ImageIcon(Main.class.getResource("/Images/1.jpg")).getImage();
				isSelectScreen = true;
			}
		});
		add(startButton);
		
		//quitButton
		quitButton.setBounds(390, 370, 200, 100); //위치
		quitButton.setBorderPainted(false);
		quitButton.setContentAreaFilled(false);
		quitButton.setFocusPainted(false);
		quitButton.addMouseListener(new MouseAdapter() { //마우스 이벤트 처리 
			
			@Override
			public void mouseClicked(MouseEvent e) { //클릭 시 
				// TODO 자동 생성된 메소드 스텁
				super.mouseClicked(e);
				System.exit(0); //프로그램 종료 
			}
		});
		add(quitButton);
	
				//leftButton 처리
				leftButton.setVisible(false); //처음에는 보이면 안됨
				leftButton.setBounds(100, 310, 60, 60); //위치
				leftButton.setBorderPainted(false);
				leftButton.setContentAreaFilled(false);
				leftButton.setFocusPainted(false);
				leftButton.addMouseListener(new MouseAdapter() { //마우스 이벤트 처리 
					
					@Override
					public void mouseClicked(MouseEvent e) { //클릭 시 
						// TODO 자동 생성된 메소드 스텁
						super.mouseClicked(e);
						selectLeft(); //왼쪽 이동 
					}
				});
				add(leftButton);
				
				//rightButton 처리
				rightButton.setVisible(false);
				rightButton.setBounds(660, 310, 60, 60); //위치
				rightButton.setBorderPainted(false);
				rightButton.setContentAreaFilled(false);
				rightButton.setFocusPainted(false);
				rightButton.addMouseListener(new MouseAdapter() { //마우스 이벤트 처리 
					
					@Override
					public void mouseClicked(MouseEvent e) { //클릭 시 
						// TODO 자동 생성된 메소드 스텁
						super.mouseClicked(e);
						selectRight(); //오른쪽 이동 
					}
				});
				add(rightButton);	
				
				//selectButton 처리
				selectButton.setVisible(false);
				selectButton.setBounds(280, 460, 250, 67); //위치
				selectButton.setBorderPainted(false);
				selectButton.setContentAreaFilled(false);
				selectButton.setFocusPainted(false);
				selectButton.addActionListener(new ActionListener() {
		            @Override
		            public void actionPerformed(ActionEvent e) {
		            	gameStart(nowSelected);
		            }
		        });
				add(selectButton);	
				
				//backButton 처리
				backButton.setVisible(false);
				backButton.setBounds(5, 50, 60, 60); //위치
				backButton.setBorderPainted(false);
				backButton.setContentAreaFilled(false);
				backButton.setFocusPainted(false);
				backButton.addMouseListener(new MouseAdapter() { //마우스 이벤트 처리 
					@Override
					public void mouseClicked(MouseEvent e) { //클릭 시 
						// TODO 자동 생성된 메소드 스텁
						super.mouseClicked(e);
						//되돌아가기 이벤트
						backMain();
					}
				});
				add(backButton);	
				
				
				//게임창 관련 패널 믂음
				windowPanel = new JPanel();
				
				windowPanel.setBounds(0, 0, 800, 600);
				windowPanel.setBackground(Color.black);

				sPanel = new SudokuPanel(); //(스도쿠창) 패널 객체 생성 
				
				buttonSelectionPanel = new JPanel(); //(버튼창) 패널 객체 생성 
				buttonSelectionPanel.setSize(500, 90); 
				buttonSelectionPanel.setBackground(Color.black);
				
				bottomPanel = new JPanel(); //게임창 하단 패널 
				bottomPanel.setSize(new Dimension(540, 50));
				bottomPanel.setBackground(Color.GREEN);
				
				label = new JLabel("랭킹 확인 -->");
				label.setForeground(Color.white);
				label.setFont(new Font("나눔고딕", Font.BOLD, 30));
				bottomPanel.add(label);
				
				RankButton = new JButton("GO 랭킹");
				RankButton.addActionListener(new ActionListener() {
		            @Override
		            public void actionPerformed(ActionEvent e) {
		            	
		            	GameOver();//호출 
		            }
		        });
				bottomPanel.add(RankButton);
				
				windowPanel.add(buttonSelectionPanel);
				windowPanel.add(sPanel);
				windowPanel.add(bottomPanel);
				
				this.add(windowPanel);
				
				windowPanel.setVisible(false);	
				buttonSelectionPanel.setVisible(false);
				sPanel.setVisible(false);
				bottomPanel.setVisible(false);
				
				
	}
	
	//그림 그리는 메소드 
	public void paint(Graphics g) {
		screenImage = createImage(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		screenGraphic = screenImage.getGraphics();
		screenDraw(screenGraphic);
		g.drawImage(screenImage, 0, 0, null); 

	}
	//이미지 그리기용
	public void screenDraw(Graphics g) {
		g.drawImage(introImage, 0, 0, null);
		
		if(isSelectScreen) { // 현재 선택Screen T값인 경우에 한해서 (이미지 교체)
			g.drawImage(selectedImage, 200, 100, null); //게임 설명창(선택)
		}
		paintComponents(g); //add처리한 컴포넌트 그려주기
		this.repaint();
	}
	
	
	//게임 level 선택 이미지 관련 메소드 
	public void selectTrack(int nowSelected) { 
		//게임선택이미지 (track에서 가져옴)
		selectedImage = new ImageIcon(Main.class.getResource("/Images/" + trackList.get(nowSelected).getSelectGameImage())).getImage();
	}
	public void selectLeft() { //left 선택시
		if(nowSelected == 0) { //만약 현재 가장 첫번째 트랙이면
			nowSelected = trackList.size() - 1; //끝으로 이동 
		}
		else nowSelected--;
		selectTrack(nowSelected);
	}
	public void selectRight() { //right 선택시
		if(nowSelected == trackList.size()-1) { //만약 현재 가장 끝 트랙이면
			nowSelected = 0; //0인덱스로 이동 
		}
		else nowSelected++;
		selectTrack(nowSelected);
	}
	
	//메소드(창 rebuild)
	public void rebuildInterface(SudokuPuzzleType puzzleType, int fontSize) {
		SudokuPuzzle generatedPuzzle = new SudokuGenerator().generateRandomSudoku(puzzleType);
		sPanel.newSudokuPuzzle(generatedPuzzle);
		sPanel.setFontSize(fontSize);
		
		buttonSelectionPanel.removeAll(); //기존 버튼패널 removeAll 처리 
		
		for(String value : generatedPuzzle.getValidValues()) {
			JButton b = new JButton(value);
			b.setSize(new Dimension(40, 40));
			b.setBackground(Color.PINK);
			b.addActionListener(sPanel.new NumActionListener());
			buttonSelectionPanel.add(b);
		}
		sPanel.repaint();
		buttonSelectionPanel.revalidate();
		buttonSelectionPanel.repaint();
	}
	
	public void gameStart(int nowSelected) { //게임실행화면으로 들어가기
		isSelectScreen = false; 
		
		leftButton.setVisible(false);
		rightButton.setVisible(false);
		selectButton.setVisible(false);
		backButton.setVisible(true); //보이게
		
		if(nowSelected == 0) {
			rebuildInterface(SudokuPuzzleType.SIXBYSIX,30);
		}else if (nowSelected == 1) {
			rebuildInterface(SudokuPuzzleType.NINEBYNINE,26);
		}else if (nowSelected == 2) {
			rebuildInterface(SudokuPuzzleType.TWELVEBYTWELVE,20);
		}
		
		windowPanel.setVisible(true);
		buttonSelectionPanel.setVisible(true);
		sPanel.setVisible(true);
		bottomPanel.setVisible(true);
	}
	
	public void backMain() { //돌아가기 처리 함수
		isSelectScreen = true;
		buttonSelectionPanel.setVisible(false);
		sPanel.setVisible(false);
		windowPanel.setVisible(false);
		introImage = new ImageIcon(Main.class.getResource("/Images/1.jpg")).getImage(); //화면 전환
		leftButton.setVisible(true);
		rightButton.setVisible(true);
		selectButton.setVisible(true);
		backButton.setVisible(false); 
		selectTrack(nowSelected);
	}
	
	public void GameOver() {
		new GameOverFrame();
		
	}
	
}

//--> 여기로 Track 클래스 옮겨줌 

class Track { //level 선택 이미지관리 Track클래스 
	
	//필드
	private String selectGameImage;
	
	//생성자
	public Track(String selectGameImage) {
		super();
		this.selectGameImage = selectGameImage;
	}

	public String getSelectGameImage() {
		return selectGameImage;
	}

	public void setSelectGameImage(String selectGameImage) {
		this.selectGameImage = selectGameImage;
	}

}
