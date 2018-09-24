package darwin;

import structure5.*;

import java.util.ArrayList;
import java.util.Random;
import java.io.*;

/**
 * This class controls the simulation. The design is entirely up to you. You
 * should include a main method that takes the array of species file names
 * passed in and populates a world with species of each type. 
 * 
 */
class Darwin {
	
	public ArrayList<Creature> creatures;
	/**
	 * The array passed into main will include the arguments that appeared on
	 * the command line. For example, running "java Darwin Hop.txt Rover.txt"
	 * will call the main method with s being an array of two strings: "Hop.txt"
	 * and "Rover.txt".
	 * 
	 */
	
	
	public static void main(String s[]) {
		new Darwin(s);
	}
	
	public Darwin(String s[]) {
		try {
			Random randNum = new Random();
			creatures = new ArrayList<Creature>();
			World<Creature> world = new World<Creature>(20,20);
			WorldMap.createWorldMap(20, 20);
			
			// adds 20 creatures for each species given in the string array
			for (int i= 0; i<s.length; i++){
				BufferedReader file = new BufferedReader(new FileReader (new File (s[i])));
				Species species = new Species(file);
				for (int j = 0; j<20; j++){
					Position pos = new Position (randNum.nextInt(20), randNum.nextInt(20));
					if (world.get(pos) == null) {
						creatures.add(new Creature(species,world, pos, randNum.nextInt(4)));
					} else {
						j--;
					}
				}
			}
			
			
			simulate();
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * gives each creature 500 turns
	 */
	public void simulate() {
	
		for (int i = 0; i< 500; i++) {
			for (int j = 0; j <creatures.size(); j++) {
				creatures.get(j).takeOneTurn();
			}
			//WorldMap.pause(500);
			WorldMap.pause(1);
		}

	}
}
