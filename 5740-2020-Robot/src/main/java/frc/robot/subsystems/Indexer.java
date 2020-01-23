/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.playingwithfusion.TimeOfFlight;
import com.playingwithfusion.TimeOfFlight.RangingMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;

/**
 * This Class Grabs data from the TOF sensors and Converts them in to Inches for
 * our robot
 */

public class Indexer extends SubsystemBase {
  /**
   * Creates a new Indexer.
   */

  // inits TOF Sensors for Intake and turret

  private final VictorSPX indexerMotor = new VictorSPX(Constants.kIndexMotorCAN);

  private final TimeOfFlight inputTOF = new TimeOfFlight(Constants.kInputTOFCAN);
  private final TimeOfFlight outputTOF = new TimeOfFlight(Constants.kOutputTOFCAN);
  private int cellsContained = Constants.kCellsPreloaded;
  private IndexerState currentState;

  enum IndexerState {
    INIT,
    CELL_IN_INPUT_QUEUE,
    CELL_LOADED,
    SHOOTING,
    CELL_IN_OUTPUT_VIEW,
    SHOOTING_INTERRUPTED,
    CELLS_UNLOADED,
    FULL,
    NOT_FULL,
    ERROR,
  }

  public Indexer() {
    setIndexerState(IndexerState.INIT);
    currentState = IndexerState.INIT;
  }

  // function to set TOF refresh mils
  public void setShortRangeMode(final int RefreshTime) {
    inputTOF.setRangingMode(RangingMode.Short, RefreshTime);
    outputTOF.setRangingMode(RangingMode.Short, RefreshTime);
  }

  public double getInputDistance() {
    return inputTOF.getRange();
  }

  public double getOutputDistance() {
    return outputTOF.getRange();
  }

  public void setIndexerMotorPower(double power) {
    indexerMotor.set(ControlMode.PercentOutput, power);
  }

  public void stopIndexerMotor() {
    indexerMotor.set(ControlMode.PercentOutput, 0);
  }

  public void setIndexerState(IndexerState state) {
    switch(state) {
      case INIT: // called when subsystem initialzes
        setShortRangeMode(Constants.kIndexerSamplingPeriod);
        cellsContained = Constants.kCellsPreloaded;
        currentState = IndexerState.INIT;
      break;
      case CELL_IN_INPUT_QUEUE: // called when TOF by intake reads a cell
        setIndexerMotorPower(Constants.kIndexerStowingMotorPower);
        currentState = IndexerState.CELL_IN_INPUT_QUEUE;
      break;
      case CELL_LOADED: // called when TOF by intake loses sight of the cell
        stopIndexerMotor();
        cellsContained++;
        if(cellsContained == 5){
          setIndexerState(IndexerState.FULL);
        }else{
          setIndexerState(IndexerState.NOT_FULL);
        }
        currentState = IndexerState.CELL_LOADED;
      break;
      case SHOOTING: // called by turret subsystem
        setIndexerMotorPower(Constants.kIndexerShootingMotorPower);
        currentState = IndexerState.SHOOTING;
      break;
      case CELL_IN_OUTPUT_VIEW: // called when TOF by turret reads a cell
        System.out.println("Cell is in view");
        if(cellsContained == 5){
          setIndexerState(IndexerState.FULL);
        }else{
          setIndexerState(IndexerState.NOT_FULL);
        }
        currentState = IndexerState.CELL_IN_OUTPUT_VIEW;
      break;
      case SHOOTING_INTERRUPTED: // called manually when a cell is stuck in the exit of the indexer. It removes a gap.
        setIndexerMotorPower(-Constants.kIndexerShootingMotorPower);
        currentState = IndexerState.SHOOTING_INTERRUPTED;
      break;
      case CELLS_UNLOADED: //called when cell leaves the indexer
        cellsContained--;
        currentState = IndexerState.CELLS_UNLOADED;
      break;
      case FULL: // called when cells in indexer reach 5
        stopIndexerMotor();
        System.out.println("Indexer is full");
        currentState = IndexerState.FULL;
      break;
      case NOT_FULL: // called when cells in indexer are under 5
        stopIndexerMotor();
        System.out.println("Indexer has " + cellsContained + " cells.");
        currentState = IndexerState.NOT_FULL;
      break;
      case ERROR:
      default: // error state
        System.out.println("Error in indexer, you shouldn't see this.");
        currentState = IndexerState.ERROR;
      break;
      
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    //System.out.println(getIntakeSideDistance());
    //Input Side Sensor
    if(getInputDistance() <= Constants.kCellIncomingValueHigh && currentState != IndexerState.SHOOTING_INTERRUPTED){
      setIndexerState(IndexerState.CELL_IN_INPUT_QUEUE);
    } 
    if(getInputDistance() >= Constants.kCellIncomingValueHigh && currentState == IndexerState.CELL_IN_INPUT_QUEUE){
      setIndexerState(IndexerState.CELL_LOADED);
    }
    if(currentState == IndexerState.SHOOTING_INTERRUPTED && cellsContained < 5){
      setIndexerState(IndexerState.NOT_FULL);
    }
    if(currentState == IndexerState.SHOOTING_INTERRUPTED && cellsContained == 5){
      setIndexerState(IndexerState.FULL);
    }
    //Output Side Sensor
    if(getOutputDistance() <= Constants.kCellOutgoingValueHigh){
      setIndexerState(IndexerState.CELL_IN_OUTPUT_VIEW);
    }
    if(getOutputDistance() <= Constants.kCellOutgoingValueHigh && getOutputDistance() >= Constants.kCellIncomingValueLow && currentState == IndexerState.SHOOTING){
      setIndexerState(IndexerState.CELLS_UNLOADED);
    }
    if(getOutputDistance() <= Constants.kCellOutgoingValueHigh && currentState == IndexerState.CELLS_UNLOADED){
      setIndexerState(IndexerState.NOT_FULL);
    }
    if(currentState == IndexerState.SHOOTING_INTERRUPTED && getInputDistance() <= Constants.kCellIncomingValueHigh && cellsContained < 5){
      setIndexerState(IndexerState.NOT_FULL);
    }
    if(currentState == IndexerState.SHOOTING_INTERRUPTED && getInputDistance() <= Constants.kCellIncomingValueHigh && cellsContained == 5){
      setIndexerState(IndexerState.FULL);
    }
  }
}
