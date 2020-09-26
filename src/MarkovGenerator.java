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
	ProbabilityGenerator<T> initTokenGenerator = new ProbabilityGenerator<T>();

    
	MarkovGenerator() {
		super();
	}
		
	// returns the an ArrayList from the transitionTable
	public ArrayList<Integer> getTransTable(int i) {
		return transitionTable.get(i);
	}
	
	// prints the transition table
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
	


/* Our Data:        

alphabet – array of unique symbols/tokens in the input
transitionTable – a 2D array representing your transition tables – so, probably an array of arrays
initToken - initial/starting symbol or token – the symbol to start generating from


Our Procedure:

In order to do a markov chain, we must start with an initial symbol. You may start with a symbol by either:
using an instance of your ProbabilityGenerator class to generate one from the input
asking for user input (in the GUI, not console input)
setting it to a hard-coded number (must be a constant passed as a parameter)
Note that for your unit tests, you should use option a) but for “Do the THING” you may use any technique.

Ok, now that we have our initToken

find initToken in the alphabet (ie, get the index of where it is)
Use that index to access the row (ie one array from the array of arrays) of probabilities in transitionTable
This array is the beginning of a probability distribution. It has all the counts. It is exactly like the array
of counts that you had in Project 1. Thus, You already have a function which generates from a probability distribution.
Hand that function (your generate function from Project 1) your row(i.e., array of probabilities/counts) and
generate from that. You may have to rewrite it to accommodate your new needs. Make sure Project 1 still works
afterwards. Run your Project 1 unit tests to check.

If you need to generate more than one symbol, use this result to generate another.
That is, set initToken = the token you just generated. Go to step 1.

Note that initToken should probably be a parameter into your generate function. I suggest you have 3 functions

            T generate(T initToken){}                          

            ArrayList<T> generate(T initToken, int numberOfTokensToGenerate){} //this calls the above.

            ArrayList<T> generate(int numberOfTokensToGenerate){} //this calls the above with a random initToken */
	
	T generate(T initToken) { 
		T tokenRow = null;
		boolean found = false;
		int i = 0;
		while (!found && i < alphabet.size()) {
			//System.out.println("i is: " + i + " and alphabet(i) is: " + alphabet.get(i) + " and alphabet size is: " + alphabet.size() + " and initToken is " + initToken + " and transition table size is " + transitionTable.size());
			
			if (initToken.equals(alphabet.get(i))) {
				tokenRow = (T) getTransTable(i);
				found = true;
				// System.out.println("found true");
			} else {
				i ++;
				// System.out.println("adding to i");
			}
		}
		//System.out.println("exited while " + i);
		//System.out.println("i is: " + i + " and alphabet(i) is: " + alphabet.get(i) + " and alphabet size is: " + alphabet.size() + " and initToken is " + initToken + " and transition table size is " + transitionTable.size());

		
		return tokenRow;
	}
	
	ArrayList<T> generate(int length, T initToken) {
		// System.out.println(initToken);
		
		generate(initToken);
		ArrayList<T> newSequence = new ArrayList<T>();
		for (int i = 0; i < length; i++) {
			newSequence.add(generate());
		}
		return newSequence;
	}

	
	ArrayList<T> generate(int length) {
		initTokenGenerator.train(alphabet);
		T initToken = initTokenGenerator.generate();
		generate(length, initToken);
		ArrayList<T> newSequence = new ArrayList<T>();
		for (int i = 0; i < length; i++) {
			newSequence.add(generate());
		}
		return newSequence;
	}
	
	/*
	ArrayList<T> generate(int length) {
		initTokenGenerator.train(alphabet);
		T initToken = initTokenGenerator.generate();
		generate(length, initToken);
		ArrayList<T> newSequence = new ArrayList<T>();
		for (int i = 0; i < length; i++) {
			newSequence.add(generate());
		}
		return newSequence;
	} */
	
}
	
	
