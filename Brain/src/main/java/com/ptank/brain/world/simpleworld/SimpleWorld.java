package com.ptank.brain.world.simpleworld;

import java.util.Random;

import com.ptank.brain.world.simpleworld.mental.mouse.MouseBrain;
import com.ptank.util.gridworld.Tile;
import com.ptank.util.gridworld.World;
import com.ptank.util.random.RandomSingleton;

public class SimpleWorld extends World {

	private int NUM_CHEESES = 3;
	
	private Random random = RandomSingleton.getInstance();
	private CheeseHandler cheeseHandler = new CheeseHandler(NUM_CHEESES,this);
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
	
	private void placeMouse() {
		Tile startingLocation = getRandomStartingLocation();
		mouse.getBody().place(startingLocation);
	}
	
	public void start() {
		placeMouse();
		cheeseHandler.addCheeses();
	}
	
	public void tick() {
		mouse.think();
	}
	
	public void clear() {
		super.clear();
		cheeseHandler.clear();
	}
	
}
