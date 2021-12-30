package MyGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SudokuGenerator { 

	//스도쿠퍼즐 타입 반환 메소드 
	public SudokuPuzzle generateRandomSudoku(SudokuPuzzleType puzzleType) {
		SudokuPuzzle puzzle = new SudokuPuzzle(puzzleType.getRows(), puzzleType.getColumns(), puzzleType.getBoxWidth(), puzzleType.getBoxHeight(), puzzleType.getValidValues());
		SudokuPuzzle copy = new SudokuPuzzle(puzzle);//퍼즐객체 copy로 옮겨받고 
		
		Random random = new Random(); //랜덤 난수 생성 객체
		
		//ArrayList<> 사용하지 않은 유효값 리스트 = 해당 퍼즐 객체의 유효값 옮김
		List<String> notUsedValidValues =  new ArrayList<String>(Arrays.asList(copy.getValidValues())); 
		
		//난수생성개수 = 해당 퍼즐타입의 행 개수만큰 생성 
		for(int r = 0;r < copy.getNumRows();r++) { 
			int randomValue = random.nextInt(notUsedValidValues.size()); //유효값 범위 내의 랜덤값 받음
			copy.makeMove(r, 0, notUsedValidValues.get(randomValue), true); //퍼즐패널 위에 makeMove()
			notUsedValidValues.remove(randomValue); //랜덤으로 퍼즐 생성한 값은 notUsedValidValues[]에서 remove처리 
		}
		

		//랜덤 기본 초기 퍼즐 기반 -> 역추적을 사용하여 스도쿠 퍼즐 출기
		backtrackSudokuSolver(0, 0, copy); //0행 0열부터 ++되어 퍼즐 역추적 
		
		//초기 랜덤퍼즐에 keep 할 변수의 개수  = (전체 행 X 열)의 0.2 비율로 고정 
		int numberOfValuesToKeep = (int)(0.2*(copy.getNumRows() * copy.getNumRows())); 
		//초기 랜덤난수가 퍼즐 위에서 그려질 행,열 값도 랜덤으로 받음
		for(int i = 0;i < numberOfValuesToKeep;) {
			int randomRow = random.nextInt(puzzle.getNumRows()); //랜덤 행
			int randomColumn = random.nextInt(puzzle.getNumColumns()); //랜덤 열 
			
			if(puzzle.isSlotAvailable(randomRow, randomColumn)) { //이용가능한 슬롯에 한해서
				puzzle.makeMove(randomRow, randomColumn, copy.getValue(randomRow, randomColumn), false); //값 넣어주고 해당 슬롯 이용불가처리 
				i++;
			}
		}
		return puzzle;
	}
	
	//역추적 
    private boolean backtrackSudokuSolver(int r,int c, SudokuPuzzle puzzle) { 
    	//만약 유효하지 않은 영역일 경우 F리턴 
		if(!puzzle.inRange(r,c)) {
			return false;
		}
		//이용가능한 슬롯에 한해서 
		if(puzzle.isSlotAvailable(r, c)) { 

			//for루프
			for(int i = 0;i < puzzle.getValidValues().length;i++) { //퍼즐 객체 속 유효값 길이만큼 돌면서 
				
				//현재의 i의 값이 각 행, 열, 박스에 존재할 수 있는 값인 경우엥 한해서 
				if(!puzzle.numInRow(r, puzzle.getValidValues()[i]) && !puzzle.numInCol(c,puzzle.getValidValues()[i]) && !puzzle.numInBox(r,c,puzzle.getValidValues()[i])) {
					
					//해당 값 넣어주기 
					puzzle.makeMove(r, c, puzzle.getValidValues()[i], true); 
					
					if(puzzle.isboardFull()) {  //보드 속 모든 슬롯 full
						return true; //yes full
					}
					
					//go to next move 다음 움직임으로 
					if(r == puzzle.getNumRows() - 1) {
						if(backtrackSudokuSolver(0,c + 1,puzzle)) return true;
					} else {
						if(backtrackSudokuSolver(r + 1,c,puzzle)) return true;
					}
				}
			}
		}
		
		//이용가능한 슬롯이 아닐 경우
		else {
			if(r == puzzle.getNumRows() - 1) {
				return backtrackSudokuSolver(0, c + 1,puzzle); //다음 열로 이동 
			} else {
				return backtrackSudokuSolver(r + 1,c,puzzle); //다음 행으로 이동 
			}
		}
		
		//움직임 X
		puzzle.makeSlotEmpty(r, c); //해당 퍼즐 슬롯은 공백입력
		
		//backtrack
		return false;
	}
}