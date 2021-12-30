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

public class SudokuPanel extends JPanel { //������ �г� 
	//�ʵ�
	private SudokuPuzzle puzzle;
	private int currentlySelectedCol;
	private int currentlySelectedRow;
	private int usedWidth;
	private int usedHeight;
	private int fontSize;
	private int score = 10000;
	int KeepScore;

	private ImageIcon greatImage = new ImageIcon(Main.class.getResource("/Images/great.png")); //great�̹��� 
	private ImageIcon missImage = new ImageIcon(Main.class.getResource("/Images/miss.png"));//miss�̹���
	
	GreatImageObj greatImageObj;
	MissImageObj missImageObj;
	
	private boolean inputValid = false; //��ư �Է°��� ���� ���� 
	
	private boolean chooseNumber = false; //���ڹ�ȣ ���� ����
	
	
	//������(1)
	public SudokuPanel() {
		this.setPreferredSize(new Dimension(540, 450)); //540, 450
		this.addMouseListener(new SudokuPanelMouseAdapter()); //�гο� �̺�Ʈ ���� 
		this.puzzle = new SudokuGenerator().generateRandomSudoku(SudokuPuzzleType.NINEBYNINE); //���� ���� ���� ��ü
		currentlySelectedCol = -1;
		currentlySelectedRow = -1;
		usedWidth = 0;
		usedHeight = 0;
		fontSize = 26;//��Ʈ �ʱⰪ 26
		
		greatImageObj = new GreatImageObj(greatImage.getImage(), 90, 250, 360, 80); //great �̹�����ü
		missImageObj = new MissImageObj(missImage.getImage(), 90, 250, 360, 80); // miss �̹�����ü
		
		KeepScore = (int) (Math.random() * 10000000 +1);
		KeepScore = (int) Math.floor(KeepScore / 1000) * 100;
		
	}
	
	//������(2)
	public SudokuPanel(SudokuPuzzle puzzle) {
		this.setPreferredSize(new Dimension(540, 450));
		this.addMouseListener(new SudokuPanelMouseAdapter());
		this.puzzle = puzzle;
		currentlySelectedCol = -1;
		currentlySelectedRow = -1;
		usedWidth = 0;
		usedHeight = 0;
		fontSize = 26;
		
		greatImageObj = new GreatImageObj(greatImage.getImage(), 90, 250, 360, 80); //great �̹�����ü
		missImageObj = new MissImageObj(missImage.getImage(), 90, 250, 360, 80); // miss �̹�����ü
		
	}
	
