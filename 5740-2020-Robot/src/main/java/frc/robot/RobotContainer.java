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
//import frc.robot.commands.ExampleCommand;
//import frc.robot.commands.IndexIn;
import frc.robot.commands.triggers.IndexInTrigger;
import frc.robot.commands.TestPathCommand;
import frc.robot.commands.TurretPIDTest;
//import frc.robot.commands.ShootCommand;
import frc.robot.commands.TestPathCommand;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
//import frc.robot.commands.DriveSlowly;
//import frc.robot.commands.DropIntake;
//import frc.robot.commands.ExampleCommand;
import frc.robot.commands.RaiseIntake;
import frc.robot.commands.RunIntake;
import frc.robot.commands.RunReverseIntake;
import frc.robot.commands.RunTurret;
import frc.robot.commands.Shoot;
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
  private Indexer m_indexer = new Indexer();
  private Drivetrain m_drivetrain = new Drivetrain(); // Robot Drivetrain

  private DashBoard m_dash = new DashBoard(m_drivetrain, m_indexer);
  private ControlPanel m_controlpanel = new ControlPanel();
  private Climb m_climb = new Climb();

  private Turret m_turret = new Turret();
  private Intake m_Intake = new Intake();

  private final Command m_autoCommand;
  private JoystickButton dropIntakeButton;
  private JoystickButton raiseIntakeButton;
  private JoystickButton runIntakeButton;
  private JoystickButton runReverseIntakeButton; 
  private JoystickButton runTurretButton;
  
  private NetworkTableEntry kp, kd, kv, ka;

  public static Joystick m_driverController = new Joystick(Constants.kjoystickDriverPort);
  public static Joystick m_operatorController = new Joystick(Constants.kjoystickOperatorPort);

  public final Double ClimbSpeed = m_operatorController.getRawAxis(Constants.leftStickY);
  public final Double LiftSpeed = m_operatorController.getRawAxis(Constants.rightStickX);

  private JoystickButton shootCommandButton; 
  private JoystickButton m_raiseClimbButton;
  
  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   * 
   *
   */
  public RobotContainer() {

   /* kp = Shuffleboard.getTab("PID").add("proportional gain", 0).withWidget(BuiltInWidgets.kNumberSlider).withSize(2, 2)
        .withProperties(Map.of("min", 0, "max", 5.0)).getEntry();

    kd = Shuffleboard.getTab("PID").add("derivative gain", 0).withWidget(BuiltInWidgets.kNumberSlider).withSize(2, 2)
        .withProperties(Map.of("min", 0, "max", 1.0)).getEntry();

    kv = Shuffleboard.getTab("PID").add("velocity gain", 0).withWidget(BuiltInWidgets.kNumberSlider).withSize(2, 2)
        .withProperties(Map.of("min", 0, "max", 0.5)).getEntry();

    ka = Shuffleboard.getTab("PID2").add("acceleration gain", 0).withWidget(BuiltInWidgets.kNumberSlider).withSize(2, 2)
        .withProperties(Map.of("min", 0, "max", 0.5)).getEntry();
*/

    //m_raiseClimbButton = new JoystickButton(m_driverController, Constants.kraiseClimbButton);
    shootCommandButton = new JoystickButton(m_driverController, Constants.kShootCommandButton); 
    dropIntakeButton = new JoystickButton(m_driverController, Constants.kdropIntakeButton);
    raiseIntakeButton = new JoystickButton(m_driverController, Constants.kraiseIntakeButton);
    runIntakeButton = new JoystickButton(m_driverController, Constants.krunIntakeButton);

    // Configure the button bindings
    runReverseIntakeButton = new JoystickButton(m_driverController, Constants.krunReverseIntakeButton); 
    runTurretButton = new JoystickButton(m_driverController, 1);
    shootCommandButton = new JoystickButton(m_operatorController, 1);

    configureButtonBindings();
    

    // Add subsystems to scheduler
    m_drivetrain.register();
    m_controlpanel.register();
    m_turret.register();
    m_dash.register();
    m_indexer.register(); 

    m_autoCommand = new TestPathCommand(m_drivetrain);

    //  m_turret.setDefaultCommand(new TurretPIDTest(m_turret, m_operatorController));
    
    m_drivetrain.setDefaultCommand (
      new RunCommand(() -> m_drivetrain.deadbandedArcadeDrive(), m_drivetrain));
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */

  // turn on indexwe when the 'A' button is pressed
  private void configureButtonBindings() {
    //shootCommandButton.whenPressed(new ShootCommand(m_turret)); 
    //dropIntakeButton.whenPressed(new DropIntake(m_Intake));
    raiseIntakeButton.whenPressed(new RaiseIntake(m_Intake));
    ///runTurretButton.whileHeld(new RunTurret(m_turret));
    shootCommandButton.whileHeld(new Shoot(m_turret));
    //runIntakeButton.toggleWhenPressed(new RunIntake(m_Intake));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return m_autoCommand;
  }
}
