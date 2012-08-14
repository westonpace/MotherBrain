package com.ptank.util.gridworld;

import com.ptank.util.event.Event;
import com.ptank.util.gridworld.World.Direction;

public class Unit {

	private char textualRepresentation;
	
	public Event<UnitCollisionEvent> unitCollisionEvent = new Event<UnitCollisionEvent>();
	
	public Unit(char textualRepresentation) {
		this.textualRepresentation = textualRepresentation;
	}
	
	public void move(Tile source, Direction direction) {
		Tile newTile = source.getNeighbor(direction);
		if(newTile.isPassable()) {
			if(newTile.isOccupied()) {
				Unit collidedWith = newTile.getUnitOnTile();
				unitCollisionEvent.fireEvent(new UnitCollisionEvent(this, collidedWith));
			} else {
				source.removeUnit(this);
				newTile.addUnit(this);
			}
		} else {
			unitCollisionEvent.fireEvent(new UnitCollisionEvent(this));
		}
	}
	
	public char getTextualRepresentation() {
		return textualRepresentation;
	}
	
}
