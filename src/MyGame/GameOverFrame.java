package MyGame;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

class GameOverFrame extends JFrame implements ActionListener {

	private JLabel nameLabel;
    private JButton bt_OK;
    private JTextField tf;

    private String namePlayer;
    
    int SCORE = new SudokuPanel().KeepScore;
    
    GameOverFrame() {
    	setTitle("플레이어 이름 입력");
    	setLayout(new FlowLayout());
    	setSize(400, 100);
    	setLocation(470, 300);
    	
    	nameLabel = new JLabel();
    	nameLabel.setText("이름입력");
    	
    	tf = new JTextField(20);
    	tf.setSize(getPreferredSize());
    	tf.addActionListener(this);
    	
    	bt_OK = new JButton("OK");
    	bt_OK.setSize(getPreferredSize());
    	bt_OK.addActionListener(this);
    	
    	add(nameLabel);
    	add(tf);
    	add(bt_OK);
        setVisible(true);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        namePlayer = tf.getText(); //text로 받은 플레이어 이름
        try (
                FileWriter fw = new FileWriter("test.txt", true); //파일쓰기 (문자스트림) 
                BufferedWriter bw = new BufferedWriter(fw); //문자전용 버퍼스트림(쓰기)
        ) {
            bw.write(namePlayer);
            bw.newLine();
            bw.write(Integer.toString(SCORE));
            bw.newLine();
            bw.write(Integer.toString(SCORE));//_REPAINT
            bw.newLine();
            bw.flush();
        } catch (IOException ie) {
            System.out.println(ie);
        }
        
        new Ranking();
    }

}