/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.auto.AutoMode;
import frc.auto.TestAuto;
import edu.wpi.first.wpilibj.TimedRobot;

public class CIARobot extends TimedRobot {

	@Override
	public void robotInit() {
	 	
	}
	
	@Override
	public void robotPeriodic(){
	}

	private AutoMode autoMode;
	@Override
	public void autonomousInit() {
		autoMode = new TestAuto();
	}

	@Override
	public void autonomousPeriodic() {
		autoMode.execute();
	}
	
	@Override
	public void teleopInit(){
		}
	
	@Override
	public void teleopPeriodic() {
		
		}
		
		
	
	
	@Override
	public void testInit(){
		}
	@Override
	public void testPeriodic() {
		}
	
	@Override
	public void disabledInit(){};
	
	@Override
	public void disabledPeriodic(){};
}
