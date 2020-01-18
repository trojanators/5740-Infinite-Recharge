/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

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

    // DriveTrain can ids
    public static int FrontRightDriveCAN = 3;
    public static int FrontLeftDriveCAN = 4;

    public static int BackRightDriveCAN = 5;
    public static int BackLeftDriveCAN = 6;

    public static double kP = 3.5;
    public static double kD = 0;
    public static double kV = .08;
    public static double kA = .06;

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

    public static double PIntake = 0;
    public static double IIntake = 0;
    public static double DIntake = 0;
    public static double intakeEpsilon = 0;

    public static int leftStickY = 1;
    public static int rightStickX = 4;

    public static int kjoystickPort = 0;

    public static int kAutoDriveTime = 3; //This is for our simple auto example
    public static int kAutoTimeoutSeconds = 5; //This is for our simple auto example
	

}








