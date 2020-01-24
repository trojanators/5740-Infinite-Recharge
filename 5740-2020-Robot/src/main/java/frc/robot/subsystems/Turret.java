/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.team2363.logger.HelixLogger;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.util.CvsLoggerStrings;

public class Turret extends SubsystemBase {
  public double measuredX, tlong, thor, skewOffsetDegrees, actualXx;
  public final double pixelsToDegrees = .1419047619;
  private NetworkTableEntry shuffleDistance;
  private double x, y, y2, pitch, yaw, roll;
  /**
   * -Use computer vision to determine heading angle and distance from upper
   * target using relative size of a contour -Get encoder value to determine speed
   * of flywheel -Get encoder value to determine angle of turret -Set power of
   * flywheel motor -Set power of turret angle motor
   * 
   */

  public Turret() {
    shuffleDistance = Shuffleboard.getTab("Vision")
    .add("Actual heading", getHeadingToTarget())
    .withWidget(BuiltInWidgets.kTextView)
    .getEntry();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    HelixLogger.getInstance().addStringSource("Turret Subsystem", CvsLoggerStrings.Init::toString);
    shuffleDistance.setDouble(getHeadingToTarget());
    System.out.println("observed heading:" + NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0));
   
  }
  public double getHeadingToTarget() {
    double seesTarget = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
    double kSkew = 1000.0; // multiplier for calculated skew offset
    if(seesTarget == 1.0) {
      measuredX = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
      tlong = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tlong").getDouble(0);
      thor = NetworkTableInstance.getDefault().getTable("limelight").getEntry("thor").getDouble(0);
      skewOffsetDegrees = (tlong - thor) * pixelsToDegrees;

      if(measuredX < 0) {
        //System.out.println("skew: " + skewOffsetDegrees + " actual: " + (measuredX + skewOffsetDegrees));
        return measuredX + skewOffsetDegrees;
      } else if(measuredX > 0) {
        //System.out.println("skew: " + skewOffsetDegrees + " actual: " + -(Math.abs(measuredX) + skewOffsetDegrees));
        return -(Math.abs(measuredX) + skewOffsetDegrees);
      } else {
        return 0.0;
      }
    } else {
      return 0.0;
    }
  }
}

