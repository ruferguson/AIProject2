/* Ru Ferguson
 * 21 September 2020
 * 
 */

import java.util.ArrayList;
import java.util.Arrays;

public class MarkovGenerator<T> extends ProbabilityGenerator<T> {	
	
    ArrayList<ArrayList<Integer>> transitionTable = new ArrayList(); // a 2D array representing your transition tables – so, probably an array of arrays

    
	MarkovGenerator() {
		super();
	}
	
	T generate() {
		T newToken = null;
		// do something here
		return newToken;
	}
	
	void printTransTable () {	
		for (int j = 0; j < transitionTable.size(); j++) {

            ArrayList row = transitionTable.get(j);
        		for (int k = 0; k < row.size(); k++) {
        			 	System.out.print(row.get(k) + "  ");
        		}
        	System.out.println();
    	}
	}
	
	void train(ArrayList<T> inputTokens) {
		// This is the index of the PREVIOUS token.
	    int lastIndex = -1;
        
        for (int i = 0; i < inputTokens.size(); i++) { // for each token in the input array 

        	int tokenIndex = alphabet.indexOf(inputTokens.get(i)); // the index of the token in the alphabet

        	if (tokenIndex == -1) {  // if the current token is not found in the alphabet
        		tokenIndex = alphabet.size();
        		ArrayList<Integer> newRow = new ArrayList<Integer>(); // Create a new array that is the size of the alphabet 
        		transitionTable.add(newRow); // Then add to your transition table (the array of arrays) (expanding vertically)
        		
            	for (int j = 0; j < transitionTable.size(); j++) {
                		ArrayList row = transitionTable.get(j);
                		while (row.size() < transitionTable.size()) {
    	        			row.add(0); // for each array (row) in the transition table add 0 (expand horizontally)
                		}
    	        }
            	alphabet.add(inputTokens.get(i)); // add the token to the alphabet array
				alphabet_counts.add(0);
				System.out.println(alphabet);
            }
        	
            // ok, now add the counts to the transition table
        	if (lastIndex > -1) {
            	for (int j = 0; j < transitionTable.size(); j++) {
            		
            		if (alphabet.get(j).equals(inputTokens.get(lastIndex))) { // Use lastIndex to get the correct row (array) in your transition table.
                		ArrayList row = transitionTable.get(j);
    	        		for (int k = 0; k < row.size(); k++) {
    	        			
    	        			if (k == tokenIndex) {  // Use the tokenIndex to index the correct column (value of the row you accessed)
    	        				int currentValue = (int) row.get(k);
    	        				row.set(k, currentValue + 1); // Add 1 to that value.
    	        				
    	        				int tempCount = alphabet_counts.get(j) + 1; // update alphabet counts
    	    					alphabet_counts.set(j, tempCount);
    	        			}
    	        		}
            		}
        		}
			}
        	lastIndex = lastIndex + 1; //setting current to previous for next round
        }
}
	
	
	
	ArrayList<T> generate(int length) {
		ArrayList<T> newSequence = new ArrayList<T>();
//		for (int i = 0; i < length; i++) {
//			newSequence.add(generate());
//	 	}
		return newSequence;
	}
	
	
	ArrayList<T> generate(int length, T initToken) {
		ArrayList<T> newSequence = new ArrayList<T>();
//		for (int i = 0; i < length; i++) {
//			newSequence.add(generate());
//		}
		return newSequence;
	}
}
