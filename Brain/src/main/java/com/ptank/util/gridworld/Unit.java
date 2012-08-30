package com.ptank.util.gridworld;

import com.ptank.util.event.Event;
import com.ptank.util.gridworld.World.Direction;

public class Unit {

	private char textualRepresentation;
	private Tile tile;
	
	public Event<UnitCollisionEvent> collisionEvent = new Event<UnitCollisionEvent>();
	public Event<UnitMoveEvent> moveEvent = new Event<UnitMoveEvent>();
	
	public Unit(char textualRepresentation) {
		this.textualRepresentation = textualRepresentation;
	}
	
	public void remove() {
		tile.removeUnit(this);
		tile = null;
	}
	
	public void place(Tile destination) {
		if(!destination.isPassable()) {
			throw new RuntimeException("Unit can't be placed on an unpassable tile");
		}
		destination.addUnit(this);
		tile = destination;
		moveEvent.fireEvent(new UnitMoveEvent(null,destination));
	}
	
	public void move(Direction direction) {
		Tile newTile = tile.getNeighbor(direction);
		if(newTile.isPassable()) {
			if(newTile.isOccupied()) {
				Unit collidedWith = newTile.getUnitOnTile();
				collisionEvent.fireEvent(new UnitCollisionEvent(this, collidedWith));
			} else {
				tile.removeUnit(this);
				newTile.addUnit(this);
				tile = newTile;
				moveEvent.fireEvent(new UnitMoveEvent(tile, newTile));
			}
		} else {
			collisionEvent.fireEvent(new UnitCollisionEvent(this));
		}
	}
	
	public char getTextualRepresentation() {
		return textualRepresentation;
	}
	
}
