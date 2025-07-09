package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.cannon.Cannon;
import frc.robot.subsystems.piviot.Piviot;
import frc.robot.subsystems.tank.Tank;
import frc.robot.subsystems.tracking.Tracking;
import frc.robot.subsystems.wrist.Wrist;

public class CommandFactory extends Command {

    private Command autoAimCMD(Cannon cannon, Piviot piviot, Tank tank, Wrist wrist, Tracking tracking) {
        return Commands.run(null, null)
    }

    private Command getAlinged(Tank tank, Tracking tracking) {
        val translation = tank.getPose().transformBy(tracking.getTargetTransform());
        return Commands.run(, null)
    }
}
