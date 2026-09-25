package org.firstinspires.ftc.teamcode.constants;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.seattlesolvers.solverslib.command.CommandScheduler;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.RunCommand;
import com.seattlesolvers.solverslib.command.button.GamepadButton;
import com.seattlesolvers.solverslib.command.button.Trigger;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.robot.commands.*;
import org.firstinspires.ftc.teamcode.robot.subsystems.*;

public final class TeleOpBinds {

    public TeleOpBinds(GamepadEx driver,
                       Gamepad geypad,
                       TeleOpDriveSubsystem drive,
                       ShooterSubsystem shooter,
                       IntakeSubsystem intake,
                       StopperSubsystem stopper) {
        CommandScheduler.getInstance().schedule(new RunCommand(driver::readButtons));

        new GamepadButton(driver, GamepadKeys.Button.X)
                .whenPressed(new InstantCommand(stopper::toggle));

        new GamepadButton(driver, GamepadKeys.Button.B)
                .toggleWhenPressed(new SpinNRumbleCommand(shooter, geypad));

        new Trigger(() -> geypad.right_trigger > 0.15)
                .whileActiveOnce(new ShootCommand(shooter, intake, stopper));

        new Trigger(() -> geypad.right_bumper)
                .whileActiveOnce(new IntakeCommand(intake, stopper, RobotConstants.intakePower));

        new Trigger(() -> geypad.left_bumper)
                .whileActiveOnce(new IntakeCommand(intake, stopper, RobotConstants.outtakePower));

        new GamepadButton(driver, GamepadKeys.Button.BACK)
                .whenPressed(new InstantCommand(() -> {
                    drive.resetHeading();
                    geypad.rumble(0.5, 0.5, 200);
                }));
    }
}