package org.firstinspires.ftc.teamcode.robot.commands;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.constants.RobotConstants;
import org.firstinspires.ftc.teamcode.robot.subsystems.ShooterSubsystem;

public class SpinNRumbleCommand extends CommandBase {

    private final ShooterSubsystem shooter;
    private final Gamepad gamepad;

    public SpinNRumbleCommand(ShooterSubsystem shooter, Gamepad gamepad) {
        this.shooter = shooter;
        this.gamepad = gamepad;
        addRequirements(shooter);
    }

    @Override
    public void execute() {
        shooter.setSpinning(true);
        gamepad.rumble(RobotConstants.rumblePower, RobotConstants.rumblePower, 200);
    }

    @Override
    public void end(boolean interrupted) {
        shooter.setSpinning(false);
        gamepad.rumble(0, 0, 0);
    }
}