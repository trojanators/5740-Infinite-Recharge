/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.ColorMatch;

import edu.wpi.first.wpilibj.util.Color;


/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 */
public final class Constants {
  

    // DriveTrain CAN ids
    public static int rightDriveACAN = 10;
    public static int leftDriveACAN = 11;
    public static int rightDriveBCAN = 13;
    public static int leftDriveBCAN = 14;

    public static double kRampRate = .25;
    public static int kContinuousCurrentLimit = 40;
    public static int kPeakCurrentLimit = 45;
    public static int kPeakCurrentDuration = 50;

    // climber CAN
    public static int kClimbFXCAN = 15;

    // Autonomous Constants
    public static final double kEncoderTicksPerFoot = 2607.595;
    public static final double kp = 3.5;// 3.5
    public static final double kd = 0;
    public static final double kv = .08;// .08
    public static final double ka = .06;// .06

    public static double PTurn = .05;
    public static double ITurn = 0;
    public static double DTurn = .1;
    public static double turnEpsilon = 3.0;

    public static double PdriveTurn = .06;
    public static double IdriveTurn = 0;
    public static double DdriveTurn = 0.0;

    public static double PDrive = 1.5;
    public static double IDrive = 0.0;
    public static double DDrive = 4.0;
    public static double driveEbsilon = 2.0;

    public static double PHold = -.0001;

    public static double kIntakeSpeed = .65;
    public static int kIntakeMotorCAN = 1; // this is for the intake mechanism
    public static int kFlipMotorCAN = 2;
    public static int kIntakeTicksPerRotation = 2048;
    public static int kIntakeUpPosition = 0;
    public static int kIntakeDownPosition = 0;
    //public static int kIntakeAbsoluteInput = 0;
    public static int kIntakeEncoderOne = 6;
    public static int kIntakeEncoderTwo = 7;



    public static double PIntake = 0;
    public static double IIntake = 0;
    public static double DIntake = 0;
    public static double intakeEpsilon = 0;

    public static int kInputTOFCAN = 2;
    public static int kOutputTOFCAN = 1;
    public static int kBackupTOFCAN = 3; // not used unless one breaks
    public static int kIndexMotorCAN = 3; // temporary CAN ID

  
    // Indexer Doubles in Millimeters
    public static double kCellIncomingValueLow = 30.0; // 30 mm
    public static double kCellIncomingValueHigh = 90.0; // 110 mm
    public static double kCellOutgoingValueLow = 30.0; // 30 mm
    public static double kCellOutgoingValueHigh = 90.0; // 110 mm

    // Indexer Sampling Period In Milliseconds
    public static int kIndexerSamplingPeriod = 40; // 40 ms

    // Indexer Preloaded Power Cells / Maximum Power cell amount
    public static int kCellsPreloaded = 3;

    public static double kIndexerStowingMotorPower = .8;
    public static double kIndexerShootingMotorPower = .8;

    public static int kAutoDriveTime = 3; // This is for our simple auto example
    public static int kAutoTimeoutSeconds = 5; // This is for our simple auto example

    public static int kCPMotorPort = 4;

    public static int kShooterACAN = 5;
    public static int kShooterBCAN = 6;
    public static int kTurnTurretCAN = 12;

    public static double PShooter = 0.03; // TUNED
    public static double IShooter = 0.0; // TUNED
    public static double DShooter = 0.001; // TUNED
    public static int shooterEpsilon = 1; // TUNED

    public static double shooterMaxOutput = 0.85; // TUNED
    public static double shooterRampTime = 0.25; // TUNED
    public static int kForwardSoftLimitValue = 0;
    public static int kReverseSoftLimitValue = 0;

    public static double Prpm = 0.00035; // TUNED
    public static double Irpm = 0.0; // TUNED
    public static double Drpm = 0.0; // TUNED
    public static double rpmFF = 0.000175; // TUNED
    public static double rpmMaxOutput = 1.0; // TUNED
    public static double rpmMinOutput = 0.0; // TUNED
    public static double rpmRampTime = 0.35; // TUNED

    public static int kMaxCPTicks = 8;
    public static int kMinColorReadingUntilAccepted = 5;
    public static final double kControlPanelSpeed = 0.5;

    // Pid Loop For Climb
    public static final double PClimb = 0.00006;
    public static final double IClimb = 0;
    public static final double DClimb = 0.0001;
    public static final double climbEpsilon = 100;
    public static final double kClimbMaxOutput = 0.8;

    public static final int kraiseClimbButton = 1;
    public static final int koptestbutton = 10;

    public static Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
    public static Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
    public static Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
    public static Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);
    
    // Driver Controller Bindings

    // Driver Controller port 
    public static int kDriverPort = 0; 
    public static int kOperatorPort = 1;

    // Driver Controller Axis's

    // left stick axis's
    public static int kLeftStickX =  0;
    public static int kLeftStickY = 1;

    // Right Stick Axis's
    public static int kRightStickX = 4;
    public static int kRightStickY = 5;

    // Triggers 
    public static int kRightTrigger = 3;
    public static int kLeftTrigger = 2;

    // Buttons Bindings    
    public static int kA = 1;
    public static int kB = 2;
    public static int kX = 3;
    public static int kY = 4;

    public static int kLB = 5;
    public static int kRB = 6;

    public static int kSelect = 7;
    public static int kStart = 8;

    public static int kLeftStickPress = 9;
    public static int kRightStickPress = 10;

    


}
