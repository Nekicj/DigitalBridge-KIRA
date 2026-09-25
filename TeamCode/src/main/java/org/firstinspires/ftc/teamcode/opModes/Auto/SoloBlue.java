package org.firstinspires.ftc.teamcode.opModes.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.seattlesolvers.solverslib.command.CommandOpMode;

import org.firstinspires.ftc.teamcode.robot.containers.Auto.AutoCycleSoloAuto;
import org.firstinspires.ftc.teamcode.robot.utils.Alliance;

@Autonomous(name = "Solo Blue", group = "1")
public class SoloBlue extends CommandOpMode {

    @Override
    public void initialize() {
        Alliance.set(Alliance.BLUE);
        AutoCycleSoloAuto robot = new AutoCycleSoloAuto(hardwareMap,telemetry);
        schedule(robot.getAutonomousCommand());
    }
}