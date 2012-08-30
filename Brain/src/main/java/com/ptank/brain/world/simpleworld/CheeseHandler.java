package com.ptank.brain.world.simpleworld;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.ptank.brain.world.simpleworld.physical.Cheese;
import com.ptank.util.event.Event.EventListener;
import com.ptank.util.gridworld.Tile;
import com.ptank.util.gridworld.UnitCollisionEvent;
import com.ptank.util.gridworld.World;
import com.ptank.util.random.RandomSingleton;

public class CheeseHandler {

	private int numCheeses = 3;
	private World world;	
	private Random random = RandomSingleton.getInstance();
	
	private List<Cheese> cheeses = new ArrayList<Cheese>();
	
	public CheeseHandler(int numCheeses, World world) {
		this.numCheeses = numCheeses;
		this.world = world;
	}
	
	private Cheese createCheese() {
		Cheese cheese = new Cheese();
		cheese.collisionEvent.addListener(new CheeseCollisionHandler(cheese));
		return cheese;
	}
	
	public void addCheeses() {
		for(int i = 0; i < numCheeses; i++) {
			Cheese cheese = createCheese();
			cheeses.add(cheese);
			placeCheeseRandomly(cheese);
		}
	}

	public void clear() {
		for(Cheese cheese : cheeses) {
			cheese.collisionEvent.removeAllListeners();
			cheese.remove();
		}
		cheeses.clear();
	}
	
	private Point getRandomLocation() {
		int x = random.nextInt(world.getWidth()-1)+1;
		int y = random.nextInt(world.getHeight()-1)+1;
		return new Point(x, y);
	}
	
	private void placeCheeseRandomly(Cheese cheese) {
		int numAttempts = 10;
		for(int i = 0; i < numAttempts; i++) {
			Point point = getRandomLocation();
			Tile tile = world.getTile(point.x, point.y);
			if(tile.isPassable()) {
				cheese.place(tile);
				return;
			}
		}
		throw new RuntimeException("After " + numAttempts + " attempts, still couldn't find a spot to put the cheese :(");
	}
	
	private class CheeseCollisionHandler implements EventListener<UnitCollisionEvent>{

		private Cheese cheese;
		
		public CheeseCollisionHandler(Cheese cheese) {
			this.cheese = cheese;
		}
		
		@Override
		public void onEvent(UnitCollisionEvent event) {
			cheese.remove();
			placeCheeseRandomly(cheese);
		}
		
	}
	
}
