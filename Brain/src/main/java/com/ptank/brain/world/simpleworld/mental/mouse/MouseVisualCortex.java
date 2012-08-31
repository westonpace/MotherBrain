package com.ptank.brain.world.simpleworld.mental.mouse;

import java.util.ArrayList;
import java.util.List;

import com.ptank.brain.world.simpleworld.Eyes;
import com.ptank.brain.world.simpleworld.Eyes.VisualInput;
import com.ptank.brain.world.simpleworld.physical.Mouse;
import com.ptank.util.gridworld.World.Direction;

public class MouseVisualCortex implements NeuralInput {

	private Eyes eyes;
		
	public MouseVisualCortex(Mouse mouse) {
		this.eyes = new MouseEyes(mouse);
	}
	
	public MouseVisualCortex(Eyes eyes) {
		this.eyes = eyes;
	}
	
	public void setEyes(Eyes eyes) {
		this.eyes = eyes;
	}
	
	public Eyes getEyes() {
		return eyes;
	}
	
	@Override
	public List<Double> getNextInput() {
		List<Double> result = new ArrayList<Double>(size());
		for(int i = 0; i < size(); i++) {
			result.add(0.0);
		}
		List<VisualInput> currentView = eyes.getCurrentView();
		for(int i = 0; i < currentView.size(); i++) {
			VisualInput visualInput = currentView.get(i);
			if(visualInput != null) {
				set(result,visualInput.ordinal(),i);
			}
		}
		return result;
	}
	
	private int indexOfPath(Direction [] path) {
		return eyes.indexFromPath(path);
	}
	
	public int getNeuralIndex(VisualInput input, Direction[] path) {
		int pathIndex = indexOfPath(path);
		if(pathIndex == -1) {
			return -1;
		}
		return getNeuralIndex(input.ordinal(), pathIndex);
	}
	
	private int getNeuralIndex(int visualIndex, int pathIndex) {
		return visualIndex*numSquares()+pathIndex;
	}
	
	private int numSquares() {
		return eyes.size();
	}
	
	private int numObjects() {
		return VisualInput.values().length;
	}
	
	private void set(List<Double> list, int objectIndex, int squareIndex) {
		list.set(getNeuralIndex(objectIndex,squareIndex),1.0);
	}
	
	public int size() {
		//Each of the objects (plus a wall) could be seen in one of 8 squares.
		return numObjects()*numSquares();
	}
	
	private int getVisualIndex(int totalIndex) {
		return totalIndex / numSquares();
	}
	
	private int getPathIndex(int totalIndex) {
		return totalIndex % numSquares();
	}
	
	public String getNameOfIndex(int index) {
		return VisualInput.values()[getVisualIndex(index)].toString() + "@" + eyes.nameOfIndex(getPathIndex(index));
	}
	
}
