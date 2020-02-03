/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.team2363.logger.HelixLogger;
import frc.robot.Constants;
import frc.robot.util.CvsLoggerStrings;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.util.PID;

public class Intake extends SubsystemBase {

  /**
   * Creates a new ExampleSubsystem.
   */
  private final VictorSPX m_robotIntake = new VictorSPX(Constants.kIntakeMotor);

  private final VictorSPX m_intakeFlip = new VictorSPX(Constants.kFlipMotor);

  private final Encoder m_intakeEncoder = new Encoder(Constants.kIntakeEncoderOne, Constants.kIntakeEncoderTwo);

  private final PID intakePID = new PID(Constants.PIntake, Constants.IIntake, Constants.DIntake, Constants.intakeEpsilon);

  public Intake() {
    intakePID.setMaxOutput(1.0);
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

  public double getEncoderRate(){
    return m_intakeEncoder.getRate();
    //Tracks how many rotations/time (speed)
  }

  public void zeroIntakeEncoders() {
    m_intakeEncoder.reset();
    //Resets intake encoders
	}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    // starts system?
    System.out.println(getEncoderDistance());
  }
}
