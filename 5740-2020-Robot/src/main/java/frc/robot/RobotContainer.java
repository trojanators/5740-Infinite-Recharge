/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

<<<<<<< Updated upstream
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
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
=======
import java.util.Map;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;

>>>>>>> Stashed changes

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
<<<<<<< Updated upstream
=======
  private Indexer m_indexer = new Indexer();
  private Drivetrain m_drivetrain = new Drivetrain(); // Robot Drivetrain
  //private DashBoard m_dash = new DashBoard(m_drivetrain, m_indexer);
  private ControlPanel m_controlpanel = new ControlPanel();
  private Climb m_climb = new Climb();
  private Turret m_turret = new Turret();
  private Intake m_intake = new Intake();
>>>>>>> Stashed changes

  private final Drivetrain m_drivetrain = new Drivetrain(); // Robot Drivetrain
  //private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);
  private final Command m_autoCommand = 
    // zero encoders
    new InstantCommand(m_drivetrain::zeroSensors, m_drivetrain).andThen(
      // drive forward slowly
      new InstantCommand(m_drivetrain::driveForwardSlowly, m_drivetrain).andThen(
      //Drive forward for 1 second, timeout if 3 seconds go by  
      new WaitCommand(Constants.kAutoDriveTime).withTimeout(Constants.kAutoTimeoutSeconds).andThen(
      // stop driving  
      new InstantCommand(m_drivetrain::stop, m_drivetrain) 
      )));

  // Driver Controler
  public static Joystick driverController = new Joystick(Constants.kjoystickPort);

<<<<<<< Updated upstream
  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
=======
  private final Command m_autoCommand;
  private JoystickButton dropIntakeButton = new JoystickButton(m_driverController, Constants.kdropIntakeButton);
  private JoystickButton raiseIntakeButton = new JoystickButton(m_driverController, Constants.kraiseIntakeButton);
  private JoystickButton runIntakeButton = new JoystickButton(m_driverController, Constants.krunIntakeButton);
  private JoystickButton runReverseIntakeButton = new JoystickButton(m_driverController, Constants.krunReverseIntakeButton);  
  private JoystickButton runTurretButton = new JoystickButton(m_driverController, 1);
  private JoystickButton shootButton = new JoystickButton(m_driverController, Constants.kShootCommandButton);  
  private JoystickButton manualIndexerFWDButton = new JoystickButton(m_driverController, Constants.kmanualIndexerFWDButton);
  private JoystickButton manualIndexerREVButton = new JoystickButton(m_driverController, Constants.kmanualIndexerREVButton);  

  public final Double ClimbSpeed = m_operatorController.getRawAxis(Constants.leftStickY);
  public final Double LiftSpeed = m_operatorController.getRawAxis(Constants.rightStickX);

  private NetworkTableEntry kp, kd, kv, ka;
  
  /* The container for the robot. Contains subsystems, OI devices, and commands. */
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
    
    // Configure the button bindings
    configureButtonBindings();

    // Add subsystems to scheduler
    m_drivetrain.register();
    m_controlpanel.register();
    m_turret.register();
   // m_dash.register();
   // m_indexer.register(); 

    m_autoCommand = new TestPathCommand(m_drivetrain);

  /* Subsystem Default commands */  
    m_turret.setDefaultCommand(new TurretPIDTest(m_turret, m_operatorController));
>>>>>>> Stashed changes
    m_drivetrain.setDefaultCommand (
      new RunCommand(() -> m_drivetrain.deadbandedArcadeDrive(), m_drivetrain));
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
<<<<<<< Updated upstream
  private void configureButtonBindings() {

=======

  private void configureButtonBindings() {
    dropIntakeButton.whenPressed(new DropIntake(m_intake));
    raiseIntakeButton.whenPressed(new RaiseIntake(m_intake));
    runIntakeButton.toggleWhenPressed(new RunIntake(m_intake));
    runTurretButton.whileHeld(new RunTurret(m_turret));
    shootButton.whileHeld(new Shoot(m_turret));
    runReverseIntakeButton.whileHeld(new RunReverseIntake(m_intake));
    manualIndexerFWDButton.whileHeld(new ManualIndexerControl(m_indexer, true));
    manualIndexerREVButton.whileHeld(new ManualIndexerControl(m_indexer, false));
>>>>>>> Stashed changes
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
}
