/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.Map;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import frc.robot.commands.DriveSlowly;
import frc.robot.commands.DropIntake;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.RaiseIntake;
import frc.robot.commands.RunIntake;
import frc.robot.subsystems.Climb;
import frc.robot.subsystems.ControlPanel;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Turret;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
//import frc.robot.auto.AutoMode;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...

  private Drivetrain m_drivetrain = new Drivetrain(); // Robot Drivetrain
  private final Command m_autoCommand;
  private Intake m_Intake = new Intake();
  private final Command m_dropIntake;
  private JoystickButton dropIntakeButton;
  private final Command m_raiseIntake;
  private JoystickButton raiseIntakeButton;
  private final Command m_runIntake;
  private final JoystickButton runIntakeButton;
  

  //private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);
  /*private final Command m_autoCommand = 
    // zero encoders
    new InstantCommand(m_drivetrain::zeroSensors, m_drivetrain).andThen(
      // drive forward slowly
      new InstantCommand(m_drivetrain::driveForwardSlowly, m_drivetrain).andThen(
      //Drive forward for 1 second, timeout if 3 seconds go by  
      new WaitCommand(Constants.kAutoDriveTime).andThen(
      // stop driving  
      new InstantCommand(m_drivetrain::stop, m_drivetrain) 
      )));*/

  // Driver Controler
  public static Joystick m_driverController = new Joystick(Constants.kdriverJoystickPort);
  public static Joystick m_operatorController = new Joystick(Constants.koperatorJoystickPort);
  public NetworkTableEntry kp, kd, kv, ka; 
  

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    m_autoCommand = new DriveSlowly(m_drivetrain).withTimeout(3);
    // Configure the button bindings

    kp = Shuffleboard.getTab("PID")
      .add("proportional gain", 0)
      .withWidget(BuiltInWidgets.kNumberSlider).withSize(2, 2)
      .withProperties(Map.of("min", 0, "max", 5.0))
      .getEntry();

    kd = Shuffleboard.getTab("PID")
      .add("derivative gain", 0)
      .withWidget(BuiltInWidgets.kNumberSlider).withSize(2, 2)
      .withProperties(Map.of("min", 0, "max", 1.0))
      .getEntry();
    
    kv = Shuffleboard.getTab("PID")
      .add("velocity gain", 0)
      .withWidget(BuiltInWidgets.kNumberSlider).withSize(2, 2)
      .withProperties(Map.of("min", 0, "max", 0.5))
      .getEntry();
    
    ka = Shuffleboard.getTab("PID2")
      .add("acceleration gain", 0)
      .withWidget(BuiltInWidgets.kNumberSlider).withSize(2,2)
      .withProperties(Map.of("min", 0, "max", 0.5))
      .getEntry();

    dropIntakeButton = new JoystickButton(m_driverController, Constants.kdropIntakeButton);
    raiseIntakeButton = new JoystickButton(m_driverController, Constants.kraiseIntakeButton);
    runIntakeButton = new JoystickButton(m_driverController, Constants.krunIntakeButton);
    m_dropIntake = new DropIntake();
    m_raiseIntake = new RaiseIntake();
    m_runIntake = new RunIntake();
    configureButtonBindings();
    m_drivetrain.setDefaultCommand (
      new RunCommand(() -> m_drivetrain.deadbandedArcadeDrive(), m_drivetrain));
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */

  private void configureButtonBindings() {
    dropIntakeButton.whenPressed(m_dropIntake);
    raiseIntakeButton.whenPressed(m_raiseIntake);
    runIntakeButton.toggleWhenPressed(m_runIntake);
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_autoCommand;
  }
  public Drivetrain getDrivetrain() {
    return m_drivetrain;
  }
}
