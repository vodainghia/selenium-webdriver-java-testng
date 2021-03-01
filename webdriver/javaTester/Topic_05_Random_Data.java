package javaTester;

import java.util.Random;

public class Topic_05_Random_Data {

	public static void main(String[] args) {
		//Java class
		Random rand = new Random();
		rand.nextInt(999999);
		System.out.println(rand.nextInt(999999));
		
		//Random email
		//format: prefix + random + postfix (webmail server: github/ gmail/...)
		System.out.println("auto" + rand.nextInt(999999) + "@gmail.com");
		System.out.println("auto" + rand.nextInt(999999) + "@gmail.com");
		System.out.println("auto" + rand.nextInt(999999) + "@gmail.com");
		System.out.println("auto" + rand.nextInt(999999) + "@gmail.com");
		
		
		//Java lib: faker/ jfairy/...
		
	}

}
