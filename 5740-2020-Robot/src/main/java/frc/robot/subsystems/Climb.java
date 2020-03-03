/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.util.PID;

public class Climb extends SubsystemBase {

  private final TalonFX climbFx = new TalonFX(Constants.kClimbFXCAN);
  public final PID climbPID = new PID(Constants.PClimb, Constants.IClimb, Constants.DClimb, Constants.climbEpsilon);
  //private final NetworkTableEntry ClimbP, Encoderticks, ClimbD, setpointEntry;
  

  public Climb() {

    // Zeros Encoder Reading
    zeroSensors();
    climbFx.configFactoryDefault();
    climbPID.setMaxOutput(Constants.kClimbMaxOutput);
    //Sets Climb PID output as full output
    /*climbPID.setMaxOutput(Constants.kClimbMaxOutput);

    // Setting shuffleboard PID tuner
    ClimbP = Shuffleboard.getTab("Climb").add("Proportional", 0).withWidget(BuiltInWidgets.kTextView).getEntry();
    ClimbD = Shuffleboard.getTab("Climb").add("Deritive", 0).withWidget(BuiltInWidgets.kTextView).getEntry();

    // Encoder Ticks and Setpoint for Pid loop
    Encoderticks = Shuffleboard.getTab("Climb").add("Encoder ticks", 0).withWidget(BuiltInWidgets.kTextView).getEntry();
    setpointEntry = Shuffleboard.getTab("Climb").add("Setpoint", 0).withWidget(BuiltInWidgets.kTextView).getEntry();   */ 
    
  }

  //Get's Climbs TallonFX Encoder Pos
  public double getClimbDistance() {
		return climbFx.getSelectedSensorPosition();
  }
  
  // Zeros ClimbFX encoder Pos
  public void zeroSensors() {
    climbFx.setSelectedSensorPosition(0);
  }
  
  // Sets ClimbFx speed for Climb
  public void setPower(double power) {
    climbFx.set(ControlMode.PercentOutput, power);
  }

  // Sets ClimbFx SetPos 
  public void setClimbSetpoint(double setpoint) {
    climbPID.setDesiredValue(setpoint);
  }

  // Reads if Pid is finished 
  public boolean isPidFinished() {
      return climbPID.isDone();
  }

  // Calcs Pid for Climb
  public void climbPidCalc(double power){
    power = climbPID.calcPID(getClimbDistance());
  }

  @Override
  public void periodic() {
  }
}
