package frc.auto;

import frc.robot.CIAObjects;

public abstract class AutoMode extends CIAObjects {
	
	public AutoMode(){
	}
	
	public abstract void init();
	
	public abstract void execute();
	
	public abstract boolean isValid(boolean startOnLeft, boolean switchOnLeft, boolean scaleOnLeft);
	
	public abstract void outputToSmartDashboard();
	
}
