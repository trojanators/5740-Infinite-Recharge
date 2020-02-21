/** 
 * This class is for a Test mode Trigger to start the shuffleboard in test mode
 *  DO NOT WRITE ANYTHING IN THIS CLASS IT IS A TRGGER 
 * @author Nicholas blackburn    
 */

package frc.robot.commands.triggers;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.subsystems.DashBoard;

public class testmode extends Trigger {

  @Override
  public boolean get() {
    if (DriverStation.getInstance().isTest()) {

      DriverStation.reportWarning("TestMode is Active", true);

      return true;
    } else {

      DriverStation.reportWarning("TestMode Not Active", true);
      return false;
    }

  }
}
