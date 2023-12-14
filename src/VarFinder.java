import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;

public class VarFinder

{
		//encoutered some import issues so I incorporated the FastaSequence Parser methods into this class

    private String header;
    private String sequence;

    public VarFinder(String header, String sequence) //constructor - no threads
	{
        	this.header = header;
        	this.sequence = sequence;
    	}

    public synchronized static List<VarFinder> readFastaFile(String filepath) throws Exception
	{
        	List<VarFinder> HeaderAndSequences = new ArrayList<>();
        	BufferedReader reader = new BufferedReader(new FileReader(filepath));

	        String line;
        	StringBuilder sequenceBuilder = new StringBuilder();
        	String header = null;

        	while ((line = reader.readLine()) != null)
		{
           		 if (line.startsWith(">"))
			{
               		 	if (header != null)
				{
                   			 VarFinder fastaSequence = new VarFinder(header, sequenceBuilder.toString());
                   			 HeaderAndSequences.add(fastaSequence);
        				 sequenceBuilder = new StringBuilder();
     				}
              			header = line.substring(1);
           		}
			 else
			{
               			sequenceBuilder.append(line);
            		}
        	}

        	if (header != null && sequenceBuilder.length() > 0)
		{
           		 VarFinder fastaSequence = new VarFinder(header, sequenceBuilder.toString());
           		 HeaderAndSequences.add(fastaSequence);
        	}

		reader.close();
       		return HeaderAndSequences;
	}
	public String getHeader()
	{
        	return header;
	}

	public String getSequence()
	{
        	return sequence;
    	}

    public synchronized static void STRFinder(List<VarFinder> sequences, int readingFrame, String outputFile)
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile)))
	{
        	writer.write("Sequence Header, Start Position, Repeated Sequence, Repeat Count, Repeat Length\n");

            	for (VarFinder sequence : sequences)
		{
               		String singleSequence = sequence.getSequence();

                	if (readingFrame >= singleSequence.length())
			{
                    		System.out.println("Reading frame exceeds sequence length for sequence: " + sequence.getHeader());
                   		 continue; // skip too short
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

                        if (x - i >= len * 2)
			{
                            writer.write(sequence.getHeader() + "," + i + "," + sub + "," + (x - i) / len + "," + len + "\n\n");
                        }
                    }
                }
            }

        }
	catch (IOException e)
	{
            e.printStackTrace();
        }
    }

    public static int getMinSeqLength(List<VarFinder> sequences)
	{
    		int minSeqLen = Integer.MAX_VALUE;

    		for (VarFinder sequence : sequences)
		{
        		int seqLen = sequence.getSequence().length();

        		if (seqLen < minSeqLen)
			{
           			 minSeqLen = seqLen;
        		}
    		}

    		return minSeqLen;
	}

   public synchronized static void SNPFinder(List<VarFinder> sequences, String outputFile)
   {
	try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile, true)))
	{
	        writer.write("Potential Single Nucleotide Polymorphisms (SNPs)\n\n");

	        int sequenceCount = sequences.size();
		int minSequenceLength = getMinSeqLength(sequences);

	        for (int i = 0; i < minSequenceLength; i++)
		{
	            char[] nucleotides = new char[4];
	            int[] counts = new int[4];

	            for (VarFinder sequence : sequences)
		    {
	                char currentNucleotide = sequence.getSequence().charAt(i);
	                int index = "ACGT".indexOf(currentNucleotide);

	                nucleotides[index] = currentNucleotide;
	                counts[index]++;
	            }

	            for (int j = 0; j < 4; j++)
		    {

	                if (counts[j] >= sequenceCount * 0.99 && counts[j] <= sequenceCount * 1.01)
									{
	                 writer.write("Position: " + i + ", Nucleotide: " + nucleotides[j] + "\n");
	            }
	        }
	    }

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
		System.out.println("Input File Pathway: ");
		Scanner scanner = new Scanner(System.in);
		String filePath = scanner.nextLine();
		List<VarFinder> HeaderAndSequences = VarFinder.readFastaFile(filePath);

		System.out.println("Enter the Reading Frame (0, 1, or 2): ");
		int userReadingFrame = scanner.nextInt();
		scanner.nextLine();
		System.out.println("Enter Desired Output File Name: ");
		String outputFile = scanner.nextLine();

		VarFinder.STRFinder(HeaderAndSequences, userReadingFrame, outputFile);
		VarFinder.SNPFinder(HeaderAndSequences, outputFile);

		System.out.println("File Written");

		scanner.close();
	}
	catch (Exception e)
	{
		e.printStackTrace();
	}
   }
}

