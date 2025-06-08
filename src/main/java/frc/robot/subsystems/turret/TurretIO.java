package frc.robot.subsystems.turret;

import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.Volt;
import org.littletonrobotics.junction.AutoLog;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Voltage;

public interface TurretIO {

    @AutoLog
    public class TurertInputs {
        Angle position = Radians.of(0.0);
        Voltage voltage = Volt.of(0.0);
        AngularVelocity velocity = RadiansPerSecond.of(0.0);
    }

    public void updateInputs(TurertInputs inputs);

    public void setVoltage(double v);

    public void setAngle(double angle);

    public class Empty implements TurretIO {

        @Override
        public void updateInputs(TurertInputs inputs) {

        }

        @Override
        public void setVoltage(double v) {

        }

        @Override
        public void setAngle(double angle) {

        }

    }
}
