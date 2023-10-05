
import java.io.BufferedReader;
import java.io.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class FastaParser{
	
	public static List<FastaParser> readFastaFile(String filepath) throws Exception {
}
	List<FastaParser> headers = new ArrayList<>();
	List<FastaParser> sequences = new ArrayList<>();
	
	BufferedReader reader = new BufferedReader ( new FileReader ( "/Users/meganlynch/Desktop/test.fa"));
	String line;
	
	
	while ((line = reader.readLine()) != null) {
    	if (line.charAt(0) == '>') {
    		headers.add(line.substring(1)));
    	if (line.charAt(0) != '>') {
    		sequences.add(line);
	    	
    	}
    		
    	
    	}
    }
	
}

class FastaSequence{
	
public static void main(String[] args) throws Exception
{ List<FastaSequence> fastaList = FastaSequence.readFaastaFile("/Users/meganlynch/Desktop/test.fa");

	     for( FastaSequence fs : fastaList)
	     {
	         System.out.println(fs.getHeader());
	        // System.out.println(fs.getSequence());
	        // System.out.println(fs.getGCRatio());
	      }

	     //File myFile = new File("c:\\yourFilePathHere\\out.txt");

	     //writeTableSummary( fastaList,  myFile);

}

public String getHeader(){
		return header;
}

// these methods below here are unedited to compile with the fasta parser
public static void getSequence(String[] args) {
	try {
		
		BufferedReader reader = new BufferedReader ( new FileReader ( "/Users/meganlynch/Desktop/test.fa"));
		String line;
		
		while ((line = reader.readLine()) != null) {
	    	if (line.charAt(0) != '>') {
	    		System.out.println(line); 
	    	}
	    }
		reader.close();
		}
		catch (FileNotFoundException e){
		    System.out.println(e);
		}
		catch (IOException e){
		    System.out.println(e);
	
		}
	
}

public static void getGCRatio(String[] args) {
	try {
		
		BufferedReader reader = new BufferedReader ( new FileReader ( "/Users/meganlynch/Desktop/test.fa"));
		String line;
		
		while ((line = reader.readLine()) != null) {
	    	if (line.charAt(0) != '>') {
	    		char Gs = 'G';
	    		char Cs = 'C';
	    		int G = 0;
	    		int C = 0;
	    		float GC = 0;
	    		float ratio;
	    		for (int i = 0; i < line.length(); i++) {
	    			if(line.charAt(i) == Gs) {
	    				G++;
	    			}
	    			if(line.charAt(i) == Cs) {
	    				C++;
	    			}
	    		}
	    		GC = G + C;
	    		ratio = GC/line.length();
	    		System.out.println(ratio);
	    		}
		}reader.close();
		
	}
		
		catch (FileNotFoundException e){
		    System.out.println(e);
		}
		catch (IOException e){
		    System.out.println(e);
	
		}



	
	
}}
	
