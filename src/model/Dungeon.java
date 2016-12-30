package model;

import java.util.ArrayList;
import java.util.List;

public class Dungeon {
	final int WIDTH = 60;
	final int HEIGHT = 20;
	final int startingX = 5;
	final int startingY = 5;
	
	private List<Floor> floors;
	private int currentFloor;
	
	public Dungeon(int floorCount){
		floors = new ArrayList<Floor>();
		currentFloor = 1;
		
		for(int i = 0; i < floorCount; i++){
			floors.add(new Floor(WIDTH, HEIGHT, startingX, startingY));
		}
	}
	
	public Floor getCurrentFloor(){
		return floors.get(currentFloor - 1);
	}
	
	public int getCurrentFloorNumber(){
		return currentFloor;
	}
	
	public boolean nextFloor(){
		if(currentFloor < floors.size()){
			currentFloor++;
			return true;
		}
		return false;
		
	}
	
	public boolean prevFloor(){
		if(currentFloor > 1){
			currentFloor--;
			return true;
		}
		return false;
	}
}
