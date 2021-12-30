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
    	setTitle("��ŷ ����");
        setLayout(null);
        setBounds(369, 100, 600, 600);

        printRanking();

        setVisible(true);
    }

    private void printRanking() {
        printRankingTitle();
        printActualRanking();
    }

    private void printRankingTitle() { //��ŷ Ÿ��Ʋ ����Ʈ 
        labelRank = new JLabel("����");
        labelRank.setBounds(X_RANK, Y, WIDTH_LABEL, HEIGHT_LABEL);
        add(labelRank);

        labelName = new JLabel("�̸�");
        labelName.setBounds(X_NAME, Y, WIDTH_LABEL, HEIGHT_LABEL);
        add(labelName);

        labelScore = new JLabel("����");
        labelScore.setBounds(X_SCORE, Y, WIDTH_LABEL, HEIGHT_LABEL);
        add(labelScore);

    }

    private void printActualRanking() { //������ ��ŷ ����Ʈ
        try (
                FileReader fr = new FileReader("test.txt"); //���ڽ�Ʈ��(���� ����)
                BufferedReader br = new BufferedReader(fr);//�������� ���۽�Ʈ�� (�б�) 
        ) {
            String readLine = null;
            while ((readLine = br.readLine()) != null) { //�ٴ����� �б� 
                ls.add(readLine);
            }
        } catch (IOException e) {
        	
        }
        
        ArrayList<Integer> lsScore = new ArrayList<Integer>(); // ������ü�� ArrayList<>
        for (int i = 1; i <= ls.size() / 3; i++) {
            lsScore.add(Integer.valueOf((String) ls.get(3 * i - 2))); //3*i-2 = ls�� score��ġ 
        }
        Collections.sort(lsScore); //(�������� ����)
       
        
        ArrayList<String> lsScore2 = new ArrayList<String>(); // String ��ü�� ArrayList<> 
        for (int i = 0; i < lsScore.size(); i++) { //intŸ���� Score�� �ٽ� String����
            lsScore2.add(String.valueOf(lsScore.get(i)));
        }
        
        int rank = 0;//���� =0 �ʱ�ȭ
        for (int i = lsScore2.size(); i >= 1; i--) {  //�������� ���ĵ� list�� �Ųٷ� �������� 
            int x = ls.indexOf(lsScore2.get(i - 1)); //ls�� �ε����� 1, 4, 7, 10.. ã�� (�� ���� �� �̿�) 
            rank++;
            
            genName(x - 1, rank); 
            genScore(x, rank); 
            genRank(Integer.toString(rank), rank); 
        }

    }

    private void genRank(String number, int rank) {
        labelRank = new JLabel(number);
        labelRank.setBounds(X_RANK, Y + 25 * rank, WIDTH_LABEL, HEIGHT_LABEL); //rank �� �� ��ȣ ������ 
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
