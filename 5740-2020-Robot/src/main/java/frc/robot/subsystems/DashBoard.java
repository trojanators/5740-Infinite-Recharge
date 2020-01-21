/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.lang.reflect.Array;
import java.util.Map;

import org.apache.commons.math3.util.DoubleArray;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * This Class is used to develop shuffleboard code test robots subsystem
 */
// TODO: add Color Display Widget after robot is complete
// TODO: get Sensor Data without throwing a nullpointer

public class DashBoard extends SubsystemBase {
  /**
   * Creates a new DashBoard.
   */
  // Create Network Table entries
  public Drivetrain driver;

  // Widget Network Table Entrye
  public NetworkTableEntry isTargetVis;
  public NetworkTableEntry ballpos;
  public NetworkTableEntry colorDetect;
  public NetworkTableEntry GyroPos;

  public NetworkTableEntry VisionTargetCenter;
  public NetworkTableEntry visionXEntry;
  public NetworkTableEntry visionYEntry;

  private double limeLightTarget;
  private double limelightPixelH;
  private double limelightPixelW = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);

  public boolean targetCheck;

  public DashBoard(final Drivetrain m_Drivetrain) {
    driver = m_Drivetrain;

    ShuffleboardTab dev_Dashboard = Shuffleboard.getTab("Dev");

    this.isTargetVis = dev_Dashboard.add("Is Target Visible", false).withPosition(0, 0).withSize(2, 1)
        .withWidget(BuiltInWidgets.kBooleanBox).getEntry();

    this.GyroPos = dev_Dashboard.add("Gyro Pos", 0).withPosition(0, 2).withSize(2, 1).withWidget(BuiltInWidgets.kGyro)
        .getEntry();

    this.ballpos = dev_Dashboard.add("Ball Count", 0).withPosition(4, 0).withSize(2, 1).withWidget(BuiltInWidgets.kDial)
        .withProperties(Map.of("min", 0, "max", 5)).getEntry();

    this.VisionTargetCenter = dev_Dashboard.add("Target_Center", 0).withPosition(2, 0).withSize(2, 2)
        .withWidget(BuiltInWidgets.kDial).withProperties(Map.of("min", 0, "max", 100)).getEntry();

    this.visionXEntry = dev_Dashboard.add("Vision_X", 0).withPosition(2, 2).withSize(2, 1)
        .withWidget(BuiltInWidgets.kTextView).getEntry();

    this.visionYEntry = dev_Dashboard.add("Vision_Y", 0).withPosition(2, 0).withSize(2, 1)
        .withWidget(BuiltInWidgets.kTextView).getEntry();

  }

  // Creates Dashboard layout and links Dashboard widgets to network tables

  public void dashData() {

    this.limeLightTarget = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
    this.limelightPixelH = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tlong").getDouble(0);
    this.limelightPixelW = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tshort").getDouble(0);

    if (limeLightTarget == 1) {
      targetCheck = true;

    } else {
      targetCheck = false;

    }
    this.GyroPos.setDouble(driver.gyro.getAngle());

    this.isTargetVis.setBoolean(targetCheck);

    this.visionXEntry.setDouble(limelightPixelH);
    this.visionYEntry.setDouble(limelightPixelW);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    dashData();
  }
}
