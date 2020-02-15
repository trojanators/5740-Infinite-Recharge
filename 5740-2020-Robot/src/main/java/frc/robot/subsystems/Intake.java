/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import frc.robot.Constants;
import frc.robot.util.CvsLoggerStrings;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.util.PID;

public class Intake extends SubsystemBase {

  /**
   * Creates a new ExampleSubsystem.
   */
  private final VictorSPX m_robotIntake = new VictorSPX(Constants.kIntakeMotor);

  private final TalonSRX m_intakeFlip = new TalonSRX(Constants.kFlipMotor);

  private final Encoder m_intakeEncoder = new Encoder(Constants.kIntakeEncoderOne, Constants.kIntakeEncoderTwo);
  private final DigitalInput m_absoluteEncoder = new DigitalInput(Constants.kIntakeAbsoluteInput);

  private final PID intakePID = new PID(Constants.PIntake, Constants.IIntake, Constants.DIntake, Constants.intakeEpsilon);

  public Intake() {
    intakePID.setMaxOutput(1.0);
    //m_absoluteEncoder.get
    //HelixLogger.getInstance().addStringSource("Intake Subsystem", CvsLoggerStrings.Init::toString);
  }

  public void setFlipPower(double power){
    m_intakeFlip.set(ControlMode.PercentOutput, power); 
    // Sets the power of the motor that flips out the intake
  }

  public void setIntakePower(double power){
    m_robotIntake.set(ControlMode.PercentOutput, power); 
    //Sets the power of the motor that turns the belts for the intake
  }

  public void setReverseIntakePower(double power){
    m_robotIntake.set(ControlMode.PercentOutput, -power); 
  }
  
  public double getEncoderDistance(){
    return m_intakeEncoder.getDistance();
    //To track how many rotations of the motor of intake
  }

  public void zeroIntakeEncoders() {
    m_intakeEncoder.reset();
    //Resets intake encoders
	}

  public void setpointPID(double setpoint) {
    intakePID.setDesiredValue(setpoint);
  }

  public double intakeCalcPID() {
    return intakePID.calcPID(getEncoderDistance());
  }

  public boolean pidIsFinished() {
    return intakePID.isDone();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    // starts system?
    //System.out.println(getEncoderDistance());
  }
}
