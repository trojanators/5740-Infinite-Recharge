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
  public Turret turret;

  private NetworkTableEntry isTargetVis;
  private NetworkTableEntry indexerState;

  private NetworkTableEntry indexerCount;
  
private NetworkTableEntry colorSensorColor;

  public boolean targetCheck = false;

  // This function Sets up Shuffleboard layout
  public DashBoard(final Drivetrain m_Drivetrain, final Indexer m_indexer, final Turret m_turret) {

    this.driver = m_Drivetrain;
    this.indexer = m_indexer;
    this.turret = m_turret;

    TeleopDashboard();
    //DevDashboard();
  }

  public void TeleopDashboard() {

    //final ShuffleboardTab Teleop_Dashboard = Shuffleboard.getTab("TeleopDash");

    this.isTargetVis =Shuffleboard.getTab("Teleop").add("Is Target Visible", false).withSize(2, 1).withPosition(0, 0)
        .withWidget(BuiltInWidgets.kBooleanBox).withProperties(Map.of("colorWhenTrue", "blue", "colorWhenFalse", "black")).getEntry();

    this.colorSensorColor = Shuffleboard.getTab("Teleop").add("is color = to Required color",false).withSize(2, 1).withPosition(0, 2)
        .withWidget(BuiltInWidgets.kBooleanBox)   .withProperties(Map.of("colorWhenTrue", "blue", "colorWhenFalse", "black")).getEntry();

    this.indexerCount = Shuffleboard.getTab("Teleop").add("Ballcount",0).withSize(2, 1).withPosition(2, 0).withWidget(BuiltInWidgets.kDial).getEntry();  }




  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    this.isTargetVis.setBoolean(this.turret.seesTarget());

    this.indexerCount.setDouble(this.indexer.cellsContained);

  }
}
