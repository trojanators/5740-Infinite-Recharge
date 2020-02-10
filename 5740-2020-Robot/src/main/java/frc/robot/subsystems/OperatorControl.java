package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Intake;
/**
 * Class for Operator Custom Control Panal
 * 
 * defines all buttons and bindings in classs
 * 
 *  */

public class OperatorControl  extends SubsystemBase {

    Joystick Operator;
    Intake intake;

    boolean operatorIntake;
    boolean operatorIntakeDump;
    boolean operatorEnable;

    public OperatorControl(Joystick operator, Intake intake){

        this.Operator = operator;

        this.intake = intake;

    
    }
    
    public void Perodic(){


        if(operatorIntake == this.Operator.getRawButton(Constants.kOperatorbutton1)){
            intake.setFlipPower(-Constants.kIntakeSpeed);
        
        }else{
            intake.setFlipPower(Constants.kIntakeSpeed);
        }
        



    }
    

}
