package org.firstinspires.ftc.teamcode.robot.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.constants.RobotConstants;
import org.firstinspires.ftc.teamcode.robot.controllers.ShooterController;


public class ShooterSubsystem extends SubsystemBase {

    private final ShooterController shooterController;
    private double targetVelocity = 0;
    private final Telemetry telemetry;
    private boolean spinning = false;

    public ShooterSubsystem(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;
        shooterController = new ShooterController();
        shooterController.initialize(hardwareMap, "shooter_l", "shooter_r");
    }

    public void setSpinning(boolean spinning) { this.spinning = spinning; }
    public boolean isSpinning() { return spinning; }

    public double getTargetVelocity() {
        return spinning ? RobotConstants.shooterSpeedBorder : RobotConstants.idleVelocity;
    }

    public void setTargetVelocity(double velocity) {
        targetVelocity = velocity;
        shooterController.setShooterVelocity(velocity);
    }

    @Override
    public void periodic() {
        shooterController.setShooterVelocity(getTargetVelocity());
        shooterController.update();
        shooterController.showTelemetry(telemetry);
    }
}