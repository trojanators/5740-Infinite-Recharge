/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class Limelight extends SubsystemBase {
  /**
   * Creates a new Limelight.
   */
  public boolean LedOn = false;
  public boolean LedOff = false;
  public boolean LedBlink = false;

  
  public LimeLedState currentState;
  enum LimeLedState{
    ON,OFF,BLINK,DEFAULT
  }

  public Limelight() {

  }
  public void LimelightState(final LimeLedState state) {
    switch (currentState) {
      case OFF:
        // Sets LimeLed tp be off
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);
        break;
      case ON:
        // Sets LimeLed tp be on
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(3);
        break;

      case BLINK:

        // Sets LimeLed to blink
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(2);
        break;
      case DEFAULT:

      // Sets LimeLed to use current settings from pipeline
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(0);
        break;
    }
  }



  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    if (DriverStation.getInstance().isTest()){
      // Sets Lime LED ON
      if(LedOn){
        currentState = LimeLedState.ON;

      } else{

        currentState = LimeLedState.DEFAULT;
      }
      // Sets Lime LED OFF
      if(LedOff){
        currentState = LimeLedState.OFF;

      } else{

        currentState = LimeLedState.DEFAULT;
      }
      // Sets Lime LED BLINK
      if(LedBlink){

        currentState = LimeLedState.BLINK;
      } else{

        currentState = LimeLedState.DEFAULT;
      }
    }
  }

}
