/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ControlPanel extends SubsystemBase {

  public static ColorSensorV3 colorSensor = new ColorSensorV3(Port.kOnboard);

  /**
   * Creates a new ExampleSubsystem.
   */
  public ControlPanel() {

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    System.out.println(ControlPanel.getCurrentColor());
  }
  public static Color getCurrentColor() {
    return colorSensor.getColor();
  }

  public static char getCurrentFieldData() {
    return DriverStation.getInstance().getGameSpecificMessage().charAt(0);
  }
}
/*
 * Turn on RBG sensor Find out what color is at the front Set motor to coast
 * mode to make the band rotate Keep sensor on to track how many rotations have
 * passed Set motor in brake mode once two rotations have passed Turn off RBG
 * sensor
 */
/*
 * Turn on RBG sensor Set motor to coast mode Set motor back in brake mode when
 * it gets to the color needed Turn off RBG sensor
 */
