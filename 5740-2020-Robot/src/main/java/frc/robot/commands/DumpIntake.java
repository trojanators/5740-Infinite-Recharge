/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;

/**
 * This Class is For dumping Indexer Contents before We climb
 * Uses Indexer State Unloaded to unload contents from robot 
 *  Operator Controller will Dump indexer before driver climbs 
 */
public class DumpIntake extends CommandBase {

  private Indexer indexer;
  private Intake intake;



  public DumpIntake(Indexer m_indexer, Intake m_intake) {

    this.indexer = m_indexer;
    this.intake = m_intake;
  
  }

  @Override
  public void initialize() {
    

  }

  
  @Override
  public void execute() {
    // flips intake down
    
  }

  @Override
  public void end(boolean interrupted) {
  }

 
  @Override
  public boolean isFinished() {
    return false;
  }
}
