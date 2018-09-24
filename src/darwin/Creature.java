package darwin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

/**
 * This class represents one creature on the board. Each creature must remember
 * its species, position, direction, and the world in which it is living.
 * 
 */

public class Creature {

	private Species species;
	private World<Creature> world;
	private Position pos;
	private int dir;
	protected int i = 1;

	/**
	 * Create a creature of the given species, with the indicated position and
	 * direction.
	 */
	public Creature(Species species, World<Creature> world, Position pos, int dir) {
		this.species = species;
		this.world = world;
		this.pos = pos;
		this.dir = dir;
		world.set(pos, this);
		WorldMap.displaySquare(pos, species.getSpeciesChar(), dir, species.getColor());
	}

	/**
	 * Return the species of the creature.
	 */
	public Species species() {
		return species;
	}

	/**
	 * Return the current direction of the creature.
	 */
	public int direction() {
		return dir;
	}

	/**
	 * Return the position of the creature.
	 */
	public Position position() {
		return pos;
	}

	/**
	 * Execute steps from the Creature's program until a hop, left, right, or
	 * infect instruction is executed.
	 */
	public void takeOneTurn() {
		//checks whether or not the turn is over
		boolean turnOver = false;
		
		// goes thru the program until a hop, left, right, or infect, which is
		// where turnover is set to true
		while (!turnOver) {
			//checks that the step being asked for is within the program size.
			//if not, loops back to 1
			if (i >= species.programSize()) {
				i = 1;
			}
			
			//checks the opcode
			if (species.programStep(i).getOpcode() == Instruction.HOP) {
				// if the adjacent pos is in range and null, the creature will move there
				if (world.inRange(pos.getAdjacent(dir)) && world.get(pos.getAdjacent(dir)) == null) {
					WorldMap.displaySquare(pos, ' ', dir, species.getColor());
					world.set(pos, null);
					pos = pos.getAdjacent(dir);
					WorldMap.displaySquare(pos, species.getSpeciesChar(), dir, species.getColor());
					world.set(pos, this);
				}
				turnOver = true;
			} else if (species.programStep(i).getOpcode() == Instruction.LEFT) {
				//turns the creatures display to the left and updates its direction
				dir = leftFrom(dir);
				WorldMap.displaySquare(pos, species.getSpeciesChar(), dir, species.getColor());
				turnOver = true;
			} else if (species.programStep(i).getOpcode() == Instruction.RIGHT) {
				//turns the creatures display to the right and updates its direction
				dir = rightFrom(dir);
				WorldMap.displaySquare(pos, species.getSpeciesChar(), dir, species.getColor());
				turnOver = true;
			} else if (species.programStep(i).getOpcode() == Instruction.INFECT) {
				// if the adjacent pos is in range, not null, and the species is
				// different then the enemies species is changed and it goes to the
				//step that the address tells it to
				if ( world.inRange(pos.getAdjacent(dir)) && species != world.get(pos.getAdjacent(dir)).species()
						&& world.get(pos.getAdjacent(dir)) != null) {

					world.get(pos.getAdjacent(dir)).species = this.species;
					world.get(pos.getAdjacent(dir)).i = species.programStep(i).getAddress();
					
				}
				turnOver = true;
			} else if (species.programStep(i).getOpcode() == Instruction.IFEMPTY) {
				// if the adjacent pos is in range and not null, then the creature will
				// go to the step of the address of this instruction
				if (world.inRange(pos.getAdjacent(dir)) && world.get(pos.getAdjacent(dir)) != null) {

					i = (species.programStep(i).getAddress()) - 1;
				}
			} else if (species.programStep(i).getOpcode() == Instruction.IFWALL) {
				// if the adjacent pos is not in range, then the creature will
				// go to the step of the address of this instruction
				if (!world.inRange(pos.getAdjacent(dir))) {
					i = (species.programStep(i).getAddress()) - 1;
				}
			} else if (species.programStep(i).getOpcode() == Instruction.IFSAME) {
				// if the adjacent pos is in range and not null and has the same species,
				// then the creature will go to the step of the address of this instruction
				if (world.inRange(pos.getAdjacent(dir)) && world.get(pos.getAdjacent(dir)) != null && 
						species == world.get(pos.getAdjacent(dir)).species()) {

					i = (species.programStep(i).getAddress()) - 1;
				}
			} else if (species.programStep(i).getOpcode() == Instruction.IFENEMY) {
				// if the adjacent pos is in range and not null and doesnt have the
				// same species, then the creature will go to the step of the address
				//of this instruction
				if (world.inRange(pos.getAdjacent(dir)) && world.get(pos.getAdjacent(dir)) != null
						&& species != world.get(pos.getAdjacent(dir)).species()) {
					i = (species.programStep(i).getAddress()) - 1;
				}
			} else if (species.programStep(i).getOpcode() == Instruction.IFRANDOM) {
				// makes a random num generator
				Random randomNum = new Random();
				// if the  generator spits out a 0, then the creature will go to the
				// step of the address of this instruction
				if (randomNum.nextInt(2) == 0) {
					i = (species.programStep(i).getAddress()) - 1;
				}
			} else if (species.programStep(i).getOpcode() == Instruction.GO) {
				//the creature will go to the step of the address of this instruction
				i = (species.programStep(i).getAddress()) - 1;
			}

			i++;

		}
	}

	/**
	 * Return the compass direction the is 90 degrees left of the one passed in.
	 */
	public static int leftFrom(int direction) {
		return (4 + direction - 1) % 4;
	}

	/**
	 * Return the compass direction the is 90 degrees right of the one passed
	 * in.
	 */
	public static int rightFrom(int direction) {
		return (direction + 1) % 4;
	}

	/**
	 * You may test in the main method if you like, but you could also include a
	 * JUnit test class to check correctness.
	 */
	public static void main(String st[]) {

		 //test creature code here.
		 try {
		 BufferedReader file = new BufferedReader(new FileReader (new File
		 ("src/darwin/Creatures/Rover.txt")));
		 Species brook = new Species(file);
		 World<Creature> worldy = new World<Creature>(10,10);
		 WorldMap.createWorldMap(10, 10);
		 new Creature(brook,worldy, new Position (5,5), 2);
		 } catch (FileNotFoundException e) {
		 // TODO Auto-generated catch block
		 e.printStackTrace();
		 }

	}
}
