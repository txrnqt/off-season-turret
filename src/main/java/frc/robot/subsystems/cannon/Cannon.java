package frc.robot.subsystems.cannon;

import org.littletonrobotics.junction.Logger;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants;

public class Cannon extends SubsystemBase {
    private CannonIO io;
    private CannonInputsAutoLogged inputs = new CannonInputsAutoLogged();

    /** triggers */
    public Trigger readyPresure =
        new Trigger(() -> inputs.presusre >= Constants.Cannon.READY_PRESURE);

    public Cannon(CannonIO io) {
        this.io = io;
        io.updateInputs(inputs);
    }

    @Override
    public void periodic() {
        Logger.processInputs("Cannon", inputs);
        Logger.recordOutput("Cannon/Activating", inputs.activated);
        Logger.recordOutput("Cannon/Presure", inputs.presusre);
    }

    public Command shootCMD() {
        if (inputs.presusre >= Constants.Cannon.READY_PRESURE) {
            return Commands.runEnd(() -> io.openAndClose(true), () -> io.openAndClose(false), this);
        } else {
            return null;
        }
    }

}
