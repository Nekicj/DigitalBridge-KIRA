package org.firstinspires.ftc.teamcode.containers;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.ParallelRaceGroup;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.RobotConstants;
import org.firstinspires.ftc.teamcode.commands.FollowPathCommand;
import org.firstinspires.ftc.teamcode.commands.ShootCommand;
import org.firstinspires.ftc.teamcode.sequences.TransferSequence;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.StopperSubsystem;
import org.firstinspires.ftc.teamcode.utils.auto.AutoPaths;
import org.firstinspires.ftc.teamcode.utils.auto.FieldPositions;

public final class AutoCycleSoloAuto {

    private final DriveSubsystem drive;
    private final ShooterSubsystem shooter;
    private final IntakeSubsystem intake;
    private final StopperSubsystem stopper;

    public AutoCycleSoloAuto(HardwareMap hardwareMap, Telemetry telemetry) {
        drive   = new DriveSubsystem(hardwareMap, FieldPositions.start(), telemetry);
        shooter = new ShooterSubsystem(hardwareMap);
        intake  = new IntakeSubsystem(hardwareMap);
        stopper = new StopperSubsystem(hardwareMap);

        shooter.setTargetVelocity(RobotConstants.idleVelocity);
    }

    public Command getAutonomousCommand() {
        return new SequentialCommandGroup(

                new FollowPathCommand(drive, AutoPaths::startToScore),
                new WaitCommand(500),

                new ParallelRaceGroup(
                        new ShootCommand(shooter, RobotConstants.shooterSpeedBorder),
                        new TransferSequence(stopper)
                ),

                new InstantCommand(() -> shooter.setTargetVelocity(RobotConstants.idleVelocity)),

                new FollowPathCommand(drive, AutoPaths::scoreToPark)
        );
    }

    public DriveSubsystem getDrive() { return drive; }
}