package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.controllers.ShooterController;

public class ShooterSubsystem extends SubsystemBase {

    private final ShooterController shooterController;
    private double targetVelocity = 0;

    public ShooterSubsystem(HardwareMap hardwareMap) {
        shooterController = new ShooterController();
        shooterController.initialize(hardwareMap, "shooter_l", "shooter_r");
    }

    public void setTargetVelocity(double velocity) {
        targetVelocity = velocity;
        shooterController.setShooterVelocity(velocity);
    }

    public double getTargetVelocity() {
        return targetVelocity;
    }

    @Override
    public void periodic() {
        shooterController.update();
    }
}