package org.firstinspires.ftc.teamcode.opmodes;

import com.pedropathing.api.Paths;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.command.CommandScheduler;
import org.firstinspires.ftc.teamcode.command.SequentialCommandGroup;
import org.firstinspires.ftc.teamcode.commands.FollowPathCommand;
import org.firstinspires.ftc.teamcode.commands.ShootCommand;
import org.firstinspires.ftc.teamcode.pedro.Constants; // Constants.create(hardwareMap) из нашего Pedro-гайда
import org.firstinspires.ftc.teamcode.pedro.FieldPositions;
import org.firstinspires.ftc.teamcode.subsystems.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.StopperSubsystem;
import org.firstinspires.ftc.teamcode.util.Alliance;

@Autonomous(name = "Auto (Command-Based)")
public class AutoMain extends LinearOpMode {

    @Override
    public void runOpMode() {
        CommandScheduler.getInstance().reset();

        Follower follower = Constants.create(hardwareMap);
        ShooterSubsystem shooter = new ShooterSubsystem(hardwareMap, "shooterLeft", "shooterRight", "directionServo", 0.7);
        StopperSubsystem stopper = new StopperSubsystem(hardwareMap, "stopper", true);

        // Выбор альянса в init-loop: dpad_left = RED, dpad_right = BLUE
        while (!isStarted() && !isStopRequested()) {
            if (gamepad1.dpad_left) Alliance.set(Alliance.RED);
            if (gamepad1.dpad_right) Alliance.set(Alliance.BLUE);
            telemetry.addData("Alliance", Alliance.get());
            telemetry.addLine("dpad_left = RED, dpad_right = BLUE");
            telemetry.update();
        }

        // Alliance уже выбран -> геттеры FieldPositions вернут отзеркаленные позы
        Pose startPose = FieldPositions.start();
        Pose scorePose = FieldPositions.score();

        follower.setPose(startPose);

        Path toScore = Paths.line(startPose, scorePose).linear(startPose, scorePose);

        CommandScheduler.getInstance().schedule(
            new SequentialCommandGroup(
                new FollowPathCommand(follower, toScore),
                new ShootCommand(shooter, stopper, 1200)
            )
        );

        while (opModeIsActive()) {
            follower.update(); // обновляем локализацию/путь каждый цикл, независимо от команд
            CommandScheduler.getInstance().run();

            telemetry.addData("X", follower.pose().x());
            telemetry.addData("Y", follower.pose().y());
            telemetry.addData("Alliance", Alliance.get());
            telemetry.update();
        }
    }
}
