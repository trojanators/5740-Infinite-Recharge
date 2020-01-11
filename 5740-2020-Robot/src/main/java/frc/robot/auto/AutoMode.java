package frc.robot.auto;

import frc.robot.Systems;

public abstract class AutoMode extends Systems {
	
	public AutoMode(){
	}
	
	public abstract void init();
	
	public abstract void execute();
	
	public abstract boolean isValid(boolean startOnLeft, boolean switchOnLeft, boolean scaleOnLeft);
	
	public abstract void outputToSmartDashboard();
	
}
