package org.firstinspires.ftc.teamcode.robot.subsystems;

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.constants.RobotConstants.AprilTagGroup;
import org.firstinspires.ftc.teamcode.robot.controllers.LimeLightController;


public class LimeLightSubsystem extends SubsystemBase {

    public static final int TAG_PIPELINE = 0;

    private final LimeLightController controller;
    private final Telemetry telemetry;

    private AprilTagGroup lastScanResult = null;

    public LimeLightSubsystem(HardwareMap hardwareMap, String limelightName, Telemetry telemetry) {
        controller = new LimeLightController(hardwareMap, limelightName);
        this.telemetry = telemetry;
    }

    public void setPipeline(int index) { controller.setPipeline(index); }


    public boolean sees(AprilTagGroup group) { return controller.sees(group); }

    public int getDetectedId() {
        return controller.getVisibleIds().isEmpty() ? -1 : controller.getVisibleIds().get(0);
    }

    public void setScanResult(AprilTagGroup group) { lastScanResult = group; }
    public AprilTagGroup getScanResult() { return lastScanResult; }

    public AprilTagGroup detect(AprilTagGroup... candidates) {
        return controller.detect(candidates);
    }

    @Override
    public void periodic() {
        controller.update();

        telemetry.addData("LL connected", controller.isConnected());
        telemetry.addData("visible tags", controller.getVisibleIds());
        telemetry.addData("last scan", lastScanResult);
    }
}