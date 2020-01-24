package frc.auto;

import frc.auto.arrays.StraightTestPath;
import frc.auto.arrays.RightSwitchPath;
import frc.auto.arrays.NearScalePath;
import frc.pathfollower.Trajectory;
import frc.pathfollower.TrajectoryDriveController;

public class TestAuto extends AutoMode {
	
	private TrajectoryDriveController controller;
	private Trajectory trajectoryLeft;
	private Trajectory trajectoryRight;
	
	public TestAuto(){
		System.out.println("Starting TestAuto");
		// Get the left and right trajectories from auto.arrays
		// if the robot is on the blue side, run normally
			trajectoryLeft = NearScalePath.trajectoryArray[0];
			trajectoryRight = NearScalePath.trajectoryArray[1];
		 
		// Create a new TDC controller using the trajectories from 
		controller = new TrajectoryDriveController(trajectoryLeft, trajectoryRight, 1.0);
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

	@Override
	public void outputToSmartDashboard() {
		
		
	}

	@Override
	public boolean isValid(boolean startOnLeft, boolean switchOnLeft, boolean scaleOnLeft) {
		// TODO Auto-generated method stub
		return true;
	}

}
