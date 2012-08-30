package com.ptank.brain.world.simpleworld.mental.mouse;

import java.util.List;

import org.apache.log4j.Logger;

import com.ptank.brain.world.simpleworld.physical.Mouse;
import com.ptank.util.event.Event.EventListener;
import com.ptank.util.gridworld.Tile;
import com.ptank.util.gridworld.UnitMoveEvent;
import com.ptank.util.gridworld.World.Direction;

/**
 * Translates a set of neurons into mouse movement.
 */
public class MouseCerebellum implements NeuralOutput,EventListener<UnitMoveEvent> {

	private static Logger logger = Logger.getLogger(MouseCerebellum.class);
	
	private Mouse mouse;
	private Tile currentTile;
	
	public MouseCerebellum(Mouse mouse) {
		this.mouse = mouse;
		this.currentTile = null;
		mouse.moveEvent.addListener(this);
	}
	
	public MouseCerebellum(Mouse mouse, Tile currentTile) {
		this.mouse = mouse;
		this.currentTile = currentTile;
		mouse.moveEvent.addListener(this);
	}
	
	public enum MouseMove {
		ROTATE_LEFT,
		ROTATE_RIGHT,
		MOVE_FORWARD,
		MOVE_BACKWARD;
	}
	
	public String getNameOfIndex(int index) {
		return MouseMove.values()[index].toString();
	}
	
	@Override
	public void setOutput(List<Double> output) {
		logger.debug("Neural input arrived at motor control: " + output);
		int maxIndex = -1;
		double maxValue = Double.NaN;
		
		for(int i = 0; i < output.size(); i++) {
			double value = output.get(i);
			if(Double.isNaN(maxValue) || value > maxValue) {
				maxValue = value;
				maxIndex = i;
			}
		}
		
		//If all the values are negative then don't move
		if(maxValue > 0) {
			MouseMove move = MouseMove.values()[maxIndex];
			makeMove(move);
		} else {
			logger.debug("Standing Still");
		}
	}
	
	private void makeMove(MouseMove move) {
		Direction moveDirection;
		switch(move){
		case MOVE_BACKWARD:
			logger.debug("Moving Backwards");
			moveDirection = mouse.getFacingDirection().combine(Direction.South);
			mouse.move(moveDirection);
			break;
		case MOVE_FORWARD:
			logger.debug("Moving Forwards");
			moveDirection = mouse.getFacingDirection().combine(Direction.North);
			mouse.move(moveDirection);
			break;
		case ROTATE_LEFT:
			logger.debug("Turning Left");
			mouse.turn(Direction.East);
			break;
		case ROTATE_RIGHT:
			logger.debug("Turning Right");
			mouse.turn(Direction.West);
			break;
		default:
			throw new RuntimeException("No move action available for move: " + move);
		}
	}
	
	public int getNeuralIndex(MouseMove move) {
		return move.ordinal();
	}
	
	public int size() {
		return MouseMove.values().length;
	}

	@Override
	public void onEvent(UnitMoveEvent event) {
		currentTile = event.getDestination();
	}

}
