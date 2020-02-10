/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import frc.robot.Constants;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ControlPanel extends SubsystemBase {


  private final ColorSensorV3 m_colorSensor = new ColorSensorV3(Port.kOnboard);

  private final Victor m_CpMotor = new Victor(Constants.kCpMotorPort);

  public int targetCounter, acceptCounter;

  private final ColorMatch m_colorMatcher = new ColorMatch();

  public  ColorState targetColor, currentColor;

  enum ControlPanelState {
    INIT, 
    HOLD, 
    INIT_ROTATION_CONTROL,
    ROTATION_CONTROL,
    SEES_TARGET_COLOR_ROTATION,
    TARGET_COLOR_LEFT_VIEW,
    INIT_POSITION_CONTROL,
    POSITION_CONTROL, 
    SEES_TARGET_COLOR_POSITION, 
    ERROR
  }

  private enum ColorState {
    RED,
    GREEN,
    BLUE,
    YELLOW,
    NONE
  }

  private ControlPanelState currentState;

  /**
   * Creates the Control Panel Subsystem.
   */

  public ControlPanel() {
    acceptCounter = 0;
    m_colorMatcher.addColorMatch(Constants.kBlueTarget);
    m_colorMatcher.addColorMatch(Constants.kGreenTarget);
    m_colorMatcher.addColorMatch(Constants.kRedTarget);
    m_colorMatcher.addColorMatch(Constants.kYellowTarget);
    currentState = ControlPanelState.INIT;
    setControlPanelState(ControlPanelState.INIT);
    // //Logger.getInstance().addSource("Color sensor Test ", (Supplier<Object>)
  }

  /* Get the current color from the color sensor */
  public Color getCurrentColor() {
    return m_colorSensor.getColor();
  }

  /* get the color assigned to our alliance by the field management system */
  public char getFMSColor() {
    return DriverStation.getInstance().getGameSpecificMessage().charAt(0);
  }

  public void runControlPanel(double speed){
    m_CpMotor.setSpeed(speed);
  }

  public void stopControlPanel() {
    m_CpMotor.set(0);
  }

  public ColorState getCurrentCPColor() {
    Color detectedColor = m_colorSensor.getColor();
    ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor);
    if (match.color == Constants.kBlueTarget) {
      //System.out.println("Blue, Confidence " + match.confidence);
      return ColorState.BLUE;
    } else if (match.color == Constants.kRedTarget) {
      //System.out.println("Red, Confidence " + match.confidence);
      return ColorState.RED;
    } else if (match.color == Constants.kGreenTarget) {
      //System.out.println("Green, Confidence " + match.confidence);
      return ColorState.GREEN;
    } else if (match.color == Constants.kYellowTarget) {
      //System.out.println("Yellow, Confidence " + match.confidence);
      return ColorState.YELLOW;
    } else {
      //System.out.println("NONE");
      return ColorState.NONE;
    }
  }

  private ColorState getPositionTargetColor() {
    char fmscolor = getFMSColor();
    if(fmscolor == 'R') {
      return ColorState.BLUE;
    } else if(fmscolor == 'B') {
      return ColorState.RED;
    } else if(fmscolor == 'G') {
      return ColorState.YELLOW;
    } else if(fmscolor == 'Y') {
      return ColorState.GREEN;
    } else {
      return ColorState.NONE;
    }
  }

  public void setControlPanelState(ControlPanelState state) {
    switch(state) {
      case INIT:
        stopControlPanel();
        targetCounter = 0;
        currentState = ControlPanelState.INIT;
      break;
      case HOLD:
        stopControlPanel();
        currentState = ControlPanelState.HOLD;
      break;
      case INIT_ROTATION_CONTROL:
        if(currentColor != ColorState.NONE) {
          targetColor = getCurrentCPColor();
          runControlPanel(Constants.kControlPanelSpeed);
          setControlPanelState(ControlPanelState.ROTATION_CONTROL);
        } else {

          DriverStation.reportError("No color found, cancelling rotation control", true); //TODO: Dashboard message
          setControlPanelState(ControlPanelState.HOLD);
        }
      break;
      case ROTATION_CONTROL:
        runControlPanel(Constants.kControlPanelSpeed);
        currentState = ControlPanelState.ROTATION_CONTROL;
      break;
      case SEES_TARGET_COLOR_ROTATION:
        currentState = ControlPanelState.SEES_TARGET_COLOR_ROTATION;
      break;
      case TARGET_COLOR_LEFT_VIEW:
        targetCounter++;
        setControlPanelState(ControlPanelState.ROTATION_CONTROL);
      break;
      case INIT_POSITION_CONTROL:
        if(currentColor != ColorState.NONE) {
          targetColor = getPositionTargetColor();
          setControlPanelState(ControlPanelState.POSITION_CONTROL);
        } else {

          DriverStation.reportError("No color found, cancelling position control.", true);
          System.out.println("No color found, cancelling position control."); //TODO: Dashboard Message
          setControlPanelState(ControlPanelState.HOLD);
        }
      break;
      case POSITION_CONTROL:
        runControlPanel(Constants.kControlPanelSpeed);
        currentState = ControlPanelState.POSITION_CONTROL;
      break;
      case SEES_TARGET_COLOR_POSITION:
        stopControlPanel();
        setControlPanelState(ControlPanelState.HOLD);
      break;
      case ERROR:
      default:
        DriverStation.reportError("Error in ControlPanel, you shouldn't see this. SUPER CRINGE", true);
        System.out.println("Error in ControlPanel, you shouldn't see this. Cringe.");
        currentState = ControlPanelState.ERROR;
      break;
    }
  }

  @Override
  public void periodic() {
    currentColor = getCurrentCPColor();
    if(currentColor == targetColor && currentState == ControlPanelState.ROTATION_CONTROL && targetCounter < Constants.kMaxCPTicks) {
      setControlPanelState(ControlPanelState.SEES_TARGET_COLOR_ROTATION);
    }
    if(currentColor != targetColor && currentState == ControlPanelState.SEES_TARGET_COLOR_ROTATION) {
      setControlPanelState(ControlPanelState.TARGET_COLOR_LEFT_VIEW);
    }
    if(currentColor == targetColor && currentState == ControlPanelState.ROTATION_CONTROL && targetCounter >= Constants.kMaxCPTicks) {
      setControlPanelState(ControlPanelState.HOLD);
    }
    if(currentState == ControlPanelState.POSITION_CONTROL && getCurrentCPColor() == targetColor) {
      setControlPanelState(ControlPanelState.SEES_TARGET_COLOR_POSITION);
    }
    //System.out.println("Current State: " + currentState);
    //System.out.println("Counter: " + targetCounter);
    //System.out.println("Target: " + targetColor);
  }
  // Returns State of ContolPanelState 
  public ControlPanelState getControlPanelState() {
    return currentState;
  }
}