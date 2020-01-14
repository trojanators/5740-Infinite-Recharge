package frc.robot.datacollection.dashboard;

import java.util.Map;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.util.Color;

/**
 * This class is for Displaying Data To the DriverStation in TeleOpMode and
 * Shows user the specific Data from Teleop mode
 */

public class TeleopModeDashBoard {

        // Creates Tab for Shuffleboard called TeleOp-Tab
        public static ShuffleboardTab TeleOpTab = Shuffleboard.getTab("TeleOp-Tab");

        // Limelight Target is seen
        public final NetworkTableEntry isTargetSeen = TeleOpTab.add("Is_Target_Seen", false)
                        .withWidget(BuiltInWidgets.kBooleanBox).withPosition(0, 0).withSize(2, 1)
                        .withProperties(Map.of("True", Color.kLimeGreen, "False", Color.kMaroon)).getEntry();

        // Limelight Target accurcy
        public final NetworkTableEntry targetAccuracy = TeleOpTab.add("Target_Accuracy", 0)
                        .withWidget(BuiltInWidgets.kNumberBar).withPosition(0, 1).withSize(2, 1)
                        .withProperties(Map.of("min", 0, "max", 100)).getEntry();

        /*
         * // Robot Pressure Control public final NetworkTableEntry pressureEntry =
         * TeleOpTab.add("Pressure", 0)
         * .withWidget(BuiltInWidgets.kToggleButton).withPosition(7, 1).withSize(2, 2)
         * .withProperties(Map.of("min", 0, "max", 100)).getEntry();
         */
}