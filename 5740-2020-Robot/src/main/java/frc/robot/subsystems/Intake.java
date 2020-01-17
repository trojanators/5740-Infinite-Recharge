/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.SparkMax;
import com.team2363.logger.HelixLogger;

import frc.robot.*;

import frc.robot.util.CvsLoggerStrings;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {

  /**
   * Creates a new ExampleSubsystem.
   */
  private final TalonSRX m_robotIntake = new WPI_TalonSRX(Constants.IntakeMotor);

  private final TalonSRX m_intakeFlip = new WPI_TalonSRX(Constants.FlipMotor);

  private final Encoder m_intakeEncoder = new Encoder(5,6);

  //private final PID intakePID = new PID(Constants.PDrive, Constants.IDrive, Constants.DDrive, 1.0);

  
  new JoystickButton(m_intakeOpen, Button.kX.value)
  .toggleWhenPressed(new InstantCommand(m_intakeFlip::enable, m_intakeFlip));

  //Turn on the intake system
  new JoystickButton(m_intakeOn, Button.kA.value)
  .toggleWhenPressed(new InstantCommand(m_robotIntake::enable, m_robotIntake));
  
  // Turn off the when the 'B' button is pressed
  new JoystickButton(m_intakeOff, Button.kB.value)
  .toggleWhenPressed(new InstantCommand(m_robotIntake::disable, m_robotIntake));

  /*
   * ` *Auto Intake flips down -Actuator (Define) -Control 2 motors -one for belts
   * -one for fold Reverse mode incase ball is stuck Fold up contengency
   */

  public Intake() {
    //intakePID.setMaxOutput(1.0);
    //HelixLogger.getInstance().addStringSource("Intake Subsystem", CvsLoggerStrings.Init::toString);
    // starts up intake and counts the balls via motion sensor, once over 5 it stops
  }

  /*
   * public void Storage(){ If (m_robotIntake = stop){ m_storage.set(on) } else{
   * m_storage.set(off) } }
   * 
   */
  // starts and stops storage pully system

  public double getDistance(){
    return m_intakeEncoder.getDistance();
  }

  public double getRate(){
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
