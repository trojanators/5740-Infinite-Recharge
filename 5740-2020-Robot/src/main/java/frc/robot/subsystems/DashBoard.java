/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

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
import edu.wpi.first.wpilibj.shuffleboard.WidgetType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.subsystems.*;

/**
 * This subsystem is for the Shuffleboard Dashboard It inits the dashboard and
 * updates all the data in one subsystem
 */

public class DashBoard extends SubsystemBase {
  /**
   * Creates a new DashBoard.
   */

  // TODO: add Color Display Widget after robot is complete

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
  private NetworkTableEntry batteryUsage; 

  private NetworkTableEntry resetTurret;
  private NetworkTableEntry turretLimit;
  
  private NetworkTableEntry intakeEncoder;
  private NetworkTableEntry indexInput;
  private NetworkTableEntry indexOutput;

  // Limelight Entrys

  private NetworkTableEntry LedOn;
  private NetworkTableEntry LedOff;
  private NetworkTableEntry LedBlink;

  private ShuffleboardLayout limeLightCommandLayout;
  private ShuffleboardLayout turretLayout;
  private ShuffleboardLayout indexerLayout;
  
 



  // This function Sets up Shuffleboard layout
  public DashBoard(final Drivetrain m_Drivetrain, final Indexer m_indexer, final Turret m_turret, final ControlPanel m_control, final Intake m_intake, final Limelight m_Limelight) {

    this.driver = m_Drivetrain;
    this.indexer = m_indexer;
    this.turret = m_turret;
    this.control = m_control;
    this.intake = m_intake;
    this.limelight = m_Limelight;

    if(DriverStation.getInstance().isTest()){
      DriverStation.reportError("[Nicholas's DashBoard]"+"TestMode For Dashboard is enabled", true);
      TestModeDashboard();
    }

    if(DriverStation.getInstance().isOperatorControl()){
      DriverStation.reportError("[Nicholas's DashBoard]"+"TeleOPMode for Dashboard is enabled", true);
      TeleopDashboard();

    }
  }

