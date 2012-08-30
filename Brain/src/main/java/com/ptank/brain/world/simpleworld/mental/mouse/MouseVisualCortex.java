package com.ptank.brain.world.simpleworld.mental.mouse;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import com.ptank.brain.world.simpleworld.physical.Cheese;
import com.ptank.brain.world.simpleworld.physical.Cat;
import com.ptank.brain.world.simpleworld.physical.Mouse;
import com.ptank.util.event.Event.EventListener;
import com.ptank.util.gridworld.Tile;
import com.ptank.util.gridworld.UnitMoveEvent;
import com.ptank.util.gridworld.World.Direction;

public class MouseVisualCortex implements NeuralInput,EventListener<UnitMoveEvent> {

	private Mouse mouse;
	private Tile currentTile;
		
	public MouseVisualCortex(Mouse mouse) {
		this.mouse = mouse;
		this.currentTile = null;
		mouse.moveEvent.addListener(this);
	}
	
	public MouseVisualCortex(Mouse mouse, Tile currentTile) {
		this.mouse = mouse;
		this.currentTile = currentTile;
		mouse.moveEvent.addListener(this);
	}

	@Override
	public List<Double> getNextInput() {
		List<Double> result = new ArrayList<Double>(size());
		for(int i = 0; i < size(); i++) {
			result.add(0.0);
		}
		TileIterator tileIterator = new TileIterator(mouse.getFacingDirection(),currentTile);
		int tileIndex = 0;
		while(tileIterator.hasNext()) {
			Tile tile = tileIterator.next();
			VisualInput visualInput = null;
			if(tile != null && tile.isOccupied()) {
				if(tile.getUnitOnTile() instanceof Cat) {
					visualInput = VisualInput.Cat;
				} else if (tile.getUnitOnTile() instanceof Mouse) {
					visualInput = VisualInput.Mouse;
				} else if (tile.getUnitOnTile() instanceof Cheese) {
					visualInput = VisualInput.Cheese;
				} else {
					throw new RuntimeException("Don't know how to visually interpret: " + tile.getUnitOnTile().getClass().getSimpleName());
				}
			} else if(tile == null || !tile.isPassable()) {
				visualInput = VisualInput.Wall;
			}
			if(visualInput != null) {
				set(result,visualInput.ordinal(),tileIndex);
			}
			tileIndex++;
		}
		return result;
	}
	
	private int indexOfPath(Direction [] path) {
		int pathIndex = -1;
		for(int j = 0; j < TileIterator.directions.length; j++) {
			Direction [] nextPath = TileIterator.directions[j];
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
	
	public int getNeuralIndex(VisualInput input, Direction[] path) {
		int pathIndex = indexOfPath(path);
		if(pathIndex == -1) {
			return -1;
		}
		return getNeuralIndex(input.ordinal(), pathIndex);
	}
	
	private int getNeuralIndex(int visualIndex, int pathIndex) {
		return visualIndex*numSquares()+pathIndex;
	}
	
	private int numSquares() {
		return TileIterator.directions.length;
	}
	
	private int numObjects() {
		return VisualInput.values().length;
	}
	
	private void set(List<Double> list, int objectIndex, int squareIndex) {
		list.set(getNeuralIndex(objectIndex,squareIndex),1.0);
	}
	
	public int size() {
		//Each of the objects (plus a wall) could be seen in one of 8 squares.
		return numObjects()*numSquares();
	}
	
	private int getVisualIndex(int totalIndex) {
		return totalIndex / numSquares();
	}
	
	private int getPathIndex(int totalIndex) {
		return totalIndex % numSquares();
	}
	
	public String getNameOfIndex(int index) {
		return VisualInput.values()[getVisualIndex(index)].toString() + "@" + directionPathToString(getPathIndex(index));
	}
	
	private String directionPathToString(int pathIndex) {
		Direction [] path = TileIterator.directions[pathIndex];
		StringBuilder result = new StringBuilder();
		for(Direction direction : path) {
			result.append(direction.getShortString());
			result.append(".");
		}
		return result.toString();
	}
	
	@Override
	public void onEvent(UnitMoveEvent event) {
		currentTile = event.getDestination();
	}
	
	public static class TileIterator implements Iterator<Tile> {

		private Direction baseDirection;
		private Tile startTile;
		/**  
		 * 
		 *       34567
		 *        218
		 *         X
		 * 
		 */
		private static final Direction [][] directions = {{Direction.North},
			                                              {Direction.NorthWest},
			                                              {Direction.NorthWest,Direction.NorthWest},
			                                              {Direction.NorthWest,Direction.North},
			                                              {Direction.North,Direction.North},
			                                              {Direction.North,Direction.NorthEast},
			                                              {Direction.NorthEast,Direction.NorthEast},
			                                              {Direction.NorthEast}};
		private int currentIndex = 0;
		
		public TileIterator(Direction baseDirection, Tile startingTile) {
			this.baseDirection = baseDirection;
			this.startTile = startingTile;
		}
		
		@Override
		public boolean hasNext() {
			return currentIndex < directions.length;
		}

		@Override
		public Tile next() {
			if(!hasNext()) {
				throw new NoSuchElementException();
			}
			Tile currentTile = startTile;
			for(Direction direction : directions[currentIndex]) {
				Direction combinedDirection = direction.combine(baseDirection);
				currentTile = currentTile.getNeighbor(combinedDirection);
				if(currentTile == null) {
					break;
				}
			}
			currentIndex++;
			return currentTile;
		}

		@Override
		public void remove() {
			throw new RuntimeException("Operation not implemented");
		}

	}

}
