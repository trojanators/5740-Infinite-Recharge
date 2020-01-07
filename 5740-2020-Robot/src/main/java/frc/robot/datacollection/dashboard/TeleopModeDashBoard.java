package frc.robot.datacollection.dashboard;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

/**
 * This class is for Displaying Data To the DriverStation in TeleOpMode and
 * Shows user the specific Data from Teleop mode
 */

public class TeleopModeDashBoard {

    // Creates Tab for Shuffleboard called TeleOp-Tab
    public static ShuffleboardTab TeleOpTab = Shuffleboard.getTab("TeleOp-Tab");

}