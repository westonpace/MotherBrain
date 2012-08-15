package com.ptank.brain.world.simpleworld.mental.mouse;

import java.util.List;

import com.ptank.brain.world.simpleworld.physical.Mouse;
import com.ptank.util.gridworld.Tile;
import com.ptank.util.gridworld.World.Direction;

/**
 * Translates a set of neurons into mouse movement.
 */
public class MouseMotorControl implements NeuralOutput {

	private Mouse mouse;
	private Tile currentTile;
	
	private enum MouseMove {
		ROTATE_LEFT,
		ROTATE_RIGHT,
		MOVE_FORWARD,
		MOVE_BACKWARD;
	}
	
	@Override
	public void setOutput(List<Double> output) {
		int maxIndex = -1;
		double maxValue = Double.NaN;
		
		for(int i = 0; i < output.size(); i++) {
			double value = output.get(i);
			if(maxValue == Double.NaN || value > maxValue) {
				maxValue = value;
				maxIndex = i;
			}
		}
		
		//If all the values are negative then don't move
		if(maxValue > 0) {
			MouseMove move = MouseMove.values()[maxIndex];
			makeMove(move);
		}
	}
	
	private void makeMove(MouseMove move) {
		Direction moveDirection;
		switch(move){
		case MOVE_BACKWARD:
			moveDirection = mouse.getFacingDirection().combine(Direction.South);
			mouse.move(currentTile, moveDirection);
			break;
		case MOVE_FORWARD:
			moveDirection = mouse.getFacingDirection().combine(Direction.North);
			mouse.move(currentTile, moveDirection);
			break;
		case ROTATE_LEFT:
			mouse.turn(Direction.East);
			break;
		case ROTATE_RIGHT:
			mouse.turn(Direction.West);
			break;
		default:
			throw new RuntimeException("No move action available for move: " + move);
		}
	}
	
	public int size() {
		return MouseMove.values().length;
	}

}
