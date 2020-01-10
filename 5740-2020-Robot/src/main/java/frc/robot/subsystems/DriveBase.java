/*package org.usfirst.frc.team291.subsystems;

import org.usfirst.frc.team291.robot.CIAConstants;
import org.usfirst.frc.team291.robot.CIAObjects;
import org.usfirst.frc.team291.util.PID;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveBase extends Subsystem{
	
	private Spark leftDriveA = new Spark(CIAConstants.leftDrivePortA);
	private Spark leftDriveB = new Spark(CIAConstants.leftDrivePortB);
	private Spark rightDriveA = new Spark(CIAConstants.rightDrivePortA);
	private Spark rightDriveB = new Spark(CIAConstants.rightDrivePortB);
	private Solenoid shifter = new Solenoid(0, CIAConstants.shifterPort);
	private Encoder leftEncoder = new Encoder(CIAConstants.leftEncoderPortA, CIAConstants.leftEncoderPortB);
	private Encoder rightEncoder = new Encoder(CIAConstants.rightEncoderPortA, CIAConstants.rightEncoderPortB);
	private AHRS gyro;
	private PID turnPID = new PID(CIAConstants.Pturn, CIAConstants.Iturn, CIAConstants.Dturn, CIAConstants.turnEpsilon);
	private PID drivePID = new PID(CIAConstants.Pdrive, CIAConstants.Idrive, CIAConstants.Ddrive, 1.0);

	private double gyroWorkingZero = 0;
	
	public DriveBase(){
		leftEncoder.setDistancePerPulse(1);
		rightEncoder.setDistancePerPulse(1);
		rightDriveA.setInverted(true);
		rightDriveB.setInverted(true);
	//	gyro = new AHRS(I2C.Port.kMXP);
	//	gyro.enableLogging(false);
		turnPID.setMaxOutput(1.0);
		drivePID.setMaxOutput(1.0);
	}
	
	/*public enum DriveState{
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
		
		if(antiTipEnabled && gyroIsConnected() && CIAObjects.cubeArm.armIsHigh()){
			if(gyro.getRoll() < -CIAConstants.antiTipThreshold){
				power -= CIAConstants.kpAntiTip*gyro.getRoll();
				CIAObjects.lights.antiTip = true;
				SmartDashboard.putBoolean("Tipping", true);
			}
			else{
				SmartDashboard.putBoolean("Tipping", false);
				CIAObjects.lights.antiTip = false;
			}
		}
		else{
			CIAObjects.lights.antiTip = false;
			SmartDashboard.putBoolean("Tipping", false);
		}
		
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
	
	// Encoders
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
	
	private boolean lowGear = false;
	private boolean lastShift = false;
	
	public void shift(boolean shift){
		if(shift){
			if(!lastShift){// Only toggle once every button press. 
				lowGear = !lowGear;// Reverse the Shift State
			}
		}
		shifter.set(lowGear);
		lastShift = shift;
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
	
	// Gyro
	public double getGyroYaw(){
		return gyro.getAngle() - gyroWorkingZero;
	}
	
	public void setGyroYaw(double yaw){
		gyroWorkingZero = gyro.getAngle() - yaw;
	}
	
	public boolean gyroIsConnected(){
		return gyro.isConnected();
	}
	
	public boolean angleIsStable = false;
	
	public void turnToAngle(double setpointAngle){
		double currentAngle = getGyroYaw();
		turnPID.setMaxOutput(1.0);
		turnPID.setConstants(CIAConstants.Pturn, CIAConstants.Iturn, CIAConstants.Dturn);
		if(!turnPID.isDone()){
			turnPID.setDesiredValue(setpointAngle);
			double turnPower = turnPID.calcPID(currentAngle);
			setLeftRightPower(turnPower, -turnPower);
			angleIsStable = false;
		}
		else {
			setLeftRightPower(0,0);
			angleIsStable = true;
		}
	}
	
	public boolean distanceIsStable = false;
	
	public void driveToDistancePID(double setpointDistance, double maxPower, double angle){
		if(!drivePID.isDone()){
			turnPID.setConstants(CIAConstants.Pdriveturn, CIAConstants.Idriveturn, CIAConstants.Ddriveturn);
			turnPID.setMaxOutput(.6);
			drivePID.setMaxOutput(maxPower);
			drivePID.setDesiredValue(setpointDistance);
			turnPID.setDesiredValue(angle);
			double power = drivePID.calcPID(leftEncoder.getDistance()/217.3);
			double turn = turnPID.calcPID(getGyroYaw());
			setLeftRightPower(power + turn, power - turn);
			distanceIsStable = false;
		}
		else{
			setLeftRightPower(0,0);
			distanceIsStable = true;
		}
	}
	
	boolean turned = false;
	public void fastTurnAndDriveDistancePID(double setpointDistance, double maxPower, double angle){
		double currentAngle = getGyroYaw();
		if(!turned){
			turnPID.setConstants(CIAConstants.Pturn, CIAConstants.Iturn, CIAConstants.Dturn);
			turnPID.setDesiredValue(angle);
			double turnPower = turnPID.calcPID(currentAngle);
			setLeftRightPower(turnPower, -turnPower);
			if(Math.abs(currentAngle - angle) < 5) turned = true;
			else turned = false;
		}
		else if(!drivePID.isDone()){
			turnPID.setConstants(CIAConstants.Pdriveturn, CIAConstants.Idriveturn, CIAConstants.Ddriveturn);
			drivePID.setMaxOutput(maxPower);
			drivePID.setDesiredValue(setpointDistance);
			turnPID.setDesiredValue(angle);
			double power = drivePID.calcPID(leftEncoder.getDistance());
			double turn = turnPID.calcPID(getGyroYaw());
			setLeftRightPower(power + turn, power - turn);
			distanceIsStable = false;
		}
		else{
			setLeftRightPower(0,0);
			distanceIsStable = true;
		}
		
	}
	
	public void resetPID(){
		turned = false;
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
		setGyroYaw(0);
		leftEncoder.reset();
		rightEncoder.reset();
	}
	
	public void outputToSmartDashboard(){
		SmartDashboard.putNumber("Left Distance", leftEncoder.getDistance()/217.3);
		SmartDashboard.putNumber("Right Distance", rightEncoder.getDistance()/217.3);
		//SmartDashboard.putNumber("Left Velocity", leftEncoder.getRate()/217.3);
		//SmartDashboard.putNumber("Right Velocity", rightEncoder.getRate()/217.3);
		SmartDashboard.putNumber("Gyro Yaw", gyro.getAngle());
		SmartDashboard.putNumber("Robot Pitch", gyro.getRoll());
		SmartDashboard.putBoolean("Gyro Connected", gyro.isConnected());
	}


}*/
