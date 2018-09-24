package darwin;

import java.io.*;
import java.util.ArrayList;

/**
 * The individual creatures in the world are all representatives of some
 * species class and share certain common characteristics, such as the species
 * name and the program they execute. Rather than copy this information into
 * each creature, this data can be recorded once as part of the description for
 * a species and then each creature can simply include the appropriate species
 * reference as part of its internal data structure.
 * 
 */
public class Species {

	protected String name;
	protected String color;
	protected char speciesChar; // the first character of Species name
	protected ArrayList<Instruction> program;

	/**
	 * Create a species for the given file. 
	 * @pre fileName exists in the Creature subdirectory.
	 */
	public Species(BufferedReader fileReader) {
		try {
	
			
			name = fileReader.readLine();
			color = fileReader.readLine();
			speciesChar = name.charAt(0);
			program = new ArrayList<Instruction>();
			String temp = fileReader.readLine();
			String[] splitted = {""};
			
			// Continuously adds instructions to the program until there is an
			// 'enter'
			while (temp.length() > 0) {
				if (temp.contains("hop")) {
					program.add(new Instruction(1,0));
				} else if (temp.contains("left")) {
					program.add(new Instruction(2,0));
				} else if (temp.contains("right")) {
					program.add(new Instruction(3,0));
				} else if (temp.contains("infect")) {
					if (temp.contains(" ")) {
						splitted = temp.split(" ");
						program.add(new Instruction(4, Integer.parseInt(splitted[1])));
					} else {
						program.add(new Instruction(4, 1));
					}
				} else if (temp.contains("ifempty")) {
					splitted = temp.split(" ");
					program.add(new Instruction(5, Integer.parseInt(splitted[1])));
				} else if (temp.contains("ifwall")) {
					splitted = temp.split(" ");
					program.add(new Instruction(6, Integer.parseInt(splitted[1])));
				} else if (temp.contains("ifsame")) {
					splitted = temp.split(" ");
					program.add(new Instruction(7, Integer.parseInt(splitted[1])));
				} else if (temp.contains("ifenemy")) {
					splitted = temp.split(" ");
					program.add(new Instruction(8, Integer.parseInt(splitted[1])));
				} else if (temp.contains("ifrandom")) {
					splitted = temp.split(" ");
					program.add(new Instruction(9, Integer.parseInt(splitted[1])));
				} else if (temp.contains("go")) {
					splitted = temp.split(" ");
					program.add(new Instruction(10, Integer.parseInt(splitted[1])));
				} 
				temp = fileReader.readLine();
			}
		} catch (IOException e) {
			System.out.println(
				"Could not read file '"
					+ fileReader
					+ "'");
			System.exit(1);
		}
	}


	/**
	* Return the char for the species
	*/
	public char getSpeciesChar() {
		return speciesChar;
	}

	/**
	 * Return the name of the species.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Return the color of the species.
	 */
	public String getColor() {
		return color;
	}

	/**
	 * Return the number of instructions in the program.
	 */
	public int programSize() {
		return program.size();
	}

	/**
	 * Return an instruction from the program.
	 * @pre 1 <= i <= programSize().
	 * @post returns instruction i of the program.
	 */
	public Instruction programStep(int i) {
		if (i >= 1 && i <= programSize()) {
			return program.get(i-1);
		}
		return null;
	}

	/**
	 * Return a String representation of the program.
	 * 
	 * do not change
	 */
	public String programToString() {
		String s = "";
		for (int i = 1; i <= programSize(); i++) {
			s = s + (i) + ": " + programStep(i) + "\n";
		}
		return s;
	}

}
