/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.util.logging.Logger;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * This Class is the System Logger It Logs any exceptions and any info you want
 * and it will display it in the rio log
 */

public class RobotLogger extends SubsystemBase {

  private static Logger RobotLogger;

  /**
   * Creates a new Robot Logger.
   */
  public RobotLogger() {

  }

  public static void logInfo(final String info) {
    RobotLogger.info(info);
  }

  // Used for logging warnings with exceptions

  public static void logWarningException(final String Waring, final Exception exception) throws Exception {
    RobotLogger.warning(Waring + ":");
  }

  public static void logWarning(final String Warning) {
    RobotLogger.warning(Warning);

  }

  public static void logDangerous(final String Danger, final Exception exception) throws Exception {

    RobotLogger.severe(Danger + ":");

  }
}
