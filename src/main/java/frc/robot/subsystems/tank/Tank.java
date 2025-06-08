package frc.robot.subsystems.tank;

import org.littletonrobotics.junction.Logger;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants;

public class Tank extends SubsystemBase {
    TankIO io;
    private TankInputsAutoLogged inputs = new TankInputsAutoLogged();
    private final DifferentialDriveKinematics kinematics =
        new DifferentialDriveKinematics(Constants.Tank.TRACK_WIDTH);

    public Tank(TankIO io) {
        this.io = io;
        io.updateInputs(inputs);
    }

    @Override
    public void periodic() {
        io.updateInputs(inputs);
        Logger.processInputs("Tank", inputs);
    }


    /** Runs the drive at the desired velocity. */
    public void runClosedLoop(ChassisSpeeds speeds) {
        var wheelSpeeds = kinematics.toWheelSpeeds(speeds);
        runClosedLoop(wheelSpeeds.leftMetersPerSecond, wheelSpeeds.rightMetersPerSecond);
    }

    /** Runs the drive at the desired left and right velocities. */
    public void runClosedLoop(double leftMetersPerSec, double rightMetersPerSec) {
        double leftRadPerSec = leftMetersPerSec / Constants.Tank.WHEEL_RADIUS_METERS;
        double rightRadPerSec = rightMetersPerSec / Constants.Tank.WHEEL_RADIUS_METERS;
        Logger.recordOutput("Drive/LeftSetpointRadPerSec", leftRadPerSec);
        Logger.recordOutput("Drive/RightSetpointRadPerSec", rightRadPerSec);

        double leftFFVolts =
            Constants.Tank.KS * Math.signum(leftRadPerSec) + Constants.Tank.KV * leftRadPerSec;
        double rightFFVolts =
            Constants.Tank.KS * Math.signum(rightRadPerSec) + Constants.Tank.KV * rightRadPerSec;
        io.setVelocity(leftRadPerSec, rightRadPerSec, leftFFVolts, rightFFVolts);
    }


    public Command arcadeDrive(CommandXboxController controller) {
        return Commands.run(() -> {
            // Apply deadband
            double fwd = MathUtil.applyDeadband(controller.getLeftY(), Constants.Tank.DEADBAND);
            double rot = MathUtil.applyDeadband(controller.getLeftX(), Constants.Tank.DEADBAND);

            // Calculate speeds
            var speeds =
                DifferentialDrive.curvatureDriveIK(fwd, rot, controller.a().getAsBoolean());

            // Apply output
            runClosedLoop(speeds.left * Constants.Tank.MAX_METERS_PER_SEC,
                speeds.right * Constants.Tank.MAX_METERS_PER_SEC);
        });
    }
}
