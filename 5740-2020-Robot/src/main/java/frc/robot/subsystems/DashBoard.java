/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DashBoard extends SubsystemBase {
  /**
   * Creates a new DashBoard.
   */

  public NetworkTableEntry isTargetVis;
  public NetworkTableEntry ballpos;
  public NetworkTableEntry gyro;

  public DashBoard() {

  }

  public void dashInit() {
    isTargetVis = Shuffleboard.getTab("Dev").add("Is Target Visible", false).withPosition(0, 0).withSize(2, 1)
        .withWidget(BuiltInWidgets.kBooleanBox).getEntry();

    ballpos = Shuffleboard.getTab("Dev").add("Ball Count", 0).withPosition(0, 2).withSize(2, 1)
        .withWidget(BuiltInWidgets.kDial).getEntry();

    gyro = Shuffleboard.getTab("Dev").add("Gyro Pos", 0).withPosition(0, 4).withSize(2, 2)
        .withWidget(BuiltInWidgets.kGyro).getEntry();

  }

  public void dashData() {

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
