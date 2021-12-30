package MyGame;

public class SudokuPuzzle {
	//�ʵ�
	protected String [][] board; //String [][]  
	protected boolean [][] mutable; //���Կ� �� ���� �� ������ T, �̹� ���� �����ϸ� F 
	private final int ROWS;
	private final int COLUMNS;
	private final int BOXWIDTH;
	private final int BOXHEIGHT;
	private final String [] VALIDVALUES; //��ȿ�� �� []
	
	//������(1)-Generator ���� ������
	public SudokuPuzzle(int rows,int columns,int boxWidth,int boxHeight,String [] validValues) {
		this.ROWS = rows;
		this.COLUMNS = columns;
		this.BOXWIDTH = boxWidth;
		this.BOXHEIGHT = boxHeight;
		this.VALIDVALUES = validValues;
		this.board = new String[ROWS][COLUMNS];
		this.mutable = new boolean[ROWS][COLUMNS];
		
		initializeBoard(); //�ʱ� ���� ó�� 
		initializeMutableSlots(); //�ʱ� Tó��(�� ���� �� �ִ� ����) 
	}
	//������(2) -Generated �� ���� ���� ������
	public SudokuPuzzle(SudokuPuzzle puzzle) { //���� ��ü �޴� ���������
		this.ROWS = puzzle.ROWS;
		this.COLUMNS = puzzle.COLUMNS;
		this.BOXWIDTH = puzzle.BOXWIDTH;
		this.BOXHEIGHT = puzzle.BOXHEIGHT;
		this.VALIDVALUES = puzzle.VALIDVALUES;
		this.board = new String[ROWS][COLUMNS]; 
		for(int r = 0;r < ROWS;r++) {  //���� ����ü String �� �Ű��ְ� 
			for(int c = 0;c < COLUMNS;c++) {
				board[r][c] = puzzle.board[r][c];
			}
		}
		this.mutable = new boolean[ROWS][COLUMNS];
		for(int r = 0;r < ROWS;r++) { //���� ����ü mutable T or F �� �Ű��ְ� 
			for(int c = 0;c < COLUMNS;c++) {
				this.mutable[r][c] = puzzle.mutable[r][c];
			}
		}
	}
	public int getNumRows() {
		return this.ROWS;
	}
	
	public int getNumColumns() {
		return this.COLUMNS;
	}
	
	public int getBoxWidth() {
		return this.BOXWIDTH;
	}
	
	public int getBoxHeight() {
		return this.BOXHEIGHT;
	}
	
	public String [] getValidValues() {
		return this.VALIDVALUES;
	}
	
	public String [][] getBoard() {
		return this.board;
	}
	
	public String getValue(int row,int col) {
		if(this.inRange(row,col)) { 
			return this.board[row][col];
		}
		return "";
	}
	
	
	//�޼ҵ� 
	
	//<--���� ó���� �޼ҵ� -->
	
	//move
	public boolean makeMove(int row,int col, String value, boolean isMutable) { // 2021114 ����
		//���� ������ȿ���� T�̰� ������ȿ�ѿ����� T�̰�, ���� �������̺� T�� ��� (��, �� �� �¾ƶ���������)
		if(this.isValidValue(value) && this.isValidMove(row,col,value) && this.isSlotMutable(row, col)) {
			this.board[row][col] = value; //������ ����[][] �� �� �־��ְ� 
			this.mutable[row][col] = isMutable;
			return true;	// 2021114 ����
		}
		return false;		// 2021114 ���� 
	}
	
	//not move
	public void makeSlotEmpty(int row,int col) { //���� ���� ����� (�߸��� �Է°�""ó����)
		this.board[row][col] = ""; 
		
	}
	
	
	//<--�� �ߺ� ���� -->
	
