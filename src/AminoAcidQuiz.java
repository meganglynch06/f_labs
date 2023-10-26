import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.util.Random;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;




public class AminoAcidQuiz extends JFrame

{	
	private static final long serialVersionUID = 379405992216115530L;
	private JFrame frame;
	private JPanel panel;
	private JLabel aminoAcid;
	private JTextField answerBox;
	private JButton startButton;
	private JButton answerButton;
	private JButton cancelButton;
	private JLabel totalScore;
	long start = System.currentTimeMillis();
	long end = start+30000;
	boolean m = true;
	private int score = 0;
	private int incorrect = 0;
	
	
	private String[] full_name = {"alanine","arginine", "asparagine","aspartic acid", "cysteine","glutamine", "glutamic acid","glycine" ,"histidine","isoleucine","leucine","lysine","methionine","phenylalanine","proline","serine","threonine","tryptophan","tyrosine","valine"};
	private String[] short_name = {"A","R", "N", "D", "C", "Q", "E", "G",  "H", "I", "L", "K", "M", "F", "P", "S", "T", "W", "Y", "V"};

	
	public AminoAcidQuiz()
	{
		frame = new JFrame("Amino Acid Quiz");
		frame.setLocationRelativeTo(null);
		frame.setSize(400,300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		
		panel = new JPanel();
		panel.setLayout(new GridLayout(6,1));
		
		
		aminoAcid = new JLabel();
		answerBox = new JTextField();
		startButton = new JButton("Start");
		answerButton = new JButton("Check Answer");
		totalScore = new JLabel();
		
		panel.add(startButton);
		
		frame.add(panel);
		frame.setVisible(true);
		
		startButton.addActionListener(new ActionListener() 
		{
            public void actionPerformed(ActionEvent e) 
            {
                startQuiz();
            }
        });
		
		answerButton.addActionListener(new ActionListener() 
		{
            public void actionPerformed(ActionEvent e) 
            {
                checkAnswer();
            }
        });
		
	}
	
	public void startQuiz()
	{	panel.remove(startButton);
		panel.add(aminoAcid);
		panel.add(answerBox);
	  panel.add(answerButton); 
	  panel.add(totalScore);


	    Timer timer = new Timer(30000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	long timeLeft = (end - System.currentTimeMillis()) / 1000;
                endQuiz();
            }
        });
      timer.setRepeats(false);
      timer.start();

	    Random name = new Random();
	    int index = name.nextInt(full_name.length);
	    aminoAcid.setText(full_name[index]);
	}
	
	private void checkAnswer() 
	{
        String input = answerBox.getText().trim();
        String correct = short_name[AAindex(aminoAcid.getText())];

        if (input.equalsIgnoreCase(correct)) 
        {
            score++;
        }
        else 
        {
        	incorrect++;
        }

        Random name = new Random();
        int index = name.nextInt(full_name.length);
        aminoAcid.setText(full_name[index]);
        answerBox.setText("");
        
        totalScore.setText("Correct: " + score + " Incorrect: " + incorrect);
    }
	
	private int AAindex(String AA)
	{
		for (int i = 0; i < full_name.length; i++) 
		{
            if (full_name[i].equalsIgnoreCase(AA)) 
            {
                return i;
            }
        }
        return -1;
	}
	
	private void endQuiz()
	{
        answerButton.setEnabled(false);
        totalScore.setText("Times Up! Total Correct: " + score + " Total Incorrect: " + incorrect);
    }
	
	public static void main(String[] args) {
		
		new AminoAcidQuiz();
		
	}
	
	
}
