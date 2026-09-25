package org.firstinspires.ftc.teamcode.robot.containers.TeleOp;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.constants.TeleOpBinds;
import org.firstinspires.ftc.teamcode.robot.commands.TeleOpDriveCommand;
import org.firstinspires.ftc.teamcode.robot.subsystems.*;

public final class RobotContainer {

    public RobotContainer(HardwareMap hardwareMap, Gamepad gamepad1, Telemetry telemetry) {
        GamepadEx driver = new GamepadEx(gamepad1);

        TeleOpDriveSubsystem drive = new TeleOpDriveSubsystem(hardwareMap);
        ShooterSubsystem shooter   = new ShooterSubsystem(hardwareMap, telemetry);
        IntakeSubsystem intake     = new IntakeSubsystem(hardwareMap);
        StopperSubsystem stopper   = new StopperSubsystem(hardwareMap);

        drive.setDefaultCommand(new TeleOpDriveCommand(drive, gamepad1));

        new TeleOpBinds(driver, gamepad1, drive, shooter, intake, stopper);
    }
}