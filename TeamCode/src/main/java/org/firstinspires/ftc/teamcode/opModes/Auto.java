package org.firstinspires.ftc.teamcode.opModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.seattlesolvers.solverslib.command.CommandOpMode;

import org.firstinspires.ftc.teamcode.containers.AutoCycleSoloAuto;

@Autonomous(name = "Сольный коч", group = "1")
public class Auto extends CommandOpMode {

    @Override
    public void initialize() {
        AutoCycleSoloAuto robot = new AutoCycleSoloAuto(hardwareMap,telemetry);
        schedule(robot.getAutonomousCommand());
    }
}