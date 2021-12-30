package MyGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SudokuGenerator { 

	//���������� Ÿ�� ��ȯ �޼ҵ� 
	public SudokuPuzzle generateRandomSudoku(SudokuPuzzleType puzzleType) {
		SudokuPuzzle puzzle = new SudokuPuzzle(puzzleType.getRows(), puzzleType.getColumns(), puzzleType.getBoxWidth(), puzzleType.getBoxHeight(), puzzleType.getValidValues());
		SudokuPuzzle copy = new SudokuPuzzle(puzzle);//����ü copy�� �Űܹް� 
		
		Random random = new Random(); //���� ���� ���� ��ü
		
		//ArrayList<> ������� ���� ��ȿ�� ����Ʈ = �ش� ���� ��ü�� ��ȿ�� �ű�
		List<String> notUsedValidValues =  new ArrayList<String>(Arrays.asList(copy.getValidValues())); 
		
		//������������ = �ش� ����Ÿ���� �� ������ū ���� 
		for(int r = 0;r < copy.getNumRows();r++) { 
			int randomValue = random.nextInt(notUsedValidValues.size()); //��ȿ�� ���� ���� ������ ����
			copy.makeMove(r, 0, notUsedValidValues.get(randomValue), true); //�����г� ���� makeMove()
			notUsedValidValues.remove(randomValue); //�������� ���� ������ ���� notUsedValidValues[]���� removeó�� 
		}
		

		//���� �⺻ �ʱ� ���� ��� -> �������� ����Ͽ� ������ ���� ���
		backtrackSudokuSolver(0, 0, copy); //0�� 0������ ++�Ǿ� ���� ������ 
		
		//�ʱ� �������� keep �� ������ ����  = (��ü �� X ��)�� 0.2 ������ ���� 
		int numberOfValuesToKeep = (int)(0.2*(copy.getNumRows() * copy.getNumRows())); 
		//�ʱ� ���������� ���� ������ �׷��� ��,�� ���� �������� ����
		for(int i = 0;i < numberOfValuesToKeep;) {
			int randomRow = random.nextInt(puzzle.getNumRows()); //���� ��
			int randomColumn = random.nextInt(puzzle.getNumColumns()); //���� �� 
			
			if(puzzle.isSlotAvailable(randomRow, randomColumn)) { //�̿밡���� ���Կ� ���ؼ�
				puzzle.makeMove(randomRow, randomColumn, copy.getValue(randomRow, randomColumn), false); //�� �־��ְ� �ش� ���� �̿�Ұ�ó�� 
				i++;
			}
		}
		return puzzle;
	}
	
	//������ 
    private boolean backtrackSudokuSolver(int r,int c, SudokuPuzzle puzzle) { 
    	//���� ��ȿ���� ���� ������ ��� F���� 
		if(!puzzle.inRange(r,c)) {
			return false;
		}
		//�̿밡���� ���Կ� ���ؼ� 
		if(puzzle.isSlotAvailable(r, c)) { 

			//for����
			for(int i = 0;i < puzzle.getValidValues().length;i++) { //���� ��ü �� ��ȿ�� ���̸�ŭ ���鼭 
				
				//������ i�� ���� �� ��, ��, �ڽ��� ������ �� �ִ� ���� ��쿨 ���ؼ� 
				if(!puzzle.numInRow(r, puzzle.getValidValues()[i]) && !puzzle.numInCol(c,puzzle.getValidValues()[i]) && !puzzle.numInBox(r,c,puzzle.getValidValues()[i])) {
					
					//�ش� �� �־��ֱ� 
					puzzle.makeMove(r, c, puzzle.getValidValues()[i], true); 
					
					if(puzzle.isboardFull()) {  //���� �� ��� ���� full
						return true; //yes full
					}
					
					//go to next move ���� ���������� 
					if(r == puzzle.getNumRows() - 1) {
						if(backtrackSudokuSolver(0,c + 1,puzzle)) return true;
					} else {
						if(backtrackSudokuSolver(r + 1,c,puzzle)) return true;
					}
				}
			}
		}
		
		//�̿밡���� ������ �ƴ� ���
		else {
			if(r == puzzle.getNumRows() - 1) {
				return backtrackSudokuSolver(0, c + 1,puzzle); //���� ���� �̵� 
			} else {
				return backtrackSudokuSolver(r + 1,c,puzzle); //���� ������ �̵� 
			}
		}
		
		//������ X
		puzzle.makeSlotEmpty(r, c); //�ش� ���� ������ �����Է�
		
		//backtrack
		return false;
	}
}