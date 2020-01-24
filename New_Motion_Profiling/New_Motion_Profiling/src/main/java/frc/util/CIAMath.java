package frc.util;

public class CIAMath {
	
	public static double sin(double a){
		double result = Math.sin(Math.toRadians(a));
		return result;
	}
	
	public static double cos(double a){
		double result = Math.cos(Math.toRadians(a));
		return result;
	}
	
	public static double tan(double a){
		double result = Math.tan(Math.toRadians(a));
		return result;
	}
	
	public static double asin(double a){
		double result = Math.toDegrees(Math.asin(a));
		return result;
	}
	
	public static double acos(double a){
		double result = Math.toDegrees(Math.acos(a));
		return result;
	}
	
	public static double atan(double a){
		double result = Math.toDegrees(Math.atan(a));
		return result;
	}
	
	public static double atan2(double y, double x){
		double result = Math.toDegrees(Math.atan2(y, x));
		return result;
	}
	

}
