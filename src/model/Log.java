package model;

import java.util.ArrayList;
import java.util.List;

public class Log {
	private static List<String> entries = new ArrayList<String>();
	
	public static void add(String s){
		entries.add(s);
	}
	
	public static String[] getLastEntries(int i){
		String[] lastEntries = new String[i];
		i--;
		for( ; i >= 0; i--){
			if(entries.size() > i){
				lastEntries[i] = entries.get(entries.size()-i-1);
			}
		}
		return lastEntries;
	}
}
