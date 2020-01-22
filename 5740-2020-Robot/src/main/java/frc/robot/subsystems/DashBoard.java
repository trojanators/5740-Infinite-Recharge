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
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DashBoard extends SubsystemBase {
  /**
   * Creates a new DashBoard.
   */

  // TODO: add Color Display Widget after robot is complete
  // TODO: get Sensor Data without throwing a nullpointer

  public Drivetrain driver;
  public Indexer indexer;

  public NetworkTableEntry isTargetVis;
  public NetworkTableEntry ballpos;
  public NetworkTableEntry GyroPOS;

  public NetworkTableEntry shootEntry;
  public NetworkTableEntry colorDetect;

  public boolean targetCheck;

  // This function Sets up Shuffleboard layout
  public DashBoard(Drivetrain m_Drivetrain, Indexer m_indexer) {

    driver = m_Drivetrain;
    indexer = m_indexer;

    final ShuffleboardTab dev_Dashboard = Shuffleboard.getTab("Dev");

    this.isTargetVis = dev_Dashboard.add("Is Target Visible", false).withPosition(0, 0).withSize(2, 1)
        .withWidget(BuiltInWidgets.kBooleanBox).getEntry();

    this.GyroPOS = dev_Dashboard.add("Gyro Pos", 0).withPosition(0, 2).withSize(2, 2).withWidget(BuiltInWidgets.kGyro)
        .getEntry();

    this.ballpos = dev_Dashboard.add("Ball Count Intake", 0).withPosition(0, 4).withSize(2, 1)
        .withWidget(BuiltInWidgets.kDial).withProperties(Map.of("min", 0, "max", 5)).getEntry();

    this.shootEntry = dev_Dashboard.add("Launched balls", 0).withPosition(2, 0).withSize(2, 1)
        .withWidget(BuiltInWidgets.kDial).withProperties(Map.of("min", 0, "max", 5)).getEntry();

  }

  public void dashData() {
    final double limeLightTarget = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);

    if (limeLightTarget == 1) {
      this.targetCheck = true;

    } else {
      this.targetCheck = false;

    }

    this.GyroPOS.setDouble(driver.gyro.getAngle());

    this.isTargetVis.setBoolean(targetCheck);

    this.shootEntry.setDouble();

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    dashData();
  }
}
