package com.ptank.brain.world.simpleworld.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ptank.brain.world.simpleworld.model.World.Direction;

public class Tile {

	private Map<Direction,Tile> neighbors;
	private List<Unit> unitsOnTile = new ArrayList<Unit>();
	
	public Tile() {
		
	}
	
	public void setNeighbors(Map<Direction,Tile> neighbors) {
		this.neighbors = neighbors;
	}
	
	public Tile getNeighbor(Direction direction) {
		return neighbors.get(direction);
	}
	
	public String toString() {
		if(neighbors.get(Direction.North) == null && neighbors.get(Direction.West) == null) {
			return "/";
		}
		if(neighbors.get(Direction.North) == null && neighbors.get(Direction.East) == null) {
			return "\\";
		}
		if(neighbors.get(Direction.North) == null) {
			return "-";
		}
		if(neighbors.get(Direction.South) == null && neighbors.get(Direction.West) == null) {
			return "\\";
		}
		if(neighbors.get(Direction.South) == null && neighbors.get(Direction.East) == null) {
			return "/";
		}
		if(neighbors.get(Direction.South) == null) {
			return "_";
		}
		if(neighbors.get(Direction.East) == null || neighbors.get(Direction.West) == null) {
			return "|";
		}
		return "#";
	}
	
}
