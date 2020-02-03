package frc.robot.vision;

import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.RobotContainer;

public class VisionData {
    public VisionData() {

    }
    public double getHeadingToTarget() {
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
    }
}