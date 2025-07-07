package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.turret.Turret;
import frc.robot.subsystems.wrist.Wrist;

public class CommandFactory extends Command {

    public static Command rightAim(Turret turret, Wrist wrist) {
        return turret.rightCMD().andThen(wrist.upCMD());
    }
}
