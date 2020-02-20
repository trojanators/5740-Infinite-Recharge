/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the Robot periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 * @author LukeCrum Nicholas Blackburn  
 */
package frc.robot;

import java.util.Map;
import java.util.function.BooleanSupplier;

import edu.wpi.first.networktables.NetworkTableEntry;

import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj.Joystick;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

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

import frc.robot.commands.RaiseIntake;
import frc.robot.commands.RunClimb;
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
import frc.robot.subsystems.Limelight;
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


public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private Indexer m_indexer = new Indexer(m_driverController);
  private Drivetrain m_drivetrain = new Drivetrain(); // Robot Drivetrain
  private Limelight m_Limelight = new Limelight();

  private ControlPanel m_controlpanel = new ControlPanel();
  private Climb m_climb = new Climb();

  private Turret m_turret = new Turret();
  private Intake m_Intake = new Intake(m_driverController);

  //private DashBoard m_dash = new DashBoard(m_drivetrain, m_indexer,m_turret,m_controlpanel,m_Intake,m_Limelight);

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
  private JoystickButton raiseClimbButton;
  private JoystickButton climbButton;
  private JoystickButton indexerbutton;
  
  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   * 
   *
   */
  public RobotContainer() {

   

  
    shootCommandButton = new JoystickButton(m_driverController, Constants.kShootCommandButton); 
    dropIntakeButton = new JoystickButton(m_driverController, Constants.kdropIntakeButton);
    raiseIntakeButton = new JoystickButton(m_driverController, Constants.kraiseIntakeButton);
    runIntakeButton = new JoystickButton(m_driverController, Constants.krunIntakeButton);
    climbButton = new JoystickButton(m_driverController, 8
    );

    // Configure the button bindings
    runReverseIntakeButton = new JoystickButton(m_driverController, Constants.krunReverseIntakeButton); 
    runTurretButton = new JoystickButton(m_driverController, 10);
    shootCommandButton = new JoystickButton(m_driverController, 9);
    indexerbutton = new JoystickButton(m_driverController, 8);

    configureButtonBindings();
    

    // Add subsystems to scheduler
    m_drivetrain.register();
    m_controlpanel.register();
    m_turret.register();
    m_Intake.register();
    //m_dash.register();
    m_Limelight.register();
    m_indexer.register(); 
   

    m_autoCommand = new TestPathCommand(m_drivetrain);

    //  m_turret.setDefaultCommand(new TurretPIDTest(m_turret, m_operatorController));
    
    m_drivetrain.setDefaultCommand (
      new RunCommand(() -> m_drivetrain.deadbandedArcadeDrive(), m_drivetrain));
  }

  // turn on indexwe when the 'A' button is pressed
  private void configureButtonBindings() {
    
    raiseIntakeButton.whenPressed(new RaiseIntake(m_Intake));
       shootCommandButton.whileHeld(new Shoot(m_turret));
    climbButton.whileHeld(new RunClimb(m_climb));
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
