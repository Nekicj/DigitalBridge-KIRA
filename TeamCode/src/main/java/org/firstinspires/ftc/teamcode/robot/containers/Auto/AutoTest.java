package org.firstinspires.ftc.teamcode.robot.containers.Auto;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.ParallelRaceGroup;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.constants.RobotConstants;
import org.firstinspires.ftc.teamcode.robot.commands.FollowPathCommand;
import org.firstinspires.ftc.teamcode.robot.commands.ShootCommand;
import org.firstinspires.ftc.teamcode.robot.sequences.TransferSequence;
import org.firstinspires.ftc.teamcode.robot.subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.robot.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.robot.subsystems.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.robot.subsystems.StopperSubsystem;
import org.firstinspires.ftc.teamcode.robot.utils.auto.AutoPaths;
import org.firstinspires.ftc.teamcode.robot.utils.auto.FieldPositions;

public final class AutoTest {

    private final DriveSubsystem drive;
    private final ShooterSubsystem shooter;
    private final IntakeSubsystem intake;
    private final StopperSubsystem stopper;

    public AutoTest(HardwareMap hardwareMap, Telemetry telemetry) {
        drive   = new DriveSubsystem(hardwareMap, FieldPositions.start(), telemetry);
        shooter = new ShooterSubsystem(hardwareMap,telemetry);
        intake  = new IntakeSubsystem(hardwareMap);
        stopper = new StopperSubsystem(hardwareMap);

        shooter.setTargetVelocity(RobotConstants.idleVelocity);
    }

    public Command getAutonomousCommand() {
        return new SequentialCommandGroup(

                new FollowPathCommand(drive, AutoPaths::startToScore),
                new WaitCommand(500),

                new ParallelRaceGroup(
                        new ShootCommand(shooter,intake,stopper),
                        new TransferSequence(stopper)
                ),

                new InstantCommand(() -> shooter.setTargetVelocity(RobotConstants.idleVelocity)),

                new FollowPathCommand(drive, () -> AutoPaths.scoreToPark(true))
        );
    }

    public DriveSubsystem getDrive() { return drive; }
}