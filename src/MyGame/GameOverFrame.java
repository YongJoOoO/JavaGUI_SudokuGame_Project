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
    	setTitle("�÷��̾� �̸� �Է�");
    	setLayout(new FlowLayout());
    	setSize(400, 100);
    	setLocation(470, 300);
    	
    	nameLabel = new JLabel();
    	nameLabel.setText("�̸��Է�");
    	
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
        namePlayer = tf.getText(); //text�� ���� �÷��̾� �̸�
        try (
                FileWriter fw = new FileWriter("test.txt", true); //���Ͼ��� (���ڽ�Ʈ��) 
                BufferedWriter bw = new BufferedWriter(fw); //�������� ���۽�Ʈ��(����)
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