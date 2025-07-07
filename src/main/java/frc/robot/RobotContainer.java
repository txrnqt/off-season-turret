package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Robot.RobotRunType;
import frc.robot.subsystems.cannon.Cannon;
import frc.robot.subsystems.cannon.CannonIO;
import frc.robot.subsystems.cannon.CannonReal;
import frc.robot.subsystems.tank.Tank;
import frc.robot.subsystems.tank.TankIO;
import frc.robot.subsystems.tank.TankReal;
import frc.robot.subsystems.turret.Turret;
import frc.robot.subsystems.turret.TurretIO;
import frc.robot.subsystems.turret.TurretReal;
import frc.robot.subsystems.wrist.Wrist;
import frc.robot.subsystems.wrist.WristIO;
import frc.robot.subsystems.wrist.WristReal;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {

    /* Controllers */
    private final CommandXboxController driver = new CommandXboxController(Constants.driverID);
    private final CommandXboxController operator = new CommandXboxController(Constants.operatorID);

    // Initialize AutoChooser Sendable
    private final SendableChooser<String> autoChooser = new SendableChooser<>();

    /* Subsystems */
    Tank tank;
    Turret turret;
    Cannon cannon;
    Wrist wrist;

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer(RobotRunType runtimeType) {
        SmartDashboard.putData("Choose Auto: ", autoChooser);
        autoChooser.setDefaultOption("Wait 1 Second", "wait");
        switch (runtimeType) {
            case kReal:
                tank = new Tank(new TankReal());
                turret = new Turret(new TurretReal());
                cannon = new Cannon(new CannonReal());
                wrist = new Wrist(new WristReal());
                break;
            case kSimulation:
                break;
            default:
                tank = new Tank(new TankIO.Empty());
                turret = new Turret(new TurretIO.Empty());
                cannon = new Cannon(new CannonIO.Empty());
                wrist = new Wrist(new WristIO.Empty());
        }
        // Configure the button bindings
        configDriverControls();
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses
     * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
     * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configDriverControls() {
        /** sticks */
        tank.setDefaultCommand(tank.arcadeDrive(driver));
        turret.setDefaultCommand(turret.stickCMD(driver));
        wrist.setDefaultCommand(wrist.stickCMD(driver));

        /** buttons */
        driver.povUp().onTrue(turret.frontCMD());
        driver.povLeft().onTrue(turret.leftCMD());
        driver.povRight().onTrue(turret.rightCMD());
        driver.povDown().onTrue(turret.backCMD());
        driver.rightTrigger().onTrue(cannon.shootCMD());
        driver.leftTrigger().onTrue(frc.robot.commands.CommandFactory.rightAim(turret, wrist));

        /** alerts */
        cannon.readyPresure
            .onTrue(Commands.run(() -> driver.setRumble(RumbleType.kBothRumble, 1))
                .ignoringDisable(true))
            .onFalse(Commands.run(() -> driver.setRumble(RumbleType.kBothRumble, 0))
                .ignoringDisable(true));
    }

    /**
     * Gets the user's selected autonomous command.
     *
     * @return Returns autonomous command selected.
     */
    public Command getAutonomousCommand() {
        Command autocommand;
        String stuff = autoChooser.getSelected();
        switch (stuff) {
            case "wait":
                autocommand = new WaitCommand(1.0);
                break;
            default:
                autocommand = new InstantCommand();
        }
        return autocommand;
    }
}
