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
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import frc.robot.commands.DriveSlowly;
import frc.robot.commands.ExampleCommand;
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
  private Intake m_intake;
  
  

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
  public static Joystick driverController = new Joystick(Constants.kjoystickPort);
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

    /*new JoystickButton(m_intakeOpen, Button.kX.value)
    .toggleWhenPressed(new InstantCommand(m_intakeFlip::enable, m_intakeFlip));
  
    //Turn on the intake system
    new JoystickButton(m_intakeOn, Button.kA.value)
    .toggleWhenPressed(new InstantCommand(m_robotIntake::enable, m_robotIntake));
    
    // Turn off the when the 'B' button is pressed
    new JoystickButton(m_intakeOff, Button.kB.value)
    .toggleWhenPressed(new InstantCommand(m_robotIntake::disable, m_robotIntake));*/
  
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
