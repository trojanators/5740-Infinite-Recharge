package frc.robot.datacollection.limelightData;

import com.team2363.logger.HelixLogger;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.datacollection.dashboard.TeleopModeDashBoard;
import frc.robot.util.CvsLoggerStrings;

/**
 * This Class is for calling Data from Limelight and Pulls the NetworkTab info
 * in a simple function
 */

public class LimelightData {

    private static TeleopModeDashBoard TelopDash = new TeleopModeDashBoard();

    // Small fuction to see if target is visible from the Limelight
    public static void isTargetVisible() {

        // runs logger
        HelixLogger.getInstance().addStringSource("Limelight_Target Seen", CvsLoggerStrings.Init);

        // Writes The limelight netwokrtable entry to shuffleboard boolean box
        TelopDash.isTargetSeen
                .setDouble(NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0));
    }

    public static void setLimeLightState() {
        
    }

}