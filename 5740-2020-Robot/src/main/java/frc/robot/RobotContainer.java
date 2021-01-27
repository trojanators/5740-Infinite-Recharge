/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the Robot periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 * @author LukeCrum, Nicholas Blackburn  
 */
package frc.robot;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import frc.robot.commands.*;
import frc.robot.subsystems.*;

public class RobotContainer {
  // The robot's subsystems and commands are defined here...
 //private final Command m_autoCommand;

  private NetworkTableEntry kp, kd, kv, ka;

  public static Joystick m_driverController = new Joystick(Constants.kDriverPort);
  public static Joystick m_operatorController = new Joystick(Constants.kOperatorPort);

  public static JoystickButton climberUp, climberDown, intakeIn, intakeOut, intakeFlip, rotationControl, positionControl, shoot, target, indexerOverride, turretOverride, setAutomatic; 
  //TODO: Manual indexer, shooter, override toggle

  private final Indexer m_indexer = new Indexer(m_driverController);
  private final Drivetrain m_drivetrain = new Drivetrain();
  private final ControlPanel m_controlpanel = new ControlPanel();
  private final Climb m_climb = new Climb();
  private final Turret m_turret = new Turret();
  private final Intake m_intake = new Intake();
  private final DashBoard m_dash = new DashBoard(m_drivetrain, m_indexer, m_turret, m_controlpanel, m_intake);

  public RobotContainer() {
  
    configureButtonBindings();

    //m_autoCommand = new Autotestpath(m_drivetrain, m_intake, m_turret, m_indexer);
    //m_autoCommand = new TestPathCommand(m_drivetrain);

    m_drivetrain.setDefaultCommand(new RunCommand(() -> m_drivetrain.deadbandedArcadeDrive(), m_drivetrain));
    m_indexer.setDefaultCommand(new AutomatedIndexer(m_indexer));
   // m_dash.setDefaultCommand(new RunCommand(() -> m_dash.dashboardData(), m_dash));
    m_intake.setDefaultCommand(new RaiseIntake(m_intake));
  }
  
  private void configureButtonBindings() {
   
    shoot = new JoystickButton(m_driverController, Constants.kA);
    target = new JoystickButton(m_driverController, Constants.kRB);
    climberUp = new JoystickButton(m_driverController, Constants.kStart);
    climberDown = new JoystickButton(m_driverController, Constants.kSelect);

    intakeIn = new JoystickButton(m_operatorController, Constants.kRB);
    intakeOut = new JoystickButton(m_operatorController, Constants.kLB);
    intakeFlip = new JoystickButton(m_operatorController, Constants.kA);
    
    rotationControl = new JoystickButton(m_operatorController, Constants.kStart);
    positionControl = new JoystickButton(m_operatorController, Constants.kSelect);
    
    indexerOverride = new JoystickButton(m_operatorController, Constants.kLeftStickPress);
    turretOverride = new JoystickButton(m_operatorController, Constants.kRightStickPress);
    setAutomatic = new JoystickButton(m_operatorController, Constants.kX);

    shoot.whileHeld(new Shoot(m_turret, m_indexer));
    target.whileHeld(new Target(m_turret));
    climberUp.whenPressed(new RaiseClimber(m_climb));
    climberDown.whileHeld(new RunClimb(m_climb));

    intakeIn.whileHeld(new RunIntake(m_intake));
    intakeOut.whileHeld(new RunReverseIntake(m_intake));
    intakeFlip.toggleWhenPressed(new RaiseIntake(m_intake)); 

    indexerOverride.toggleWhenPressed(new ManualIndexer(m_operatorController, m_indexer));
    turretOverride.toggleWhenPressed(new ManualTurret(m_turret, m_operatorController));
    //setAutomatic.whenPressed(new InstantCommand(() -> setAutomatic(), m_turret, m_indexer));
   }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  /*public Command getAutonomousCommand() {
   // return m_autoCommand;
  }*/

  public void manualOverrideIndexer() {
    m_indexer.setDefaultCommand(new ManualIndexer(m_operatorController, m_indexer));
  }

  public void manualOverrideTurret() {
    target.toggleWhenPressed(new VoidCommand());
    shoot.toggleWhenPressed(new RunTurret(m_turret));
    m_turret.setDefaultCommand(new ManualTurret(m_turret, m_operatorController));
  }

  public void setAutomatic() {
    m_indexer.setDefaultCommand(new AutomatedIndexer(m_indexer));
    target.toggleWhenPressed(new Target(m_turret));
    shoot.toggleWhenPressed(new Shoot(m_turret, m_indexer));
    m_turret.setDefaultCommand(new VoidCommand());
  }
}
