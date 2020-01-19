/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Climb extends SubsystemBase {

  /**
   * -elevator goes up -winch pulls robot up by rope
   */

  private static WPI_TalonFX ClimeFx = new WPI_TalonFX(Constants.RClimeCAN);

  public Climb() {

  }

  // Over Drive Clime motors to clime super fast
  public void superClime() {

  }

  // Break mode for Clime motors used when robot is disabled
  public void climeBreak() {

  }

  // Pid Loop to control Clime from a button press
  public void climePid() {

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
