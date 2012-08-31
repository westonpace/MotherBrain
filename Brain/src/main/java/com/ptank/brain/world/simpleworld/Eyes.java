package com.ptank.brain.world.simpleworld;

import java.util.List;

import com.ptank.util.gridworld.World.Direction;

public abstract class Eyes {

	public enum VisualInput {
		Mouse,
		Cat,
		Cheese,
		Wall;
	}

	public abstract List<VisualInput> getCurrentView();
	
	public int size() {
		return directions.length;
	}
	
	public String nameOfIndex(int pathIndex) {
		Direction [] path = directions[pathIndex];
		StringBuilder result = new StringBuilder();
		for(Direction direction : path) {
			result.append(direction.getShortString());
			result.append(".");
		}
		return result.toString();
	}
	
	public int indexFromPath(Direction [] path) {
		int pathIndex = -1;
		for(int j = 0; j < directions.length; j++) {
			Direction [] nextPath = directions[j];
			if(nextPath.length == path.length) {
				boolean matches = true;
				for(int i = 0; i < nextPath.length; i++) {
					if(!path[i].equals(nextPath[i])) {
						matches = false;
					}
				}
				if(matches) {
					pathIndex = j;
					break;
				}
			}
		}
		return pathIndex;
	}
	
	/**  
	 * 
	 *       34567
	 *        218
	 *         X
	 * 
	 */
	protected static final Direction [][] directions = {{Direction.North},
		                                              {Direction.NorthWest},
		                                              {Direction.NorthWest,Direction.NorthWest},
		                                              {Direction.NorthWest,Direction.North},
		                                              {Direction.North,Direction.North},
		                                              {Direction.North,Direction.NorthEast},
		                                              {Direction.NorthEast,Direction.NorthEast},
		                                              {Direction.NorthEast}};

}
