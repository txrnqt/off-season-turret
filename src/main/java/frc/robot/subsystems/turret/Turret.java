package frc.robot.subsystems.turret;

import static edu.wpi.first.units.Units.Degrees;
import java.util.function.Supplier;
import org.littletonrobotics.junction.Logger;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class Turret extends SubsystemBase {
    private TurretIO io;
    private TurertInputsAutoLogged inputs = new TurertInputsAutoLogged();

    public Turret(TurretIO io) {
        this.io = io;
        io.updateInputs(inputs);
    }

    @Override
    public void periodic() {
        io.updateInputs(inputs);
        Logger.processInputs("Turret", inputs);
        Logger.recordOutput("Turret/Angle", inputs.position);
    }

    /**
     * sets angle of elevator
     *
     * @param angle desired angle of elevator
     * @return elevator angle change
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
        double raxis = -controller.getLeftY();
        raxis = MathUtil.applyDeadband(raxis, 0.1);
        raxis = (Math.abs(raxis) < 0.1) ? 0 : raxis;
        double rotation = raxis * 0.5;
        return run(() -> io.setAngle(rotation));
    }

    public Command frontCMD() {
        return moveTo(() -> Degrees.of(10000000));
    }

    public Command backCMD() {
        return moveTo(() -> Degrees.of(10000000));
    }

    public Command rightCMD() {
        return moveTo(() -> Degrees.of(10000000));
    }

    public Command leftCMD() {
        return moveTo(() -> Degrees.of(10000000));
    }
}
