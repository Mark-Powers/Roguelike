package util;
import java.util.Random;

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
}
