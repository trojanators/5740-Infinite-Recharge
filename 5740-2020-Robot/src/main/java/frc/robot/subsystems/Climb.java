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

  private final WPI_TalonFX climbFx = new WPI_TalonFX(Constants.FxClimbCAN);

  public Climb() {

  }

  public void setRobotRaise(Double ClimbSpeed) {
    setClimbPower(ClimbSpeed);
  }

  public void setClimbPower(Double ClimbSpeed) {
    climbFx.set(ClimbSpeed);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

  }
}
