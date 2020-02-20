/** 
 * This class is for controlling the limelight Led light
 * via the networkTable entry's on the Shuffleboard  
 */

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class Limelight extends SubsystemBase {
  public boolean LedOn = false;
  public boolean LedOff = false;
  public boolean LedBlink = false;

  
  public LimeLedState currentState;

  // Enum for setting limelight state 
  enum LimeLedState{
    ON,OFF,BLINK,DEFAULT
  }

  public Limelight() {

  }
  
  // Controls Limelight LED State With A simple state machine
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


