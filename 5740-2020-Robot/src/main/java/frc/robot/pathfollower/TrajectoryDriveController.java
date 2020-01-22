package frc.robot.pathfollower;

/*
 * TrajectoryDriveController.java
 * Mostly stolen from team 254's 2014 code. Drives the robot along a path.
 * Uses TrajectoryFollower.java to calculate the outputs before the angle 
 * is taken into consideration.
 * @author Julia Cecchetti
 */

import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drivetrain;
import frc.robot.pathfollower.TrajectoryFollower;

public class TrajectoryDriveController {
	Drivetrain m_drivetrain;
	TrajectoryFollower followerLeft;
	TrajectoryFollower followerRight;
	double lefterrorAccumulator, righterrorAccumulator = 0;
	double direction;
	double kTurn = 0/1000.0;//Not using gyro for now
	RobotContainer m_robotContainer; 

	public TrajectoryDriveController(Trajectory trajectoryLeft, Trajectory trajectoryRight, double direction, Drivetrain drivetrain) {
		this.m_drivetrain = drivetrain;
		followerLeft = new TrajectoryFollower("left", trajectoryLeft);
		followerRight = new TrajectoryFollower("right", trajectoryRight);
		this.direction = direction;
		//followerLeft.configure(SmartDashboard.getDouble("Kp Turret"), 0, 0, SmartDashboard.getDouble("Ki Turret"), SmartDashboard.getDouble("Kd Turret"));
		//followerRight.configure(SmartDashboard.getDouble("Kp Turret"), 0, 0, SmartDashboard.getDouble("Ki Turret"), SmartDashboard.getDouble("Kd Turret"));
		followerLeft.configure(0, 0, 0, 0, 0);
		followerRight.configure(0, 0, 0, 0, 0);
	}
	public TrajectoryFollower getLeft(){
		return followerLeft;
	}
	public TrajectoryFollower getRight() {
		return followerRight;
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
		m_drivetrain.zeroSensors();
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
			m_drivetrain.setLeftRightPower(0,0);
			System.out.println("On Target");
		} 
		else {
			double distanceL = direction * m_drivetrain.leftEncoderDistance()/217.3;
			double distanceR = direction * m_drivetrain.rightEncoderDistance()/217.3;

			double speedLeft = direction * followerLeft.calculate(distanceL);
			double speedRight = direction * followerRight.calculate(distanceR);
			
			lefterrorAccumulator += followerLeft.last_error_;
			righterrorAccumulator += followerRight.last_error_;

			double goalHeading = followerLeft.getHeading();
			double observedHeading = -m_drivetrain.getGyroYaw();
			double angleDiffRads = observedHeading - goalHeading;
			double angleDiff = Math.toDegrees(angleDiffRads);
			double turn = kTurn * angleDiff;
			
			m_drivetrain.setLeftRightPower(speedLeft + turn, speedRight - turn);
		}
	}
	
	public void updateTurn(){
		if (onTarget()) {
			m_drivetrain.setLeftRightPower(0,0);
			System.out.println("On Target");
		} 
		else {
			double distanceL = -direction * m_drivetrain.leftEncoderDistance()/217.3;
			double distanceR = direction * m_drivetrain.rightEncoderDistance()/217.3;

			double speedLeft = -direction * followerLeft.calculate(distanceL);
			double speedRight = direction * followerRight.calculate(distanceR);
			
			m_drivetrain.setLeftRightPower(speedLeft, speedRight);
		}
	}

}