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
	
	//�ʵ� 
	private JPanel windowPanel;
	private JPanel buttonSelectionPanel;
	private SudokuPanel sPanel;
	private JPanel bottomPanel; //��ŷ��ư ���� �г� 
	private JLabel label;
	private JButton RankButton;
	
	private Image screenImage;
	private Graphics screenGraphic;
    boolean isSelectScreen = false; // ���� â�� ���Ӽ���screen â���� ���� 
	
	private SudokuPuzzleType selectedPuzzle; //������ ���� Ÿ��
	private int fontSize;
	
	//�̹��� ������ ���� 
	
	//�����ϱ� ��ư �̹���(�⺻)
	private ImageIcon startButtonBasicImage = new ImageIcon(Main.class.getResource("/Images/startButton.png"));

	//�����ϱ� ��ư �̹���(�⺻)
	private ImageIcon quitButtonBasicImage = new ImageIcon(Main.class.getResource("/Images/quitButton.png"));

	//introImage
	private Image introImage= new ImageIcon(Main.class.getResource("/Images/introImage.png")).getImage();

	//�޴���(JLabel)
	private JLabel menuBar =  new JLabel(new ImageIcon(Main.class.getResource("/Images/menuBar.png")));

	//X ��ư �̹���(�⺻)
	private ImageIcon exitBasicImage = new ImageIcon(Main.class.getResource("/Images/exitButtonBasic.png"));

	//���� ����â 
	private Image selectedImage;
	//left �̹���
	private ImageIcon leftButtonImage = new ImageIcon(Main.class.getResource("/Images/Left.png"));
	//right �̹���
	private ImageIcon rightButtonImage = new ImageIcon(Main.class.getResource("/Images/Right.png"));

	//�����ϱ� ��ư �̹��� 
	private ImageIcon selectButtonImage = new ImageIcon(Main.class.getResource("/Images/selectButton.png"));

	//back ��ư �̹���
	private ImageIcon backButtonImage = new ImageIcon(Main.class.getResource("/Images/backButton.png"));
	
	
	//ArrayList�� ���Ӽ��� Track ���� 
	ArrayList<Track> trackList = new ArrayList<Track>();
	
	private int nowSelected = 0; //���� �ε��� �⺻ 0�ʱ�ȭ

	//J��ư�� �� �̹��������� �־��ֱ�
	private JButton exitButton = new JButton(exitBasicImage); 
	private JButton startButton = new JButton(startButtonBasicImage);
	private JButton quitButton = new JButton(quitButtonBasicImage);
	private JButton leftButton = new JButton(leftButtonImage); 
	private JButton rightButton = new JButton(rightButtonImage);
	private JButton selectButton = new JButton(selectButtonImage);
	private JButton backButton = new JButton(backButtonImage);
	
	//���콺 ��ǥ ���� 
	private int mouseX, mouseY;
	
	//������
	public MainFrame() {
		setUndecorated(true); //�⺻ �޴��ٴ� �����ֱ� (���� ������ �޴��ٷ� )
		setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT); //â ũ�� 
		setResizable(false);//�� �� ������� â�� ����ڰ� ���������� ���� ������Ŵ 
		setLocationRelativeTo(null); //���� �� ��ǻ�� ���߾ӿ� ��
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //����â ����� ���α׷� ���� ����
		setVisible(true); //����â ȭ�鿡 ���� �����ֱ�
		setBackground(new Color(0, 0, 0, 0));
		setLayout(null);
		
		//trackList(���� ����â ȭ��) 
		trackList.add(new Track("level1.png")); //0��
		trackList.add(new Track("level2.png")); //1��
		trackList.add(new Track("level3.jpg")); //2��
		
		//exitButton ó��
		exitButton.setBounds(765, 0, 30, 30); //exit ��ư ��ġ(�޴��� ���� ������ ��ġ��) 
		exitButton.setBorderPainted(false);
		exitButton.setContentAreaFilled(false);
		exitButton.setFocusPainted(false);
		exitButton.addMouseListener(new MouseAdapter() { //���콺 �̺�Ʈ ó�� 
			@Override
			public void mouseClicked(MouseEvent e) { //Ŭ�� �� 
				// TODO �ڵ� ������ �޼ҵ� ����
				super.mouseClicked(e);
				System.exit(0); //���α׷� ���� 
			}
		});
		add(exitButton);	
		
		//�޴��� ó��
		menuBar.setBounds(0, 0, 800, 10); //�޴��� ��ġ
		menuBar.addMouseListener(new MouseAdapter() { //�޴��� Ŭ���̺�Ʈ
					public void mouseClicked(MouseEvent e) {
						mouseX = e.getX();
						mouseY = e.getY();
					}
				});
		menuBar.addMouseMotionListener(new MouseMotionAdapter() {
					//�޴��� ���콺 �巹�� ��, ���콺 ��ǥ�� ���� ȭ��â�� ������ 
					@Override
					public void mouseDragged(MouseEvent e) {
						// TODO �ڵ� ������ �޼ҵ� ����
						super.mouseDragged(e);
						int x = e.getXOnScreen();	
						int y = e.getYOnScreen();
						setLocation(x - mouseX, y - mouseY);
					}
		});
		add(menuBar);

		//startButton ó��
		startButton.setBounds(180, 370, 200, 100); //��ġ
		startButton.setBorderPainted(false);
		startButton.setContentAreaFilled(false);
		startButton.setFocusPainted(false);
		startButton.addMouseListener(new MouseAdapter() { //���콺 �̺�Ʈ ó�� 
			@Override
			public void mouseClicked(MouseEvent e) { //Ŭ�� �� -> ȭ�� ��ȯ 
				// TODO �ڵ� ������ �޼ҵ� ����
				super.mouseClicked(e);
				selectTrack(0); //���۹�ư ������ �� ��ȯ�Ǵ� ù Ʈ���� 0�ε���
				startButton.setVisible(false); //ȭ�鿡 �Ⱥ��̰�
				quitButton.setVisible(false);
				leftButton.setVisible(true); //ȭ�鿡 ���̰� 
				rightButton.setVisible(true);
				selectButton.setVisible(true);
				introImage = new ImageIcon(Main.class.getResource("/Images/1.jpg")).getImage();
				isSelectScreen = true;
			}
		});
		add(startButton);
		
		//quitButton
		quitButton.setBounds(390, 370, 200, 100); //��ġ
		quitButton.setBorderPainted(false);
		quitButton.setContentAreaFilled(false);
		quitButton.setFocusPainted(false);
		quitButton.addMouseListener(new MouseAdapter() { //���콺 �̺�Ʈ ó�� 
			
			@Override
			public void mouseClicked(MouseEvent e) { //Ŭ�� �� 
				// TODO �ڵ� ������ �޼ҵ� ����
				super.mouseClicked(e);
				System.exit(0); //���α׷� ���� 
			}
		});
		add(quitButton);
	
				//leftButton ó��
				leftButton.setVisible(false); //ó������ ���̸� �ȵ�
				leftButton.setBounds(100, 310, 60, 60); //��ġ
				leftButton.setBorderPainted(false);
				leftButton.setContentAreaFilled(false);
				leftButton.setFocusPainted(false);
				leftButton.addMouseListener(new MouseAdapter() { //���콺 �̺�Ʈ ó�� 
					
					@Override
					public void mouseClicked(MouseEvent e) { //Ŭ�� �� 
						// TODO �ڵ� ������ �޼ҵ� ����
						super.mouseClicked(e);
						selectLeft(); //���� �̵� 
					}
				});
				add(leftButton);
				
				//rightButton ó��
				rightButton.setVisible(false);
				rightButton.setBounds(660, 310, 60, 60); //��ġ
				rightButton.setBorderPainted(false);
				rightButton.setContentAreaFilled(false);
				rightButton.setFocusPainted(false);
				rightButton.addMouseListener(new MouseAdapter() { //���콺 �̺�Ʈ ó�� 
					
					@Override
					public void mouseClicked(MouseEvent e) { //Ŭ�� �� 
						// TODO �ڵ� ������ �޼ҵ� ����
						super.mouseClicked(e);
						selectRight(); //������ �̵� 
					}
				});
				add(rightButton);	
				
				//selectButton ó��
				selectButton.setVisible(false);
				selectButton.setBounds(280, 460, 250, 67); //��ġ
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
				
				//backButton ó��
				backButton.setVisible(false);
				backButton.setBounds(5, 50, 60, 60); //��ġ
				backButton.setBorderPainted(false);
				backButton.setContentAreaFilled(false);
				backButton.setFocusPainted(false);
				backButton.addMouseListener(new MouseAdapter() { //���콺 �̺�Ʈ ó�� 
					@Override
					public void mouseClicked(MouseEvent e) { //Ŭ�� �� 
						// TODO �ڵ� ������ �޼ҵ� ����
						super.mouseClicked(e);
						//�ǵ��ư��� �̺�Ʈ
						backMain();
					}
				});
				add(backButton);	
				
				
				//����â ���� �г� ����
				windowPanel = new JPanel();
				
				windowPanel.setBounds(0, 0, 800, 600);
				windowPanel.setBackground(Color.black);

				sPanel = new SudokuPanel(); //(������â) �г� ��ü ���� 
				
				buttonSelectionPanel = new JPanel(); //(��ưâ) �г� ��ü ���� 
				buttonSelectionPanel.setSize(500, 90); 
				buttonSelectionPanel.setBackground(Color.black);
				
				bottomPanel = new JPanel(); //����â �ϴ� �г� 
				bottomPanel.setSize(new Dimension(540, 50));
				bottomPanel.setBackground(Color.GREEN);
				
				label = new JLabel("��ŷ Ȯ�� -->");
				label.setForeground(Color.white);
				label.setFont(new Font("�������", Font.BOLD, 30));
				bottomPanel.add(label);
				
				RankButton = new JButton("GO ��ŷ");
				RankButton.addActionListener(new ActionListener() {
		            @Override
		            public void actionPerformed(ActionEvent e) {
		            	
		            	GameOver();//ȣ�� 
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
	
	//�׸� �׸��� �޼ҵ� 
	public void paint(Graphics g) {
		screenImage = createImage(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		screenGraphic = screenImage.getGraphics();
		screenDraw(screenGraphic);
		g.drawImage(screenImage, 0, 0, null); 

	}
	//�̹��� �׸����
	public void screenDraw(Graphics g) {
		g.drawImage(introImage, 0, 0, null);
		
		if(isSelectScreen) { // ���� ����Screen T���� ��쿡 ���ؼ� (�̹��� ��ü)
			g.drawImage(selectedImage, 200, 100, null); //���� ����â(����)
		}
		paintComponents(g); //addó���� ������Ʈ �׷��ֱ�
		this.repaint();
	}
	
	
	//���� level ���� �̹��� ���� �޼ҵ� 
	public void selectTrack(int nowSelected) { 
		//���Ӽ����̹��� (track���� ������)
		selectedImage = new ImageIcon(Main.class.getResource("/Images/" + trackList.get(nowSelected).getSelectGameImage())).getImage();
	}
	public void selectLeft() { //left ���ý�
		if(nowSelected == 0) { //���� ���� ���� ù��° Ʈ���̸�
			nowSelected = trackList.size() - 1; //������ �̵� 
		}
		else nowSelected--;
		selectTrack(nowSelected);
	}
	public void selectRight() { //right ���ý�
		if(nowSelected == trackList.size()-1) { //���� ���� ���� �� Ʈ���̸�
			nowSelected = 0; //0�ε����� �̵� 
		}
		else nowSelected++;
		selectTrack(nowSelected);
	}
	
	//�޼ҵ�(â rebuild)
	public void rebuildInterface(SudokuPuzzleType puzzleType, int fontSize) {
		SudokuPuzzle generatedPuzzle = new SudokuGenerator().generateRandomSudoku(puzzleType);
		sPanel.newSudokuPuzzle(generatedPuzzle);
		sPanel.setFontSize(fontSize);
		
		buttonSelectionPanel.removeAll(); //���� ��ư�г� removeAll ó�� 
		
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
	
	public void gameStart(int nowSelected) { //���ӽ���ȭ������ ����
		isSelectScreen = false; 
		
		leftButton.setVisible(false);
		rightButton.setVisible(false);
		selectButton.setVisible(false);
		backButton.setVisible(true); //���̰�
		
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
	
	public void backMain() { //���ư��� ó�� �Լ�
		isSelectScreen = true;
		buttonSelectionPanel.setVisible(false);
		sPanel.setVisible(false);
		windowPanel.setVisible(false);
		introImage = new ImageIcon(Main.class.getResource("/Images/1.jpg")).getImage(); //ȭ�� ��ȯ
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

//--> ����� Track Ŭ���� �Ű��� 

class Track { //level ���� �̹������� TrackŬ���� 
	
	//�ʵ�
	private String selectGameImage;
	
	//������
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
