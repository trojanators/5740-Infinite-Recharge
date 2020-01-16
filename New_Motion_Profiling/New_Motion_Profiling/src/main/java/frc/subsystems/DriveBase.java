package frc.subsystems;

import frc.robot.CIAConstants;
import frc.util.PID;


import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Victor;

public class DriveBase extends Subsystem{
	
	private Victor leftDriveA = new Victor(CIAConstants.leftDrivePortA);
	private Victor leftDriveB = new Victor(CIAConstants.leftDrivePortB);
	private Victor rightDriveA = new Victor(CIAConstants.rightDrivePortA);
	private Victor rightDriveB = new Victor(CIAConstants.rightDrivePortB);
	private Encoder leftEncoder = new Encoder(CIAConstants.leftEncoderPortA, CIAConstants.leftEncoderPortB);
	private Encoder rightEncoder = new Encoder(CIAConstants.rightEncoderPortA, CIAConstants.rightEncoderPortB);
	private PID turnPID = new PID(CIAConstants.Pturn, CIAConstants.Iturn, CIAConstants.Dturn, CIAConstants.turnEpsilon);
	private PID drivePID = new PID(CIAConstants.Pdrive, CIAConstants.Idrive, CIAConstants.Ddrive, 1.0);

	public DriveBase(){
		leftEncoder.setDistancePerPulse(1);
		rightEncoder.setDistancePerPulse(1);
		leftEncoder.setReverseDirection(true);
		rightEncoder.setReverseDirection(true);
		rightDriveA.setInverted(true);
		rightDriveB.setInverted(true);
		turnPID.setMaxOutput(1.0);
		drivePID.setMaxOutput(1.0);
	}
	
	public enum DriveState{
		OVERRIDE, STANDBY
	}
	
	public void setLeftRightPower(double left, double right){
		leftDriveA.set(left);
		leftDriveB.set(left);
		rightDriveA.set(right);
		rightDriveB.set(right);
		//System.out.println("DriveBase: " + left + ", " + right);
	}
	
	//Custom Arcade Drive
	public void CIADrive(double power, double turn, boolean precisionMode){
		double multiplier = 1;
		double turnLimiter = 1;//CIAConstants.turnLimiter;
		double deadband = .03;
		
		if(precisionMode) multiplier = .6;
		
		if(turn > 0) turn = turn*turn*multiplier;
		else turn = -turn*turn*multiplier;
		
		if(turn < deadband && turn > -deadband) turn = 0;
		if(power < deadband && power > -deadband) power = 0;
		
		
        double left;
        double right;
        left = -power + turn * turnLimiter;
        right = -power - turn * turnLimiter;
        setLeftRightPower(left, right);
	}
	

	public void stop(){
		leftDriveA.set(0);
		leftDriveB.set(0);
		rightDriveA.set(0);
		rightDriveB.set(0);
	}
	
	// Used for test mode
	public void setleftDriveA(double power){ leftDriveA.set(power); }
	public void setleftDriveB(double power){ leftDriveB.set(power); }
	public void setrightDriveA(double power){ rightDriveA.set(power); }
	public void setrightDriveB(double power){ rightDriveB.set(power); }
	
	/* Encoders */
	public double leftEncoderDistance(){
		return leftEncoder.getDistance();
	}
	public double rightEncoderDistance(){
		return rightEncoder.getDistance();
	}
	public double leftEncoderRate(){
		return leftEncoder.getRate();
	}
	public double rightEncoderRate(){
		return rightEncoder.getRate();
	}
	public void zeroEncoders(){
		leftEncoder.reset();
		rightEncoder.reset();
	}
	
		private boolean antiTipEnabled = true;
	private boolean lastEnable = false;
	
	
	public void enableAntiTip(boolean enable){
		if(enable){
			if(!lastEnable){// Only toggle once every button press. 
				antiTipEnabled = !antiTipEnabled;// Reverse State
				if(antiTipEnabled) System.out.println("Anti-Tip has been enabled");
				else System.out.println("Anti-Tip has been disabled");
			}
		}
		lastEnable = enable;
	}
	
	
	public boolean angleIsStable = false;
	
	
	public boolean distanceIsStable = false;
	
	
	public void resetPID(){
		distanceIsStable = false;
		angleIsStable = false;
		zeroEncoders();
	}
	
	
	public void holdPosition(){
		double left = leftEncoder.getDistance()*217.3*CIAConstants.Phold;
		double right = rightEncoder.getDistance()*217.3*CIAConstants.Phold;
		setLeftRightPower(left, right);
	}
	
	public void zeroSensors(){
		leftEncoder.reset();
		rightEncoder.reset();
	}
	
	

}
