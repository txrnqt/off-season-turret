package frc.robot;

import static edu.wpi.first.units.Units.Degrees;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.units.measure.Angle;

/**
 * Constants file.
 */
public final class Constants {
    /**
     * Stick Deadband
     */
    public static final double stickDeadband = 0.1;
    /**
     * Driver ID
     */
    public static final int driverID = 0;
    /**
     * Operator ID
     */
    public static final int operatorID = 1;

    /**
     * Motor CAN id's.
     */
    public static final class Motors {
    }

    /**
     * Pneumatics CAN id constants.
     */
    public static final class Pneumatics {
    }

    public static final class Tank {
        public static final NeutralModeValue BREAK = NeutralModeValue.Brake;
        public static final InvertedValue INVERTE = InvertedValue.Clockwise_Positive;
        public static final double TRACK_WIDTH = Units.inchesToMeters(Double.MAX_VALUE);
        public static final double WHEEL_RADIUS_METERS =
            Units.inchesToMeters(Double.NEGATIVE_INFINITY);
        public static final double DEADBAND = 0.1;
        public static final double MAX_METERS_PER_SEC = 0.5;

        public static final double KP = 0.0;
        public static final double KI = 0.0;
        public static final double KD = 0.0;
        public static final double KS = 0.0;
        public static final double KG = 0.0;
        public static final double KV = 0.0;
        public static final double KA = 0.0;

    }

    public static final class Turret {
        public static final NeutralModeValue BREAK = NeutralModeValue.Brake;

        public static final double CVeleocity = 4.0;
        public static final double Acceleration = 10.0;
        public static final double Jerk = 6000000.0;

        public static final double KP = 0.0;
        public static final double KI = 0.0;
        public static final double KD = 0.0;
        public static final double KS = 0.0;
        public static final double KG = 0.0;
        public static final double KV = 0.0;
        public static final double KA = 0.0;

        public static final double GEAR_RATIO = 0.0;
        public static final Angle MAX_ROTATION = Degrees.of(Double.POSITIVE_INFINITY);
    }
}
