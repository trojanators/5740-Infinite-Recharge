/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.team2363.logger.HelixLogger;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.util.CvsLoggerStrings;

public class Turret extends SubsystemBase {

  /**
   * -Use computer vision to determine heading angle and distance from upper
   * target using relative size of a contour -Get encoder value to determine speed
   * of flywheel -Get encoder value to determine angle of turret -Set power of
   * flywheel motor -Set power of turret angle motor
   * 
   */

  public Turret() {
    HelixLogger.getInstance().addStringSource("Turret Subsystem", CvsLoggerStrings.Init::toString);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
