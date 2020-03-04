/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Indexer;

public class ManualIndexer extends CommandBase {
  /**
   * Creates a new ManualIndexer.
   */
  Joystick m_controller;
  Indexer m_indexer;

  public ManualIndexer(Joystick controller, Indexer indexer) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_indexer = indexer;
    m_controller = controller;
    addRequirements(m_indexer);

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_indexer.stopIndexerMotor();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

   // m_indexer.indexerController();
   m_indexer.setIndexerMotorPower(m_controller.getRawAxis(Constants.kLeftStickY));
    // While loop for when commands run in Testmode
   // while (DriverStation.getInstance().isTest()) {
    //  m_indexer.testMode();
    //  break;
   // }
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
