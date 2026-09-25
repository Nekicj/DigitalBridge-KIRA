package org.firstinspires.ftc.teamcode.robot.commands;

import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.robot.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.robot.subsystems.StopperSubsystem;


public class IntakeCommand extends CommandBase {

    private final IntakeSubsystem intake;
    private final StopperSubsystem stopper;
    private final double power;

    public IntakeCommand(IntakeSubsystem intake, StopperSubsystem stopper, double power) {
        this.intake = intake;
        this.stopper = stopper;
        this.power = power;
        addRequirements(intake, stopper);
    }

    @Override
    public void execute() {
        stopper.setOpen(false);
        intake.setPower(power);
    }

    @Override
    public void end(boolean interrupted) {
        intake.setPower(0);
        stopper.setOpen(false);
    }
}