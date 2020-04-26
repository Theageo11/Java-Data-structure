
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Anagrams {
	Map<Character,Integer> letterTable;
	Map<Long,ArrayList<String>> anagramTable;
	final Integer[] primes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101};
	
	public Anagrams() {
		buildLetterTable();
		anagramTable = new HashMap<Long,ArrayList<String>>();
	}
	
	private void processFile(String s) throws IOException {
		FileInputStream fStream = new FileInputStream(s);
		BufferedReader br = new BufferedReader(new InputStreamReader(fStream));
		String strLine;
		while((strLine = br.readLine()) != null) {
			this.addWord(strLine);
		}
		br.close();
	}
	
	private void buildLetterTable() {
		Character[] letters = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o','p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
		letterTable = new HashMap<Character,Integer>();
		for(int i = 0; i < 26; i++) {letterTable.put(letters[i], primes[i]);}
	}
	/*
	 * @param s
	 * @return
	 */
	private Long myHashCode(String s) {
		long number = 1;
		for(int i = 0; i < s.length(); i++) {number = (long)letterTable.get(s.charAt(i))*(number);}
		return number;
	}
	/*
	 * @param s
	 */
	private void addWord(String s) {	
		Long Word = this.myHashCode(s);
		if(anagramTable.get(Word) == null) {
			ArrayList<String> temporary = new ArrayList<String>();
			temporary.add(s);
			anagramTable.put(Word, temporary);
		} else {anagramTable.get(Word).add(s);}
	}
	/*
	 * @return
	 */
	private ArrayList<Map.Entry<Long,ArrayList<String>>> getMaxEntries() {
		int maxEntry = 0;
		ArrayList<Map.Entry<Long,ArrayList<String>>> temporary = new ArrayList<>(); 
		for (Map.Entry<Long,ArrayList<String>> theEntry : anagramTable.entrySet()) {
			if(theEntry.getValue().size() > maxEntry) {temporary.clear();
				temporary.add(theEntry);
				maxEntry = theEntry.getValue().size();
			} else if(theEntry.getValue().size() == maxEntry) {temporary.add(theEntry);}}
		 return temporary;
		 
	}
	
	public static void main(String[] args) {
		Anagrams a = new Anagrams();
		
		final long startTime = System.nanoTime();
		try {
			a.processFile ("words_alpha.txt");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		ArrayList<Map.Entry<Long,ArrayList<String>>> maxEntries = a.getMaxEntries ();
		final long estimatedTime = System.nanoTime() - startTime ;
		final double seconds = ((double)estimatedTime/1000000000);
		System.out.println("Time: "+ seconds);
		System.out.println("List of max anagrams: " + maxEntries);
		// TODO Auto-generated method stub

	}

}
