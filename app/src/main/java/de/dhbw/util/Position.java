package de.dhbw.util;

import java.io.Serializable;

public class Position implements Serializable {
	private int x;
	private int y;

	/**
	 * Constructor
	 * @param x
	 * @param y
	 */
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Get the x value, usually in px.
	 * @return x value
	 */
	public int getX() {
		return x;
	}

	/**
	 * Get the y value, usually in px.
	 * @return y value
	 */
	public int getY() {
		return y;
	}

	/**
	 * Checks if the values of another position object are equal to the current position's values.
	 * @param obj
	 * @return true if both position values are equal
	 */
	@Override
	public boolean equals(Object obj) {
		Position pos = (Position) obj;
		return pos.getX() == x && pos.getY() == y;
	}

	/**
	 * Creates a string of x and y values.
	 * @return string of values
	 */
	@Override
	public String toString() {
		return String.valueOf(x) + String.valueOf(y);
	}

}
