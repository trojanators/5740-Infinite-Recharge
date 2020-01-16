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
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {

  /**
   * Creates a new ExampleSubsystem.
   */
  private final TalonSRX m_robotIntake = new WPI_TalonSRX(Constants.IntakeMotor);

  private final Ultrasonic m_ballCounter = new Ultrasonic(pingChannel, echoChannel);

  private final Joystick m_intakeOn = new Joystick(1); // Creates a joystick on port 1
  /*
   * ` *Auto Intake flips down -Actuator (Define) -Control 2 motors -one for belts
   * -one for fold Reverse mode incase ball is stuck Fold up contengency
   */
  /*
   * Private final GroundIntake m_robotIntake = new GroundIntake(motor(port));
   *
   * Private final BallCounter m_counter = new BallCounter();
   */
  // define

  
  public Intake() {
    m_intakeOn.whenPressed(new m_intakeOn(true));
    //Button activated intake for teleop
    //HelixLogger.getInstance().addStringSource("Intake Subsystem", CvsLoggerStrings.Init::toString);
    /*If (m_ballCounter <= 5){ m_robotIntake.set(0); 
      }else{ m_robotIntake.set(1); 
    }*/
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
