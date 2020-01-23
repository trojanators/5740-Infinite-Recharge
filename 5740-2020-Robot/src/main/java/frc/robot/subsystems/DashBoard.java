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

  public NetworkTableEntry isTargetVis;
  public NetworkTableEntry GyroPOS;

  public NetworkTableEntry shootEntry;
  public NetworkTableEntry inTakeEntry;

  public NetworkTableEntry colorDetect;

  public boolean targetCheck;

  // This function Sets up Shuffleboard layout
  public DashBoard(final Drivetrain m_Drivetrain, final Indexer m_indexer) {

    driver = m_Drivetrain;
    indexer = m_indexer;

    TeleopDashboard();
    DevDashboard();
  }

  public void TeleopDashboard() {

    final ShuffleboardTab Teleop_Dashboard = Shuffleboard.getTab("TeleopDash");

    this.isTargetVis = Teleop_Dashboard.add("Is Target Visible", false).withPosition(0, 0).withSize(2, 1)
        .withWidget(BuiltInWidgets.kBooleanBox).getEntry();

    this.shootEntry = Teleop_Dashboard.add("Launched power-cell", 0).withPosition(2, 0).withSize(2, 1)
        .withWidget(BuiltInWidgets.kDial).withProperties(Map.of("min", 0, "max", 5)).getEntry();

    this.inTakeEntry = Teleop_Dashboard.add("Intake power-cell", 0).withPosition(2, 2).withSize(2, 0)
        .withWidget(BuiltInWidgets.kDial).withProperties(Map.of("min", 0, "max", 1)).getEntry();

  }

  public void DevDashboard() {

    final ShuffleboardTab dev_Dashboard = Shuffleboard.getTab("Dev");

    this.isTargetVis = dev_Dashboard.add("Is Target Visible", false).withPosition(0, 0).withSize(2, 1)
        .withWidget(BuiltInWidgets.kBooleanBox).getEntry();

    this.GyroPOS = dev_Dashboard.add("Gyro Pos", 0).withPosition(0, 2).withSize(2, 2).withWidget(BuiltInWidgets.kGyro)
        .getEntry();

    this.shootEntry = dev_Dashboard.add("Launched power-cell", 0).withPosition(2, 0).withSize(2, 1)
        .withWidget(BuiltInWidgets.kDial).withProperties(Map.of("min", 0, "max", 5)).getEntry();

    this.inTakeEntry = dev_Dashboard.add("Intake power-cell", 0).withPosition(2, 2).withSize(2, 0)
        .withWidget(BuiltInWidgets.kDial).withProperties(Map.of("min", 0, "max", 1)).getEntry();

  }

  public void dashData() {
    final double limeLightTarget = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);

    if (limeLightTarget == 1) {
      this.targetCheck = true;

    } else {
      this.targetCheck = false;

    }

    this.GyroPOS.setDouble(driver.gyro.getRate());

    this.isTargetVis.setBoolean(targetCheck);

    //this.shootEntry.setDouble();

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    dashData();
  }
}
