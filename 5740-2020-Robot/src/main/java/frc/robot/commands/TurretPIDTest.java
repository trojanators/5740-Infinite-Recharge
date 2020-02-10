/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Turret;
import frc.robot.util.PID;

public class TurretPIDTest extends CommandBase {

  Turret turret;
  PID pid;
  private NetworkTableEntry pos, setPoint, absolute, p, i, d;
  Joystick joystick;
  public TurretPIDTest(Turret m_turret, Joystick m_joystick) {
    // Use addRequirements() here to declare subsystem dependencies.
    joystick = m_joystick;
    turret = m_turret;
    pid = turret.getTurnPID();
    pos = Shuffleboard.getTab("PID").add("pos", 0).withWidget(BuiltInWidgets.kTextView).withSize(2, 2)
    .getEntry();
    
    setPoint = Shuffleboard.getTab("PID").add("Setpoint", 0).withWidget(BuiltInWidgets.kTextView).withSize(2, 2)
    .getEntry();

    absolute = Shuffleboard.getTab("PID").add("Absolute Encoder Position", 0).withWidget(BuiltInWidgets.kTextView).withSize(2, 2)
    .getEntry();

    p = Shuffleboard.getTab("PID").add("p", 0).withWidget(BuiltInWidgets.kTextView).withSize(2, 2)
    .getEntry();

    i = Shuffleboard.getTab("PID").add("i", 0).withWidget(BuiltInWidgets.kTextView).withSize(2, 2)
    .getEntry();

    d = Shuffleboard.getTab("PID").add("d", 0).withWidget(BuiltInWidgets.kTextView).withSize(2, 2)
    .getEntry();

    pos.setDouble(turret.getTurnEncoderValue());
    absolute.setDouble(turret.getAbsoluteEncoderValue());

    addRequirements(m_turret);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    turret.setTurnSpeed(joystick.getRawAxis(1));
    //pos.setDouble(turret.getTurnEncoderValue());
    //absolute.setDouble(turret.getAbsoluteEncoderValue());

    //pid.setConstants(p.getDouble(0), i.getDouble(0), d.getDouble(0));
    //pid.setDesiredValue(setPoint.getDouble(0));
    //turret.setTurnSpeed(-pid.calcPID(turret.getTurnEncoderValue()));
    //System.out.println("Calc PID: " + -pid.calcPID(turret.getTurnEncoderValue()));
    //turret.setTurnSpeed(-.25);
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
