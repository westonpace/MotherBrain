package com.ptank.brain.world.simpleworld.model;

import java.util.HashMap;

public class World {

	public enum Direction {
		North,
		NorthEast,
		East,
		SouthEast,
		South,
		SouthWest,
		West,
		NorthWest;
	}
	
	private int width;
	private int height;
	
	private Tile[][] tiles;
	
	public World(int width, int height) {
		this.width = width;
		this.height = height;
		tiles = new Tile[width][height];
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				tiles[i][j] = new Tile();
			}
		}
		updateTileNeighbors();
	}
	
	private void updateTileNeighbors() {
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				HashMap<Direction,Tile> neighbors = new HashMap<Direction,Tile>();
				if(i > 0 && j > 0) {
					neighbors.put(Direction.NorthWest,tiles[i-1][j-1]);
				}
				if(i > 0) {
					neighbors.put(Direction.West,tiles[i-1][j]);
				}
				if(i > 0 && j < height - 1) {
					neighbors.put(Direction.SouthWest,tiles[i-1][j+1]);
				}
				if(j > 0) {
					neighbors.put(Direction.North,tiles[i][j-1]);
				}
				if(j > 0 && i < width - 1) {
					neighbors.put(Direction.NorthEast,tiles[i+1][j-1]);
				}
				if(i < width - 1) {
					neighbors.put(Direction.East,tiles[i+1][j]);
				}
				if(i < width - 1 && j < height - 1) {
					neighbors.put(Direction.SouthEast,tiles[i+1][j+1]);
				}
				if(j < height - 1) {
					neighbors.put(Direction.South,tiles[i][j+1]);
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
				resultBuilder.append(tiles[j][i]);
			}
			resultBuilder.append("\n");
		}
		return resultBuilder.toString();
	}
	
	public static void main(String args[]) {
		System.out.println(new World(30,50));
		
		System.out.println();
		
		System.out.println(new World(50,30));
	}
}
