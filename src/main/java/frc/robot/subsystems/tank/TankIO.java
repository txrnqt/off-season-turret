package frc.robot.subsystems.tank;

import org.littletonrobotics.junction.AutoLog;

public interface TankIO {

    @AutoLog
    public class TankInputs {
    }

    public void updateInputs(TankInputs inputs);

    public void setVelocity(double leftRadPerSec, double rightRadPerSec, double leftFFVolts,
        double rightFFVolts);

    public static class Empty implements TankIO {

        @Override
        public void setVelocity(double leftRadPerSec, double rightRadPerSec, double leftFFVolts,
            double rightFFVolts) {}

        @Override
        public void updateInputs(TankInputs inputs) {}
    }
}
