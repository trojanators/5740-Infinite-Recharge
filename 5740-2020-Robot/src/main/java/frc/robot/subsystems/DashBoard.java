/**
 * This subsystem is for the Shuffleboard Dashboard It inits the dashboard and
 * updates all the data in one subsystem
 * @author Nicholas Blackburn
 *  */

package frc.robot.subsystems;

import java.util.Map;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.subsystems.*;

public class DashBoard extends SubsystemBase {

  // TODO: add Color Display Widget after robot is complete
  // TODO: add TestMode dashBoard to load in testmode
  public Drivetrain driver;
  public Indexer indexer;
  public Turret turret;
  public Intake intake;
  public ControlPanel control;
  public Limelight limelight;

  // TeleOp Networktable entry's for Teleop Dashboard
  private NetworkTableEntry isTargetVis;
  private NetworkTableEntry isTurretActive;
  private NetworkTableEntry indexerCount;
  private NetworkTableEntry isIntakeActive;
  private NetworkTableEntry isIntakeRaised;
  private NetworkTableEntry colorSensorColor;

  // Test Mode NetworkTable Entry's for Test Dashboard
  private NetworkTableEntry turretShootPid;
  private NetworkTableEntry turretFlywheel;
  private NetworkTableEntry resetTurret;
  private NetworkTableEntry turretLimit;

  private NetworkTableEntry intakeEncoder;
  private NetworkTableEntry indexInput;
  private NetworkTableEntry indexOutput;

  // Limelight Entrys

  private NetworkTableEntry LedOn;
  private NetworkTableEntry LedOff;
  private NetworkTableEntry LedBlink;

  private ShuffleboardLayout indexerLayout;

  // This function Sets up Shuffleboard layout
  public DashBoard(final Drivetrain m_Drivetrain, final Indexer m_indexer, final Turret m_turret,
      final ControlPanel m_control, final Intake m_intake, final Limelight m_Limelight) {

    this.driver = m_Drivetrain;
    this.indexer = m_indexer;
    this.turret = m_turret;
    this.control = m_control;
    this.intake = m_intake;
    this.limelight = m_Limelight;

  }

  // function load's our Teleop Dash board
  public void TeleopDashboard() {
    DriverStation.reportWarning("[Nicholas's DashBoard]" + "TeleOPMode for Dashboard is enabled", true);

    final ShuffleboardTab Teleop_Dashboard = Shuffleboard.getTab("TeleopDash");

    this.isTargetVis = Teleop_Dashboard.add(" Target Visible", false).withSize(2, 1).withPosition(0, 0)
        .withWidget(BuiltInWidgets.kBooleanBox)
        .withProperties(Map.of("colorWhenTrue", "blue", "colorWhenFalse", "black")).getEntry();

    this.indexerCount = Teleop_Dashboard.add("Cell count", 0).withSize(2, 2).withPosition(0, 2)
        .withWidget(BuiltInWidgets.kDial).withProperties(Map.of("min", 0, "max", 5)).getEntry();

    this.colorSensorColor = Teleop_Dashboard.add("ControlPanal Required Color", false).withSize(2, 1).withPosition(2, 0)
        .withWidget(BuiltInWidgets.kBooleanBox)
        .withProperties(Map.of("colorWhenTrue", "blue", "colorWhenFalse", "black")).getEntry();

    this.isTurretActive = Teleop_Dashboard.add("isTurret Firing", false).withSize(2, 1).withPosition(4, 0)
        .withWidget(BuiltInWidgets.kBooleanBox)
        .withProperties(Map.of("colorWhenTrue", "blue", "colorWhenFalse", "black")).getEntry();

    this.isIntakeRaised = Teleop_Dashboard.add("Is Intake Active", false).withSize(2, 1).withPosition(6, 0)
        .withWidget(BuiltInWidgets.kBooleanBox)
        .withProperties(Map.of("colorWhenTrue", "blue", "colorWhenFalse", "black")).getEntry();
  }

