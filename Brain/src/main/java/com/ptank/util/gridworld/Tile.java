package com.ptank.util.gridworld;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ptank.util.gridworld.World.Direction;

public class Tile {

	private Map<Direction,Tile> neighbors;
	private List<Unit> unitsOnTile = new ArrayList<Unit>();
	private TileType tileType;
	private int xPos;
	private int yPos;
	
	public enum TileType {
		NorthEastWall,
		EastWall,
		SouthEastWall,
		SouthWall,
		SouthWestWall,
		WestWall,
		NorthWestWall,
		NorthWall,
		OpenSpace;
	}
	
	public Tile(int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
	}
	
	public int getX() {
		return xPos;
	}
	
	public int getY() {
		return yPos;
	}
	
	public void setNeighbors(Map<Direction,Tile> neighbors) {
		this.neighbors = neighbors;
		this.tileType = getTileType();
	}
	
	public Tile getNeighbor(Direction direction) {
		return neighbors.get(direction);
	}
	
	public boolean isPassable() {
		return tileType == TileType.OpenSpace;
	}
	
	public void addUnit(Unit unit) {
		unitsOnTile.add(unit);
	}
	
	public void removeUnit(Unit unit) {
		unitsOnTile.remove(unit);
	}
	
	public boolean isOccupied() {
		return !unitsOnTile.isEmpty();
	}
	
	public Unit getUnitOnTile() {
		return unitsOnTile.get(0);
	}
	
	private TileType getTileType() {
		if(neighbors.get(Direction.North) == null && neighbors.get(Direction.West) == null) {
			return TileType.NorthWestWall;
		}
		if(neighbors.get(Direction.North) == null && neighbors.get(Direction.East) == null) {
			return TileType.NorthEastWall;
		}
		if(neighbors.get(Direction.North) == null) {
			return TileType.NorthWall;
		}
		if(neighbors.get(Direction.South) == null && neighbors.get(Direction.West) == null) {
			return TileType.SouthWestWall;
		}
		if(neighbors.get(Direction.South) == null && neighbors.get(Direction.East) == null) {
			return TileType.SouthEastWall;
		}
		if(neighbors.get(Direction.South) == null) {
			return TileType.SouthWall;
		}
		if(neighbors.get(Direction.East) == null) {
			return TileType.EastWall;
		}
		if(neighbors.get(Direction.West) == null) {
			return TileType.WestWall;
		}
		return TileType.OpenSpace;
	}
	
	public String toString() {
		if(isOccupied()) {
			return String.valueOf(getUnitOnTile().getTextualRepresentation());
		}
		
		switch(tileType) {
		case NorthWestWall:
		case SouthEastWall:
			return "/";
		case NorthEastWall:
		case SouthWestWall:
			return "\\";
		case NorthWall:
		case SouthWall:
			return "-";
		case EastWall:
		case WestWall:
			return "|";
		case OpenSpace:
			return "#";
		default:
			throw new RuntimeException("Don't know how to convert tile type to string: " + tileType);
		}
	}
	
}
