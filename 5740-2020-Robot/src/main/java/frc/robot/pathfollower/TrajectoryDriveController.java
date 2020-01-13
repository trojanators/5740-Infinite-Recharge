package frc.robot.pathfollower;

/*
 * TrajectoryDriveController.java
 * Mostly stolen from team 254's 2014 code. Drives the robot along a path.
 * Uses TrajectoryFollower.java to calculate the outputs before the angle 
 * is taken into consideration.
 * @author Julia Cecchetti
 */

import frc.robot.Constants;
import frc.robot.subsystems.Drivetrain;

public class TrajectoryDriveController {

	Drivetrain drivetrain;
	TrajectoryFollower followerLeft;
	TrajectoryFollower followerRight;
	double lefterrorAccumulator, righterrorAccumulator = 0;
	double direction;
	double kTurn = 0/1000.0;//Not using gyro for now
	
	public TrajectoryDriveController(Trajectory trajectoryLeft, Trajectory trajectoryRight, double direction) {
		followerLeft = new TrajectoryFollower("left", trajectoryLeft);
		followerRight = new TrajectoryFollower("right", trajectoryRight);
		this.direction = direction;
		//followerLeft.configure(SmartDashboard.getDouble("Kp Turret"), 0, 0, SmartDashboard.getDouble("Ki Turret"), SmartDashboard.getDouble("Kd Turret"));
		//followerRight.configure(SmartDashboard.getDouble("Kp Turret"), 0, 0, SmartDashboard.getDouble("Ki Turret"), SmartDashboard.getDouble("Kd Turret"));
		followerLeft.configure(Constants.kP, 0, Constants.kD, Constants.kV, Constants.kA);
		followerRight.configure(Constants.kP, 0, Constants.kD, Constants.kV, Constants.kA);
	}

	public boolean onTarget() {
		return followerLeft.isFinishedTrajectory();
	}
	
	public boolean almostDone(){
		return followerLeft.isAlmostFinishedTrajectory();
	}

	public double timeLeft(){
		return followerLeft.timeLeft();
	}
	public void reset() {
		followerLeft.reset();
		followerRight.reset();
		drivetrain.zeroSensors();
		lefterrorAccumulator = righterrorAccumulator = 0;
	}

	public int getFollowerCurrentSegment() {
		return followerLeft.getCurrentSegment();
	}

	public int getNumSegments() {
		return followerLeft.getNumSegments();
	}

	public void update() {
		
		if (onTarget()) {
			drivetrain.setLeftRightPower(0,0);
			System.out.println("On Target");
		} 
		else {
			double distanceL = direction * drivetrain.leftEncoderDistance()/217.3;
			double distanceR = direction * drivetrain.rightEncoderDistance()/217.3;

			double speedLeft = direction * followerLeft.calculate(distanceL);
			double speedRight = direction * followerRight.calculate(distanceR);
			
			lefterrorAccumulator += followerLeft.last_error_;
			righterrorAccumulator += followerRight.last_error_;

			double goalHeading = followerLeft.getHeading();
			double observedHeading = -drivetrain.getGyroYaw();
			double angleDiffRads = observedHeading - goalHeading;
			double angleDiff = Math.toDegrees(angleDiffRads);
			double turn = kTurn * angleDiff;
			
			drivetrain.setLeftRightPower(speedLeft + turn, speedRight - turn);
		}
	}
	
	public void updateTurn(){
		if (onTarget()) {
			drivetrain.setLeftRightPower(0,0);
			System.out.println("On Target");
		} 
		else {
			double distanceL = -direction * drivetrain.leftEncoderDistance()/217.3;
			double distanceR = direction * drivetrain.rightEncoderDistance()/217.3;

			double speedLeft = -direction * followerLeft.calculate(distanceL);
			double speedRight = direction * followerRight.calculate(distanceR);
			
			drivetrain.setLeftRightPower(speedLeft, speedRight);
		}
	}

}

