/** 
 * This Class is where our turret shoot command
 * this command is linked to a button on the Driver controller     
 *   
*/
package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Turret;


public class Shoot extends CommandBase {
  
  Turret turret;
  Indexer indexer;

  double initialSkew;
  NetworkTableEntry p, i, d, skew, current, calcpid;

  public Shoot(Turret m_turret, Indexer m_indexer) {
    // Use addRequirements() here to declare subsystem dependencies.
    turret = m_turret;
    indexer = m_indexer;
    /*p = Shuffleboard.getTab("ll").add("p", 0).withWidget(BuiltInWidgets.kTextView).withSize(2, 2)
    .getEntry();

    i = Shuffleboard.getTab("ll").add("i", 0).withWidget(BuiltInWidgets.kTextView).withSize(2, 2)
    .getEntry();

    d = Shuffleboard.getTab("ll").add("d", 0).withWidget(BuiltInWidgets.kTextView).withSize(2, 2)
    .getEntry();*/

    //skew = Shuffleboard.getTab("ll").add("skew", 0).withWidget(BuiltInWidgets.kTextView).withSize(2, 2)
    //.getEntry();

    /*current = Shuffleboard.getTab("ll").add("current", 0).withWidget(BuiltInWidgets.kTextView).withSize(2, 2)
    .getEntry();

    calcpid = Shuffleboard.getTab("ll").add("calcpid", 0).withWidget(BuiltInWidgets.kTextView).withSize(2, 2)
    .getEntry();*/
    addRequirements(m_turret);
   // addRequirements(m_indexer);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    //initialSkew = turret.getSkew();

    //current.setDouble(turret.getX());
    //calcpid.setDouble(turret.getTurnPID().calcPID(turret.getX()));
    //skew.setDouble(initialSkew);
    //indexer.setIndexerMotorPower(-.8);

  }

  

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //System.out.println("rpm " + isRPMTarget());
    //System.out.println("x " + isTurnTarget());

    // This if Statement is to only Run in Test mode
   // if(DriverStation.getInstance().isTest()){
    //  testMode();
   // }
    //current.setDouble(turret.getX());
    //calcpid.setDouble(-turret.getTurnPID().calcPID(turret.getX()));

   // turret.setTurnSpeed(-turret.getTurnPID().calcPID(turret.getX()));
    //turret.setShooterRPM((int)calcSpeed(turret.getHeight()));
   // turret.setShooterRPM(4500);
   if(!isRPMTarget() || !isTurnTarget()) {
     turret.setShooterRPM((int)calcSpeed(turret.getY()));
   } else if(isRPMTarget() && isTurnTarget()){
     turret.setShooterRPM(((int)calcSpeed(turret.getY())));
     indexer.setIndexerMotorPower(0.8);
   }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    turret.stopTurn();
    turret.setShooterRPM(0);
    turret.stopShooter();
    indexer.stopIndexerMotor();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }

  public double calcSpeed(double height) {
    double h = turret.getHeight();
    return 3994 + 1.01*(h) + 1.36*(Math.pow(h, 2)) - .395*(Math.pow(h, 3)) -.0312*(Math.pow(h, 4)) + .0017*(Math.pow(h, 5)) + .000177*(Math.pow(h, 6));
    //return (int)Math.round(6708 - 145 * (h) + 1.85 * (h * h));
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

  public boolean isTurnTarget() {
    if(Math.abs(turret.getX()) < 2) {
      return true;
    } else {
      return false;
    }
  }

  public boolean isRPMTarget() {
    double error = Math.abs(turret.getRPM() - calcSpeed(turret.getHeight()));
    if(error < 50) {
      return true;
    } else {
      return false;
    }
  }
}
