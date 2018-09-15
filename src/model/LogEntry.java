package model;

public class LogEntry {
	public int entryType;
	public String text;

	public LogEntry(String s, int e) {
		entryType = e;
		text = s;
	}
}
