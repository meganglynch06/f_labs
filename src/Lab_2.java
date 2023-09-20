import java.util.Random;

public class Lab_2
{
	public static void main(String[] args)
	{	
		long start = System.currentTimeMillis();
		long end = start+30000;
		int score = 0;
		boolean m = true;
		
	while(System.currentTimeMillis()<end && m==true) {
			
		long currentTime = System.currentTimeMillis();
		double elapsedTime = (currentTime - start)/1000.0;
		
		String[] full_name = {"alanine","arginine", "asparagine","aspartic acid", "cysteine","glutamine", "glutamic acid","glycine" ,"histidine","isoleucine","leucine","lysine","methionine","phenylalanine","proline","serine","threonine","tryptophan","tyrosine","valine"};
		String[] short_name = {"A","R", "N", "D", "C", "Q", "E", "G",  "H", "I", "L", "K", "M", "F", "P", "S", "T", "W", "Y", "V"};
		
		Random name = new Random();
		int index = name.nextInt(full_name.length);
		System.out.println(full_name[index]);
		
		String input = System.console().readLine().toUpperCase();
		
		for(int i = 0; i<short_name.length; i++) {
			if(short_name[i].equals(input)) {
				if(short_name[i]==full_name[index]) {
					index = i;
				}
			if(index==i) {
				score++;
				System.out.println("Correct!\nScore: " + score + " ; Time: " + elapsedTime + " out of 30 seconds\n");

			} else{
				m = false;
				System.out.println("Incorrect!\nScore: " + score + " ; Time: " + elapsedTime + " out of 30 seconds\n");
				
			}
			}
		}
		}	
	}
}

