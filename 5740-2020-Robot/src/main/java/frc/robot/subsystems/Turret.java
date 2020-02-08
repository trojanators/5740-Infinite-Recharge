/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.util.Map;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.team2363.logger.HelixLogger;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.util.CvsLoggerStrings;
import frc.robot.util.PID;
import sun.net.www.content.text.plain;

public class Turret extends SubsystemBase {

  private TalonSRX turretControl = new TalonSRX(Constants.TurretCAN);

  public double measuredX, tlong, thor, skewOffsetDegrees, actualXx;
  public final double pixelsToDegrees = .1419047619;
  
  private NetworkTableEntry shuffleDistance;
  private NetworkTableEntry kp, ki, kd, setPoint, pos, absolute, kff;
  
  private PID turretPID = new PID(Constants.PShooter, Constants.IShooter, Constants.DShooter, Constants.shooterEpsilon);

  private CANSparkMax shooterA = new CANSparkMax(Constants.kShooterACAN, MotorType.kBrushless);
  private CANSparkMax shooterB = new CANSparkMax(Constants.kShooterBCAN, MotorType.kBrushless);
  public TalonSRX turnTurret = new TalonSRX(Constants.kTurnTurretCAN);

  

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

    //shooterA.setOpenLoopRampRate(.25);
    //shooterB.setOpenLoopRampRate(.25);
    shooterA.setClosedLoopRampRate(Constants.shooterRampTime);
    shooterB.setClosedLoopRampRate(Constants.shooterRampTime);
    shooterB.follow(shooterA, true);
    shooterA.getPIDController().setP(Constants.Prpm);
    shooterA.getPIDController().setI(Constants.Irpm);
    shooterA.getPIDController().setD(Constants.Drpm);
    shooterA.getPIDController().setFF(Constants.rpmFF);
    shooterA.getPIDController().setOutputRange(Constants.rpmMinOutput, Constants.rpmMaxOutput);

    //turnTurret.configForwardSoftLimitEnable(true);
    //turnTurret.configReverseSoftLimitEnable(true);

    //turnTurret.setSelectedSensorPosition(0);  
    turnTurret.configClosedloopRamp(Constants.shooterRampTime);
    turnTurret.configOpenloopRamp(Constants.shooterRampTime);

    //turnTurret.configForwardSoftLimitThreshold(10000);
    //turnTurret.configReverseSoftLimitThreshold(-10000);
    resetTurnEncoder();

    turretPID.setMaxOutput(Constants.shooterMaxOutput);
   // turretPID.setConstants(Constants.PShooter, Constants.IShooter, Constants.IShooter);

    //turnTurret.getSensorCollection().syncQuadratureWithPulseWidth(-10000, 10000, true);

    /*kp1 = Shuffleboard.getTab("PID").add("proportional gain", 0).withWidget(BuiltInWidgets.kTextView).withSize(2, 2)
       .getEntry();

    kd1 = Shuffleboard.getTab("PID").add("derivative gain", 0).withWidget(BuiltInWidgets.kNumberSlider).withSize(2, 2)
        .withProperties(Map.of("min", 0, "max", 1.0)).getEntry();
    */
    /*kp = Shuffleboard.getTab("PID").add("proportional gain", 0).withWidget(BuiltInWidgets.kTextView).withSize(2, 2)
       .getEntry();
    ki = Shuffleboard.getTab("PID").add("integral gain", 0).withWidget(BuiltInWidgets.kTextView).withSize(2, 2)
       .getEntry();
    kd = Shuffleboard.getTab("PID").add("derivative gain", 0).withWidget(BuiltInWidgets.kTextView).withSize(2, 2)
       .getEntry();
    kff = Shuffleboard.getTab("PID").add("feed forward", 0).withWidget(BuiltInWidgets.kTextView).withSize(2, 2)
       .getEntry(); */
    setPoint = Shuffleboard.getTab("PID").add("Setpoint", 0).withWidget(BuiltInWidgets.kTextView).withSize(2, 2)
       .getEntry();

    pos = Shuffleboard.getTab("PID").add("Velocity", 0).withWidget(BuiltInWidgets.kTextView).withSize(2, 2)
       .getEntry();

  }

  @Override
  public void periodic() {

    
    // This method will be called once per scheduler run
    HelixLogger.getInstance().addStringSource("Turret Subsystem", CvsLoggerStrings.Init::toString);
    shuffleDistance.setDouble(getHeadingToTarget());
    pos.setDouble(shooterA.getEncoder().getVelocity());
    //shooterA.getPIDController().setP(kp.getDouble(0));
    //shooterA.getPIDController().setI(ki.getDouble(0));
    //shooterA.getPIDController().setD(kd.getDouble(0));
    //shooterA.getPIDController().setFF(kff.getDouble(0));

    //shooterA.getPIDController().setReference(setPoint.getDouble(0), ControlType.kVelocity);
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

  public void setShooterRPM(int rpm) {
    shooterA.getPIDController().setReference(rpm, ControlType.kVelocity);
  }
  
  public void setTurnSpeed(double speed) {
    turnTurret.set(ControlMode.PercentOutput, speed);
  }

  public void stopTurn() {
    turnTurret.set(ControlMode.PercentOutput, 0);
  }

  public void turretSetpoint(double setpoint) {
    turretPID.setDesiredValue(setpoint);
  }

  public double turretCalcPID() {
    return turretPID.calcPID(getHeadingToTarget());
  }

  public double getTurnEncoderValue() {
    return turnTurret.getSelectedSensorPosition();
  }

  public double getAbsoluteEncoderValue() {
    return turnTurret.getSensorCollection().getPulseWidthPosition();
  }

  public void resetTurnEncoder() {
    turnTurret.setSelectedSensorPosition((int)getAbsoluteEncoderValue());
  }

  public PID getTurnPID() {
    return turretPID;
  }

}
