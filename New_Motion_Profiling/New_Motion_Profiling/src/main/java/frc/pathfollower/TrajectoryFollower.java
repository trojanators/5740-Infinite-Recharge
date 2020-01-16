package frc.pathfollower;

/*
 * TrajectoryFollower.java
 * PID + Feedforward controller for following a Trajectory.
 * @author Jared341
 */

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TrajectoryFollower {

	  private double kp_ = 0;
	  //private double ki_;  // Not currently used, but might be in the future.
	  private double kd_ = 0;
	  private double kv_ = 0;
	  private double ka_ = 0;
	  public double last_error_;
	  private double current_heading = 0;
	  private int current_segment;
	  private Trajectory profile_;
	  public String name;
	  
	  public TrajectoryFollower(String name, Trajectory profile) {
	    this.name = name;
	    this.profile_ = profile;
	  }
	  
	  // Sets all of the PIDF values. Must be called before calculate() is used!!!
	  public void configure(double kp, double ki, double kd, double kv, double ka) {
	    kp_ = kp;
	    //ki_ = ki;
	    kd_ = kd;
	    kv_ = kv;
	    ka_ = ka;
	  }

	  public void reset() {
	    last_error_ = 0.0;
	    current_segment = 0;
	  }
	  
	  /* This is where the magic happens. Uses PIDF to calculate the 
	   * left and right outputs before the gyro is taken into consideration.
	   * calculate() is used in TrajectoryDriveController*/
	  public double calculate(double distance_so_far) {
	   
	    if (current_segment < profile_.getNumSegments()) {
	      Trajectory.Segment segment = profile_.getSegment(current_segment);
	      double error = segment.pos - distance_so_far;
	      double output = kp_ * error + kd_ * ((error - last_error_)
	              / segment.dt - segment.vel) + (kv_ * segment.vel
	              + ka_ * segment.acc);

	      last_error_ = error;
	      current_heading = segment.heading;
	      current_segment++;
	      
	      //double array[] = {current_segment, segment.pos, distance_so_far};
		  System.out.println("Current Segment: " + current_segment);
		  System.out.println("Segment Position: " + segment.pos);
		  System.out.println("Distance so far: " + distance_so_far);
	      //SmartDashboard.putNumber("CurrentSegment", current_segment);
	      SmartDashboard.putNumber(name + "FollowerSensor", distance_so_far);
	      SmartDashboard.putNumber(name + "FollowerGoal", segment.pos);
	      SmartDashboard.putNumber(name + "FollowerGoalVelocity", segment.vel);
	      //SmartDashboard.putNumber(name + "FollowerAcceleration", segment.acc);
	      SmartDashboard.putNumber(name + "FollowerError", error);
	      //SmartDashboard.putNumber(name + "Output", output);
	      return output;
	    } else {
	      return 0;
	    }
	  }

	  public double getHeading() {
	    return current_heading;
	  }

	  public boolean isFinishedTrajectory() {
		//return current_segment <= profile_.getNumSegments();
		return current_segment >= profile_.getNumSegments();
	  }
	  
	  public boolean isAlmostFinishedTrajectory(){
		  //return current_segment <= profile_.getNumSegments()*.85;
		  return current_segment >= profile_.getNumSegments()*.85;
	  }
	  
	  public double timeLeft(){
		  return (profile_.getNumSegments() - current_segment)*50.0/1000;
	  }
	  
	  public int getCurrentSegment() {
	    return current_segment;
	  }
	  
	  public int getNumSegments() {
	    return profile_.getNumSegments();
	  }
}
