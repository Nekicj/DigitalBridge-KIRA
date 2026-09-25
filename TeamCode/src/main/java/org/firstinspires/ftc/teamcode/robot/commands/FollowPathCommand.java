package org.firstinspires.ftc.teamcode.robot.commands;

import com.pedropathing.paths.Path;
import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.robot.subsystems.DriveSubsystem;

import java.util.function.Supplier;

public class FollowPathCommand extends CommandBase {

    private final DriveSubsystem drive;
    private final Supplier<Path> pathSupplier;

    public FollowPathCommand(DriveSubsystem drive, Supplier<Path> pathSupplier) {
        this.drive = drive;
        this.pathSupplier = pathSupplier;
        addRequirements(drive);
    }

    public FollowPathCommand(DriveSubsystem drive, Path path) {
        this(drive, () -> path);
    }

    @Override
    public void initialize() {
        drive.followPath(pathSupplier.get());
    }

    @Override
    public boolean isFinished() {
        return !drive.isBusy();
    }
}