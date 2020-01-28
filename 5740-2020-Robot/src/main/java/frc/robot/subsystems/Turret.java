/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.team2363.logger.HelixLogger;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.util.CvsLoggerStrings;
import frc.robot.util.PID;

public class Turret extends SubsystemBase {

  private TalonSRX turretControl = new TalonSRX(Constants.TurretCAN);

  public double measuredX, tlong, thor, skewOffsetDegrees, actualXx;
  public final double pixelsToDegrees = .1419047619;
  private NetworkTableEntry shuffleDistance;
  private PID turretPID = new PID(Constants.PShooter, Constants.IShooter, Constants.DShooter, Constants.shooterEpsilon);
  private CANSparkMax shooterA = new CANSparkMax(Constants.kShooterACAN, MotorType.kBrushless);
  private CANSparkMax shooterB = new CANSparkMax(Constants.kShooterBCAN, MotorType.kBrushless);
  private VictorSPX turnTurret = new VictorSPX(Constants.kTurnTurretCAN);
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
    shooterB.setInverted(true);
    shooterB.follow(shooterA);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    HelixLogger.getInstance().addStringSource("Turret Subsystem", CvsLoggerStrings.Init::toString);
    shuffleDistance.setDouble(getHeadingToTarget());
    //System.out.println("observed heading:" + NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0));
   
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
  public void setShooterSpeed(double speed) {
    shooterA.set(speed);
  }
  public void stopShooter() {
    shooterA.set(0);
  }
  public void setTurnSpeed(double speed) {
    turnTurret.set(ControlMode.PercentOutput, speed);
  }
  public void stopTurn() {
    turnTurret.set(ControlMode.PercentOutput, 0);
  }
}