	//�޼ҵ�
	public void newSudokuPuzzle(SudokuPuzzle puzzle) {
		this.puzzle = puzzle;
	}
	
	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}


	//�г��� paintComponent() ������ (������ �г� �׸���)
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		//ū�簢�� �׷��ֱ�� 
		g2d.setColor(Color.black); //�� ���� 
		
		usedWidth = (this.getWidth()/puzzle.getNumColumns())*puzzle.getNumColumns();
		usedHeight = (this.getHeight()/puzzle.getNumRows())*puzzle.getNumRows();
		
		g2d.fillRect(0, 0, usedWidth,usedHeight); //ū �簢�� �׷��ֱ� (������ �г� ��뿵��)
		
		
		int slotWidth = this.getWidth()/puzzle.getNumColumns(); //�ʺ� 
		int slotHeight = this.getHeight()/puzzle.getNumRows(); //����
		
		
		//������ �׷��ֱ�� 
		g2d.setColor(Color.MAGENTA); //�� ����
		for(int x = 0; x <= usedWidth; x+=slotWidth) { 
			if((x/slotWidth) % puzzle.getBoxWidth() == 0) { //box ���� 
				g2d.setStroke(new BasicStroke(5));
				g2d.drawLine(x, 0, x, usedHeight);
			}
			else {									//�Ϲ� ���� 
				g2d.setStroke(new BasicStroke(1));
				g2d.drawLine(x, 0, x, usedHeight);
			}
		}
		
		//������ �׷��ֱ�� 
		g2d.setColor(Color.magenta);
		for(int y = 0;y <= usedHeight;y+=slotHeight) {
			if((y/slotHeight) % puzzle.getBoxHeight() == 0) { //box ���� 
				g2d.setStroke(new BasicStroke(5));
				g2d.drawLine(0, y, usedWidth, y);
			}
			else { 									//�Ϲ� ����
				g2d.setStroke(new BasicStroke(1));
				g2d.drawLine(0, y, usedWidth, y);
			}
		}
		
		Font f = new Font("���� ���", Font.PLAIN, fontSize); //��Ʈ ��ü ���� 
		g2d.setFont(f);
		//��Ȯ�� ���� ������ �������� drawString()
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
		
		// ���� �׸���
		g2d.setColor(Color.green);
		g2d.drawString("Score : " + String.valueOf(this.score), 170, 450); // 20211206
		
		//���õ� ������ ���ؼ� (�ش� ���� ���� ���� ����)
		if(currentlySelectedCol != -1 && currentlySelectedRow != -1) {
			g2d.setColor(new Color(0.0f,0.0f,1.0f,0.3f)); //new Color(0.0f,0.0f,1.0f,0.3f)
			g2d.fillRect(currentlySelectedCol * slotWidth, currentlySelectedRow * slotHeight , slotWidth, slotHeight);
		}
		
		// Great, Miss ����� drawImage
		if(currentlySelectedCol != -1 && currentlySelectedRow != -1) {			
			if(chooseNumber) {//��ư �Էµ� ���
				if(inputValid) { //��ȿ�� �Է°��� ��� -> great �̹���
					g.drawImage(greatImageObj.getImage(), greatImageObj.getGreatImageX(), greatImageObj.getGreatImageY(), greatImageObj.getGreatImageWidth(), greatImageObj.getGreatImageHeight(), null);

				} else { //��ȿ���� ���� �Է°��� ��� -> miss �̹���
					g.drawImage(missImageObj.getImage(), missImageObj.getMissImageX(), missImageObj.getMissImageY(), missImageObj.getMissImageWidth(), missImageObj.getMissImageHeight(), null);
				}
			}
			
		}
	}

	//���ڿ� �г� ��ư ó�� �̺�Ʈ ������ 
	public class NumActionListener implements ActionListener { 
		@Override
		public void actionPerformed(ActionEvent e) { 
			 String buttonValue = ((JButton) e.getSource()).getText(); //���� ��ư �� �ؽ�Ʈ �� ���ͼ� 
			 
			 System.out.println("buttonValue : " + buttonValue);
			 
			 if(currentlySelectedCol != -1 && currentlySelectedRow != -1) { //���õ� ������ ��ȿ�� ��,�� ������ ���� ��쿡 ���ؼ� 
				inputValid = puzzle.makeMove(currentlySelectedRow, currentlySelectedCol, buttonValue, true); //���õ� ���� �ӿ� ��ư �� �ְ� (������)true �ֱ�, 20211114 ����
				if(inputValid) {
					score += 10500;
					
				} else {
					score -= 5000;
					
				}
				chooseNumber = true; // ��ư Ŭ�� ��� Tó�� 
				repaint(); //�ٽ� repaint()
			}
		}
	}

	
	
	//������ ���� �г� �� ���� ���� �̺�Ʈ ������ 
	private class SudokuPanelMouseAdapter extends MouseAdapter { 
		@Override
		public void mouseClicked(MouseEvent e) { //������ �г� �� ���õ� �гο��� ���콺 �̺�Ʈ ó�� 
			
			if(e.getButton() == MouseEvent.BUTTON1) { //���콺���� Ŭ�� �� 
				int slotWidth = usedWidth/puzzle.getNumColumns(); // ���Գʺ� =  �ʺ�/ ���� �� ���� 
				int slotHeight = usedHeight/puzzle.getNumRows();  // ���Գ��� =  ����/��
				currentlySelectedRow = e.getY() / slotHeight; //���õ� �� = e.Y�� / ���� ���� 
				currentlySelectedCol = e.getX() / slotWidth; //���õ� �� = e.X�� / ���� �ʺ�
				
				chooseNumber = false; // ���� Ŭ�� ��-> great, miss ���� �ʵ��� Fó��
				
				e.getComponent().repaint(); //���õ� �г� ������(�� ���� ĭ) �׷��ֱ�� 
			}
		}
	}
	
	
	
	
	// Great Image ���� ��ü
	public class GreatImageObj {
		//�ʵ� 
		Image image; //Great�̹���	
		//Great�̹��� ��ǥ�ʵ� x, y, width, height
		private int greatImageX;
		private int greatImageY;
		private int greatImageWidth;
		private int greatImageHeight;
		
		//������
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
	
	//miss �̹��� ���� ��ü 
	public class MissImageObj {
		
		//�ʵ� 
		Image image; //miss �̹��� 
		//miss �̹��� x, y, width, height ��ǥ�� �ʵ�
		private int missImageX;
		private int missImageY;
		private int missImageWidth;
		private int missImageHeight;		

		//������
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