  public void TeleopDashboard() {

    final ShuffleboardTab Teleop_Dashboard = Shuffleboard.getTab("TeleopDash");

    this.isTargetVis = Teleop_Dashboard.add("Is Target Visible", false).withSize(2, 1).withPosition(0, 0)
        .withWidget(BuiltInWidgets.kBooleanBox).withProperties(Map.of("colorWhenTrue", "blue", "colorWhenFalse", "black")).getEntry();

    this.indexerCount = Teleop_Dashboard.add("Cell count",0).withSize(2, 2).withPosition(0, 2)
        .withWidget(BuiltInWidgets.kDial).withProperties(Map.of("min", 0, "max", 5)).getEntry();  
    
    this.colorSensorColor = Teleop_Dashboard.add("ControlPanal Required Color",false).withSize(2,1).withPosition(2,0)
        .withWidget(BuiltInWidgets.kBooleanBox).withProperties(Map.of("colorWhenTrue", "blue", "colorWhenFalse", "black")).getEntry();

    this.isTurretActive = Teleop_Dashboard.add("isTurret Firing",false).withSize(2, 1).withPosition(4 ,0)
        .withWidget(BuiltInWidgets.kBooleanBox).withProperties(Map.of("colorWhenTrue", "blue", "colorWhenFalse", "black")).getEntry();

    this.isIntakeRaised = Teleop_Dashboard.add("Is Intake Active",false).withSize(2,1).withPosition(6, 0)
        .withWidget(BuiltInWidgets.kBooleanBox).withProperties(Map.of("colorWhenTrue", "blue", "colorWhenFalse", "black")).getEntry();
  }
  
  
  public void TestModeDashboard(){
    // Shuffleboard Tab
    
    this.limeLightCommandLayout = Shuffleboard.getTab("Test").getLayout("lime_Led_Control").withSize(2, 3).withPosition(0, 0)
      .withProperties(Map.of("Label position", "BOTTOM")); // hide labels for commands

    this.turretLayout = Shuffleboard.getTab("Test").getLayout("Turret Control").withSize(2, 3).withPosition(2, 0)
      .withProperties(Map.of("Label position", "BOTTOM")); // hide labels for commands

    this.indexerLayout = Shuffleboard.getTab("Test").getLayout("Indexer Control").withSize(2, 3).withPosition(4, 0)
      .withProperties(Map.of("Label position", "BOTTOM")); // hide labels for commands


    /**This Section is for our List layout for The Turret Testing */
    this.isTurretActive = this.turretLayout.add("Is Turret Active",false).withSize(2, 1).withPosition(0, 0)
      .withWidget(BuiltInWidgets.kBooleanBox).withProperties(Map.of("colorWhenTrue", "blue", "colorWhenFalse", "black")).getEntry();

    this.resetTurret = this.turretLayout.add("Reset Turret to Starting Pos", false).withSize(2, 1).withPosition(0, 1)
      .withWidget(BuiltInWidgets.kCommand).withProperties(Map.of("colorWhenTrue", "blue", "colorWhenFalse", "black")).getEntry();

    
    this.turretFlywheel = this.turretLayout.add("FlyWheel RPM's",0).withSize(2, 2).withPosition(0, 2)
      .withWidget(BuiltInWidgets.kDial).withProperties(Map.of("min","0","max","1000")).getEntry();

    this.turretShootPid = this.turretLayout.add("Turret Flywheel PID TEST", false).withSize(2, 1).withPosition(0, 4)
    .withWidget(BuiltInWidgets.kCommand).withProperties(Map.of("colorWhenTrue", "blue", "colorWhenFalse", "black")).getEntry();




      /**This Section is for our List Layout of out LimeLight Led Control */

    this.isTargetVis = this.limeLightCommandLayout.add("Is Target Visable", false).withSize(2, 2).withPosition(0, 0)
    .withWidget(BuiltInWidgets.kBooleanBox).withProperties(Map.of("colorWhenTrue", "blue", "colorWhenFalse", "black")).getEntry();

    this.LedOn = this.limeLightCommandLayout.add("LedOn", false).withSize(2, 1).withPosition(0, 2)
      .withWidget(BuiltInWidgets.kCommand).withProperties(Map.of("colorWhenTrue", "blue", "colorWhenFalse", "black")).getEntry();

    this.LedOff = this.limeLightCommandLayout.add("LedOFF", false).withSize(2, 1).withPosition(0, 4)
      .withWidget(BuiltInWidgets.kCommand).withProperties(Map.of("colorWhenTrue", "blue", "colorWhenFalse", "black")).getEntry();

    this.LedBlink = this.limeLightCommandLayout.add("LedBLINK", false).withSize(2, 1).withPosition(0, 3)
      .withWidget(BuiltInWidgets.kCommand).withProperties(Map.of("colorWhenTrue", "blue", "colorWhenFalse", "black")).getEntry();

  }

  @Override
  public void periodic() {

    // This method will be called once per scheduler run
    this.isTargetVis.setBoolean(this.turret.seesTarget());

  
    this.isIntakeRaised.setBoolean(this.intake.isIntakeActive());
    this.intakeEncoder.setDouble(this.intake.getEncoderDistance());
    this.indexerCount.setDouble((int)this.indexer.getCurrentCellCount());

    this.isTurretActive.setBoolean(this.turret.isTurretActive());
    this.turretFlywheel.setDouble(this.turret.getShooterAverageRPM());
    
    if(this.turretShootPid.getBoolean(false)){

      this.turret.testpid = true;
    }else{
      this.turret.testpid  =false;
    }
    

    /**these if statements are setting the Test shuffleboard  */
    if(this.LedOn.getBoolean(false)){
      this.limelight.LedOn = true;
    } else{
      this.limelight.LedOn = false;
    }

    if(this.LedOff.getBoolean(false)){
      this.limelight.LedOff = true;
    } else{
      this.limelight.LedOff = false;
    }

    if(this.LedBlink.getBoolean(false)){
      this.limelight.LedBlink = true;
    } else{
      this.limelight.LedBlink = false;
    }
    

    

  }
}
