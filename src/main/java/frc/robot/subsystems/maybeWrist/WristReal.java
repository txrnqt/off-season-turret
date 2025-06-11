package frc.robot.subsystems.maybeWrist;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Voltage;
import frc.robot.Constants;

public class WristReal implements WristIO {
    private TalonFX wristMotor = new TalonFX(Integer.BYTES);
    private TalonFXConfiguration config = new TalonFXConfiguration();
    private MotionMagicVoltage MMControl = new MotionMagicVoltage(0.0);

    private StatusSignal<Angle> position = wristMotor.getPosition();
    private StatusSignal<Voltage> voltage = wristMotor.getMotorVoltage();
    private StatusSignal<AngularVelocity> velocity = wristMotor.getVelocity();

    public WristReal() {
        config();
    }

    private void config() {
        config.MotorOutput.NeutralMode = Constants.MaybeWrist.BREAK;

        config.Feedback.SensorToMechanismRatio = Constants.MaybeWrist.GEAR_RATIO;

        config.Slot0.kP = Constants.MaybeWrist.KP;
        config.Slot0.kI = Constants.MaybeWrist.KI;
        config.Slot0.kD = Constants.MaybeWrist.KD;
        config.Slot0.kS = Constants.MaybeWrist.KS;
        config.Slot0.kG = Constants.MaybeWrist.KG;
        config.Slot0.kV = Constants.MaybeWrist.KV;
        config.Slot0.kA = Constants.MaybeWrist.KA;

        config.MotionMagic.MotionMagicCruiseVelocity = Constants.MaybeWrist.CVeleocity;
        config.MotionMagic.MotionMagicAcceleration = Constants.MaybeWrist.Acceleration;
        config.MotionMagic.MotionMagicJerk = Constants.MaybeWrist.Jerk;

        wristMotor.getConfigurator().apply(config);
    }

    @Override
    public void setVoltage(double v) {
        wristMotor.set(v);
    }

    @Override
    public void setAngle(double angle) {
        wristMotor.setControl(MMControl.withPosition(angle));
    }

    @Override
    public void updateInputs(WristInputs inputs) {
        BaseStatusSignal.refreshAll(position, velocity, voltage);

        inputs.position = position.getValue();
        inputs.velocity = velocity.getValue();
        inputs.voltage = voltage.getValue();
    }


}
