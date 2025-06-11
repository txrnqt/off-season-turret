package frc.robot.subsystems.maybeWrist;

import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.Volt;
import org.littletonrobotics.junction.AutoLog;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Voltage;

public interface WristIO {

    @AutoLog
    public class WristInputs {
        Angle position = Radians.of(0.0);
        Voltage voltage = Volt.of(0.0);
        AngularVelocity velocity = RadiansPerSecond.of(0.0);
    }

    public void updateInputs(WristInputs inputs);

    public void setVoltage(double v);

    public void setAngle(double angle);

    public class Empty implements WristIO {

        @Override
        public void updateInputs(WristInputs inputs) {}

        @Override
        public void setVoltage(double v) {}

        @Override
        public void setAngle(double angle) {}
    }
}
