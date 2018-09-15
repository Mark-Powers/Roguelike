package model;

import java.util.ArrayList;
import java.util.List;

import gui.EntryType;

public class Log {
	private static List<LogEntry> entries = new ArrayList<LogEntry>();
	
	public static void add(String s){
		entries.add(new LogEntry(s, EntryType.NORMAL));
	}
	
	public static void add(String s, int entryType){
		entries.add(new LogEntry(s, entryType));
	}
	
	public static LogEntry[] getLastEntries(int i){
		LogEntry[] lastEntries = new LogEntry[i];
		i--;
		for( ; i >= 0; i--){
			if(entries.size() > i){
				lastEntries[i] = entries.get(entries.size()-i-1);
			}
		}
		return lastEntries;
	}
}
