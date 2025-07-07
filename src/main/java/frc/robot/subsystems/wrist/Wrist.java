package frc.robot.subsystems.wrist;

import static edu.wpi.first.units.Units.Degrees;
import java.util.function.Supplier;
import org.littletonrobotics.junction.Logger;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants;

public class Wrist extends SubsystemBase {
    private WristIO io;
    private WristInputsAutoLogged inputs = new WristInputsAutoLogged();

    public Wrist(WristIO io) {
        this.io = io;
        io.updateInputs(inputs);
    }

    @Override
    public void periodic() {
        io.updateInputs(inputs);
        Logger.processInputs("Wrist", inputs);
        Logger.recordOutput("Wrist/Angle", inputs.position);
    }

    /**
     * sets angle of wrist
     *
     * @param angle desired angle of wrist
     * @return wrist angle change
     *
     */
    public Command moveTo(Supplier<Angle> angle) {
        return runOnce(() -> {
            Logger.recordOutput("targetangle", angle.get().in(Degrees));
            io.setAngle(angle.get().in(Degrees));
        }).andThen(Commands
            .waitUntil(() -> Math.abs(inputs.position.in(Degrees) - angle.get().in(Degrees)) < 1));
    }

    public Command stickCMD(CommandXboxController controller) {
        double raxis = -controller.getRightX();
        raxis = MathUtil.applyDeadband(raxis, 0.1);
        raxis = (Math.abs(raxis) < 0.1) ? 0 : raxis;
        double rotation = raxis * 0.5;
        return run(() -> io.setAngle(rotation));
    }

    public Command upCMD() {
        return run(() -> moveTo(() -> Constants.MaybeWrist.UP_ANGLE));
    }
}
