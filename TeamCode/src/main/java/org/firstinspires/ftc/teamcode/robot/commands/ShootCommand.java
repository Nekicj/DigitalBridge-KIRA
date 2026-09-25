package org.firstinspires.ftc.teamcode.robot.commands;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.constants.RobotConstants;
import org.firstinspires.ftc.teamcode.robot.subsystems.*;

public class ShootCommand extends CommandBase {

    private final ShooterSubsystem shooter;
    private final IntakeSubsystem intake;
    private final StopperSubsystem stopper;

    private final ElapsedTime timer = new ElapsedTime();
    private boolean openedByUs;
    private boolean wasSpinning;
    private boolean readyToFeed;

    public ShootCommand(ShooterSubsystem shooter, IntakeSubsystem intake, StopperSubsystem stopper) {
        this.shooter = shooter;
        this.intake = intake;
        this.stopper = stopper;
        addRequirements(intake, stopper);
    }

    @Override
    public void initialize() {
        wasSpinning = shooter.isSpinning();
        shooter.setSpinning(true);

        openedByUs = !stopper.isOpen();
        if (openedByUs) {
            stopper.setOpen(true);
            timer.reset();
            readyToFeed = false;
        } else {
            readyToFeed = true;
        }
    }

    @Override
    public void execute() {
        if (!readyToFeed && timer.milliseconds() >= RobotConstants.stopperOpenTimeMs) {
            readyToFeed = true;
        }
        if (readyToFeed) {
            intake.setPower(RobotConstants.intakePower * RobotConstants.shootIntakeCoeff);
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        intake.setPower(0);
        if (openedByUs) stopper.setOpen(false);
        shooter.setSpinning(wasSpinning);
    }
}