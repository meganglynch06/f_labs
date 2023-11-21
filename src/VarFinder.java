import FinalProject.FastaSequence;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class VarFinder 
{
	// unfinished ideas: 
	// GUI -> CSV file (Excel Sheet?)
	// add way to shorten or clean up sequence header names
	// add multi-threading method in case of large files
	// add SNP method
	// command line / GUI report of file name and location?
	
    public static void STRFinder(List<FastaSequence> sequences, int readingFrame, String outputFile) 
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) 
        {
            writer.write("Sequence Header, Start Position, Repeated Sequence, Repeat Count, Repeat Length\n\n");

            for (FastaSequence sequence : sequences) 
            {
                String singleSequence = sequence.getSequence();
                
                if (readingFrame >= singleSequence.length()) 
                {
                    System.out.println("Reading frame exceeds sequence length for sequence: " + sequence.getHeader());
                    continue; //skip too short
                }

                for (int len = 2; len <= 6; len++) 
                {
                    for (int i = readingFrame; i <= singleSequence.length() - len; i += 3) 
                    {
                        String sub = singleSequence.substring(i, i + len);
                        int x = i + len;
                        while (x + len <= singleSequence.length() && singleSequence.substring(x, x + len).equals(sub)) 
                        {
                            x += len;
                        }
                        if (x - i >= len * 2) {
                            writer.write(sequence.getHeader() + "," + i + "," + sub + "," + (x - i) / len + "," + len + "\n");
                        }
                    }
                }
            }

            System.out.println("File Written");

        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) 
    {
        try 
        {
            System.out.println("Enter File Pathway: ");
            Scanner input = new Scanner(System.in);
            String filePath = input.nextLine();
            System.out.println("Enter the Reading Frame (0, 1, or 2): ");
            int userReadingFrame = input.nextInt();
            input.nextLine();
            System.out.println("Enter Desired Output File Name: ");
            String outputFile = input.nextLine();

            List<FastaSequence> sequences = FastaSequence.readFastaFile(filePath);

            
            VarFinder.STRFinder(sequences, userReadingFrame, outputFile);

            input.close();

        }
        catch (Exception e)
        {
            e.printStackTrace(); 
        }
    }
}
