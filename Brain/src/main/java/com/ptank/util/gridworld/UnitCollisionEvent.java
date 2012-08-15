package com.ptank.util.gridworld;

public class UnitCollisionEvent {

	private Unit sourceUnit;
	private Unit otherUnit;
	
	public UnitCollisionEvent(Unit sourceUnit, Unit otherUnit) {
		this.sourceUnit = sourceUnit;
		this.otherUnit = otherUnit;
	}
	
	public UnitCollisionEvent(Unit sourceUnit) {
		this.sourceUnit = sourceUnit;
		this.otherUnit = null;
	}

	public Unit getSourceUnit() {
		return sourceUnit;
	}

	public Unit getOtherUnit() {
		return otherUnit;
	}

}
