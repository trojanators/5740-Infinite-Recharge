/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.auto.arrays.*;
import frc.robot.pathfollower.Trajectory;
import frc.robot.pathfollower.TrajectoryDriveController;
import frc.robot.subsystems.*;


/*public class Autotestpath extends CommandBase {
  /**
   * Creates a new TestPathCommand.
   
  private Drivetrain driveBase;
  private Intake m_intake;
  private Turret m_turret;
  private Indexer m_indexer;
	private TrajectoryDriveController controller;
	private Trajectory trajectoryLeft;
  private Trajectory trajectoryRight;
  private Timer timer;

  private Shoot m_shoot;
  private Target m_target;*/
  //private Target target;
  //private Shoot shoot;
  
  /*public Autotestpath(Drivetrain drivetrain, Intake intake, Turret turret, Indexer indexer) {
    // Use addRequirements() here to declare subsystem dependencies.
    System.out.println("Starting TestAuto");
    m_intake = intake;
    driveBase = drivetrain;
    m_turret = turret;
    m_indexer = indexer; 
    //m_target = new Target(m_turret);
    //m_shoot = new Shoot(m_turret, m_indexer);

    System.out.println("Testpath init");
    addRequirements(driveBase);
    addRequirements(m_intake);
   // addRequirements(m_turret);
    //addRequirements(m_indexer);
  
    timer = new Timer();
    m_target = new Target(m_turret);
    m_shoot = new Shoot(m_turret, m_indexer);
  }*/

  // Called when the command is initially scheduled.
  /*@Override
  public void initialize() {
   driveBase.zeroSensors();
   driveBase.autoDriveTrainConfig();
  }

  private State state = State.INIT;
  private State laststate = State.INIT;
  

  private enum State {
    INIT,
    STRAIGHT_FORWARD,
    TO_SHOOT_POS,
    TO_INTAKE_POS,
    SHOOTING,
    TARGETING,
    FINISHED,
  }*/


  // Called every time the scheduler runs while the command is scheduled.
  /*@Override
  public void execute() {

    switch(state){
      case INIT:
        System.out.println("State " + state);
        initialize();
        trajectoryLeft = ToFirstBall.trajectoryArray[0];
	    	trajectoryRight = ToFirstBall.trajectoryArray[1]; 
        controller = new TrajectoryDriveController(trajectoryLeft, trajectoryRight, -1.0, driveBase);
        state = State.STRAIGHT_FORWARD;
        break;

    case STRAIGHT_FORWARD:
      System.out.println("State " + state);

      if(!controller.onTarget()){
          controller.update();
          m_intake.setIntakePower(Constants.kIntakeSpeed);
       }
       else {
         state = State.TO_SHOOT_POS;
        m_intake.setIntakePower(0.0);
        driveBase.setLeftRightPower(0.0, 0.0);
        driveBase.zeroSensors();
        trajectoryLeft = ToShootingSpot.trajectoryArray[0];
	    	trajectoryRight = ToShootingSpot.trajectoryArray[1]; 
        controller = new TrajectoryDriveController(trajectoryLeft, trajectoryRight, 1.0, driveBase);
       }
       break; 

      case TO_SHOOT_POS:
       System.out.println("State " + state);
       if(!controller.onTarget()){
         controller.update();
       }
       else {
        state = State.TARGETING;
        driveBase.setLeftRightPower(0.0, 0.0);
        timer.start();

        m_target.initialize();
        m_shoot.initialize();
        m_shoot.schedule();
        m_target.schedule();

       
       }
       break;

       case TARGETING:
        System.out.println("State " + state);
        System.out.println("Timer: " + timer.get());

       if(timer.get() >= 1.5){
        state = State.SHOOTING;
        //m_shoot.end(false);
        m_target.end(false);  
        m_shoot.schedule();
        timer.reset();    
      }
      break;
       
      case SHOOTING:
      System.out.println("Timer: " + timer.get());
      if(timer.get() > 2) {
        m_shoot.end(false);
        m_turret.stopShooter();
        m_indexer.stopIndexerMotor();
        m_turret.setShooterSpeed(0);
        state = State.TO_INTAKE_POS;
        m_intake.setIntakePower(Constants.kIntakeSpeed);
        driveBase.setLeftRightPower(0.0, 0.0);
        driveBase.zeroSensors();
        trajectoryLeft = ToIntakeSpot.trajectoryArray[0];
	    	trajectoryRight = ToIntakeSpot.trajectoryArray[1]; 
        controller = new TrajectoryDriveController(trajectoryLeft, trajectoryRight, -1.0, driveBase);
      }
      break;

      case TO_INTAKE_POS:
      System.out.println("State " + state);

      if(!controller.onTarget()){
          controller.update();
       }
       else {
         state = State.FINISHED;
       
       }
      break;

      case FINISHED: 
       System.out.println("State " + state);
       m_turret.stopShooter();
       m_indexer.stopIndexerMotor();*/
       //m_target.isFinished();
       //m_shoot.isFinished();
      /*break;
    }
      if(state != laststate) System.out.println(state);
      laststate = state;
    
  }

    /*controller.update();// does the calculations and updates the driveBase
    if(controller.onTarget()) {
      m_intake.setIntakePower(0);
    }
  

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    //m_intake.setIntakePower(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}*/
