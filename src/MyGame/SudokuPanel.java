package MyGame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class SudokuPanel extends JPanel { //스도쿠 패널 
	//필드
	private SudokuPuzzle puzzle;
	private int currentlySelectedCol;
	private int currentlySelectedRow;
	private int usedWidth;
	private int usedHeight;
	private int fontSize;
	private int score = 10000;
	int KeepScore;

	private ImageIcon greatImage = new ImageIcon(Main.class.getResource("/Images/great.png")); //great이미지 
	private ImageIcon missImage = new ImageIcon(Main.class.getResource("/Images/miss.png"));//miss이미지
	
	GreatImageObj greatImageObj;
	MissImageObj missImageObj;
	
	private boolean inputValid = false; //버튼 입력값의 정답 여부 
	
	private boolean chooseNumber = false; //숫자번호 선택 여부
	
	
	//생성자(1)
	public SudokuPanel() {
		this.setPreferredSize(new Dimension(540, 450)); //540, 450
		this.addMouseListener(new SudokuPanelMouseAdapter()); //패널에 이벤트 연결 
		this.puzzle = new SudokuGenerator().generateRandomSudoku(SudokuPuzzleType.NINEBYNINE); //랜덤 생성 퍼즐 객체
		currentlySelectedCol = -1;
		currentlySelectedRow = -1;
		usedWidth = 0;
		usedHeight = 0;
		fontSize = 26;//폰트 초기값 26
		
		greatImageObj = new GreatImageObj(greatImage.getImage(), 90, 250, 360, 80); //great 이미지객체
		missImageObj = new MissImageObj(missImage.getImage(), 90, 250, 360, 80); // miss 이미지객체
		
		KeepScore = (int) (Math.random() * 10000000 +1);
		KeepScore = (int) Math.floor(KeepScore / 1000) * 100;
		
	}
	
	//생성자(2)
	public SudokuPanel(SudokuPuzzle puzzle) {
		this.setPreferredSize(new Dimension(540, 450));
		this.addMouseListener(new SudokuPanelMouseAdapter());
		this.puzzle = puzzle;
		currentlySelectedCol = -1;
		currentlySelectedRow = -1;
		usedWidth = 0;
		usedHeight = 0;
		fontSize = 26;
		
		greatImageObj = new GreatImageObj(greatImage.getImage(), 90, 250, 360, 80); //great 이미지객체
		missImageObj = new MissImageObj(missImage.getImage(), 90, 250, 360, 80); // miss 이미지객체
		
	}
	
	//메소드
	public void newSudokuPuzzle(SudokuPuzzle puzzle) {
		this.puzzle = puzzle;
	}
	
	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}


	//패널의 paintComponent() 재정의 (스도쿠 패널 그리기)
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		//큰사각형 그려주기용 
		g2d.setColor(Color.black); //색 지정 
		
		usedWidth = (this.getWidth()/puzzle.getNumColumns())*puzzle.getNumColumns();
		usedHeight = (this.getHeight()/puzzle.getNumRows())*puzzle.getNumRows();
		
		g2d.fillRect(0, 0, usedWidth,usedHeight); //큰 사각형 그려주기 (스도쿠 패널 사용영역)
		
		
		int slotWidth = this.getWidth()/puzzle.getNumColumns(); //너비 
		int slotHeight = this.getHeight()/puzzle.getNumRows(); //높이
		
		
		//가로줄 그려주기용 
		g2d.setColor(Color.MAGENTA); //색 지정
		for(int x = 0; x <= usedWidth; x+=slotWidth) { 
			if((x/slotWidth) % puzzle.getBoxWidth() == 0) { //box 라인 
				g2d.setStroke(new BasicStroke(5));
				g2d.drawLine(x, 0, x, usedHeight);
			}
			else {									//일반 라인 
				g2d.setStroke(new BasicStroke(1));
				g2d.drawLine(x, 0, x, usedHeight);
			}
		}
		
		//세로줄 그려주기용 
		g2d.setColor(Color.magenta);
		for(int y = 0;y <= usedHeight;y+=slotHeight) {
			if((y/slotHeight) % puzzle.getBoxHeight() == 0) { //box 라인 
				g2d.setStroke(new BasicStroke(5));
				g2d.drawLine(0, y, usedWidth, y);
			}
			else { 									//일반 라인
				g2d.setStroke(new BasicStroke(1));
				g2d.drawLine(0, y, usedWidth, y);
			}
		}
		
		Font f = new Font("나눔 고딕", Font.PLAIN, fontSize); //폰트 객체 생성 
		g2d.setFont(f);
		//정확한 슬롯 영역에 랜덤퍼즐값 drawString()
		FontRenderContext fContext = g2d.getFontRenderContext();
		for(int row=0;row < puzzle.getNumRows();row++) {
			for(int col=0;col < puzzle.getNumColumns();col++) {
				if(!puzzle.isSlotAvailable(row, col)) {
					int textWidth = (int) f.getStringBounds(puzzle.getValue(row, col), fContext).getWidth();
					int textHeight = (int) f.getStringBounds(puzzle.getValue(row, col), fContext).getHeight();
					g2d.drawString(puzzle.getValue(row, col), (col*slotWidth)+((slotWidth/2)-(textWidth/2)), (row*slotHeight)+((slotHeight/2)+(textHeight/2)));
				}
			}
		}
		
		// 점수 그리기
		g2d.setColor(Color.green);
		g2d.drawString("Score : " + String.valueOf(this.score), 170, 450); // 20211206
		
		//선택된 영역에 한해서 (해당 슬롯 영역 색상 변경)
		if(currentlySelectedCol != -1 && currentlySelectedRow != -1) {
			g2d.setColor(new Color(0.0f,0.0f,1.0f,0.3f)); //new Color(0.0f,0.0f,1.0f,0.3f)
			g2d.fillRect(currentlySelectedCol * slotWidth, currentlySelectedRow * slotHeight , slotWidth, slotHeight);
		}
		
		// Great, Miss 띄우기용 drawImage
		if(currentlySelectedCol != -1 && currentlySelectedRow != -1) {			
			if(chooseNumber) {//버튼 입력된 경우
				if(inputValid) { //유효한 입력값일 경우 -> great 이미지
					g.drawImage(greatImageObj.getImage(), greatImageObj.getGreatImageX(), greatImageObj.getGreatImageY(), greatImageObj.getGreatImageWidth(), greatImageObj.getGreatImageHeight(), null);

				} else { //유효하지 않은 입력값일 경우 -> miss 이미지
					g.drawImage(missImageObj.getImage(), missImageObj.getMissImageX(), missImageObj.getMissImageY(), missImageObj.getMissImageWidth(), missImageObj.getMissImageHeight(), null);
				}
			}
			
		}
	}

	//숫자용 패널 버튼 처리 이벤트 리스너 
	public class NumActionListener implements ActionListener { 
		@Override
		public void actionPerformed(ActionEvent e) { 
			 String buttonValue = ((JButton) e.getSource()).getText(); //누른 버튼 속 텍스트 값 얻어와서 
			 
			 System.out.println("buttonValue : " + buttonValue);
			 
			 if(currentlySelectedCol != -1 && currentlySelectedRow != -1) { //선택된 영역이 유효한 행,열 내부의 값일 경우에 한해서 
				inputValid = puzzle.makeMove(currentlySelectedRow, currentlySelectedCol, buttonValue, true); //선택된 영역 속에 버튼 값 넣고 (값존재)true 넣기, 20211114 수정
				if(inputValid) {
					score += 10500;
					
				} else {
					score -= 5000;
					
				}
				chooseNumber = true; // 버튼 클릭 경우 T처리 
				repaint(); //다시 repaint()
			}
		}
	}

	
	
	//스도쿠 퍼즐 패널 위 슬롯 영역 이벤트 리스너 
	private class SudokuPanelMouseAdapter extends MouseAdapter { 
		@Override
		public void mouseClicked(MouseEvent e) { //스도쿠 패널 속 선택된 패널영역 마우스 이벤트 처리 
			
			if(e.getButton() == MouseEvent.BUTTON1) { //마우스왼쪽 클릭 시 
				int slotWidth = usedWidth/puzzle.getNumColumns(); // 슬롯너비 =  너비/ 퍼즐 열 개수 
				int slotHeight = usedHeight/puzzle.getNumRows();  // 슬롯높이 =  높이/행
				currentlySelectedRow = e.getY() / slotHeight; //선택된 행 = e.Y값 / 슬롯 높이 
				currentlySelectedCol = e.getX() / slotWidth; //선택된 열 = e.X값 / 슬롯 너비
				
				chooseNumber = false; // 슬롯 클릭 시-> great, miss 뜨지 않도록 F처리
				
				e.getComponent().repaint(); //선택된 패널 영역의(한 슬롯 칸) 그려주기용 
			}
		}
	}
	
	
	
	
	// Great Image 생성 객체
	public class GreatImageObj {
		//필드 
		Image image; //Great이미지	
		//Great이미지 좌표필드 x, y, width, height
		private int greatImageX;
		private int greatImageY;
		private int greatImageWidth;
		private int greatImageHeight;
		
		//생성자
		public GreatImageObj(Image image, int x, int y, int width, int height) {
			this.image = image;
			this.greatImageX = x;
			this.greatImageY = y;
			this.greatImageWidth = width;
			this.greatImageHeight = height;
		}
		
		//getter, setter() 
		public Image getImage() {
			return image;
		}
		public void setImage(Image image) {
			this.image = image;
		}
		public int getGreatImageX() {
			return greatImageX;
		}
		public void setGreatImageX(int greatImageX) {
			this.greatImageX = greatImageX;
		}
		public int getGreatImageY() {
			return greatImageY;
		}
		public void setGreatImageY(int greatImageY) {
			this.greatImageY = greatImageY;
		}
		public int getGreatImageWidth() {
			return greatImageWidth;
		}
		public void setGreatImageWidth(int greatImageWidth) {
			this.greatImageWidth = greatImageWidth;
		}
		public int getGreatImageHeight() {
			return greatImageHeight;
		}
		public void setGreatImageHeight(int greatImageHeight) {
			this.greatImageHeight = greatImageHeight;
		}
	}
	
	//miss 이미지 생성 객체 
	public class MissImageObj {
		
		//필드 
		Image image; //miss 이미지 
		//miss 이미지 x, y, width, height 좌표값 필드
		private int missImageX;
		private int missImageY;
		private int missImageWidth;
		private int missImageHeight;		

		//생성자
		public MissImageObj(Image image, int x, int y, int width, int height) {
			this.image = image;
			this.missImageX = x;
			this.missImageY = y;
			this.missImageWidth = width;
			this.missImageHeight = height;
		}
		
		public Image getImage() {
			return image;
		}
		public void setImage(Image image) {
			this.image = image;
		}
		public int getMissImageX() {
			return missImageX;
		}
		public void setMissImageX(int missImageX) {
			this.missImageX = missImageX;
		}
		public int getMissImageY() {
			return missImageY;
		}
		public void setMissImageY(int missImageY) {
			this.missImageY = missImageY;
		}
		public int getMissImageWidth() {
			return missImageWidth;
		}
		public void setMissImageWidth(int missImageWidth) {
			this.missImageWidth = missImageWidth;
		}
		public int getMissImageHeight() {
			return missImageHeight;
		}
		public void setMissImageHeight(int missImageHeight) {
			this.missImageHeight = missImageHeight;
		}
	}
}

