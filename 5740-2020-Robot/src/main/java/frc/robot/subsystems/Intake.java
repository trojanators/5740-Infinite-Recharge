/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.SparkMax;
import com.team2363.logger.HelixLogger;

import frc.robot.*;

import frc.robot.util.CvsLoggerStrings;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.util.PID;

public class Intake extends SubsystemBase {

  /**
   * Creates a new ExampleSubsystem.
   */
  private final TalonSRX m_robotIntake = new TalonSRX(Constants.kIntakeMotor);

  private final TalonSRX m_intakeFlip = new TalonSRX(Constants.kFlipMotor);

  private final Encoder m_intakeEncoder = new Encoder(Constants.kIntakeEncoderRight, Constants.kIntakeEncoderLeft);

  private final PID intakePID = new PID(Constants.PIntake, Constants.IIntake, Constants.DIntake, Constants.intakeEpsilon);

  
  /*
   * ` *Auto Intake flips down -Actuator (Define) -Control 2 motors -one for belts
   * -one for fold Reverse mode incase ball is stuck Fold up contengency
   */

  public Intake() {
    intakePID.setMaxOutput(1.0);
    //HelixLogger.getInstance().addStringSource("Intake Subsystem", CvsLoggerStrings.Init::toString);
    // starts up intake and counts the balls via motion sensor, once over 5 it stops
  }

  /*
   * public void Storage(){ If (m_robotIntake = stop){ m_storage.set(on) } else{
   * m_storage.set(off) } }
   * 
   */
  // starts and stops storage pully system
  public void setFlipPower(double power){
    m_intakeFlip.set(ControlMode.PercentOutput, power);
  }

  public void setIntakePower(double power){
    m_robotIntake.set(ControlMode.PercentOutput, power);
  }

  public double getEncoderDistance(){
    return m_intakeEncoder.getDistance();
  }

  public double getEncoderRate(){
    return m_intakeEncoder.getRate();
  }

 /* public void setIntakePower(){
    m_intakeEncoder.set();
  }*/

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    /*
     * Intake.startIntake;
     */
    // starts system?
  }
}
