package frc.robot;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.Hertz;
import static edu.wpi.first.units.Units.Seconds;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.Frequency;
import edu.wpi.first.units.measure.Time;

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

    public static final class Cannon {
        public static final double READY_PRESURE = Double.POSITIVE_INFINITY;
    }

    public static final class MaybeWrist {
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

        public static final Angle UP_ANGLE = Degrees.of(Double.NaN);
    }

    /** Vision Constants */
    public static class Vision {

        public static AprilTagFieldLayout fieldLayout;

        /** Constants for an individual camera. */
        public static final record CameraConstants(String name, int height, int width,
            Rotation2d horizontalFieldOfView, Frequency framesPerSecond, Time latencyAvg,
            Time latencyStdDev, double calibErrorAvg, double calibErrorStdDev,
            Transform3d robotToCamera, double offset) {
            public double centerPixelYawRads() {
                return robotToCamera.getRotation().getZ();
            }
        }


        public static final CameraConstants[] cameras =
            new CameraConstants[] {new CameraConstants("camObj", 800, 1280,
                Rotation2d.fromDegrees(1000000), Hertz.of(240), Seconds.of(0.3), Seconds.of(0.02),
                0.0, 0.0, new Transform3d(0.0, 0.0, 0.0, new Rotation3d(Math.PI, 0, 0)),
                Units.inchesToMeters(1000000))};


        public static final double zMargin = 0.75;
        public static final double fieldBorderMargin = 0.5;
    }

}
