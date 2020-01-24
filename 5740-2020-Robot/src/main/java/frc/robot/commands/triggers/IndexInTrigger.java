package frc.robot.commands.triggers;

import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants;
import frc.robot.subsystems.Indexer;

public class IndexInTrigger extends Trigger {
    private Indexer indexer;
    private double value;

    public IndexInTrigger(Indexer m_indexer) {
        this.indexer = m_indexer;
    }
    @Override
    public boolean get() {
        this.value = indexer.getIntakeSideDistance();
        if(value <= Constants.kCellIncomingValueHigh && value >= Constants.kCellIncomingValueLow) {
            return true;
        } else {
            return false;
        }
      // This returns whether the trigger is active
    }
  }