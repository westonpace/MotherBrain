package com.ptank.brain.world.simpleworld.physical;

import com.ptank.util.event.Event.EventListener;
import com.ptank.util.gridworld.FacingUnit;
import com.ptank.util.gridworld.UnitCollisionEvent;

public class Mouse extends FacingUnit implements EventListener<UnitCollisionEvent> {

	private int score = 0;
	
	private int pointsLostOnCatCollision = 20;
	private int pointsLostOnWallCollision = 10;
	private int pointsGainedOnCheeseEating = 10;
	
	public Mouse() {
		super('M');
		collisionEvent.addListener(this);
	}

	@Override
	public void onEvent(UnitCollisionEvent event) {
		if(event.getOtherUnit() == null) {
			score -= pointsLostOnWallCollision;
		} else if (event.getOtherUnit() instanceof Cat) {
			score -= pointsLostOnCatCollision;
		} else if (event.getOtherUnit() instanceof Bread) {
			score += pointsGainedOnCheeseEating;
		}
	}
	
	public int getScore() {
		return score;
	}
	
}
