/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import frc.robot.Constants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.util.PID;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycleEncoder;


public class Intake extends SubsystemBase {


  private final VictorSPX m_intakeFlip = new VictorSPX(Constants.kFlipMotorCAN);
  private final VictorSPX m_intakeMotor = new VictorSPX(Constants.kIntakeMotorCAN);

  private final Encoder m_intakeEncoder = new Encoder(Constants.kIntakeEncoderOne, Constants.kIntakeEncoderTwo);
  private final DutyCycleEncoder m_DutyCycleEncoder = new DutyCycleEncoder(Constants.kIntakeEncoderAbsolute);
  private final PID intakePID = new PID(Constants.PIntake, Constants.IIntake, Constants.DIntake,
      Constants.intakeEpsilon);

  public Intake() {
    intakePID.setMaxOutput(1.0);
    m_intakeFlip.setNeutralMode(NeutralMode.Coast);
    //m_intakeEncoder.setDistancePerPulse(m_intakeEncoder.getDistancePerPulse());
     //m_absoluteEncoder.get
  }


  public void setFlipPower(double power) {
    m_intakeFlip.set(ControlMode.PercentOutput, power);
    // Sets the power of the motor that flips out the intake
  }

  public void setIntakePower(final double power) {
    m_intakeMotor.set(ControlMode.PercentOutput, power);
    // Sets the power of the motor that turns the belts for the intake
  }


  public boolean isIntakeActive() {
    return true;
  }

  public double getEncoderDistance() {
    return m_intakeEncoder.getDistance();
    // To track how many rotations of the motor of intake
  }

  public void zeroIntakeEncoders() {
    m_intakeEncoder.reset();
   // Resets intake encoders
  }

  public double getAbsoluteEncoder(){
    return m_DutyCycleEncoder.getDistance();
  }

  public void setpointPID(final double setpoint) {
    intakePID.setDesiredValue(setpoint);
  }

  public double intakeCalcPID() {
    return intakePID.calcPID(getAbsoluteEncoder());
  }

  public boolean pidIsFinished() {
    return intakePID.isDone();
  }

 @Override
  public void periodic() {
   
  }
}
