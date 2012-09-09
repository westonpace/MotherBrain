package com.ptank.util.gridworld;

import com.ptank.util.gridworld.World.Direction;

public class FacingUnit extends Unit {

	public FacingUnit(char textualRepresentation) {
		super(textualRepresentation);
	}
	
	private Direction facingDirection = Direction.North;
	
	/**
	 * Moves relative to the facing direction.  
	 * The direction you input should be the direction you want to move
	 * relative to the direction you are facing.  In other words, N = move forwards
	 * and S = move backwards.  East and west represent strafing left and right.
	 */
	public void relativeMove(Direction direction) {
		Direction trueDirectionToMove = facingDirection.combine(direction);
		move(trueDirectionToMove);
	}

	public Direction getFacingDirection() {
		return facingDirection;
	}
	
	public void turn(Direction directionToTurn) {
		facingDirection = facingDirection.combine(directionToTurn);
	}

	public void face(Direction directionToFace) {
		facingDirection = directionToFace;
	}
	
}
