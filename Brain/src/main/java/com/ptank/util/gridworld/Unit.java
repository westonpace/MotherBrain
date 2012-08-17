package com.ptank.util.gridworld;

import com.ptank.util.event.Event;
import com.ptank.util.gridworld.World.Direction;

public class Unit {

	private char textualRepresentation;
	
	public Event<UnitCollisionEvent> collisionEvent = new Event<UnitCollisionEvent>();
	public Event<UnitMoveEvent> moveEvent = new Event<UnitMoveEvent>();
	
	public Unit(char textualRepresentation) {
		this.textualRepresentation = textualRepresentation;
	}
	
	public void place(Tile destination) {
		if(!destination.isPassable()) {
			throw new RuntimeException("Unit can't be placed on an unpassable tile");
		}
		destination.addUnit(this);
		moveEvent.fireEvent(new UnitMoveEvent(null,destination));
	}
	
	public void move(Tile source, Direction direction) {
		Tile newTile = source.getNeighbor(direction);
		if(newTile.isPassable()) {
			if(newTile.isOccupied()) {
				Unit collidedWith = newTile.getUnitOnTile();
				collisionEvent.fireEvent(new UnitCollisionEvent(this, collidedWith));
			} else {
				source.removeUnit(this);
				newTile.addUnit(this);
				moveEvent.fireEvent(new UnitMoveEvent(source, newTile));
			}
		} else {
			collisionEvent.fireEvent(new UnitCollisionEvent(this));
		}
	}
	
	public char getTextualRepresentation() {
		return textualRepresentation;
	}
	
}
