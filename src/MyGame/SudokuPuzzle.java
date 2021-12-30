package MyGame;

public class SudokuPuzzle {
	//필드
	protected String [][] board; //String [][]  
	protected boolean [][] mutable; //슬롯에 값 넣을 수 있으면 T, 이미 값이 존재하면 F 
	private final int ROWS;
	private final int COLUMNS;
	private final int BOXWIDTH;
	private final int BOXHEIGHT;
	private final String [] VALIDVALUES; //유효한 값 []
	
	//생성자(1)-Generator 목적 생성자
	public SudokuPuzzle(int rows,int columns,int boxWidth,int boxHeight,String [] validValues) {
		this.ROWS = rows;
		this.COLUMNS = columns;
		this.BOXWIDTH = boxWidth;
		this.BOXHEIGHT = boxHeight;
		this.VALIDVALUES = validValues;
		this.board = new String[ROWS][COLUMNS];
		this.mutable = new boolean[ROWS][COLUMNS];
		
		initializeBoard(); //초기 공백 처리 
		initializeMutableSlots(); //초기 T처리(값 넣을 수 있는 상태) 
	}
	//생성자(2) -Generated 된 퍼즐 받을 생성자
	public SudokuPuzzle(SudokuPuzzle puzzle) { //퍼즐 객체 받는 퍼즐생성자
		this.ROWS = puzzle.ROWS;
		this.COLUMNS = puzzle.COLUMNS;
		this.BOXWIDTH = puzzle.BOXWIDTH;
		this.BOXHEIGHT = puzzle.BOXHEIGHT;
		this.VALIDVALUES = puzzle.VALIDVALUES;
		this.board = new String[ROWS][COLUMNS]; 
		for(int r = 0;r < ROWS;r++) {  //받은 퍼즐객체 String 값 옮겨주고 
			for(int c = 0;c < COLUMNS;c++) {
				board[r][c] = puzzle.board[r][c];
			}
		}
		this.mutable = new boolean[ROWS][COLUMNS];
		for(int r = 0;r < ROWS;r++) { //받은 퍼즐객체 mutable T or F 값 옮겨주고 
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
	
	
	//메소드 
	
	//<--퍼즐 처리용 메소드 -->
	
	//move
	public boolean makeMove(int row,int col, String value, boolean isMutable) { // 2021114 수정
		//만약 현재유효값이 T이고 현재유효한움직임 T이고, 현재 슬롯테이블도 T인 경우 (즉, 다 잘 맞아떨어졌으면)
		if(this.isValidValue(value) && this.isValidMove(row,col,value) && this.isSlotMutable(row, col)) {
			this.board[row][col] = value; //현재의 보드[][] 에 값 넣어주고 
			this.mutable[row][col] = isMutable;
			return true;	// 2021114 수정
		}
		return false;		// 2021114 수정 
	}
	
	//not move
	public void makeSlotEmpty(int row,int col) { //공백 슬롯 만들기 (잘못된 입력값""처리용)
		this.board[row][col] = ""; 
		
	}
	
	
	//<--값 중복 여부 -->
	
	//유효한 행,열 내부인지 여부 
	public boolean inRange(int row,int col) { //범위 내부의 행, 열 값 여부 리턴 (0보다 크면서 정해진 규격 내부의 값인지)
		return row <= this.ROWS && col <= this.COLUMNS && row >= 0 && col >= 0;
	}
	//해당 열 속 유효값 중복 여부
	public boolean numInCol(int col,String value) { 
		if(col <= this.COLUMNS) {
			for(int row=0;row < this.ROWS;row++) {
				if(this.board[row][col].equals(value)) {
					return true; //중복O 
				}
			}
		}
		return false; //중복X
	}
	//해당 행 속 유효값 중복 여부 
	public boolean numInRow(int row,String value) { //행, 값 받아서 (해당 행의 값)범위 존재여부 리턴 
		if(row <= this.ROWS) {
			for(int col=0;col < this.COLUMNS;col++) { 
				if(this.board[row][col].equals(value)) {
					return true;  //중복O
				}
			}
		}
		return false; //중복X
	}
	
	//선택된 슬롯이 속한 박스 내부에 value 중복 여부 리턴 
	public boolean numInBox(int row,int col,String value) { 
		if(this.inRange(row, col)) { //유효한 영역 내부이면서  
			//선택한 슬롯의 행,열이 속한 Box의 행,열값 
			int boxRow = row / this.BOXHEIGHT;
			int boxCol = col / this.BOXWIDTH;  
			//속한 Box 속 중복 검사 시작 행,열 값 
			int startingRow = (boxRow*this.BOXHEIGHT);
			int startingCol = (boxCol*this.BOXWIDTH); 
			//검사용 for루프 
			for(int r = startingRow;r <= (startingRow+this.BOXHEIGHT)-1; r++) {
				for(int c = startingCol;c <= (startingCol+this.BOXWIDTH)-1; c++) {
					if(this.board[r][c].equals(value)) {
						return true; //중복O
					}
				}
			}
		}
		return false; //중복X
	}
	
	//<--is메소드 -->
	
	//move가능 여부 리턴 
	public boolean isValidMove(int row,int col,String value) {
		if(this.inRange(row,col)) { 		
			//열에 해당값 없고, 행에 해당값 없고, 박스에 해당값 없을 경우, 
			if(!this.numInCol(col,value) && !this.numInRow(row,value) && !this.numInBox(row,col,value)) {
				return true; //move Ok
			}
		}
		return false; //move NO
	}
	
	//이용가능한 슬롯여부 리턴 
	public boolean isSlotAvailable(int row,int col) {
		
		//해당 슬롯이 전체 영역에서 유효한 영역이면서, 공백 상태이면서, 값넣을 수 있는 슬롯인 경우에 한해서 T 리턴 
		 return (this.inRange(row,col) && this.board[row][col].equals("") && this.isSlotMutable(row, col));
	}
	
	public boolean isSlotMutable(int row,int col) {  //변수 여부 리턴
		return this.mutable[row][col];
	}
	
	private boolean isValidValue(String value) { //유효값 여부 리턴 
		for(String str : this.VALIDVALUES) {
			if(str.equals(value)) return true;  //유효한 값 
		}
		return false; //잘못된 값 
	}
	
	public boolean isboardFull() { //전체 보드 full 여부 리턴 
		for(int r = 0;r < this.ROWS;r++) {
			for(int c = 0;c < this.COLUMNS;c++) {
				if(this.board[r][c].equals("")) return false; //빈 상태 
			}
		}
		return true; //찬 상태 
	}
	
	
	//<--퍼즐 초기화용 메소드-->
	
	private void initializeBoard() {  //초기 보드 
		for(int row = 0;row < this.ROWS;row++) {
			for(int col = 0;col < this.COLUMNS;col++) {
				this.board[row][col] = ""; //공백 처리 
			}
		}
	}
	
	private void initializeMutableSlots() { //초기 슬롯
		for(int row = 0;row < this.ROWS;row++) {
			for(int col = 0;col < this.COLUMNS;col++) {
				this.mutable[row][col] = true;  //사용가능 T
			}
		}
	}
}