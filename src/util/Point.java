package util;

public class Point {
	public int x;
	public int y;
	
	public Point(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public static double distance(Point p1, Point p2){
		int xDiff = p2.x - p1.x;
		int yDiff = p2.y - p1.y;
		
		return Math.sqrt(xDiff*xDiff + yDiff*yDiff);
	}
	
	public static Point midPoint(Point p1, Point p2){
		return new Point((p1.x + p2.x)/2, (p1.y+p2.y)/2);
	}
	
	@Override
	public String toString(){
		return "(" + x + "," + y + ")";
	}
	
	@Override
	public boolean equals(Object other){
		if(other instanceof Point){
			Point o = (Point)other;
			return o.x == this.x && o.y == this.y; 
		}
		return false;
	}
}
