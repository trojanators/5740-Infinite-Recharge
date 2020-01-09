/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Drivetrain extends SubsystemBase {
  /**
   * Creates a new ExampleSubsystem.
   */

  // Talon Motor Controller Methods
  final WPI_TalonSRX frontRDrive = new WPI_TalonSRX(Constants.FrontRightDriveCAN);
  final WPI_TalonSRX backRDrive = new WPI_TalonSRX(Constants.BackRightDriveCAN);

  final WPI_TalonSRX frontLDrive = new WPI_TalonSRX(Constants.FrontLeftDriveCAN);
  final WPI_TalonSRX backLDrive = new WPI_TalonSRX(Constants.BackLeftDriveCAN);

  // Creates a SpeedController group to control both groups of motors
  final SpeedControllerGroup leftDriveGroup = new SpeedControllerGroup(frontLDrive, backLDrive);
  final SpeedControllerGroup rightDriveGroup = new SpeedControllerGroup(frontRDrive, backRDrive);

  public final DifferentialDrive drive = new DifferentialDrive(leftDriveGroup, rightDriveGroup);

  /**
   * TODO: pid loop hold mode
   * 
   * TODO: current limiting
   * 
   * TODO: tip detection pid loop
   * 
   * TODO: add encoders and gyros
   * 
   * TODO: Remove Speed Comtroller group and rewrite Differteal drive
   * 
   * 
   */

  public Drivetrain() {

    // Talon Motor Controller Methods

  }

  @Override
  public void periodic() {

    // This method will be called once per scheduler run
  }
}
