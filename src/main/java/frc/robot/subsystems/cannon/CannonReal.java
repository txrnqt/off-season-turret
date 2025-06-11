package frc.robot.subsystems.cannon;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;

public class CannonReal implements CannonIO {
    private Compressor compressor = new Compressor(PneumaticsModuleType.REVPH);
    private Solenoid solenoid = new Solenoid(PneumaticsModuleType.REVPH, Integer.MAX_VALUE);

    public CannonReal() {
        compressor.enableDigital();
        compressor.enableHybrid(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    @Override
    public void openAndClose(boolean is_open) {
        solenoid.set(is_open);
    }

    @Override
    public void updateInputs(CannonInputs inputs) {
        inputs.presusre = compressor.getPressure();
        inputs.activated = compressor.isEnabled();
    }
}
