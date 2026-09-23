package org.firstinspires.ftc.teamcode.commands;

import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.ShooterSubsystem;

public class ShootCommand extends CommandBase {

    private final ShooterSubsystem shooterSubsystem;
    private final double targetVelocity;

    public ShootCommand(ShooterSubsystem shooterSubsystem, double targetVelocity) {
        this.shooterSubsystem = shooterSubsystem;
        this.targetVelocity = targetVelocity;
        addRequirements(shooterSubsystem);
    }

    @Override
    public void execute() {
        shooterSubsystem.setTargetVelocity(targetVelocity);
    }

    @Override
    public void end(boolean interrupted) {
        shooterSubsystem.setTargetVelocity(0);
    }
}