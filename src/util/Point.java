package util;

public class Point {
	public int x;
	public int y;
	
	public Point(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	static Point midPoint(Point p1, Point p2){
		return new Point((p1.x + p2.x)/2, (p1.y+p2.y)/2);
	}
	
	@Override
	public String toString(){
		return "(" + x + "," + y + ")";
	}
}
