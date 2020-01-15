package frc.robot.auto;

import frc.robot.RobotContainer;
import frc.robot.auto.arrays.Turn90Path;
import frc.robot.pathfollower.Trajectory;
import frc.robot.pathfollower.TrajectoryDriveController;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj.Timer;

public class TestPath extends AutoMode {
    private Drivetrain m_drivetrain;
	private TrajectoryDriveController controller;
	private Trajectory trajectoryLeft;
	private Trajectory trajectoryRight;
	private Timer timer;
	private RobotContainer m_robotContainer; 
	
	
	private boolean startOnLeft;
	
	public TestPath(Drivetrain drivetrain) {
        System.out.println("Starting FarSideOneCubeScaleAuto");
        m_drivetrain = drivetrain;
		timer = new Timer();
		trajectoryLeft = Turn90Path.trajectoryArray[0];
		trajectoryRight = Turn90Path.trajectoryArray[1];
		controller = new TrajectoryDriveController(trajectoryLeft, trajectoryRight, -1.0, m_drivetrain);
		timer.start();
		timer.reset();
	}

	@Override
	public void init() {
		m_drivetrain.zeroSensors();
		
	}
	



	@Override
	public void execute() {
		if(!controller.onTarget()){// Still driving the path.
			controller.update();// does the calculations and updates the driveBase
		}
		else{ // Finished the path.
			//trajectoryLeft = Turn90Path.trajectoryArray[0];
			//trajectoryRight = Turn90Path.trajectoryArray[1];
			//if(startOnLeft) controller = new TrajectoryDriveController(trajectoryLeft, trajectoryRight, 1.0);
			//else controller = new TrajectoryDriveController(trajectoryLeft, trajectoryRight, -1.0);
			//state = State.FINISHED;
			timer.reset();
			m_drivetrain.setLeftRightPower(0,0);
			m_drivetrain.zeroEncoders();
			m_drivetrain.resetPID();
			//if(!driveBase.gyroIsConnected()) System.out.println("Gyro is not connected!!! Using encoders to execute turn.");
         }
     }
}
