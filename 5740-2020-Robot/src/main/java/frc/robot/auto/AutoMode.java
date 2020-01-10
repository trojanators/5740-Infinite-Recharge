package frc.robot.auto;

import frc.robot.RobotContainer;

public abstract class AutoMode extends RobotContainer{
	
	public AutoMode(){
	}
	
	public abstract void init();
	
	public abstract void execute();
	
	public abstract boolean isValid(boolean startOnLeft, boolean switchOnLeft, boolean scaleOnLeft);
	
	public abstract void outputToSmartDashboard();
	
}
