package javaTester;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Topic_07_Loop_Statement {

	public static void main(String[] args) {
		//
		String[] studentName = {"Trường", "Tiến", "Thơm", "Thu"};
		
//		for(int i = 0; i < studentName.length; i++) {
//			System.out.println(studentName[i]);
//		}
		

		List<String> address = new ArrayList<String>();
		address.add("Trường");
		address.add("Tiến");
		address.add("Thơm");
		address.add("Thu");
		
//		for (Iterator iterator = address.iterator(); iterator.hasNext();) {
//			System.out.println(iterator.next());
//		}
		
		for (String add : address) {
			System.out.println(add);
		}
	}

}
