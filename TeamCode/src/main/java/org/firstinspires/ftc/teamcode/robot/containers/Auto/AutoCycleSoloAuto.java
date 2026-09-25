package org.firstinspires.ftc.teamcode.robot.containers.Auto;

import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.ConditionalCommand;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.ParallelRaceGroup;
import com.seattlesolvers.solverslib.command.SelectCommand;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.constants.RobotConstants;
import org.firstinspires.ftc.teamcode.robot.commands.FollowPathCommand;
import org.firstinspires.ftc.teamcode.robot.commands.IntakeCommand;
import org.firstinspires.ftc.teamcode.robot.commands.ScanTagCommand;
import org.firstinspires.ftc.teamcode.robot.commands.ShootCommand;
import org.firstinspires.ftc.teamcode.robot.sequences.TransferSequence;
import org.firstinspires.ftc.teamcode.robot.subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.robot.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.robot.subsystems.LimeLightSubsystem;
import org.firstinspires.ftc.teamcode.robot.subsystems.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.robot.subsystems.StopperSubsystem;
import org.firstinspires.ftc.teamcode.robot.utils.auto.AutoPaths;
import org.firstinspires.ftc.teamcode.robot.utils.auto.FieldPositions;
import org.firstinspires.ftc.teamcode.constants.RobotConstants.AprilTagGroup;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public final class AutoCycleSoloAuto {

    private final DriveSubsystem drive;
    private final ShooterSubsystem shooter;
    private final IntakeSubsystem intake;
    private final StopperSubsystem stopper;
    private final LimeLightSubsystem limelight;

    public AutoCycleSoloAuto(HardwareMap hardwareMap, Telemetry telemetry) {
        drive   = new DriveSubsystem(hardwareMap, FieldPositions.start(), telemetry);
        shooter = new ShooterSubsystem(hardwareMap,telemetry);
        intake  = new IntakeSubsystem(hardwareMap);
        stopper = new StopperSubsystem(hardwareMap);
        limelight = new LimeLightSubsystem(hardwareMap,"limelight",telemetry);

        shooter.setTargetVelocity(RobotConstants.idleVelocity);
    }

    public Command getAutonomousCommand() {
        return new SequentialCommandGroup(
                new InstantCommand(() -> shooter.setSpinning(true)),
                new FollowPathCommand(drive, AutoPaths::startToScore),
                new WaitCommand(100),

                shoot(),
                new InstantCommand(() -> shooter.setSpinning(false)),

                new FollowPathCommand(drive,AutoPaths::scoreToTakeHuman),
                new InstantCommand(intake::on,intake),
                new FollowPathCommand(drive,AutoPaths::scoreToHuman),
                new InstantCommand(intake::off,intake),
                new ParallelRaceGroup(
                        new IntakeCommand(intake, stopper, RobotConstants.intakePower),
                        new WaitCommand(1200)
                ),

                new InstantCommand(() -> shooter.setSpinning(true)),
                new FollowPathCommand(drive,AutoPaths::humanToScore),

                new ScanTagCommand(limelight,2, TimeUnit.SECONDS, AprilTagGroup.own(true)),

                new ConditionalCommand(
                        new SelectCommand(
                                Map.of(
                                        AprilTagGroup.RED_RIGHT, hiveNotFallen(),
                                        AprilTagGroup.BLUE_RIGHT, hiveNotFallen()
                                ),
                                () ->limelight.getScanResult()
                        ),
                        hiveFallen(),
                        () -> limelight.getScanResult() != null
                ),
                new InstantCommand(() -> shooter.setSpinning(false)),

                new FollowPathCommand(drive,AutoPaths::scoreRightToFlowerBottom),
                new ParallelRaceGroup(
                        new IntakeCommand(intake, stopper, RobotConstants.intakePower),
                        new WaitCommand(1200)
                ),
                new InstantCommand(() -> shooter.setSpinning(true)),
                new FollowPathCommand(drive,AutoPaths::flowerBottomToScoreLeft),
                new WaitCommand(100),
                shoot(),
                new InstantCommand(() -> shooter.setSpinning(false))
        );
    }



    private Command hiveNotFallen() {
        return new SequentialCommandGroup(
                shoot()
        );
    }

    private Command hiveFallen() {
        return new InstantCommand();
    }

    private Command shoot(){
        return new ParallelRaceGroup(
                new ShootCommand(shooter,intake,stopper),
                new WaitCommand(500)
        );
    }
}