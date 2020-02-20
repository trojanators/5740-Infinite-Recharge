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

  
  
  private ShuffleboardLayout indexerLayout;
  
 



  // This function Sets up Shuffleboard layout
  public DashBoard(final Drivetrain m_Drivetrain, final Indexer m_indexer, final Turret m_turret, final ControlPanel m_control, final Intake m_intake, final Limelight m_Limelight) {

    this.driver = m_Drivetrain;
    this.indexer = m_indexer;
    this.turret = m_turret;
    this.control = m_control;
    this.intake = m_intake;
    this.limelight = m_Limelight;


  }

}
