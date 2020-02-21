/**
 * This Command Is For Calling Shuffleboard Properly During runtime
 * @author Nicholas Blackburn    
*/
package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DashBoard;

public class LoadDashboard extends CommandBase {

  DashBoard Dash;

  public LoadDashboard(DashBoard m_Dashboard) {

    this.Dash = m_Dashboard;

    addRequirements(m_Dashboard);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    if (DriverStation.getInstance().isEnabled()) {

      // Inits Teleop Dashboard Layout
      Dash.TeleopDashboard();
    }

    if (DriverStation.getInstance().isTest()) {

      // Init Test Mode Dashboard Layout
      Dash.TestModeDashboard();
    }

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    // Runs /Updates Dashboard Data
    Dash.dashboardData();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
