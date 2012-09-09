package com.ptank.brain.world.simpleworld;

import java.util.Random;

import com.ptank.brain.world.simpleworld.mental.mouse.MouseBrain;
import com.ptank.util.gridworld.Tile;
import com.ptank.util.gridworld.World;
import com.ptank.util.random.RandomSingleton;

public class SimpleWorld extends World {

	private int NUM_CHEESES = 3;
	
	private Random random = RandomSingleton.getInstance();
	private MouseBrain mouse;
	
	public SimpleWorld(int width, int height) {
		super(width,height);
	}
	
	public void setMouse(MouseBrain mouse) {
		this.mouse = mouse;
	}
	
	private Tile getRandomStartingLocation() {
		int x = random.nextInt(getWidth()-2)+1;
		int y = random.nextInt(getHeight()-2)+1;
		return getTile(x, y);
	}
	
	private void spinMouseRandomly() {
		int directionToFaceInt = random.nextInt(4);
		Direction directionToFace = null;
		switch(directionToFaceInt) {
		case 0:
			directionToFace = Direction.North;
			break;
		case 1:
			directionToFace = Direction.East;
			break;
		case 2:
			directionToFace = Direction.South;
			break;
		case 3:
			directionToFace = Direction.West;
			break;
		}
		mouse.face(directionToFace);
	}
	
	private void placeMouse() {
		Tile startingLocation = getRandomStartingLocation();
		spinMouseRandomly();
		mouse.getBody().place(startingLocation);
	}
	
	public void start() {
		placeMouse();
	}
	
	public void tick() {
		mouse.think();
	}
	
	public void clear() {
		super.clear();
	}
	
}
