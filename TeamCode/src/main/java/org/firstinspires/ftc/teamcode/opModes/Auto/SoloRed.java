package org.firstinspires.ftc.teamcode.opModes.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.seattlesolvers.solverslib.command.CommandOpMode;

import org.firstinspires.ftc.teamcode.robot.containers.Auto.AutoCycleSoloAuto;
import org.firstinspires.ftc.teamcode.robot.utils.Alliance;

@Autonomous(name = "Solo RED", group = "1")
public class SoloRed extends CommandOpMode {

    @Override
    public void initialize() {
        Alliance.set(Alliance.RED);
        AutoCycleSoloAuto robot = new AutoCycleSoloAuto(hardwareMap,telemetry);
        schedule(robot.getAutonomousCommand());
    }
}