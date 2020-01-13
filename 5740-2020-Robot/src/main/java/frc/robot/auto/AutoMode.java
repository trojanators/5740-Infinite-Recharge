package frc.robot.auto;

public abstract class AutoMode {
	
	public AutoMode(){
	}
	
	public abstract void init();
	
	public abstract void execute();
	
	public abstract boolean isValid(boolean startOnLeft, boolean switchOnLeft, boolean scaleOnLeft);
	
	public abstract void outputToSmartDashboard();
	
}
