/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.SparkMax;
import com.team2363.logger.HelixLogger;

import frc.robot.*;

import frc.robot.util.CvsLoggerStrings;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {

  /**
   * Creates a new ExampleSubsystem.
   */
  // public static motor IntakeMotor = new motor()
  /*
   * ` *Auto Intake flips down -Actuator (Define) -Control 2 motors -one for belts
   * -one for fold Reverse mode incase ball is stuck Fold up contengency
   */
  /*
   * Private final GroundIntake m_robotIntake = new GroundIntake(motor(port));
   * Private final Storage m_storage = new Storage(motor);
   * 
   * Private final BallCounter m_counter = new BallCounter();
   */
  // define

  public Intake() {
    HelixLogger.getInstance().addStringSource("Intake Subsystem", CvsLoggerStrings.Init::toString);
    /*
     * If (m_counter < 5){ m_robotIntake.set(on); } else{ m_robotIntake.set(stop); }
     */
    // starts up intake and counts the balls via motion sensor, once over 5 it stops
  }

  /*
   * public void Storage(){ If (m_robotIntake = stop){ m_storage.set(on) } else{
   * m_storage.set(off) } }
   * 
   */
  // starts and stops storage pully system

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    /*
     * Intake.startIntake;
     */
    // starts system?
  }
}
