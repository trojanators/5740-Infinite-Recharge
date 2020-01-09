/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drivetrain;

public class Drivetrain_TankDrive extends CommandBase {
  /**
   * Creates a new Drivetrain_TankDrive.
   */
  public final Drivetrain m_drivetrain;

  public Drivetrain_TankDrive(Drivetrain drivetrain) {
    // Use addRequirements() here to declare subsystem dependencies.

    m_drivetrain = drivetrain;

    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    // driver control
    m_drivetrain.drive.arcadeDrive(RobotContainer.driverController.getRawAxis(0),
        RobotContainer.driverController.getRawAxis(5));
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(final boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
