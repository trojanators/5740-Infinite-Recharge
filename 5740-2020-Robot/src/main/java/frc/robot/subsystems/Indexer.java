/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Indexer extends SubsystemBase {
  /**
   * Creates a new Indexer.
   */
  private final Victor m_indexMotor = new Victor (5);
  private final Encoder m_indexEncoder = new Encoder (4,5); 
  //private final TimeofFlight ballSensor = new TimeofFlight(7);

  // turn on indexwe when the 'A' button is pressed
  new JoystickButton(m_storage, Button.kA.value)
  .whenPressed(new InstantCommand(m_indexMotor::enable, m_indexMotor));

  public Indexer() {

  }

  public double getRate(){
    return m_indexEncoder.getRate(); 
  }
    
  public double getDistance(){
    return m_indexEncoder.getDistance();
  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
