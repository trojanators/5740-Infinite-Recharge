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
    public static int rightDriveACAN = 4;
    public static int leftDriveACAN = 3;
    public static int rightDriveBCAN = 5;
    public static int leftDriveBCAN = 6;

    public static double kRampRate = .25;
    public static int kContinuousCurrentLimit = 40;
    public static int kPeakCurrentLimit = 45;
    public static int kPeakCurrentDuration = 50;

    // climber CAN
    public static int kClimbFXCAN = 0;

    //Autonomous Constants
	public static final double kp = 3.5;//3.5
	public static final double kd = 0;
	public static final double kv = .08;//.08
	public static final double ka = .06;//.06
	

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

    public static double kIntakeSpeed = 4;
    public static int kIntakeMotor = 7; //this is for the intake mechanism
    public static int kIntakeButton = 3; 
    public static int kFlipMotor = 8;
    public static int kIntakeEncoderOne = 6;
    public static int kIntakeEncoderTwo = 7;
    public static int kIntakeTicksPerRotation = 2048;
    public static int kDropIntakeSetpoint = 0;
    public static int kRaiseIntakeSetpoint = 0;
    public static int kIntakeUpPosition = 0;
    public static int kIntakeDownPosition = 0;
    public static int kIntakeAbsoluteInput = 0;

    public static double PIntake = 0;
    public static double IIntake = 0;
    public static double DIntake = 0;
    public static double intakeEpsilon = 0;


    public static int leftStickY = 1;
    public static int rightStickX = 4;

    public static int kjoystickDriverPort = 0;
    public static int kjoystickOperatorPort = 1;

    public static int kInputTOFCAN = 1;
    public static int kOutputTOFCAN = 2;
    public static int kBackupTOFCAN = 3; //not used unless one breaks
    public static int kIndexMotorCAN = 4; //temporary CAN ID

    // Indexer Doubles in Millimeters
    public static double kCellIncomingValueLow = 30.0; //30 mm
    public static double kCellIncomingValueHigh = 110.0; //110 mm
    public static double kCellOutgoingValueLow = 30.0; //30 mm
    public static double kCellOutgoingValueHigh = 110.0; //110 mm

    // Indexer Sampling Period In Milliseconds 
    public static int kIndexerSamplingPeriod = 40; //40 ms
    
    // Indexer Preloaded Power Cells / Maximum Power cell amount 
    public static int kCellsPreloaded = 3;

    public static double kIndexerStowingMotorPower = .8;
    public static double kIndexerShootingMotorPower = .8;
    
    public static int kdriverJoystickPort = 0;
    public static int koperatorJoystickPort = 1;
    public static int kdropIntakeButton = 2;
    public static int kraiseIntakeButton = 3;
    public static int krunIntakeButton = 4;
    public static int krunReverseIntakeButton = 0;

    public static int kAutoDriveTime = 3; //This is for our simple auto example
    public static int kAutoTimeoutSeconds = 5; //This is for our simple auto example

    public static int kCpMotorPort = 4;

    public static int kShootCommandButton = 5; 
    public static int kjoystickPort = 0; 
    
    public static int kShooterACAN = 5;
    public static int kShooterBCAN = 6;
    public static int kTurnTurretCAN = 10;

    public static double PShooter = 0.02; //TUNED
    public static double IShooter = 0.0; //TUNED
    public static double DShooter = 0.001; //TUNED
    public static int shooterEpsilon = 1; //TUNED
    
    public static double shooterMaxOutput = 0.75; //TUNED
    public static double shooterRampTime = 0.25; //TUNED
    public static double kForwardSoftLimitValue = 0.0;
    public static double kReverseSoftLimitValue = 0.0;


    public static double Prpm = 0.00035; //TUNED
    public static double Irpm = 0.0; //TUNED
    public static double Drpm = 0.0; //TUNED
    public static double rpmFF = 0.000175; //TUNED
    public static double rpmMaxOutput = 1.0; //TUNED
    public static double rpmMinOutput = -1.0; //TUNED
    public static double rpmRampTime = 0.35; //TUNED

    public static int kMaxCPTicks = 8;
    public static int kMinColorReadingUntilAccepted = 5;
    public static final double kControlPanelSpeed = 0.5;
    public static Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
    public static Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
    public static Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
    public static Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);

}
