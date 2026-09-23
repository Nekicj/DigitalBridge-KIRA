package org.firstinspires.ftc.teamcode.subsystems;

import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.pedro.Constants;

public class DriveSubsystem extends SubsystemBase {

    private final Follower follower;
    private final Telemetry telemetry;

    public DriveSubsystem(HardwareMap hardwareMap, Pose startPose,Telemetry telemetry) {
        this.telemetry = telemetry;
        follower = Constants.create(hardwareMap);
        follower.setPose(startPose);
        follower.update();
    }

    public Follower getFollower() {
        return follower;
    }

    public Pose getPose() {
        return follower.pose();
    }

    public void followPath(Path path) {
        follower.follow(path);
    }

    public boolean isBusy() {
        return follower.isBusy();
    }

    @Override
    public void periodic() {

        follower.update();
    }

    public void debug(){
        telemetry.addLine("DriveSubsystem");
        telemetry.addData("x",follower.pose().x());
        telemetry.addData("y",follower.pose().y());
        telemetry.addData("heading",follower.pose().heading());
        telemetry.update();
    }
}