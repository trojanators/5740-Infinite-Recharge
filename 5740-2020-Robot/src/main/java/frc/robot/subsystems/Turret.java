/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.ControlType;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.util.PID;

public class Turret extends SubsystemBase {
  
  public boolean testpid = false;

  public double seesTarget = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
  public double measuredX, tlong, thor, skewOffsetDegrees, actualXx;
  public final double pixelsToDegrees = .1419047619;

  private NetworkTableEntry shuffleDistance;
  private NetworkTableEntry abs, quad, kp, ki, kd, kff, period, pos, setPoint, height;

  private PID turretPID = new PID(Constants.PShooter, Constants.IShooter, Constants.DShooter, Constants.shooterEpsilon);

  private CANSparkMax shooterA = new CANSparkMax(Constants.kShooterACAN, MotorType.kBrushless);
  private CANSparkMax shooterB = new CANSparkMax(Constants.kShooterBCAN, MotorType.kBrushless);
  public WPI_TalonSRX turnTurret = new WPI_TalonSRX(Constants.kTurnTurretCAN);


  /**
   * -Use computer vision to determine heading angle and distance from upper
   * target using relative size of a contour -Get encoder value to determine speed
   * of flywheel -Get encoder value to determine angle of turret -Set power of
   * flywheel motor -Set power of turret angle motor
   * 
   */

  public Turret() {

    shuffleDistance = Shuffleboard.getTab("Vision").add("Actual heading", getHeadingToTarget())
        .withWidget(BuiltInWidgets.kTextView).getEntry();

    shooterA.setClosedLoopRampRate(Constants.rpmRampTime);
    shooterB.setClosedLoopRampRate(Constants.rpmRampTime);
    shooterB.follow(shooterA, true);
    shooterA.getPIDController().setP(Constants.Prpm);
    shooterA.getPIDController().setI(Constants.Irpm);
    shooterA.getPIDController().setD(Constants.Drpm);
    shooterA.getPIDController().setFF(Constants.rpmFF);
    shooterA.getPIDController().setOutputRange(Constants.rpmMinOutput, Constants.rpmMaxOutput);

    turnTurret.configClosedloopRamp(Constants.shooterRampTime);
    turnTurret.configOpenloopRamp(Constants.shooterRampTime);

    resetTurnEncoder();

    turretPID.setMaxOutput(Constants.shooterMaxOutput);
    
    /*kp = Shuffleboard.getTab("PID").add("proportional gain", 0).withWidget(BuiltInWidgets.kTextView).withSize(2, 2)
        .getEntry();
    ki = Shuffleboard.getTab("PID").add("integral gain", 0).withWidget(BuiltInWidgets.kTextView).withSize(2, 2)
        .getEntry();
    kd = Shuffleboard.getTab("PID").add("derivative gain", 0).withWidget(BuiltInWidgets.kTextView).withSize(2, 2)
        .getEntry();
    kff = Shuffleboard.getTab("PID").add("feed forward", 0).withWidget(BuiltInWidgets.kTextView).withSize(2, 2)
        .getEntry();
    setPoint = Shuffleboard.getTab("PID").add("Setpoint", 0).withWidget(BuiltInWidgets.kTextView).withSize(2, 2)
        .getEntry();

    pos = Shuffleboard.getTab("PID").add("Velocity", 0).withWidget(BuiltInWidgets.kTextView).withSize(2, 2).getEntry();

     abs = Shuffleboard.getTab("turret").add("Absolute",0).withPosition(1,
     0).withSize(2,1).withWidget(BuiltInWidgets.kTextView).getEntry();
     quad = Shuffleboard.getTab("turret").add("qude",0).withPosition(1,
     2).withSize(2,1).withWidget(BuiltInWidgets.kTextView).getEntry();

    period = Shuffleboard.getTab("PID").add("Period", 0).withWidget(BuiltInWidgets.kTextView).withSize(2, 2).getEntry();

    height = Shuffleboard.getTab("PID").add("Height", 0).withWidget(BuiltInWidgets.kTextView).withSize(2, 2).getEntry();

    resetTurnEncoder();*/
  }

  @Override
  public void periodic() {

    // This method will be called once per scheduler run
    // shuffleDistance.setDouble(getHeadingToTarget());
    //pos.setDouble(shooterA.getEncoder().getVelocity());
    
     /* shooterA.getPIDController().setP(kp.getDouble(0));
      shooterA.getPIDController().setI(ki.getDouble(0));
      shooterA.getPIDController().setD(kd.getDouble(0));
      shooterA.getPIDController().setFF(kff.getDouble(0));*/
     // shooterA.setControlFramePeriodMs((int)period.getDouble(0));
     
    //height.setDouble(getHeight());
    // System.out.println((int)period.getDouble(0));
    // abs.setDouble(getAbsoluteEncoderValue());
    // quad.setDouble(getTurnEncoderValue());
     //shooterA.getPIDController().setReference(setPoint.getDouble(0), ControlType.kVelocity);
  }

  public boolean seesTarget() {
    double seesTarget = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
    if (seesTarget == 1.0) {
      return true;
    } else {
      return false;
    }
  }

  public double getHeadingToTarget() {
    double seesTarget = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
    if (seesTarget == 1.0) {
      return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0)
          + NetworkTableInstance.getDefault().getTable("limelight").getEntry("ts").getDouble(0);
    } else {
      return 0.0;
    }
  }

  public double getSkew() {
    return NetworkTableInstance.getDefault().getTable("limelight").getEntry("ts").getDouble(0);
  }

  public double getHeight() {
    return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tshort").getDouble(0);
  }

  public double getX() {
    return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
  }

  public void setShooterSpeed(double speed) {
    shooterA.set(speed);
  }

  public void stopShooter() {
    shooterA.set(0);
  }
  public double getShooterAverageRPM() {
    return shooterA.getEncoder().getVelocity() + shooterB.getEncoder().getVelocity()/2;
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

  public int getAbsoluteEncoderValue() {
    return turnTurret.getSensorCollection().getPulseWidthPosition();
  }

  public void resetTurnEncoder() {
    turnTurret.setSelectedSensorPosition(getAbsoluteEncoderValue());
  }

  public Boolean isTurretActive(){
    return turnTurret.isAlive() ;
  }

  public PID getTurnPID() {
    return turretPID;
  }
}
