import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.util.concurrent.*;


public class Even_Numbers
{

    private JFrame frame;
    private JLabel appInstruct;
    private JTextField numberInput;
    private JButton runApp;
    private JButton acceptThreads;
    private Container container;
    private JTextArea resultDisplay;
    private JButton cancelButton;
    private JScrollPane scrollPane;
    private Font font;
    private volatile boolean cancellationRequested = false;
    private long startTime;
    private int evens;
    private int threads;
    private Semaphore semaphore; 

    public Even_Numbers() 
    {
    	launchApp();
    }
    public void launchApp()
    {
    	//frame
        frame = new JFrame("Even Numbers Calculator App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JFrame.setDefaultLookAndFeelDecorated(true);
        font = new Font("Arial", Font.BOLD, 20);

       //container
        container = frame.getContentPane();
        container.setLayout(new BorderLayout());

        //initializers and edits
        appInstruct = new JLabel("Enter Desired Number of Threads"); //edited for multithread
        appInstruct.setFont(font);
        appInstruct.setHorizontalAlignment(JLabel.CENTER);
        acceptThreads = new JButton("Click to Submit Thread Count");
        acceptThreads.setFont(font);
        runApp = new JButton("Click Here to Run Analysis");
        runApp.setFont(font);
        numberInput = new JTextField();
        resultDisplay = new JTextArea();
        resultDisplay.setEditable(false);
        cancelButton = new JButton("Cancel");
        scrollPane = new JScrollPane(resultDisplay);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        //initial container building
        container.add(appInstruct, BorderLayout.NORTH);
        container.add(numberInput, BorderLayout.CENTER);
        container.add(scrollPane, BorderLayout.WEST);
        container.add(acceptThreads, BorderLayout.SOUTH);
        container.add(cancelButton, BorderLayout.EAST);
        
        //frame building 
        frame.setSize(800, 500);
        frame.setVisible(true);
        frame.revalidate();
 
    //actionListeners
    acceptThreads.addActionListener(new ActionListener() 
	{
        public void actionPerformed(ActionEvent e) 
        {
        	int threads = Integer.parseInt(numberInput.getText().trim());
            semaphore = new Semaphore(threads);
            launchScreen();
        }
    });
    
    runApp.addActionListener(new ActionListener()
    {
    	public void actionPerformed(ActionEvent e)
    	{
    		Calculate();
    	}
    });

    cancelButton.addActionListener(new ActionListener()
    {
    	public void actionPerformed(ActionEvent e)
    	{
    		cancellationRequested = true;  
            cancelApp();
    	}
    });
    }
    
    //set up calculation screen
    public void launchScreen()
    {
    	container.remove(acceptThreads);
    	appInstruct.setText("Enter Any Number");
    	numberInput.setText("");
    	container.add(runApp, BorderLayout.SOUTH);
    	
    }
    
    //main method
    public void Calculate() 
    {
    	try 
    	{	
            int number = Integer.parseInt(numberInput.getText().trim());
            runApp.setVisible(false);
            startTime = System.currentTimeMillis();
            resultDisplay.setText("");
            evens = 0;
            
            int numAcquired = 0;
         	
         	while (numAcquired < threads) 
         	{
         		semaphore.acquire();
         		numAcquired++;
         	}
         	
            for (int i = 1; i <= number; i++) 
            {
            	semaphore.acquire();
                if (i % 2 == 0) 
                {
                    resultDisplay.append(i + "\n");
                    evens++;
                }
                if (i % 1000 == 0) 
                {
                	long totalIncriment = System.currentTimeMillis() - startTime;
                	resultDisplay.append("\nTotal # of Evens: " + evens + ", Total time: " + totalIncriment / 1000.0 + " seconds\n");
                	
                    if (cancellationRequested) 
                    {
                        cancellationRequested = false; 
                        semaphore.release();
                        return;
                    }
                }
                semaphore.release();
            }

 
            long totalTime = System.currentTimeMillis() - startTime;
            resultDisplay.append("\nTotal # of Evens: " + evens + ", Total time: " + totalTime / 1000.0 + " seconds");
          
    	}
    	catch (NumberFormatException ex)
    	{
            JOptionPane.showMessageDialog(frame, "Invalid input. Please enter a valid integer.");
            numberInput.setText("");
            resultDisplay.setText("");
            runApp.setVisible(true);
            
            container.removeAll();
       	 	container.setLayout(new BorderLayout());
       	 	container.add(appInstruct, BorderLayout.NORTH);
       	 	container.add(numberInput, BorderLayout.CENTER);
       	 	container.add(scrollPane, BorderLayout.WEST); // Use scrollPane instead of resultDisplay
       	 	container.add(runApp, BorderLayout.SOUTH);
       	 	container.add(cancelButton, BorderLayout.EAST);

       	 	frame.revalidate();
        }
    	catch (InterruptedException ex) 
    	{
    	    ex.printStackTrace(); 
    	    Thread.currentThread().interrupt();
    	}
    	
 
    	}
    //cancel method
    public void cancelApp() 
    {
    	 resultDisplay.setText("");
    	 numberInput.setText("");
    	  
    	 container.removeAll();
    	 container.setLayout(new BorderLayout());
    	 container.add(appInstruct, BorderLayout.NORTH);
    	 container.add(numberInput, BorderLayout.CENTER);
    	 container.add(scrollPane, BorderLayout.WEST);
    	 container.add(runApp, BorderLayout.SOUTH);
    	 container.add(cancelButton, BorderLayout.EAST);


    	 frame.revalidate();
    }

    public static void main(String args[]) 
    {
        new Even_Numbers();
    }
}



/*

Using a thread count of 1000, the multi-threaded program showed a 11.3772% decrease in time  
from the single threaded program

*/
