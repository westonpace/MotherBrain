package com.ptank.util.gridworld;

import java.util.HashMap;

public class World {

	public enum Direction {
		North("N"),
		NorthEast("NE"),
		East("E"),
		SouthEast("SE"),
		South("S"),
		SouthWest("SW"),
		West("W"),
		NorthWest("NW");
		
		private String shortString;
		
		private Direction(String shortString) {
			this.shortString = shortString;
		}
		
		public String getShortString() {
			return shortString;
		}
		
		/**If you imagine the directions as angles (e.g. N == 0 degrees) then this is
		   the result of combining the two angles (N + N = 0 degrees = N, E + E = 180 degrees = S)
		   W + W = 540 degrees = 180 degrees = S.
		   
		   Another way to think of it is if you were facing direction X and wanted to move
		   in direction Y where direction Y is relative to the facing direction (e.g. E = east
		   of my forward direction then the result is the direction you'd actually move)
		   */ 
		public Direction combine(Direction input) {
			return Direction.values()[(this.ordinal() + input.ordinal()) % Direction.values().length]; 
		}
	}
	
	private int width;
	private int height;
	
	private Tile[][] tiles;
	
	public World(int width, int height) {
		this.width = width;
		this.height = height;
		tiles = new Tile[height][width];
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				tiles[i][j] = new Tile(j,i);
			}
		}
		updateTileNeighbors();
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public Tile getTile(int x, int y) {
		return tiles[y][x];
	}
	
	public void clear() {
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				tiles[i][j].clear();
			}
		}
	}
	
	private void updateTileNeighbors() {
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				HashMap<Direction,Tile> neighbors = new HashMap<Direction,Tile>();
				if(i > 0 && j > 0) {
					neighbors.put(Direction.NorthWest,tiles[i-1][j-1]);
				}
				if(j > 0) {
					neighbors.put(Direction.West,tiles[i][j-1]);
				}
				if(i < height - 1 && j > 0) {
					neighbors.put(Direction.SouthWest,tiles[i+1][j-1]);
				}
				if(i > 0) {
					neighbors.put(Direction.North,tiles[i-1][j]);
				}
				if(j < width - 1 && i > 0) {
					neighbors.put(Direction.NorthEast,tiles[i-1][j+1]);
				}
				if(j < width - 1) {
					neighbors.put(Direction.East,tiles[i][j+1]);
				}
				if(i < height - 1 && j < width - 1) {
					neighbors.put(Direction.SouthEast,tiles[i+1][j+1]);
				}
				if(i < height - 1) {
					neighbors.put(Direction.South,tiles[i+1][j]);
				}
				for(Direction direction : Direction.values()) {
					if(!neighbors.containsKey(direction)) {
						neighbors.put(direction, null);
					}
				}
				tiles[i][j].setNeighbors(neighbors);
			}
		}
	}
	
	public String toString() {
		StringBuilder resultBuilder = new StringBuilder();
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				resultBuilder.append(tiles[i][j]);
			}
			resultBuilder.append("\n");
		}
		return resultBuilder.toString();
	}
	
	public static void main(String args[]) {
		World world = new World(30,50);
		world.tiles[10][10].addUnit(new Unit('C'));
		System.out.println(world);		
		
		System.out.println(Direction.North.combine(Direction.North));
		System.out.println(Direction.North.combine(Direction.South));
		System.out.println(Direction.East.combine(Direction.North));
		System.out.println(Direction.East.combine(Direction.East));
		System.out.println(Direction.West.combine(Direction.West));
	}
}
