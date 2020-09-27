/* Ru Ferguson
 * 28 September 2020
 * 
 * This program will play the Super Mario Bros Theme using the Kontakt MIDI Player just as it did in Project 1
 * and will store the notes used, the occurrences of each note, and the probabilities of each note occurring 
 * in an ArrayList of ArrayLists using the MarkovGenerator class. The class can also generate the next most likely
 * note using the probabilities stored to attempt to produce "nice" sounding melodies. Project 1 methods and unit tests
 * are still available in this patch.*/

import processing.core.*;

import java.util.*; 

//importing the JMusic stuff
import jm.music.data.*;
import jm.JMC;
import jm.util.*;
import jm.midi.*;

import java.io.UnsupportedEncodingException;
import java.net.*;

//import javax.sound.midi.*;

//make sure this class name matches your file name, if not fix.
public class AIProject2Main extends PApplet {

	MelodyPlayer player; //play a midi sequence
	MidiFileToNotes midiNotes; // read a midi file
	UnitTests unitTest = new UnitTests(); // create unit tests
	
	ProbabilityGenerator<Integer> pitchGenerator;
	ProbabilityGenerator<Double> rhythmGenerator;
	MarkovGenerator<Integer> markovPitchGenerator;
	MarkovGenerator<Double> markovRhythmGenerator;
	
	String filePath;

	public static void main(String[] args) {
		PApplet.main("AIProject2Main"); 
	}

	//setting the window size to 300x300
	public void settings() {
		size(350, 500);
	}

	//doing all the setup stuff
	public void setup() {		
		// create my generators for pitch and rhythm
		markovPitchGenerator = new MarkovGenerator<Integer>();
		markovRhythmGenerator = new MarkovGenerator<Double>();
		
		// returns a url
		String filePath = getPath("mid/Super_Mario_Bros_Theme.mid"); // use this for probabilistic generation
		
		midiNotes = new MidiFileToNotes(filePath); //creates a new MidiFileToNotes -- reminder -- ALL objects in Java must 
												   //be created with "new". Note how every object is a pointer or reference. Every. single. one.

	    // which line to read in --> this object only reads one line (or ie, voice or ie, one instrument)'s worth of data from the file
		midiNotes.setWhichLine(0);

		player = new MelodyPlayer(this, 100.0f);
		
		player.setup();

		// play the midi notes as they are in the file
		player.setMelody(midiNotes.getPitchArray());
		player.setRhythm(midiNotes.getRhythmArray());
	}

	public void draw() {
	    player.play();		//play each note in the sequence -- the player will determine whether is time for a note onset
	    background(250);
	    showInstructions();
	}

	//this finds the absolute path of a file
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

	// this function is not currently called. you may call this from setup() if you want to test
	// this just plays the midi file -- all of it via your software synth. You will not use this
	// function in upcoming projects but it could be a good debug tool.
	void playMidiFile(String filename) {
		Score theScore = new Score("Temporary score");
		Read.midi(theScore, filename);
		Play.midi(theScore);
	}

	
	// this starts & restarts the melody and runs unit tests
	public void keyPressed() {
		if (key == ' ') {
			player.reset();
			println("Melody started!");
		} else if (key == 'p') {
			P2GenerateNotes(); 
		} else if (key == '1') {
			unitTest.P1UnitTest1();
		} else if (key == '2') {
			unitTest.P1UnitTest2();
		} else if (key == '3') {
			unitTest.P1UnitTest3();
		} else if (key == 'q') {
			unitTest.P2UnitTest1();
		} else if (key == 'w') {
			unitTest.P2UnitTest2();
		} else if (key == 'e') {
			unitTest.P2UnitTest3();	
		} else if (key == 's') {		
			player.hasMelody = false; // stops the player
		}
	}
	
	
	// display instructions to the user
	public void showInstructions() {
		textAlign(CENTER);
		textSize(25);
		fill(0, 100, 255);
		text("Welcome to the\nMarkov Melody Generator", width/2, height*2/20);
		textSize(16);
		fill(0, 115, 255);
		text("Press p to play 35 generated notes\nfrom Super_Mario_Bros_Theme.mid", width/2, height*5/20);
		fill(0, 130, 255);
		text("Press the spacebar to restart the melody", width/2, height*9/20);
		fill(0, 145, 255);
		text("Press s to stop playing", width/2, height*10/20);
		fill(0, 160, 255);
		text("Press 1 for Project 1: Unit Test 1", width/2, height*12/20);
		fill(0, 175, 255);
		text("Press 2 for Project 1: Unit Test 2", width/2, height*13/20); 
		fill(0, 190, 255);
		text("Press 3 for Project 1: Unit Test 3", width/2, height*14/20);
		fill(0, 160, 255);
		text("Press q for Project 2: Unit Test 1", width/2, height*16/20);
	    fill(0, 160, 255);
		text("Press w for Project 2: Unit Test 2", width/2, height*17/20);
		fill(0, 160, 255);
		text("Press e for Project 2: Unit Test 3", width/2, height*18/20);
		textSize(10);
		text("(generated by the Markov Chain)", width/2, height*7/20);
	}
	
	
	// generate a melody using the Probability Generator from Project 1
	public void P1GenerateNotes() {
		filePath = getPath("mid/Super_Mario_Bros_Theme.mid");
		midiNotes = new MidiFileToNotes(filePath);
		midiNotes.setWhichLine(0);
		
		pitchGenerator = new ProbabilityGenerator<Integer>();
		rhythmGenerator = new ProbabilityGenerator<Double>();
		
		// call the train function for both pitches and rhythms
		pitchGenerator.train(midiNotes.getPitchArray());
		rhythmGenerator.train(midiNotes.getRhythmArray());
		
		// generate 20 new notes using the probabalistic generator
		player.setMelody(pitchGenerator.generate(35)); 
		player.setRhythm(rhythmGenerator.generate(35));
	}
	
	
	// generate a melody using the Markov Generator from Project 2
	public void P2GenerateNotes() {
		filePath = getPath("mid/Super_Mario_Bros_Theme.mid");
		midiNotes = new MidiFileToNotes(filePath);
		midiNotes.setWhichLine(0);
		
		markovPitchGenerator = new MarkovGenerator<Integer>();
		markovRhythmGenerator = new MarkovGenerator<Double>();
		pitchGenerator = new ProbabilityGenerator<Integer>();
		rhythmGenerator = new ProbabilityGenerator<Double>();
		
		// call the train function for both pitches and rhythms and to generate an initial token
		markovPitchGenerator.train(midiNotes.getPitchArray());
		markovRhythmGenerator.train(midiNotes.getRhythmArray());
		pitchGenerator.train(midiNotes.getPitchArray()); 
		rhythmGenerator.train(midiNotes.getRhythmArray()); 
				
		// generate 35 new notes using the probabalistic generator
		player.setMelody(markovPitchGenerator.generate(35, pitchGenerator.generate(pitchGenerator.getProbabilities()))); 
		player.setRhythm(markovRhythmGenerator.generate(35, rhythmGenerator.generate(rhythmGenerator.getProbabilities())));	
	}
	
}
