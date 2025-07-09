package frc.robot.subsystems.tank;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.math.util.Units;
import frc.robot.Constants;

public class TankReal implements TankIO {
    private TalonFX leadRigth = new TalonFX(1738);
    private TalonFX leadLeft = new TalonFX(1738);
    private TalonFX followRigth = new TalonFX(1738);
    private TalonFX followLeft = new TalonFX(1738);

    private TalonFXConfiguration leadConfig = new TalonFXConfiguration();
    private TalonFXConfiguration followConfig = new TalonFXConfiguration();
    private VelocityVoltage velocityRequest = new VelocityVoltage(0.0);
    private VoltageOut voltageRequest = new VoltageOut(0.0);

    public TankReal() {
        config();
    }

    private void config() {
        leadConfig.MotorOutput.NeutralMode = Constants.Tank.BREAK;
        followConfig.MotorOutput.NeutralMode = Constants.Tank.BREAK;
        followConfig.MotorOutput.Inverted = Constants.Tank.INVERTE;

        followRigth.setControl(new Follower(leadRigth.getDeviceID(), true));
        followLeft.setControl(new Follower(leadLeft.getDeviceID(), true));

        followConfig.Slot0.kP = Constants.Tank.KP;
        followConfig.Slot0.kI = Constants.Tank.KI;
        followConfig.Slot0.kD = Constants.Tank.KD;
        followConfig.Slot0.kS = Constants.Tank.KS;
        followConfig.Slot0.kG = Constants.Tank.KG;
        followConfig.Slot0.kV = Constants.Tank.KV;
        followConfig.Slot0.kA = Constants.Tank.KA;

        leadConfig.Slot0.kP = Constants.Tank.KP;
        leadConfig.Slot0.kI = Constants.Tank.KI;
        leadConfig.Slot0.kD = Constants.Tank.KD;
        leadConfig.Slot0.kS = Constants.Tank.KS;
        leadConfig.Slot0.kG = Constants.Tank.KG;
        leadConfig.Slot0.kV = Constants.Tank.KV;
        leadConfig.Slot0.kA = Constants.Tank.KA;

        leadRigth.getConfigurator().apply(leadConfig);
        leadLeft.getConfigurator().apply(leadConfig);
        followRigth.getConfigurator().apply(followConfig);
        followLeft.getConfigurator().apply(followConfig);
    }

    @Override
    public void updateInputs(TankInputs inputs) {}


    @Override
    public void setVelocity(double leftRadPerSec, double rightRadPerSec, double leftFFVolts,
        double rightFFVolts) {
        leadLeft.setControl(velocityRequest.withVelocity(Units.radiansToRotations(leftRadPerSec))
            .withFeedForward(leftFFVolts));
        leadRigth.setControl(velocityRequest.withVelocity(Units.radiansToRotations(rightRadPerSec))
            .withFeedForward(rightFFVolts));
    }

    @Override
    public void setVoltage(double lV, double rV) {
        leadLeft.setVoltage(lV);
        leadRigth.setVoltage(rV);
    }
}
