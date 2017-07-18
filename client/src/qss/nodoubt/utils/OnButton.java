package qss.nodoubt.utils;

public class OnButton {
	private static final double w = 150;
	private static final double h = 72;
	public static boolean onButton(double x, double y, double rx, double ry){
		if((x >= rx-w)&&(x <= rx+w)){
			if((y >= ry-h)&&(y <= ry+h)){
				return true;
			}
			else{
				return false;
			}
		}
		else{
			return false;
		}
	}
}
