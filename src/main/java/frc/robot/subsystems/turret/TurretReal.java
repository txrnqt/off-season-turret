package frc.robot.subsystems.turret;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Voltage;
import frc.robot.Constants;

public class TurretReal implements TurretIO {
    private TalonFX turretMotor = new TalonFX(Integer.MAX_VALUE);
    private TalonFXConfiguration config = new TalonFXConfiguration();
    private final MotionMagicVoltage MMControl = new MotionMagicVoltage(0);

    private StatusSignal<Angle> position = turretMotor.getPosition();
    private StatusSignal<Voltage> voltage = turretMotor.getMotorVoltage();
    private StatusSignal<AngularVelocity> velocity = turretMotor.getVelocity();

    public TurretReal() {
        config();
    }

    private void config() {
        config.MotorOutput.NeutralMode = Constants.Turret.BREAK;

        config.Feedback.SensorToMechanismRatio = Constants.Turret.GEAR_RATIO;

        config.Slot0.kP = Constants.Turret.KP;
        config.Slot0.kI = Constants.Turret.KI;
        config.Slot0.kD = Constants.Turret.KD;
        config.Slot0.kS = Constants.Turret.KS;
        config.Slot0.kG = Constants.Turret.KG;
        config.Slot0.kV = Constants.Turret.KV;
        config.Slot0.kA = Constants.Turret.KA;

        config.MotionMagic.MotionMagicCruiseVelocity = Constants.Turret.CVeleocity;
        config.MotionMagic.MotionMagicAcceleration = Constants.Turret.Acceleration;
        config.MotionMagic.MotionMagicJerk = Constants.Turret.Jerk;

        turretMotor.getConfigurator().apply(config);
    }

    public void setVoltage(double v) {
        turretMotor.set(v);
    }

    @Override
    public void setAngle(double angle) {
        turretMotor.setControl(MMControl.withPosition(angle));
    }

    @Override
    public void updateInputs(TurertInputs inputs) {
        BaseStatusSignal.refreshAll(position, velocity, voltage);

        inputs.position = position.getValue();
        inputs.velocity = velocity.getValue();
        inputs.voltage = voltage.getValue();
    }
}
