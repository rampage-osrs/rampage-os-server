package org.brutality.model.content.teleport;


import org.brutality.util.Misc;

/**
 * A class that handles a player's absolute position in the game world.
 * @author Chris
 * @date Aug 24, 2015 10:05:05 PM
 *
 */
public class Position {
	

		/** The x coordinate. */
		private int x;

		/** The y coordinate. */
		private int y;

		/** The z coordinate. */
		private int z;

		/**
		 * Creates a new Position with the specified coordinates. The Z coordinate
		 * is set to 0.
		 * 
		 * @param x
		 *            the X coordinate.
		 * @param y
		 *            the Y coordinate.
		 */
		public Position(int x, int y) {
			this(x, y, 0);
		}

		/**
		 * Creates a new Position with the specified coordinates.
		 * 
		 * @param x
		 *            the X coordinate.
		 * @param y
		 *            the Y coordinate.
		 * @param z
		 *            the Z coordinate.
		 */
		public Position(int x, int y, int z) {
			this.setX(x);
			this.setY(y);
			this.setZ(z);
		}

		/**
		 * Creates a new position with default values (0) for the coordinates.
		 */
		public Position() {

		}

		/**
		 * Checks if this position is in the specified location.
		 * 
		 * @param other
		 *            the location to check for.
		 * @return true if this location was in the specified location.
		 */
		public boolean inLocation(Location other) {
			return this.x > other.getSouthWest().getX() && this.x < other.getNorthEast().getX() && this.y > other.getSouthWest().getY() && this.y < other.getNorthEast().getY();
		}

		/**
		 * Checks if this position is in the specified location (inclusive).
		 * 
		 * @param other
		 *            the location to check for.
		 * @return true if this location was in the specified location.
		 */
		public boolean inLocationInclusive(Location other) {
			return this.x >= other.getSouthWest().getX() && this.x <= other.getNorthEast().getX() && this.y >= other.getSouthWest().getY() && this.y <= other.getNorthEast().getY();
		}

		/**
		 * Sets this position as the other position. <b>Please use this method
		 * instead of entity.setPosition(other)</b> because of reference conflicts
		 * (if the other position gets modified, so will the entity's).
		 * 
		 * @param other
		 *            the other position.
		 */
		public Position setAs(Position other) {
			this.x = other.x;
			this.y = other.y;
			this.z = other.z;
			return this;
		}

		/**
		 * Moves the position.
		 * 
		 * @param amountX
		 *            the amount of X coordinates.
		 * @param amountY
		 *            the amount of Y coordinates.
		 * @return this position.
		 */
		public Position move(int amountX, int amountY) {
			setX(getX() + amountX);
			setY(getY() + amountY);
			return this;
		}

		/**
		 * Moves the position.
		 * 
		 * @param amountX
		 *            the amount of X coordinates.
		 * @param amountY
		 *            the amount of Y coordinates.
		 * @param amountZ
		 *            the amount of Z coordinates.
		 * @return this position.
		 */
		public Position move(int amountX, int amountY, int amountZ) {
			setX(getX() + amountX);
			setY(getY() + amountY);
			setZ(getZ() + amountZ);
			return this;
		}

		@Override
		public String toString() {
			return "Position(" + x + ", " + y + ", " + z + ")";
		}

		@Override
		public boolean equals(java.lang.Object other) {
			if (other instanceof Position) {
				Position p = (Position) other;
				return x == p.x && y == p.y && z == p.z;
			}
			return false;
		}

		@Override
		public Position clone() {
			return new Position(x, y, z);
		}
		
		

		/**
		 * Sets the X coordinate.
		 * 
		 * @param x
		 *            the X coordinate.
		 */
		public void setX(int x) {
			this.x = x;
		}

		/**
		 * Gets the X coordinate.
		 * 
		 * @return the X coordinate.
		 */
		public int getX() {
			return x;
		}

		/**
		 * Sets the Y coordinate.
		 * 
		 * @param y
		 *            the Y coordinate.
		 */
		public void setY(int y) {
			this.y = y;
		}

