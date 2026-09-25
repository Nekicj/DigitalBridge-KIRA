package org.firstinspires.ftc.teamcode.robot.commands;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.robot.subsystems.TeleOpDriveSubsystem;


public class TeleOpDriveCommand extends CommandBase {

    private final TeleOpDriveSubsystem drive;
    private final Gamepad gamepad;

    public TeleOpDriveCommand(TeleOpDriveSubsystem drive, Gamepad gamepad) {
        this.drive = drive;
        this.gamepad = gamepad;
        addRequirements(drive);
    }

    @Override
    public void execute() {
        drive.drive(gamepad.left_stick_x, -gamepad.left_stick_y, gamepad.right_stick_x);
    }
}