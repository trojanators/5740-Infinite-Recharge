/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import frc.robot.Constants;
import frc.robot.util.CvsLoggerStrings;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.util.PID;

public class Intake extends SubsystemBase {

  /**
   * Creates a new ExampleSubsystem.
   */
 

  private final Joystick m_joy;
  private final VictorSPX m_robotIntake = new VictorSPX(Constants.kIntakeMotor);

  private final VictorSPX m_intakeFlip = new VictorSPX(Constants.kFlipMotor);

  private final Encoder m_intakeEncoder = new Encoder(Constants.kIntakeEncoderOne, Constants.kIntakeEncoderTwo, true,EncodingType.k4X);
  private final DigitalInput m_absoluteEncoder = new DigitalInput(Constants.kIntakeAbsoluteInput);
  private final NetworkTableEntry FWD, REV, arm;
  private final PID intakePID = new PID(Constants.PIntake, Constants.IIntake, Constants.DIntake,
      Constants.intakeEpsilon);

  public Intake(final Joystick joy) {
    this.m_joy = joy;
    zeroIntakeEncoders();
    intakePID.setMaxOutput(1.0);
    // m_intakeEncoder.setDistancePerPulse(m_intakeEncoder.getDistancePerPulse());
    // m_absoluteEncoder.get

    this.FWD = Shuffleboard.getTab("intake").add("fwd", false).withSize(1, 1).withPosition(1, 0)
        .withWidget(BuiltInWidgets.kToggleButton).getEntry(); // HelixLogger.getInstance().addStringSource("Intake
                                                              // Subsystem", CvsLoggerStrings.Init::toString);
    this.REV = Shuffleboard.getTab("intake").add("REV", false).withSize(1, 1).withPosition(0, 0)
        .withWidget(BuiltInWidgets.kToggleButton).getEntry(); // HelixLogger.getInstance().addStringSource("Intake
                                                              // Subsystem", CvsLoggerStrings.Init::toString);
    this.arm = Shuffleboard.getTab("intake").add("arm", false).withSize(1, 1).withPosition(0, 0)
        .withWidget(BuiltInWidgets.kToggleButton).getEntry(); // HelixLogger.getInstance().addStringSource("Intake
                                                              // Subsystem", CvsLoggerStrings.Init::toString);
  }

  public void setFlipPower(final double power) {
    m_intakeFlip.set(ControlMode.PercentOutput, power);
    // Sets the power of the motor that flips out the intake
  }

  public void setIntakePower(final double power) {
    m_robotIntake.set(ControlMode.PercentOutput, power);
    // Sets the power of the motor that turns the belts for the intake
  }

  public void setReverseIntakePower(final double power) {
    m_robotIntake.set(ControlMode.PercentOutput, -power);
  }

  public boolean isIntakeActive() {
    return m_intakeEncoder.getDirection();
  }

  public double getEncoderDistance() {
    return m_intakeEncoder.getDistance();
    // To track how many rotations of the motor of intake
  }

  public void zeroIntakeEncoders() {
    m_intakeEncoder.reset();
    // Resets intake encoders
  }

  public void setpointPID(final double setpoint) {
    intakePID.setDesiredValue(setpoint);
  }

  public double intakeCalcPID() {
    return intakePID.calcPID(getEncoderDistance());
  }

  public boolean pidIsFinished() {
    return intakePID.isDone();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    // starts system?
   // System.out.print( m_intakeEncoder.getRate()/2048);
   final boolean rev = false;
   final boolean fwd = false;
   

   if(this.m_joy.getRawButton(7)){
     setFlipPower(.5);
   }else if(this.m_joy.getRawButton(8)){
    setFlipPower(-.5);
  } else{
   setFlipPower(0);
  } 

   if(this.m_joy.getRawButton(6)){
     setIntakePower(-.8);
   }else if(this.m_joy.getRawButton(5)){
    setIntakePower(.8);
  } else{
    setIntakePower(0);
  }
    
  }
}
