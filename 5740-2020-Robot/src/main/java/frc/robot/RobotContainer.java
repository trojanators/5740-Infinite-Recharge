/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.Map;
import java.util.function.BooleanSupplier;

import edu.wpi.first.networktables.NetworkTableEntry;
//import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj.Joystick;
//import edu.wpi.first.wpilibj.buttons.*;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.IndexIn;
import frc.robot.commands.triggers.IndexInTrigger;
import frc.robot.commands.ShootCommand;
import frc.robot.subsystems.Climb;
import frc.robot.subsystems.ControlPanel;
import frc.robot.subsystems.DashBoard;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Turret;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.auto.AutoMode;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final Indexer m_indexer = new Indexer();
  private Drivetrain m_drivetrain = new Drivetrain(); // Robot Drivetrain
  private final DashBoard m_dash = new DashBoard(m_drivetrain, m_indexer);
  private final Climb m_climb = new Climb();
  private Turret m_turret = new Turret();
  private JoystickButton m_raiseClimbButton;

  private final NetworkTableEntry kp, kd, kv, ka;

  private final Command m_indexIn;
  private final Trigger m_indexInTrigger;

  // Driver Controler
  public static Joystick m_driverController = new Joystick(Constants.kjoystickDriverPort);
  public static Joystick m_operatorController = new Joystick(Constants.kjoystickOperatorPort);

  public final Double ClimbSpeed = m_operatorController.getRawAxis(Constants.leftStickY);
  public final Double LiftSpeed = m_operatorController.getRawAxis(Constants.rightStickX);
  private final Command m_autoCommand;
  private JoystickButton m_shootCommandButton;
  private final Command m_shootCommand; 
  

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   * 
   *
   */
  public RobotContainer() {
    // Configure the button bindings

    kp = Shuffleboard.getTab("PID").add("proportional gain", 0).withWidget(BuiltInWidgets.kNumberSlider).withSize(2, 2)
        .withProperties(Map.of("min", 0, "max", 5.0)).getEntry();

    kd = Shuffleboard.getTab("PID").add("derivative gain", 0).withWidget(BuiltInWidgets.kNumberSlider).withSize(2, 2)
        .withProperties(Map.of("min", 0, "max", 1.0)).getEntry();

    kv = Shuffleboard.getTab("PID").add("velocity gain", 0).withWidget(BuiltInWidgets.kNumberSlider).withSize(2, 2)
        .withProperties(Map.of("min", 0, "max", 0.5)).getEntry();

    ka = Shuffleboard.getTab("PID2").add("acceleration gain", 0).withWidget(BuiltInWidgets.kNumberSlider).withSize(2, 2)
        .withProperties(Map.of("min", 0, "max", 0.5)).getEntry();

    //m_raiseClimbButton = new JoystickButton(m_driverController, Constants.kraiseClimbButton);
    configureButtonBindings();
    m_autoCommand = new ShootCommand(); //TODO: Change this
    m_indexIn = new IndexIn();
    m_indexInTrigger = new IndexInTrigger(m_indexer).whileActiveContinuous(m_indexIn);
    m_drivetrain.setDefaultCommand(new RunCommand(() -> m_drivetrain.deadbandedArcadeDrive(), m_drivetrain));
    m_dash.register();
    m_indexer.register();
    m_shootCommandButton = new JoystickButton(m_driverController, Constants.kShootCommandButton);  
    m_shootCommand = new ShootCommand(); 
    configureButtonBindings();
   // m_drivetrain.setDefaultCommand (
     // new RunCommand(() -> m_drivetrain.deadbandedArcadeDrive(), m_drivetrain));
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */

  // turn on indexwe when the 'A' button is pressed
  private void configureButtonBindings() {
    /*
     * new JoystickButton(m_storage, Button.kA.value) .whenPressed(new
     * InstantCommand(m_indexMotor::enable, m_indexMotor));
     */
    m_shootCommandButton.whenPressed(m_shootCommand); 

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return m_autoCommand;
  }

  public Drivetrain getDrivetrain() {
    return m_drivetrain;
  }

  public Indexer getIndexer() {
    return m_indexer;
  }
}
