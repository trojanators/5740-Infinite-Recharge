/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.playingwithfusion.TimeOfFlight;
import com.playingwithfusion.TimeOfFlight.RangingMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;

/**
 * This Class Grabs data from the TOF sensors and Converts them in to Inches for
 * our robot
 */

public class Indexer extends SubsystemBase {
  /**
   * Creates a new Indexer.
   */

  private double Intakemm;
  private double Turretmm;

  public double IntakeInches;
  public double TurretInches;

  // inits TOF Sensors for Intake and turret
  private final TimeOfFlight IntakeIndexer = new TimeOfFlight(Constants.IntakeIndexerCAN);
  private final TimeOfFlight TurretIndexer = new TimeOfFlight(Constants.TurretIndexerCAN);

  public Indexer() {

    setShortRangeMode(40);

  }

  // function to set TOF refresh mils
  public void setShortRangeMode(final int RefreshTime) {
    IntakeIndexer.setRangingMode(RangingMode.Short, RefreshTime);
    TurretIndexer.setRangingMode(RangingMode.Short, RefreshTime);
  }

  // grabs distance in mm -> converts them into inches
  public void getDistanceInches() {

    this.Intakemm = IntakeIndexer.getRange();
    this.Turretmm = TurretIndexer.getRange();

    // formula to calc mm -> inches
    this.IntakeInches = (double) (this.Intakemm / 25.4);
    this.TurretInches = (double) (this.Turretmm / 25.4);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    getDistanceInches();
  }
}