	//��ȿ�� ��,�� �������� ���� 
	public boolean inRange(int row,int col) { //���� ������ ��, �� �� ���� ���� (0���� ũ�鼭 ������ �԰� ������ ������)
		return row <= this.ROWS && col <= this.COLUMNS && row >= 0 && col >= 0;
	}
	//�ش� �� �� ��ȿ�� �ߺ� ����
	public boolean numInCol(int col,String value) { 
		if(col <= this.COLUMNS) {
			for(int row=0;row < this.ROWS;row++) {
				if(this.board[row][col].equals(value)) {
					return true; //�ߺ�O 
				}
			}
		}
		return false; //�ߺ�X
	}
	//�ش� �� �� ��ȿ�� �ߺ� ���� 
	public boolean numInRow(int row,String value) { //��, �� �޾Ƽ� (�ش� ���� ��)���� ���翩�� ���� 
		if(row <= this.ROWS) {
			for(int col=0;col < this.COLUMNS;col++) { 
				if(this.board[row][col].equals(value)) {
					return true;  //�ߺ�O
				}
			}
		}
		return false; //�ߺ�X
	}
	
	//���õ� ������ ���� �ڽ� ���ο� value �ߺ� ���� ���� 
	public boolean numInBox(int row,int col,String value) { 
		if(this.inRange(row, col)) { //��ȿ�� ���� �����̸鼭  
			//������ ������ ��,���� ���� Box�� ��,���� 
			int boxRow = row / this.BOXHEIGHT;
			int boxCol = col / this.BOXWIDTH;  
			//���� Box �� �ߺ� �˻� ���� ��,�� �� 
			int startingRow = (boxRow*this.BOXHEIGHT);
			int startingCol = (boxCol*this.BOXWIDTH); 
			//�˻�� for���� 
			for(int r = startingRow;r <= (startingRow+this.BOXHEIGHT)-1; r++) {
				for(int c = startingCol;c <= (startingCol+this.BOXWIDTH)-1; c++) {
					if(this.board[r][c].equals(value)) {
						return true; //�ߺ�O
					}
				}
			}
		}
		return false; //�ߺ�X
	}
	
	//<--is�޼ҵ� -->
	
	//move���� ���� ���� 
	public boolean isValidMove(int row,int col,String value) {
		if(this.inRange(row,col)) { 		
			//���� �ش簪 ����, �࿡ �ش簪 ����, �ڽ��� �ش簪 ���� ���, 
			if(!this.numInCol(col,value) && !this.numInRow(row,value) && !this.numInBox(row,col,value)) {
				return true; //move Ok
			}
		}
		return false; //move NO
	}
	
	//�̿밡���� ���Կ��� ���� 
	public boolean isSlotAvailable(int row,int col) {
		
		//�ش� ������ ��ü �������� ��ȿ�� �����̸鼭, ���� �����̸鼭, ������ �� �ִ� ������ ��쿡 ���ؼ� T ���� 
		 return (this.inRange(row,col) && this.board[row][col].equals("") && this.isSlotMutable(row, col));
	}
	
	public boolean isSlotMutable(int row,int col) {  //���� ���� ����
		return this.mutable[row][col];
	}
	
	private boolean isValidValue(String value) { //��ȿ�� ���� ���� 
		for(String str : this.VALIDVALUES) {
			if(str.equals(value)) return true;  //��ȿ�� �� 
		}
		return false; //�߸��� �� 
	}
	
	public boolean isboardFull() { //��ü ���� full ���� ���� 
		for(int r = 0;r < this.ROWS;r++) {
			for(int c = 0;c < this.COLUMNS;c++) {
				if(this.board[r][c].equals("")) return false; //�� ���� 
			}
		}
		return true; //�� ���� 
	}
	
	
	//<--���� �ʱ�ȭ�� �޼ҵ�-->
	
	private void initializeBoard() {  //�ʱ� ���� 
		for(int row = 0;row < this.ROWS;row++) {
			for(int col = 0;col < this.COLUMNS;col++) {
				this.board[row][col] = ""; //���� ó�� 
			}
		}
	}
	
	private void initializeMutableSlots() { //�ʱ� ����
		for(int row = 0;row < this.ROWS;row++) {
			for(int col = 0;col < this.COLUMNS;col++) {
				this.mutable[row][col] = true;  //��밡�� T
			}
		}
	}
}