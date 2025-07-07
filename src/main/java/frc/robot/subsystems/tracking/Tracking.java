package frc.robot.subsystems.tracking;

import java.util.function.Function;
import java.util.stream.Stream;
import org.littletonrobotics.junction.Logger;
import org.photonvision.PhotonUtils;
import org.photonvision.targeting.PhotonPipelineResult;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.subsystems.tracking.TrackingIO.VisionObjectInputs;

public class Tracking extends SubsystemBase {
    private TrackingIO io;
    private VisionObjectInputs inputs;
    private final RobotState state;
    private Transform3d[] robotToCamera;
    PhotonPipelineResult[] results;
    private String logIntro = "visionObject/";

    public Transform2d targetTransform;

    public Tracking(RobotState state, Function<Constants.Vision.CameraConstants[], TrackingIO> io) {
        super("visionObject/");
        this.state = state;
        this.io = io.apply(Constants.Vision.cameras);
        this.robotToCamera = Stream.of(Constants.Vision.cameras).map(x -> x.robotToCamera())
            .toArray(Transform3d[]::new);
        inputs = new VisionObjectInputs();
    }

    @Override
    public void periodic() {
        io.updateInputs(inputs);
        Logger.processInputs(logIntro, inputs);

        results = inputs.results;

        targetTransform = calcTransform();
        // targetTransform = new Transform2d(10.0, 10.0, Rotation2d.k180deg);
        Logger.recordOutput(logIntro + "targetTranslation", targetTransform);
    }



    /** just using slang */

    private double calcDistance() {
        // var result = results.getBestTarget();
        return PhotonUtils.calculateDistanceToTargetMeters(robotToCamera[2].getZ(),
            results[0].getBestTarget().area, robotToCamera[2].getRotation().getAngle(),
            results[0].getBestTarget().getYaw());
    }

    private Rotation2d calcRotation() {
        var result = results[0].getBestTarget();
        return new Rotation2d(result.getYaw());
    }

    private Translation2d calcTranslation() {
        var result = results[0].getBestTarget();
        return PhotonUtils.estimateCameraToTargetTranslation(calcDistance(),
            new Rotation2d(result.getYaw()));
    }

    private Transform2d calcTransform() {
        return new Transform2d(calcTranslation(), calcRotation());
    }
}
