package util;
import java.util.HashMap;
import java.util.Random;

import model.Stat;

public final class Roll {

	private static Random r = new Random();
	
	public static int d(int sides){
		return r.nextInt(sides)+1;
	}
	
	public static int between(int min, int max){
		if(min < max){
			return d(max-min)+min;
		}
		return min;
	}
	
	public static int stat(){
		int sum = 0;
		int min = Integer.MAX_VALUE;
		for(int i = 0; i < 4; i++){
			int roll = d(6);
			sum += roll;
			if(min > roll){
				min = roll;
			}
		}
		return sum - min;
	}
	
	public static HashMap<Stat, Double> fullStats(){
		HashMap<Stat, Double> stats = new HashMap<Stat, Double>();
		stats.put(Stat.ATTACK, (double) Roll.stat());
		stats.put(Stat.CONSTITUTION, (double) Roll.stat());
		stats.put(Stat.DEFENSE, (double) Roll.stat());
		stats.put(Stat.DEXTERITY, (double) Roll.stat());
		return stats;
	}
}
