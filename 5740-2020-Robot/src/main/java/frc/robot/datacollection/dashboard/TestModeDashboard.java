package frc.robot.datacollection.dashboard;

import java.util.Map;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;

import frc.robot.Constants;

/**
 * This Dashboard is Used When Robot is in TestMode Displays every data point
 * from robot in test mode
 */

public class TestModeDashboard {

    double number;

    // TODO: Add pidf tuners

    // Creates Tab for Shuffleboard called Test-Tab
    private static ShuffleboardTab TestTab = Shuffleboard.getTab("Test-Tab");

    // Created an sperate layout for shawbs PIDAV loop
    ShuffleboardLayout cottenShawbLayout = Shuffleboard.getTab("Test-Tab").getLayout("ShawbPIDAV", BuiltInLayouts.kList)
            .withSize(4, 4).withPosition(0, 0);

    // Created an sperate layout for Drivetrain turn PIDF loop
    ShuffleboardLayout drivetrainturnLayout = Shuffleboard.getTab("Test-Tab")
            .getLayout("Drivetrain-Turn", BuiltInLayouts.kList).withSize(4, 4).withPosition(0, 7);

    /*
     * Cotten Shawbs non drive pidav loop section
     */

    // Added (proportional Gain for PIDF to shuffleboard
    private final NetworkTableEntry CottonPID_P = cottenShawbLayout.add("Proportional Gain", Constants.kP)
            .withPosition(0, 2).withSize(1, 2).withWidget(BuiltInWidgets.kNumberSlider)
            .withProperties(Map.of("min", 0, "max", 1.5)).getEntry();

    // Added Intergral Gain to shuffleboard
    private final NetworkTableEntry CottonPID_I = cottenShawbLayout.add(" Velocity Gain", Constants.kV)
            .withPosition(0, 4).withSize(1, 2).withWidget(BuiltInWidgets.kNumberSlider)
            .withProperties(Map.of("min", 0, "max", 1.5)).getEntry();

    // Added Deritive Gain to shuffleboard
    private final NetworkTableEntry CottonPID_D = cottenShawbLayout.add("Derivative Gain", Constants.kD)
            .withPosition(0, 6).withSize(1, 2).withWidget(BuiltInWidgets.kNumberSlider)
            .withProperties(Map.of("min", 0, "max", 1.5)).getEntry();
    // Added Acceleration Mapping to Shuffleboard
    private final NetworkTableEntry CottonPID_A = cottenShawbLayout.add("Acceleration Gain", Constants.kA)
            .withPosition(0, 8).withSize(1, 2).withWidget(BuiltInWidgets.kNumberSlider)
            .withProperties(Map.of("min", 0, "max", 1.5)).getEntry();

    /*
     * DriveTrain turn and drive pidf loop control
     */

    // Adds Drive PID_P
    private final NetworkTableEntry DrivePID_P = drivetrainturnLayout.add("Drive_PID_P", Constants.PDrive)
            .withPosition(0, 0).withSize(1, 2).withWidget(BuiltInWidgets.kNumberSlider)
            .withProperties(Map.of("min", 0, "max", 1.5)).getEntry();

    // Adds Drive PID_I
    private final NetworkTableEntry DrivePID_I = drivetrainturnLayout.add("Drive_PID_I", Constants.IDrive)
            .withPosition(0, 1).withSize(1, 2).withWidget(BuiltInWidgets.kNumberSlider)
            .withProperties(Map.of("min", 0, "max", 1.5)).getEntry();

    // Adds Drive PID_D
    private final NetworkTableEntry DrivePID_D = drivetrainturnLayout.add("Drive_PID_D", Constants.DDrive)
            .withPosition(0, 2).withSize(1, 2).withWidget(BuiltInWidgets.kNumberSlider)
            .withProperties(Map.of("min", 0, "max", 1.5)).getEntry();

    private final NetworkTableEntry DrivePIDEnable = drivetrainturnLayout.add("Enable Drive pid", false)
            .withPosition(0, 3).withSize(1, 2).withWidget(BuiltInWidgets.kNumberSlider)
            .withProperties(Map.of("min", 0, "max", 1.5)).getEntry();

    /** PIDF for Drivetrain turn */

    // Function Should Grab Pidf numbers every 20 ms
    public void Periodic() {

        // cotten shawbs pidav constants
        CottonPID_I.getDouble(Constants.kV);

        CottonPID_D.getDouble(Constants.kD);

        CottonPID_P.getDouble(Constants.kP);

        CottonPID_A.getDouble(Constants.kA);

        // Enables Test mode from dashboard

        // DriveTrain Turn and Drive PidLoops

        DrivePID_P.getDouble(Constants.PDrive);

        DrivePID_I.getDouble(Constants.IDrive);

        DrivePID_D.getDouble(Constants.DDrive);

    }

}