/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Intake;

public class DropIntake extends CommandBase {
  private Intake intake;
  
  public DropIntake(Intake m_intake) {
    // Use addRequirements() here to declare subsystem dependencies.
    intake = m_intake;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    intake.setpointPID(Constants.kDropIntakeSetpoint);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(!intake.pidIsFinished()) {
      intake.setFlipPower(intake.intakeCalcPID());
    } else {
      this.cancel();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    intake.setFlipPower(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
