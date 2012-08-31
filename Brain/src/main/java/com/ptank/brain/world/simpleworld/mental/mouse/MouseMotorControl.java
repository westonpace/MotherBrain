package com.ptank.brain.world.simpleworld.mental.mouse;

import org.apache.log4j.Logger;

import com.ptank.brain.world.simpleworld.MotorControl;
import com.ptank.brain.world.simpleworld.physical.Mouse;
import com.ptank.util.gridworld.World.Direction;

public class MouseMotorControl implements MotorControl {

	private Mouse mouse;
	private static final Logger logger = Logger.getLogger(MouseMotorControl.class);
	
	public MouseMotorControl(Mouse mouse) {
		this.mouse = mouse;
	}
	
	@Override
	public void doAction(Action action) {
		Direction moveDirection;
		switch(action){
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
			throw new RuntimeException("No move action available for move: " + action);
		}
	}

}
