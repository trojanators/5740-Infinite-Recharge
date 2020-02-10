/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Climb extends SubsystemBase {

  private final TalonFX climbFx = new TalonFX(Constants.kClimbFXCAN);

  public Climb() {

  }

  public void setPower(double ClimbSpeed) {
    climbFx.set(ControlMode.PercentOutput, ClimbSpeed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

  }
}
