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
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.subsystems.*;

/**
 * This subsystem is for the Shuffleboard Dashboard It inits the dashboard and
 * updates all the data in one subsystem
 */

public class DashBoard extends SubsystemBase {
 
  // TODO: add Color Display Widget after robot is complete

  public Drivetrain driver;
  public Indexer indexer;
  public ControlPanel controlPanel;

  private NetworkTableEntry isTargetVis;
  private NetworkTableEntry inTakeCount;

  private NetworkTableEntry outputCount;
  private NetworkTableEntry ContolPanel;

  private NetworkTableEntry DevControlState;
  private NetworkTableEntry DevControlCounter;

  private boolean targetCheck;
  private boolean ControlPanelColor;

  // This function Sets up Shuffleboard layout
  public DashBoard(final Drivetrain m_Drivetrain, final Indexer m_indexer, final ControlPanel m_controlPanel) {

    this.driver = m_Drivetrain;
    this.indexer = m_indexer;
    this.controlPanel = m_controlPanel;

    TeleopDashboard();
    DevDashboard();
  }
  // Function for Shuffleboard Teleop Data dispaly 
  public void TeleopDashboard() {

    final ShuffleboardTab Teleop_Dashboard = Shuffleboard.getTab("TeleopDash");

    this.isTargetVis = Teleop_Dashboard.add("Is Target Visible", false).withPosition(0, 0).withSize(2, 1)
        .withWidget(BuiltInWidgets.kBooleanBox).getEntry();

    this.outputCount = Teleop_Dashboard.add("Launched power-cell", 0).withPosition(0, 2).withSize(2, 1)
        .withWidget(BuiltInWidgets.kDial).withProperties(Map.of("min", 0, "max", 5)).getEntry();

    this.inTakeCount = Teleop_Dashboard.add("Intake power-cell", 0).withPosition(2, 0).withSize(2, 1)
        .withWidget(BuiltInWidgets.kDial).withProperties(Map.of("min", 0, "max", 5)).getEntry();

    this.ContolPanel = Teleop_Dashboard.add("IS ControlPanel Required Color",0).withPosition(2, 2).withSize(2, 1)
        .withWidget(BuiltInWidgets.kBooleanBox).withProperties(Map.of("true",Color.kLimeGreen,"false",Color.kDarkRed))
        .getEntry();

  }

  public void DevDashboard() {

    final ShuffleboardTab dev_Dashboard = Shuffleboard.getTab("Dev");

    this.isTargetVis = dev_Dashboard.add("Is Target Visible", false).withPosition(0, 0).withSize(2, 1)
        .withWidget(BuiltInWidgets.kBooleanBox).getEntry();

    this.outputCount = dev_Dashboard.add("Launched power-cell", 0).withPosition(0, 2).withSize(2, 1)
        .withWidget(BuiltInWidgets.kDial).withProperties(Map.of("min", 0, "max", 5)).getEntry();

    this.inTakeCount = dev_Dashboard.add("Intake power-cell", 0).withPosition(1, 0).withSize(2, 2)
        .withWidget(BuiltInWidgets.kDial).withProperties(Map.of("min", 0, "max", 1)).getEntry();

    this.DevControlCounter = dev_Dashboard.add("ControlPanel Counter",0).withPosition(1, 2).withSize(2, 1)
      .withWidget(BuiltInWidgets.kTextView).getEntry();

    this.DevControlState = dev_Dashboard.add("ControlPanel State",0).withPosition(2, 0).withSize(2, 1)
      .withWidget(BuiltInWidgets.kTextView).getEntry();

    this.ContolPanel = dev_Dashboard.add("IS ControlPanel Required Color",0).withPosition(2, 2).withSize(2, 1)
      .withWidget(BuiltInWidgets.kBooleanBox).withProperties(Map.of("true",Color.kLimeGreen,"false",Color.kDarkRed))
      .getEntry();

  }

  public void dashData() {
    final double limeLightTarget = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);

    if (limeLightTarget == 1) {
      this.targetCheck = true;

    } else {
      this.targetCheck = false;

    }

   if(this.controlPanel.targetColor == this.controlPanel.currentColor){
    this.ControlPanelColor = true;

   } else{
    this.ControlPanelColor = false;

   }

    this.isTargetVis.setBoolean(targetCheck);
    this.ContolPanel.setBoolean(ControlPanelColor);

    this.inTakeCount.setDouble(this.indexer.getInputDistance());
    this.outputCount.setDouble(this.indexer.getOutputDistance());

    this.DevControlCounter.setDouble(this.controlPanel.targetCounter);
    this.DevControlState.setString(this.controlPanel.getControlPanelState().toString());

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    dashData();
  }
}