		/**
		 * Gets the Y coordinate.
		 * 
		 * @return the Y coordinate.
		 */
		public int getY() {
			return y;
		}

		/**
		 * Sets the Z coordinate.
		 * 
		 * @param z
		 *            the Z coordinate.
		 */
		public void setZ(int z) {
			this.z = z;
		}

		/**
		 * Gets the Z coordinate.
		 * 
		 * @return the Z coordinate.
		 */
		public int getZ() {
			return z;
		}

		/**
		 * Gets the X coordinate of the region containing this Position.
		 * 
		 * @return the region X coordinate.
		 */
		public int getRegionX() {
			return (x >> 3) - 6;
		}

		/**
		 * Gets the Y coordinate of the region containing this Position.
		 * 
		 * @return the region Y coordinate.
		 */
		public int getRegionY() {
			return (y >> 3) - 6;
		}

		/**
		 * Gets the local X coordinate relative to the base Position.
		 * 
		 * @param base
		 *            the base Position.
		 * @return the local X coordinate.
		 */
		public int getLocalX(Position base) {
			return x - 8 * base.getRegionX();
		}

		/**
		 * Gets the local Y coordinate relative to the base Position.
		 * 
		 * @param base
		 *            the base Position.
		 * @return the local Y coordinate.
		 */
		public int getLocalY(Position base) {
			return y - 8 * base.getRegionY();
		}
		
		/**
		 * Gets the player's absolute x <code>Position</code> as expressed in developer coordinates.
		 * @param base
		 * @return
		 */
		public int getDeveloperX(Position base) {
			return 0;
			//TODO
		}
		
		public double distance(Position other) {
	        if (z != other.z) {
	            return Integer.MAX_VALUE - 1;
	        }
	        
	        return distanceFormula(x, y, other.x, other.y);
	    }
	 
	 public static double distanceFormula(int x, int y, int x2, int y2) {
	        return (int) Math.sqrt(Math.pow(x2 - x, 2) + Math.pow(y2 - y, 2));
	    }
		
		/**
		 * Gets the player's absolute y <code>Position</code> as expressed in developer coordinates.
		 * @param base
		 * @return
		 */
		public int getDeveloperY(Position base) {
			//return base.getY() + 3161
			return 0;
		}

		/**
		 * Gets the local X coordinate relative to this Position.
		 * 
		 * @return the local X coordinate.
		 */
		public int getLocalX() {
			return getLocalX(this);
		}

		/**
		 * Gets the local Y coordinate relative to this Position.
		 * 
		 * @return the local Y coordinate.
		 */
		public int getLocalY() {
			return getLocalY(this);
		}

		/**
		 * Gets the X map region chunk relative to this position.
		 * 
		 * @return the X region chunk.
		 */
		public int getChunkX() {
			return (x >> 6);
		}

		/**
		 * Gets the Y map region chunk relative to this position.
		 * 
		 * @return the Y region chunk.
		 */
		public int getChunkY() {
			return (y >> 6);
		}

		/**
		 * Gets the region id relative to this position.
		 * 
		 * @return the region id.
		 */
		public int getRegionId() {
			return ((getChunkX() << 8) + getChunkY());
		}

		/**
		 * Checks if this position is viewable from the other position.
		 * 
		 * @param other
		 *            the other position.
		 * @return true if it is viewable, false otherwise.
		 */
		public boolean isViewableFrom(Position other) {
			if (this.getZ() != other.getZ())
				return false;

			Position p = Misc.delta(this, other);
			return p.x <= 14 && p.x >= -15 && p.y <= 14 && p.y >= -15;
		}

		/**
		 * Checks if this position is within distance of another position.
		 * 
		 * @param position
		 *            the position to check the distance for.
		 * @param distance
		 *            the distance to check.
		 * @return true if this position is within the distance of another position.
		 */
		public boolean withinDistance(Position position, int distance) {
			if (this.getZ() != position.getZ())
				return false;

			return Math.abs(position.getX() - this.getX()) <= distance && Math.abs(position.getY() - this.getY()) <= distance;
		}

}
