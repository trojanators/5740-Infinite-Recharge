package frc.robot.auto;

import frc.robot.auto.arrays.StraightTestPath;
import frc.robot.auto.arrays.NearScalePath;
import frc.robot.auto.arrays.RightSwitchPath;
import frc.robot.pathfollower.Trajectory;
import frc.robot.pathfollower.TrajectoryDriveController;
import frc.robot.subsystems.Drivetrain;

public class TestPath extends AutoMode {
	
	private Drivetrain driveBase;
	private TrajectoryDriveController controller;
	private Trajectory trajectoryLeft;
	private Trajectory trajectoryRight;
	
		public TestPath(Drivetrain drivetrain){
		System.out.println("Starting TestAuto");
		// Get the left and right trajectories from auto.arrays
		// if the robot is on the blue side, run normally
		this.driveBase = drivetrain;
		trajectoryLeft = NearScalePath.trajectoryArray[0];
		trajectoryRight = NearScalePath.trajectoryArray[1];
		// Create a new TDC controller using the trajectories from 
		controller = new TrajectoryDriveController(trajectoryLeft, trajectoryRight, 1.0, driveBase);
	}
	
	@Override
	public void init() {
		//timer.start();
		driveBase.zeroSensors();
		controller.reset();
	}
	
	
	/* execute() should be called in autonomousPeriodic */
	@Override
	public void execute() {
		controller.update();// does the calculations and updates the driveBase
	}
}
