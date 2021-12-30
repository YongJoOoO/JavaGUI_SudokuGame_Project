package MyGame;

public enum SudokuPuzzleType { //스도쿠게임 Type 열거타입 enum
	
	//게임 type 
	SIXBYSIX(6,6,3,2, new String[] {"1","2","3","4","5","6"}, "6 By 6 Game"),
	NINEBYNINE(9,9,3,3, new String[] {"1","2","3","4","5","6","7","8","9"}, "9 By 9 Game"),
	TWELVEBYTWELVE(12,12,4,3, new String[] {"1","2","3","4","5","6","7","8","9","10","11","12"}, "12 By 12 Game");
	
	//필드
	private final int rows; //행
	private final int columns; //열
	private final int boxWidth; //box너비
	private final int boxHeight; //box높이
	private final String [] validValues; //유효값[]
	private final String gameName; //게임이름 
	
	//생성자                  생성자 (행, 열, box너비, box높이, 유효값 [], 게임이름) 
	private SudokuPuzzleType(int rows,int columns,int boxWidth,int boxHeight, String [] validValues,String gameName) {
		this.rows = rows;
		this.columns = columns;
		this.boxWidth = boxWidth;
		this.boxHeight = boxHeight;
		this.validValues =validValues;
		this.gameName = gameName;
	}
	
	//Getter() 메소드 
	public int getRows() {
		return rows;
	}
	
	public int getColumns() {
		return columns;
	}
	
	public int getBoxWidth() {
		return boxWidth;
	}
	
	public int getBoxHeight() {
		return boxHeight;
	}
	
	public String [] getValidValues() {
		return validValues;
	}
	
	public String toString() {
		return gameName;
	}
}
