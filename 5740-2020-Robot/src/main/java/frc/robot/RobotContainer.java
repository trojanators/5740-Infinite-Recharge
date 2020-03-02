/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the Robot periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 * @author LukeCrum, Nicholas Blackburn  
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
import frc.robot.commands.TestPathCommand;
import edu.wpi.first.wpilibj2.command.button.Button;
import frc.robot.commands.AutomatedIndexer;
import frc.robot.commands.IntakeDown;
import frc.robot.commands.IntakeUp;
import frc.robot.commands.LoadDashboard;
import frc.robot.commands.ManualIndexer;
import frc.robot.commands.RaiseClimber;
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

public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final Command m_autoCommand;

  private NetworkTableEntry kp, kd, kv, ka;

  public static Joystick m_driverController = new Joystick(Constants.kDriverPort);
  public static Joystick m_operatorController = new Joystick(Constants.kOperatorPort);

  public static JoystickButton climberUp, climberDown, intakeIn, intakeOut, intakeFlip, rotationControl, positionControl, shoot; 
  //TODO: Manual indexer, shooter, override toggle

  private final Indexer m_indexer = new Indexer(m_driverController);
  private final Drivetrain m_drivetrain = new Drivetrain(); // Robot Drivetrain
  private final Limelight m_Limelight = new Limelight();
  private final ControlPanel m_controlpanel = new ControlPanel();
  private final Climb m_climb = new Climb();
  private final Turret m_turret = new Turret();
  private final Intake m_intake = new Intake(m_driverController);
  private final DashBoard m_dash = new DashBoard(m_drivetrain, m_indexer, m_turret, m_controlpanel, m_intake, m_Limelight);

  public RobotContainer() {
    m_drivetrain.register();
    m_controlpanel.register();
    m_turret.register();
    m_intake.register();
    m_dash.register();
    m_Limelight.register();
    m_indexer.register();

    configureButtonBindings();
    configureTriggers();

    m_autoCommand = new TestPathCommand(m_drivetrain);

    m_drivetrain.setDefaultCommand(new RunCommand(() -> m_drivetrain.deadbandedArcadeDrive(), m_drivetrain));
    m_indexer.setDefaultCommand(new AutomatedIndexer(m_indexer));
    //m_dash.setDefaultCommand(new RunCommand(() -> m_dash.dashboardData(), m_dash));
    m_intake.setDefaultCommand(new IntakeUp());
   //m_Intake.setDefaultCommand(new RunCommand(() -> m_Intake.runIntake(), m_Intake));
  }
  
  private void configureButtonBindings() {
    //climberUp, climberDown, intakeIn, intakeOut, intakeFlip, rotationControl, positionControl, shoot

    shoot = new JoystickButton(m_driverController, Constants.kA);
    climberUp = new JoystickButton(m_driverController, Constants.kStart);
    climberDown = new JoystickButton(m_driverController, Constants.kSelect);

    intakeIn = new JoystickButton(m_operatorController, Constants.kRB);
    intakeOut = new JoystickButton(m_operatorController, Constants.kLB);
    intakeFlip = new JoystickButton(m_operatorController, Constants.kA);
    rotationControl = new JoystickButton(m_operatorController, Constants.kStart);
    positionControl = new JoystickButton(m_operatorController, Constants.kSelect);

    shoot.whileHeld(new Shoot(m_turret, m_indexer));
    climberUp.whenPressed(new RaiseClimber(m_climb));
    climberDown.whileHeld(new RunClimb(m_climb));

    intakeIn.whileHeld(new RunIntake(m_intake));
    intakeOut.whileHeld(new RunReverseIntake(m_intake));
    intakeFlip.toggleWhenPressed(new IntakeDown());
    
   

  }

  /**
   * This Function Configs Triggers in a more Organized way
   * 
   * @author Nicholas Blackburn
   */
  private void configureTriggers() {

   // test.whenActive(new LoadDashboard(m_dash));

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
