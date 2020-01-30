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

  private int targetCounter;

  private final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
  private final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
  private final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
  private final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);
  private final ColorMatch m_colorMatcher = new ColorMatch();
  private ColorState targetColor, currentColor;

  private enum ControlPanelState {
    INIT, 
    HOLD, 
    INIT_ROTATION_CONTROL,
    ROTATION_CONTROL,
    SEES_TARGET_COLOR_ROTATION,
    TARGET_COLOR_LEFT_VIEW,
    INIT_POSITION_CONTROL,
    POSITION_CONTROL, 
    SEES_TARGET_COLOR_POSITION, 
    INTERRUPTED,
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
    setControlPanelState(ControlPanelState.INIT_ROTATION_CONTROL);
    currentState = ControlPanelState.INIT_ROTATION_CONTROL;
    m_colorMatcher.addColorMatch(kBlueTarget);
    m_colorMatcher.addColorMatch(kGreenTarget);
    m_colorMatcher.addColorMatch(kRedTarget);
    m_colorMatcher.addColorMatch(kYellowTarget);
    // HelixLogger.getInstance().addSource("Color sensor Test ", (Supplier<Object>)

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

  public double getRed(){
    return m_colorSensor.getRed();
  }

  public double getBlue(){
    return m_colorSensor.getBlue();
  }

  public double getGreen(){
    return m_colorSensor.getGreen();
  }

  public ColorState getCurrentCPColor() {
    Color detectedColor = m_colorSensor.getColor();
    ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor);
    if (match.color == kBlueTarget) {
      //System.out.println("Blue, Confidence " + match.confidence);
      return ColorState.BLUE;
    } else if (match.color == kRedTarget) {
      //System.out.println("Red, Confidence " + match.confidence);
      return ColorState.RED;
    } else if (match.color == kGreenTarget) {
      //System.out.println("Green, Confidence " + match.confidence);
      return ColorState.GREEN;
    } else if (match.color == kYellowTarget) {
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
        setControlPanelState(ControlPanelState.INIT_ROTATION_CONTROL);
      break;
      case HOLD:
        stopControlPanel();
        currentState = ControlPanelState.HOLD;
      break;
      case INIT_ROTATION_CONTROL:
        targetColor = getCurrentCPColor();
        runControlPanel(0.5);
        setControlPanelState(ControlPanelState.ROTATION_CONTROL);
      break;
      case ROTATION_CONTROL:
        runControlPanel(0.5);
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
        targetColor = getPositionTargetColor();
        setControlPanelState(ControlPanelState.POSITION_CONTROL);
      break;
      case POSITION_CONTROL:
        runControlPanel(0.5);
        currentState = ControlPanelState.POSITION_CONTROL;
      break;
      case SEES_TARGET_COLOR_POSITION:
        stopControlPanel();
        setControlPanelState(ControlPanelState.HOLD);
      break;
      case INTERRUPTED:
        stopControlPanel();
        currentState = ControlPanelState.INTERRUPTED;
      break;
      case ERROR:
      default:
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
    System.out.println("Current State: " + currentState);
    System.out.println("Counter: " + targetCounter);
    System.out.println("Target: " + targetColor);
  }
}