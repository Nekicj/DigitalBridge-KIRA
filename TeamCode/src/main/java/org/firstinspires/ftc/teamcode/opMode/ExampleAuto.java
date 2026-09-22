package org.firstinspires.ftc.teamcode.opMode;

import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import com.pedropathing.follower.Follower;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;

import static com.pedropathing.api.Paths.*;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;

import org.firstinspires.ftc.teamcode.bigDih.dihs.FieldPositions;
import org.firstinspires.ftc.teamcode.pedro.Constants;

@Autonomous(name = "коч братан",group = "1")
public class ExampleAuto extends OpMode {

    private Follower follower;
    private final PoseFactory p = PoseFactory.degrees();

    private final Pose startPose = FieldPositions.start();
    private final Pose scorePose = FieldPositions.score();
    private final Pose parkPose  = FieldPositions.park();

    private Path startToScore() {
        return line(startPose, scorePose).linear(startPose, scorePose);
    }
    private Path park() {return line(scorePose, parkPose).linear(scorePose, parkPose);}

    private Command autoRoutine() {
        return sequential(
                follow(follower, startToScore()),
                follow(follower, park())
        );
    }

    @Override
    public void init() {
        Scheduler.reset();
        follower = Constants.create(hardwareMap);
        follower.setPose(startPose);
        follower.update();
    }

    @Override
    public void start() {
        schedule(autoRoutine());
    }

    @Override
    public void loop() {
        follower.update();
        Scheduler.execute();

        telemetry.addData("X", follower.pose().x());
        telemetry.addData("Y", follower.pose().y());
        telemetry.addData("Heading", Math.toDegrees(follower.pose().heading()));
        telemetry.update();
    }
}