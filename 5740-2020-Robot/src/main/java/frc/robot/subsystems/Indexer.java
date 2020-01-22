/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.playingwithfusion.TimeOfFlight;
import com.playingwithfusion.TimeOfFlight.RangingMode;

import frc.robot.Constants;

public class Indexer extends SubsystemBase {
  /**
   * Creates a new Indexer.
   */

  private double Intakemm;
  private Double Turretmm;

  public Double IntakeInches;
  public Double TurretInches;

  // inits TOF Sensors for Intake and turret
  private final TimeOfFlight IntakeIndexer = new TimeOfFlight(Constants.IntakeIndexerCAN);
  private final TimeOfFlight TurretIndexer = new TimeOfFlight(Constants.TurretIndexerCAN);

  public Indexer() {

    setShortRangeMode(40);

  }

  // function to set TOF refresh mils
  public void setShortRangeMode(final int Refreshmills) {
    IntakeIndexer.setRangingMode(RangingMode.Short, Refreshmills);
    TurretIndexer.setRangingMode(RangingMode.Short, Refreshmills);
  }

  public void getDistanceInches() {

    this.Intakemm = IntakeIndexer.getRange();
    this.Turretmm = TurretIndexer.getRange();

    IntakeInches = (double) (this.Intakemm / 25.4);
    TurretInches = (double) (this.Turretmm / 25.4);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    getDistanceInches();
  }
}
