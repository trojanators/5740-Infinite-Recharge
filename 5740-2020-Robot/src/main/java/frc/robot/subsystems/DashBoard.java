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
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DashBoard extends SubsystemBase {
  /**
   * Creates a new DashBoard.
   */

  // TODO: add Color Display Widget after robot is complete
  // TODO: get Sensor Data without throwing a nullpointer
  public NetworkTableEntry isTargetVis;
  public NetworkTableEntry ballpos;
  public NetworkTableEntry GyroPOS;
  public NetworkTableEntry colorDetect;

  public boolean targetCheck;

  public DashBoard() {

  }

  public void dashInit() {

    isTargetVis = Shuffleboard.getTab("Dev").add("Is Target Visible", false).withPosition(0, 0).withSize(2, 1)
        .withWidget(BuiltInWidgets.kBooleanBox).getEntry();

    ballpos = Shuffleboard.getTab("Dev").add("Ball Count", 0).withPosition(0, 2).withSize(2, 1)
        .withWidget(BuiltInWidgets.kDial).withProperties(Map.of("min", 0, "max", 5)).getEntry();

    GyroPOS = Shuffleboard.getTab("Dev").add("Gyro Pos", 0).withPosition(3, 4).withSize(2, 2)
        .withWidget(BuiltInWidgets.kGyro).getEntry();

  }

  public void dashData() {

    double limeLightTarget = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);

    if (limeLightTarget == 1) {
      targetCheck = true;

    } else {
      targetCheck = false;

    }
    // isTargetVis.getBoolean(targetCheck);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    dashData();
  }
}
