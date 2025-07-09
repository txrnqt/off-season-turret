package frc.robot.subsystems.tank;

import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;


public class TankReal implements TankIO {
    private SparkMax leadRigth = new SparkMax(1738, MotorType.kBrushless);
    private SparkMax leadLeft = new SparkMax(1738, MotorType.kBrushless);
    private SparkMax followRigth = new SparkMax(1738, MotorType.kBrushless);
    private SparkMax followLeft = new SparkMax(1738, MotorType.kBrushless);
    private final SparkMaxConfig configLead = new SparkMaxConfig();
    private final SparkMaxConfig configFollowL = new SparkMaxConfig();
    private final SparkMaxConfig configFollowR = new SparkMaxConfig();
    private final SparkClosedLoopController controllerLeft = leadLeft.getClosedLoopController();
    private final SparkClosedLoopController controllerRight = leadRigth.getClosedLoopController();

    public TankReal() {
        config();
    }

    private void config() {
        configFollowL.follow(leadLeft, false).idleMode(IdleMode.kBrake).closedLoop.pidf(0, 0, 0, 0,
            null);

        configFollowR.follow(leadLeft, false).idleMode(IdleMode.kBrake).closedLoop.pidf(0, 0, 0, 0,
            null);

        configLead.idleMode(IdleMode.kBrake).closedLoop.pidf(0, 0, 0, 0, ClosedLoopSlot.kSlot0);
    }

    @Override
    public void updateInputs(TankInputs inputs) {

    }

    @Override
    public void setVelocity(double leftRadPerSec, double rightRadPerSec, double leftFFVolts,
        double rightFFVolts) {
        controllerLeft.setReference(leftRadPerSec, ControlType.kVelocity, ClosedLoopSlot.kSlot0,
            leftFFVolts);
        controllerRight.setReference(rightRadPerSec, ControlType.kVelocity, ClosedLoopSlot.kSlot0,
            rightFFVolts, null);
    }



}
