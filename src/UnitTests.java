
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.ArrayList;

import processing.core.PApplet;

public class UnitTests  extends PApplet {
	
	MelodyPlayer player; //play a midi sequence
	MidiFileToNotes midiNotes; // read a midi file
	
	ProbabilityGenerator<Integer> pitchGenerator;
	ProbabilityGenerator<Double> rhythmGenerator;
	
	MarkovGenerator<Integer> markovPitchGenerator;
	MarkovGenerator<Double> markovRhythmGenerator;
	
	ProbabilityGenerator<Integer> initPitchGenerator;
	ProbabilityGenerator<Double> initRhythmGenerator;
	
	UnitTests() {
		String filePath = getPath("mid/MaryHadALittleLamb.mid");
		midiNotes = new MidiFileToNotes(filePath);
		midiNotes.setWhichLine(0);
		pitchGenerator = new ProbabilityGenerator<Integer>();
		rhythmGenerator = new ProbabilityGenerator<Double>();
		markovPitchGenerator = new MarkovGenerator<Integer>();
		markovRhythmGenerator = new MarkovGenerator<Double>();
		initPitchGenerator = new ProbabilityGenerator<Integer>();
		initRhythmGenerator = new ProbabilityGenerator<Double>();
	}
	
	void P1UnitTest1() {	// Project 1: Unit Test 1
		trainP1();

		System.out.println("Pitches:\n\n-----Probability Distribution-----\n");
		for (int i = 0; i < pitchGenerator.getAlphabetSize(); i++) {
			System.out.println("Token: " + pitchGenerator.getToken(i) + " | Probability: " +
			pitchGenerator.getProbability(i));
		}
		System.out.println("\n------------\n\nRhythms:\n\n-----Probability Distribution-----\n");
		for (int i = 0; i < rhythmGenerator.getAlphabetSize(); i++) {
			System.out.println("Token: " + rhythmGenerator.getToken(i) + " | Probability: " + 
			rhythmGenerator.getProbability(i));
		}
		System.out.println("\n------------\n");
	}
	
	void P1UnitTest2() {	// Project 1: Unit Test 2		
		trainP1();
		
		System.out.println("20 pitches from one melody generated from Mary Had a Little Lamb:");
		System.out.println(pitchGenerator.generate(20));
		System.out.println("\n20 rhythms from one melody generated from Mary Had a Little Lamb:");
		System.out.println(rhythmGenerator.generate(20) + "\n------------\n");
		
	}
	
	void P1UnitTest3() {	// Project 1: Unit Test 3
		ProbabilityGenerator<Integer> melodyPitchGen = new ProbabilityGenerator<Integer>();
		ProbabilityGenerator<Double> melodyRhythmGen = new ProbabilityGenerator<Double>();
		ProbabilityGenerator<Integer> probDistPitchGen = new ProbabilityGenerator<Integer>();
		ProbabilityGenerator<Double> probDistRhythmGen = new ProbabilityGenerator<Double>();
		
		ArrayList<Integer> newSongPitches = new ArrayList<Integer>();
		ArrayList<Double> newSongRhythms = new ArrayList<Double>();
		
		melodyPitchGen.train(midiNotes.getPitchArray());
		melodyRhythmGen.train(midiNotes.getRhythmArray());

		for (int i = 0; i < 9999; i++) {
			newSongPitches = melodyPitchGen.generate(20);
			newSongRhythms = melodyRhythmGen.generate(20);	
			probDistPitchGen.train(newSongPitches);
			probDistRhythmGen.train(newSongRhythms);
		}
		
		System.out.println("Probability of Generated Pitches after 10,000 iterations of 20 note melodies:\n\n-----Probability Distribution-----\n");
		for (int i = 0; i < probDistPitchGen.getAlphabetSize(); i++) {
			System.out.println("Token: " + probDistPitchGen.getToken(i) + " | Probability: " + probDistPitchGen.getProbability(i));
		}
		System.out.println("\n------------\n\nProbability of Generated Rhythms after 10,000 iterations of 20 note melodies:\n\n-----Probability Distribution-----\n");
		for (int i = 0; i < probDistRhythmGen.getAlphabetSize(); i++) {
			System.out.println("Token: " + probDistRhythmGen.getToken(i) + " | Probability: " + probDistRhythmGen.getProbability(i));
		}
		System.out.println("\n------------\n");
	}
	
