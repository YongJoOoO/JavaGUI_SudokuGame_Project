package MyGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Ranking extends JFrame {

    private final int X_RANK = 150;
    private final int X_NAME = 250;
    private final int X_SCORE = 350;
    private final int Y = 75;
    private final int WIDTH_LABEL = 100;
    private final int HEIGHT_LABEL = 50;

    private JLabel labelRank, labelName, labelScore ; 

    private ArrayList ls = new ArrayList();

    Ranking() {
    	setTitle("랭킹 순위");
        setLayout(null);
        setBounds(369, 100, 600, 600);

        printRanking();

        setVisible(true);
    }

    private void printRanking() {
        printRankingTitle();
        printActualRanking();
    }

    private void printRankingTitle() { //랭킹 타이틀 프린트 
        labelRank = new JLabel("순위");
        labelRank.setBounds(X_RANK, Y, WIDTH_LABEL, HEIGHT_LABEL);
        add(labelRank);

        labelName = new JLabel("이름");
        labelName.setBounds(X_NAME, Y, WIDTH_LABEL, HEIGHT_LABEL);
        add(labelName);

        labelScore = new JLabel("점수");
        labelScore.setBounds(X_SCORE, Y, WIDTH_LABEL, HEIGHT_LABEL);
        add(labelScore);

    }

    private void printActualRanking() { //실질적 랭킹 프린트
        try (
                FileReader fr = new FileReader("test.txt"); //문자스트림(파일 리더)
                BufferedReader br = new BufferedReader(fr);//문자전용 버퍼스트림 (읽기) 
        ) {
            String readLine = null;
            while ((readLine = br.readLine()) != null) { //줄단위로 읽기 
                ls.add(readLine);
            }
        } catch (IOException e) {
        	
        }
        
        ArrayList<Integer> lsScore = new ArrayList<Integer>(); // 정수객체로 ArrayList<>
        for (int i = 1; i <= ls.size() / 3; i++) {
            lsScore.add(Integer.valueOf((String) ls.get(3 * i - 2))); //3*i-2 = ls의 score위치 
        }
        Collections.sort(lsScore); //(오름차순 정렬)
       
        
        ArrayList<String> lsScore2 = new ArrayList<String>(); // String 객체로 ArrayList<> 
        for (int i = 0; i < lsScore.size(); i++) { //int타입인 Score를 다시 String으로
            lsScore2.add(String.valueOf(lsScore.get(i)));
        }
        
        int rank = 0;//순위 =0 초기화
        for (int i = lsScore2.size(); i >= 1; i--) {  //오름차순 정렬된 list를 거꾸로 내림차순 
            int x = ls.indexOf(lsScore2.get(i - 1)); //ls의 인덱스값 1, 4, 7, 10.. 찾기 (그 속의 값 이용) 
            rank++;
            
            genName(x - 1, rank); 
            genScore(x, rank); 
            genRank(Integer.toString(rank), rank); 
        }

    }

    private void genRank(String number, int rank) {
        labelRank = new JLabel(number);
        labelRank.setBounds(X_RANK, Y + 25 * rank, WIDTH_LABEL, HEIGHT_LABEL); //rank 는 행 번호 정보임 
        add(labelRank);
    }

    private void genName(int index, int rank) {
        labelName = new JLabel((String) ls.get(index));
        labelName.setBounds(X_NAME, Y + 25 * rank, WIDTH_LABEL, HEIGHT_LABEL);
        add(labelName);
    }

    private void genScore(int index, int rank) {
        labelScore = new JLabel((String) ls.get(index));
        labelScore.setBounds(X_SCORE, Y + 25 * rank, WIDTH_LABEL, HEIGHT_LABEL);
        add(labelScore);
    }

}