  // function load's our Test Dash board
  public void TestModeDashboard() {
    DriverStation.reportWarning("[Nicholas's DashBoard]" + "TestMode for Dashboard is enabled", true);
    // Shuffleboard Tab
    final ShuffleboardTab Test = Shuffleboard.getTab("Test");

    final ShuffleboardLayout LimeLayout = Test.getLayout("LimeLight Control", BuiltInLayouts.kList).withSize(2, 3)
        .withPosition(0, 0).withProperties(Map.of("Label position", "BOTTOM"));

    final ShuffleboardLayout TurretLayout = Test.getLayout("Turret Layout", BuiltInLayouts.kList).withSize(2, 3)
        .withPosition(2, 0).withProperties(Map.of("Label position", "BOTTOM"));

    /** This Section is for our List layout for The Turret Testing */
    this.isTurretActive = TurretLayout.add("Turret Act", false).withSize(2, 1).withPosition(0, 0)
        .withWidget(BuiltInWidgets.kBooleanBox)
        .withProperties(Map.of("colorWhenTrue", "blue", "colorWhenFalse", "black")).getEntry();

    this.resetTurret = TurretLayout.add("Reset Turret to Starting Pos", false).withSize(2, 1).withPosition(0, 1)
        .withWidget(BuiltInWidgets.kToggleButton)
        .withProperties(Map.of("colorWhenTrue", "blue", "colorWhenFalse", "black")).getEntry();

    this.turretFlywheel = TurretLayout.add("FlyWheel RPM's", 0).withSize(2, 2).withPosition(0, 2)
        .withWidget(BuiltInWidgets.kDial).withProperties(Map.of("min", "0", "max", "1000")).getEntry();

    this.turretShootPid = TurretLayout.add("Turret Flywheel PID TEST", false).withSize(2, 1).withPosition(0, 4)
        .withWidget(BuiltInWidgets.kToggleButton)
        .withProperties(Map.of("colorWhenTrue", "blue", "colorWhenFalse", "black")).getEntry();

    /** This Section is for our List Layout of out LimeLight Led Control */

    this.isTargetVis = LimeLayout.add("Target vis", false).withSize(2, 2).withPosition(0, 0)
        .withWidget(BuiltInWidgets.kBooleanBox)
        .withProperties(Map.of("colorWhenTrue", "blue", "colorWhenFalse", "black")).getEntry();

    this.LedOn = LimeLayout.add("LedOn", false).withSize(2, 1).withPosition(0, 2)
        .withWidget(BuiltInWidgets.kToggleButton)
        .withProperties(Map.of("colorWhenTrue", "blue", "colorWhenFalse", "black")).getEntry();

    this.LedOff = LimeLayout.add("LedOFF", false).withSize(2, 1).withPosition(0, 4)
        .withWidget(BuiltInWidgets.kToggleButton)
        .withProperties(Map.of("colorWhenTrue", "blue", "colorWhenFalse", "black")).getEntry();

    this.LedBlink = LimeLayout.add("LedBLINK", false).withSize(2, 1).withPosition(0, 3)
        .withWidget(BuiltInWidgets.kToggleButton)
        .withProperties(Map.of("colorWhenTrue", "blue", "colorWhenFalse", "black")).getEntry();

  }

  /**
   * Updates All Teleop Tab's Vars for shuffleboard
   * 
   * @author Nicholas Blackburn
   */
  public void teleopPeriodic() {

    // This method will be called once per scheduler run
    this.isTargetVis.setBoolean(this.turret.seesTarget());
    this.isIntakeRaised.setBoolean(this.intake.isIntakeActive());
    this.intakeEncoder.setDouble(this.intake.getEncoderDistance());
    this.indexerCount.setDouble((int) this.indexer.getCurrentCellCount());

    this.isTurretActive.setBoolean(this.turret.isTurretActive());
    this.turretFlywheel.setDouble(this.turret.getShooterAverageRPM());
  }

  public void testPeriodic() {

    if (this.turretShootPid.getBoolean(false)) {

      this.turret.testpid = true;
    } else {
      this.turret.testpid = false;
    }

    /** these if statements are setting the Test shuffleboard */
    if (LedOn.getBoolean(false)) {
      this.limelight.LedOn = true;
    } else {
      this.limelight.LedOn = false;
    }

    if (LedOff.getBoolean(false)) {
      this.limelight.LedOff = true;
    } else {
      this.limelight.LedOff = false;
    }

    if (LedBlink.getBoolean(false)) {
      this.limelight.LedBlink = true;
    } else {
      this.limelight.LedBlink = false;
    }

  }

  @Override
  public void periodic() {

  }
}
