package frc.robot.subsystems.cannon;

import org.littletonrobotics.junction.AutoLog;

public interface CannonIO {
    @AutoLog
    public class cannonInputs {
        double presusre = Double.NaN;
        boolean activated = false;
    }

    public void openAndClose(boolean is_open);

    public void updateInputs(cannonInputs inputs);

    public class Empty implements CannonIO {

        @Override
        public void openAndClose(boolean is_open) {}

        @Override
        public void updateInputs(cannonInputs inputs) {}

    }
}
