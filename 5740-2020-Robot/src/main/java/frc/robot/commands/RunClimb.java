/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climb;

public class RunClimb extends CommandBase {


  private Climb m_climb;

  private double power =0;

  public RunClimb(Climb climb) {
   
    m_climb = climb;
    addRequirements(m_climb);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    // checks if Robot Pid is Not Finished 
    if(!m_climb.isPidFinished()) {

      // Calls Calc pid 
      m_climb.climbPidCalc(power);
      m_climb.setPower(power);

    }
    else {
      this.cancel();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_climb.setPower(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
