package darwin;

import structure5.Matrix;

/**
 * This class includes the functions necessary to keep track of the creatures in
 * a two-dimensional world. There are many ways to implement this class but we
 * recommend looking at the Matrix class in Bailey's structure5 package.
 */

public class World<E> {

	private int height;
	private int width;
	private Matrix<E> matrix;

	/**
	 * This function creates a new world consisting of height rows and width
	 * columns, each of which is numbered beginning at 0. A newly created world
	 * contains no objects.
	 */
	public World(int h, int w) {
		height = h;
		width = w;
		matrix = new Matrix<E>(h, w);
	}

	/**
	 * Returns the height of the world.
	 * 
	 * @post returns height of the world
	 */
	public int height() {
		return height;
	}

	/**
	 * Returns the width of the world.
	 * 
	 * @post returns width of world
	 */
	public int width() {
		return width;
	}

	/**
	 * Returns whether pos is in the world or not.
	 * 
	 * @post returns true if pos is an (x,y) location within the bounds of the
	 *       board.
	 */
	public boolean inRange(Position pos) {
		return pos.getX() < width && pos.getY() < height && pos.getX() >=0 && pos.getY() >=0;
			
	}

	/**
	 * Set a position on the board to contain c.
	 * 
	 * @pre pos is in range - throws IllegalArgumentException otherwise
	 * 
	 * @post sets a position on the board containing c
	 */
	public void set(Position pos, E c) {
		if (inRange(pos)) {
			matrix.set(pos.getX(), pos.getY(), c);
		} else {
			throw new IllegalArgumentException("Position is not in range.");
		}
	}

	/**
	 * Return the contents of a position on the board.
	 * 
	 * @pre pos is a in range - throws IllegalArgumentException otherwise
	 */
	public E get(Position pos) {
		if (inRange(pos)) {
			return matrix.get(pos.getX(), pos.getY());
		} else {
			throw new IllegalArgumentException("Position is not in range.");
		}
	}
}
