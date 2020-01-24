package frc.robot;

/*
 * CIAConstants.java
 * Defines constants used all around the robot.
 * @author Julia Cecchetti
 */

public class CIAConstants {
	
	// Motor Port Mappings
	public static final int rightDrivePortA = 2;
	public static final int rightDrivePortB = 3;
	public static final int leftDrivePortA = 0;
	public static final int leftDrivePortB = 1;
	
	// Sensor DIO port Mappings
	public static final int leftEncoderPortA = 3;
	public static final int leftEncoderPortB = 4;
	public static final int rightEncoderPortA = 1;
	public static final int rightEncoderPortB = 2;
	
	// Drive Encoders are configured to read in feet
	public static final double driveEncoderDistancePerPulse = 1;///217.3;
	
	//Autonomous Constants
	public static final double kp = 3.5;//3.5
	public static final double kd = 0;
	public static final double kv = .08;//.08
	public static final double ka = .06;//.06

	public static final double Pturn = .05;//.055
	public static final double Iturn = 0;
	public static final double Dturn = .1;
	public static final double turnEpsilon = 3.0;
	
	public static final double Pdriveturn = .06;//.055
	public static final double Idriveturn = 0;
	public static final double Ddriveturn = 0.0;
	
	public static final double Pdrive = 1.5;
	public static final double Idrive = 0.0;
	public static final double Ddrive = 4.0;
	public static final double driveEbsilon = 2.0;

	public static final double Phold = -.0001;
	
	
}