	void P2UnitTest1() {	// Project 2: Unit Test 1
		trainP2();

		System.out.println("\nPitches:\n\n-----Transition Table-----\n\n   " + markovPitchGenerator.getAlphabet());
		for (int i = 0; i < markovPitchGenerator.getAlphabetSize(); i++) {
	        System.out.println(markovPitchGenerator.getToken(i) + " " + markovPitchGenerator.getProbabilities(i));
		}
		System.out.println("\n------------\n\nRhythms:\n\n-----Transition Table-----\n\n    " + markovRhythmGenerator.getAlphabet());
		for (int i = 0; i < markovRhythmGenerator.getAlphabetSize(); i++) {
	        System.out.println(markovRhythmGenerator.getToken(i) + " " + markovRhythmGenerator.getProbabilities(i));
		}
		System.out.println("\n------------\n");		
	}
	
	void P2UnitTest2() {	// Project 2: Unit Test 2
		trainP2();
		
		System.out.println("20 pitches from one melody generated using a Markov Chain from Mary Had a Little Lamb:");
		System.out.println(markovPitchGenerator.generate(20, initPitchGenerator.generate()));
		System.out.println("\n20 rhythms from one melody generated using a Markov Chain from Mary Had a Little Lamb:");
		System.out.println(markovRhythmGenerator.generate(20, initRhythmGenerator.generate()) + "\n------------\n");
	}
	
	void P2UnitTest3() {	// Project 2: Unit Test 3
		// UNIT TEST 3
				/* So, our data/objects

		            song – the pitch or rhythm data from your test file (Mary had a little lamb) 
		            midiNotes.getPitchArray()
		            
		            melodyGen – MarkovGenerator - generates all the melodies to train on 
		            markovMelodyPitchGen
		            
		            ttGen – MarkovGenerator is trained on those melodies generated from above and prints out the transition table
		            ttDistPitchGen
		            
		            initPitchGen – our probability distribution generator from last project
					initPitchGenerator

		Our procedure:

		           firstNoteGen.train(song);
		           melodyGen.train(song);

		            for 1 to 10,000 do
		            {         

		                        initToken = firstNoteGen.generate(1); //only generate 1 token

		                        newSong = melodyGen.generate(initToken, 20); //generates 20 notes using the initToken

		                        ttGen.train(newSong); //gets the probabilities of what we are generating

		            }

		            ttGen.unitTestOne_printTransitionTable(); */
				trainP2();
		
				//MarkovGenerator<Integer> markovMelodyPitchGen = new MarkovGenerator<Integer>();
				//MarkovGenerator<Double> markovMelodyRhythmGen = new MarkovGenerator<Double>();
				MarkovGenerator<Integer> ttDistPitchGen = new MarkovGenerator<Integer>();
				MarkovGenerator<Double> ttDistRhythmGen = new MarkovGenerator<Double>();
				
				ArrayList<Integer> newSongPitches = new ArrayList<Integer>();
				ArrayList<Double> newSongRhythms = new ArrayList<Double>();
				
				//initPitchGenerator.train(midiNotes.getPitchArray()); // must train to get initial pitch
				//markovMelodyPitchGen.train(midiNotes.getPitchArray());		
				
				for (int i = 0; i < 9999; i++) {
					newSongPitches = markovPitchGenerator.generate(20, initPitchGenerator.generate());
					ttDistPitchGen.train(newSongPitches);
					newSongRhythms = markovRhythmGenerator.generate(20, initRhythmGenerator.generate());
					ttDistRhythmGen.train(newSongRhythms);
				}
				
				System.out.println("\nProbability of Generated Pitches after 10,000 iterations of 20 note melodies:\n\n-----Transition Table-----\n\n   " + markovPitchGenerator.getAlphabet());
				for (int i = 0; i < ttDistPitchGen.getAlphabetSize(); i++) {
			        System.out.println(ttDistPitchGen.getToken(i) + " " + ttDistPitchGen.getProbabilities(i));
				}
				System.out.println("\n------------\n\nProbability of Generated Rhythms after 10,000 iterations of 20 note melodies:\n\n-----Transition Table-----\n\n    " + markovRhythmGenerator.getAlphabet());
				for (int i = 0; i < ttDistRhythmGen.getAlphabetSize(); i++) {
			        System.out.println(ttDistRhythmGen.getToken(i) + " " + ttDistRhythmGen.getProbabilities(i));
				}
				System.out.println("\n------------\n");	
	}
	
	void trainP1() {
		pitchGenerator.train(midiNotes.getPitchArray());
		rhythmGenerator.train(midiNotes.getRhythmArray());
	}
	
	void trainP2() {
		markovPitchGenerator.train(midiNotes.getPitchArray());
		markovRhythmGenerator.train(midiNotes.getRhythmArray());
		
		initPitchGenerator.train(midiNotes.getPitchArray()); // must train to get initial pitch
		initRhythmGenerator.train(midiNotes.getRhythmArray()); // must train to get initial rhythm
	}
	
	String getPath(String path) {
		String filePath = "";
		try {
			filePath = URLDecoder.decode(getClass().getResource(path).getPath(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return filePath;
	}

}

