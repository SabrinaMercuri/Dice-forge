package lanceur;

import java.util.ArrayList;

public class Test2 {
	public static void main(String args[]) {
		ArrayList<String> string = new ArrayList<String>();
		string.add("Bonjour");
		string.add("Salut");
		for(int i=0;i<string.size();i++) {
			if(string.get(i).equals("Bonjour"))
				string.set(i, "Eh bah non");
		}
		
		System.out.println(string.toString());
	}
}
