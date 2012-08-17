package com.ptank.util.gridworld;

public class UnitMoveEvent {

	private Tile source;
	private Tile destination;
	
	public UnitMoveEvent(Tile source, Tile destination) {
		this.source = source;
		this.destination = destination;
	}
	
	public Tile getSource() {
		return source;
	}
	
	public Tile getDestination() {
		return destination;
	}
	
}
