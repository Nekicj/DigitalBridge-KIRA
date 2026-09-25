package org.firstinspires.ftc.teamcode.robot.controllers;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes.FiducialResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.constants.RobotConstants.AprilTagGroup;

import java.util.ArrayList;
import java.util.List;

public class LimeLightController {

    private final Limelight3A limelight;
    private final List<Integer> visibleIds = new ArrayList<>();

    public LimeLightController(HardwareMap hardwareMap, String limelightName) {
        limelight = hardwareMap.get(Limelight3A.class, limelightName);

        limelight.setPollRateHz(100);
        limelight.start();
    }

    public void setPipeline(int index) {
        limelight.pipelineSwitch(index);
    }

    public void update() {
        visibleIds.clear();

        LLResult result = limelight.getLatestResult();
        if (result == null || !result.isValid()) return;

        for (FiducialResult f : result.getFiducialResults()) {
            visibleIds.add(f.getFiducialId());
        }
    }

    public List<Integer> getVisibleIds() { return visibleIds; }

    public boolean seesAnything() { return !visibleIds.isEmpty(); }

    public boolean isConnected() { return limelight.isConnected(); }

    public boolean sees(AprilTagGroup group) {
        for (int id : visibleIds) {
            if (group.contains(id)) return true;
        }
        return false;
    }

    public AprilTagGroup detect(AprilTagGroup... candidates) {
        for (AprilTagGroup group : candidates) {
            if (sees(group)) return group;
        }
        return null;
    }
}