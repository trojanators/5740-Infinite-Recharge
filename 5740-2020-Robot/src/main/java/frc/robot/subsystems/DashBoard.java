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

  private NetworkTableEntry isTargetVis;
  private NetworkTableEntry isTurretActive;
  private NetworkTableEntry indexerCount;
  private NetworkTableEntry colorSensorColor;
  private NetworkTableEntry intakeStatus;
  
  private NetworkTableEntry limeLight;
  private NetworkTableEntry batteryUsage; 
  private NetworkTableEntry indexerState; 

 



  // This function Sets up Shuffleboard layout
  public DashBoard(final Drivetrain m_Drivetrain, final Indexer m_indexer, final Turret m_turret, final ControlPanel m_control, final Intake m_intake) {

    this.driver = m_Drivetrain;
    this.indexer = m_indexer;
    this.turret = m_turret;
    this.control = m_control;
    this.intake = m_intake;
    TeleopDashboard();
    TestModeDashboard();
  }

  public void TeleopDashboard() {

    final ShuffleboardTab Teleop_Dashboard = Shuffleboard.getTab("TeleopDash");

    this.isTargetVis = Teleop_Dashboard.add("Is Target Visible", false).withSize(2, 1).withPosition(0, 0)
        .withWidget(BuiltInWidgets.kBooleanBox).withProperties(Map.of("colorWhenTrue", "blue", "colorWhenFalse", "black")).getEntry();

    this.indexerCount = Teleop_Dashboard.add("Cell count",0).withSize(2, 2).withPosition(0, 3)
        .withWidget(BuiltInWidgets.kDial).withProperties(Map.of("min", 0, "max", 5)).getEntry();  
    
    this.colorSensorColor = Teleop_Dashboard.add("ControlPanal Required Color",false).withSize(2,1).withPosition(2,0).
      withWidget(BuiltInWidgets.kBooleanBox).withProperties(Map.of("colorWhenTrue", "blue", "colorWhenFalse", "black")).getEntry();

    this.isTurretActive = Teleop_Dashboard.add("isTurretActive",false).withSize(2, 1).withPosition(4 ,0)
        .withWidget(BuiltInWidgets.kBooleanBox).withProperties(Map.of("colorWhenTrue", "blue", "colorWhenFalse", "black")).getEntry();

    this.indexerState = Teleop_Dashboard.add("revencoder",0).withSize(2, 1).withPosition(4,3).withWidget(BuiltInWidgets.kTextView).getEntry();

    this.intakeStatus = Teleop_Dashboard.add(" Is Intake Active", false).withSize(2,1).withPosition(5, 0).withWidget(BuiltInWidgets.kBooleanBox)
      .withProperties(Map.of("colorWhenTrue", "blue", "colorWhenFalse", "black")).getEntry();
  }
  public void TestModeDashboard(){
    final ShuffleboardTab testDashShuffleboardTab = Shuffleboard.getTab("Test");

  this.limeLight = testDashShuffleboardTab.add("Limelight",0).withSize(2, 4).withPosition(0, 0).withWidget(BuiltInWidgets.kCommand).getEntry();
    
  }




  @Override
  public void periodic() {

    // This method will be called once per scheduler run
    this.isTargetVis.setBoolean(this.turret.seesTarget());
    this.isTurretActive.setBoolean(this.turret.isTurretActive());

    this.colorSensorColor.setBoolean(this.control.isCurrentColor());
    this.indexerCount.setDouble((int)this.indexer.getCurrentCellCount());
    this.indexerState.setDouble(this.intake.getEncoderDistance());
    this.intakeStatus.setBoolean(this.intake.isIntakeActive());

    

  }
}
