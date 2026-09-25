package org.firstinspires.ftc.teamcode.robot.commands;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.constants.RobotConstants.AprilTagGroup;
import org.firstinspires.ftc.teamcode.robot.subsystems.LimeLightSubsystem;

import java.util.concurrent.TimeUnit;

public class ScanTagCommand extends CommandBase {

    private final LimeLightSubsystem limelight;
    private final AprilTagGroup[] candidates;
    private final long timeoutMs;

    private final ElapsedTime timer = new ElapsedTime();

    public ScanTagCommand(LimeLightSubsystem limelight, double timeout, TimeUnit unit,
                          AprilTagGroup... candidates) {
        this.limelight = limelight;
        this.candidates = candidates;
        this.timeoutMs = unit.toMillis((long) timeout);
        addRequirements(limelight);
    }

    @Override
    public void initialize() {
        limelight.setScanResult(null);
        limelight.setPipeline(LimeLightSubsystem.TAG_PIPELINE);
        timer.reset();
    }

    @Override
    public void execute() {
        if (limelight.getScanResult() == null) {
            limelight.setScanResult(limelight.detect(candidates));
        }
    }

    @Override
    public boolean isFinished() {
        return limelight.getScanResult() != null
                || timer.milliseconds() >= timeoutMs;
    }


}