/** 
 * This Class is where our turret shoot command
 * this command is linked to a button on the Driver controller     
 *   
*/
package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Turret;


public class Shoot extends CommandBase {
  
  Turret turret;
  
  double initialSkew;
  NetworkTableEntry p, i, d, skew, current, calcpid;

  public Shoot(Turret m_turret) {
    // Use addRequirements() here to declare subsystem dependencies.
    turret = m_turret;
    /*p = Shuffleboard.getTab("ll").add("p", 0).withWidget(BuiltInWidgets.kTextView).withSize(2, 2)
    .getEntry();

    i = Shuffleboard.getTab("ll").add("i", 0).withWidget(BuiltInWidgets.kTextView).withSize(2, 2)
    .getEntry();

    d = Shuffleboard.getTab("ll").add("d", 0).withWidget(BuiltInWidgets.kTextView).withSize(2, 2)
    .getEntry();*/

    //skew = Shuffleboard.getTab("ll").add("skew", 0).withWidget(BuiltInWidgets.kTextView).withSize(2, 2)
    //.getEntry();

    current = Shuffleboard.getTab("ll").add("current", 0).withWidget(BuiltInWidgets.kTextView).withSize(2, 2)
    .getEntry();

    calcpid = Shuffleboard.getTab("ll").add("calcpid", 0).withWidget(BuiltInWidgets.kTextView).withSize(2, 2)
    .getEntry();
    addRequirements(m_turret);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    //initialSkew = turret.getSkew();

    current.setDouble(turret.getX());
    calcpid.setDouble(turret.getTurnPID().calcPID(turret.getX()));
    //skew.setDouble(initialSkew);

    if(turret.seesTarget()) {
      turret.turretSetpoint(0);
      System.out.println("target located");
    } else {
      System.out.println("target not located, cancelled");
      this.cancel();
    }
  }

  

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // This if Statement is to only Run in Test mode
    if(DriverStation.getInstance().isTest()){
      testMode();
    }
    current.setDouble(turret.getX());
    calcpid.setDouble(-turret.getTurnPID().calcPID(turret.getX()));

    turret.setTurnSpeed(-turret.getTurnPID().calcPID(turret.getX()));
    turret.setShooterRPM((int)calcSpeed(turret.getHeight()));
     

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    turret.stopTurn();
    turret.stopShooter();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }

  public int calcSpeed(double height) {
    int h = (int)Math.round(height);
    return (int)Math.round(6708 - 145 * (h) + 1.85 * (h * h));
  }

  public void testMode(){

    if(this.turret.testpid){
      current.setDouble(turret.getX());
      calcpid.setDouble(-turret.getTurnPID().calcPID(turret.getX()));
  
      turret.setTurnSpeed(-turret.getTurnPID().calcPID(turret.getX()));
      turret.setShooterRPM((int)calcSpeed(turret.getHeight()));
    }else{
      // do nothing
    }
    
  }
}
