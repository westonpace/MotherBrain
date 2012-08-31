package com.ptank.brain.world.simpleworld.mental.mouse;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import com.ptank.brain.world.simpleworld.Eyes;
import com.ptank.brain.world.simpleworld.physical.Cat;
import com.ptank.brain.world.simpleworld.physical.Cheese;
import com.ptank.brain.world.simpleworld.physical.Mouse;
import com.ptank.util.gridworld.Tile;
import com.ptank.util.gridworld.World.Direction;

public class MouseEyes extends Eyes {

	private Mouse mouse;
	
	public MouseEyes(Mouse mouse) {
		this.mouse = mouse;
	}
	
	@Override
	public List<VisualInput> getCurrentView() {
		TileIterator tileIterator = new TileIterator(mouse.getFacingDirection(),mouse.getCurrentTile());
		ArrayList<VisualInput> result = new ArrayList<VisualInput>();
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
			result.add(visualInput);
		}
		return result;
	}	

	public static class TileIterator implements Iterator<Tile> {

		private Direction baseDirection;
		private Tile startTile;
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
