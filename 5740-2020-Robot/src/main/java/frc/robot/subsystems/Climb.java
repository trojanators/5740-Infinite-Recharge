/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.util.function.Supplier;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Climb extends SubsystemBase {

  /**
   * -elevator goes up -winch pulls robot up by rope
   */

  private static WPI_TalonFX rClimeFx = new WPI_TalonFX(Constants.RClimeCAN);
  private static WPI_TalonFX lClimeFx = new WPI_TalonFX(Constants.LClimeCAN);

  DifferentialDrive climeDrive = new DifferentialDrive(rClimeFx, lClimeFx);

  public Climb() {

  }

  // Over Drive Clime motors to clime super fast
  public void superClime() {

  }

  // Diy Break mode for Clime motors
  public void climeBreak() {
    lClimeFx.set(0.5);
    rClimeFx.set(0.5);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
