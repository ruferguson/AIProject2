/* Ru Ferguson
 * 21 September 2020
 * 
 * This class inherits from the superclass, ProbabilityGenerator from project 1 and will use Markov chains
 * to predict the next most probable "best sounding" musical notes based on the probability calculated
 * using the order and occurrences of each note. The train() method formulate the probabilities and the
 * next most probable note will be output and stored using the using the generate() method. */

import java.util.ArrayList;
import java.util.Arrays;

public class MarkovGenerator<T> extends ProbabilityGenerator<T> {	
	
	// a 2D array representing your transition tables – so, probably an array of arrays
    ArrayList<ArrayList<Integer>> transitionTable = new ArrayList(); 
	MarkovGenerator() {
		super();
	}
	
	T generate() { // WORK IN PROGRESS
		T newToken = null;
		// do something here
		return newToken;
	}
	
	// returns the an ArrayList from the transitionTable
	public ArrayList<Integer> getTransTable(int i) {
		return transitionTable.get(i);
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
	
	// returns the total number of notes in the row
	public Integer getRowTotal(int i) {
		int total = 0;
		ArrayList row = transitionTable.get(i);
    	for (int j = 0; j < row.size(); j++) {
    		total = total + (int) row.get(j);
    	}
		return total;
	}
	
	// adds the probabilities to an ArrayList called probabilities
	public ArrayList<Double> getProbabilities(int i) { 
		ArrayList row = transitionTable.get(i);
		for (int j = 0; j < row.size(); j++) {
			double rowAsDouble = (int) row.get(j); // row is from an array of integers and must be converted to a double for division
			double newProb = rowAsDouble / getRowTotal(i);
			while (probabilities.isEmpty() || probabilities.size() != row.size()) {
				probabilities.add(newProb); 
			}
			if (rowAsDouble == 0 && getRowTotal(i) == 0) { // to negate any instance of 0 / 0
				newProb = 0;
			}
			probabilities.set(j, newProb); 
			// System.out.println("row.get(j) is " + row.get(j) + ". row total is " + getRowTotal(i) + ". newProb is " + newProb);

		}
		return probabilities;
	}
	
	void train(ArrayList<T> inputTokens) {
	    int lastIndex = -1; // This is the index of the PREVIOUS token.
        
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
            }
        	
        	if (lastIndex > -1) { // ok, now add the counts to the transition table
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
