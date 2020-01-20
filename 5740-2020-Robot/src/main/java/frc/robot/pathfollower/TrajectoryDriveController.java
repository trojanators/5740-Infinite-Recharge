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

	Drivetrain driveBase;
	TrajectoryFollower followerLeft;
	TrajectoryFollower followerRight;
	double lefterrorAccumulator, righterrorAccumulator = 0;
	double direction;
	double kTurn = 0/1000.0;//Not using gyro for now
	
	public TrajectoryDriveController(Trajectory trajectoryLeft, Trajectory trajectoryRight, double direction, Drivetrain drivetrain) {
		this.driveBase = drivetrain;
		followerLeft = new TrajectoryFollower("left", trajectoryLeft);
		followerRight = new TrajectoryFollower("right", trajectoryRight);
		this.direction = direction;
		//followerLeft.configure(SmartDashboard.getDouble("Kp Turret"), 0, 0, SmartDashboard.getDouble("Ki Turret"), SmartDashboard.getDouble("Kd Turret"));
		//followerRight.configure(SmartDashboard.getDouble("Kp Turret"), 0, 0, SmartDashboard.getDouble("Ki Turret"), SmartDashboard.getDouble("Kd Turret"));
		followerLeft.configure(Constants.kp, 0, Constants.kd, Constants.kv, Constants.ka);
		followerRight.configure(Constants.kp, 0, Constants.kd, Constants.kv, Constants.ka);
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
		driveBase.zeroSensors();
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
			driveBase.setLeftRightPower(0,0);
			//System.out.println("On Target");
		} 
		else {
			double distanceL = direction * driveBase.leftEncoderDistance()/651.899;/*217.3;*/
			double distanceR = direction * driveBase.rightEncoderDistance()/651.899;/*217.3;*/

			double speedLeft = direction * followerLeft.calculate(distanceL);
			double speedRight = direction * followerRight.calculate(distanceR);
			
			lefterrorAccumulator += followerLeft.last_error_;
			righterrorAccumulator += followerRight.last_error_;

			driveBase.setLeftRightPower(speedLeft, speedRight);
		}
	}
	
	public void updateTurn(){
		if (onTarget()) {
			driveBase.setLeftRightPower(0,0);
			//System.out.println("On Target");
		} 
		else {
			double distanceL = -direction * driveBase.leftEncoderDistance()/217.3;
			double distanceR = direction * driveBase.rightEncoderDistance()/217.3;

			double speedLeft = -direction * followerLeft.calculate(distanceL);
			double speedRight = direction * followerRight.calculate(distanceR);
			
			driveBase.setLeftRightPower(speedLeft, speedRight);
		}
	}
}

