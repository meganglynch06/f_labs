import java.util.Random;

public class lab_1
{
	public static void main(String[] args)
	{
		int aaa = 0;
		int a = 0; 
		int c = 0;
		int g = 0;
		int t = 0;
		
		for(int q=0;q<1000;q++)
		{
		String s = "";
		
		for(int m=0;m<3;m++)	
		{
		Random random = new Random();
		
		float x  = random.nextFloat();
		
		if (x > 0 && x < 0.25){s = s + "A";}if (x > 0.25 && x < 0.50){s = s + "C";}if (x > 0.50 && x < 0.75){s = s + "G";}if (x > 0.75 && x < 1.0){s = s + "T";};
		
		if (s.equals("A"))a = a + 1;if (s.equals("C"))c = c + 1;if (s.equals("G"))g = g + 1;if (s.equals("T"))t = t + 1;
		}
		
		if(s.equals("AAA"))
			aaa = aaa + 1;
		// I would expect to this this 3mer by chance about 0.015 percent of the time per the probability of 0.25*0.25*0.25. Trials have given me results of 11/1000, 12/1000, and even 20/1000 which are all close to the expected frequency
		
		System.out.println(s);
		
		}
		System.out.println("\nNumber of AAA codons: " + aaa+ "\n");System.out.println("Freq A: " + a);System.out.println("Freq C: " + c);System.out.println("Freq G: " + g);System.out.println("Freq T: " + t);
	}
}
