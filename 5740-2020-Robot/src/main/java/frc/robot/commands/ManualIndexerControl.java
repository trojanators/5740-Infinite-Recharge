/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Indexer;

public class ManualIndexerControl extends CommandBase {
  private Indexer m_indexer;
  private boolean m_direction;
  /**
   * Creates a new ManualIndexerControl.
   */
  public ManualIndexerControl(Indexer indexer, boolean direction) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_direction = direction;
    addRequirements(indexer);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if(m_direction){
      m_indexer.setIndexerMotorPower(Constants.kIndexerStowingMotorPower);
    }
    else{
      m_indexer.setIndexerMotorPower(-Constants.kIndexerStowingMotorPower);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_indexer.stopIndexerMotor();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
