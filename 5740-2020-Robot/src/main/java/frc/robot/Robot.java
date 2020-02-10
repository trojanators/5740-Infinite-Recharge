/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.team2363.logger.HelixLogger;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.auto.AutoMode;
import frc.robot.auto.TestPath;
import frc.robot.pathfollower.TrajectoryDriveController;
import frc.robot.subsystems.DashBoard;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;
  public DashBoard m_DashBoard;

  private RobotContainer m_robotContainer;
  //private TestPath m_testPath;
  private Drivetrain m_drivetrain;
  private TrajectoryDriveController controller;

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer. This will perform all our button bindings,
    // and put our
    // autonomous chooser on the dashboard.

    m_robotContainer = new RobotContainer();
    //m_testPath = new TestPath(m_robotContainer.getDrivetrain());
    //m_drivetrain = m_robotContainer.getDrivetrain();
    m_drivetrain.calibrateGyro();
    // dash = new TestModeDashboard();

  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for
   * items like diagnostics that you want ran during disabled, autonomous,
   * teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler. This is responsible for polling buttons, adding
    // newly-scheduled
    // commands, running already-scheduled commands, removing finished or
    // interrupted commands,
    // and running subsystem periodic() methods. This must be called from the
    // robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();

    HelixLogger.getInstance().saveLogs();
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
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();
    // autoMode = CIAObjects.autoSelector.selectAuto();
    // schedule the autonomous command (example)
    // if (m_autonomousCommand != null) {
    // m_autonomousCommand.schedule();
    // }
    //m_testPath.init();
    /*m_testPath.getController().getLeft().reset();
    m_testPath.getController().getRight().reset();
    m_testPath.getController().getLeft().configure(m_robotContainer.kp.getDouble(0), 0, m_robotContainer.kd.getDouble(0), m_robotContainer.kv.getDouble(0), m_robotContainer.ka.getDouble(0));
		m_testPath.getController().getRight().configure(m_robotContainer.kp.getDouble(0), 0,m_robotContainer.kd.getDouble(0), m_robotContainer.kv.getDouble(0), m_robotContainer.ka.getDouble(0));
		*/

  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {

    // DO NOT REMOVE THIS LOGGER Cant Be Called in Commands
    HelixLogger.getInstance().saveLogs();

    //m_testPath.execute();
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    CommandScheduler.getInstance().run();

    // DO NOT REMOVE THIS LOGGER Cant Be Called in Commands or in subsystems
    HelixLogger.getInstance().saveLogs();

  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
    CommandScheduler.getInstance().run();

    // DO NOT REMOVE THIS LOGGER Cant Be Called in Commands or in subsystems
    HelixLogger.getInstance().saveLogs();
  }
}
