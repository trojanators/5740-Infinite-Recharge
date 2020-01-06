/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;

import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Components.hardware.OperatorInterface;
import frc.robot.Components.hardware.RobotContainer;
import frc.robot.Components.hardware.RobotHardware;
import frc.robot.Robotmodes.auto.Auto;
import frc.robot.Robotmodes.teleop.TeleOp;
import frc.robot.Robotmodes.test.TestMode;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */

 //TODO:Learn command based commands

public class Robot extends TimedRobot {

  // Inits methods OI and hardware 
  public OperatorInterface OI;
  public RobotHardware hardware; 

  private RobotContainer m_robotContainer;

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer. This will perform all our button bindings,
    // and put our
    // autonomous chooser on the dashboard.

    OI = new OperatorInterface();

    hardware = new RobotHardware();
    
    m_robotContainer = new RobotContainer();
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for
   * items like diagnostics that you want ran during disabled, autonomous,
   * teleoperated and test.
   *
   */
  @Override
  public void robotPeriodic() {

    
    CommandScheduler.getInstance().run();
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   */
  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
  }

  /**
   * This autonomous runs the autonomous command selected by your
   * {@link RobotContainer} class.
   */
  @Override
  public void autonomousInit() {

    // Runs methods in the init auto function
    Auto.Init();
   

  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {

    //Runs Auto Methods every 20 ms
    Auto.Periodic();


  }

  @Override
  public void teleopInit() {

    // Runs methods in the init auto function
    TeleOp.Init();

  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {

    TeleOp.Periodic();
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();

    TestMode.Init();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {


    TestMode.Periodic();
  }
}
