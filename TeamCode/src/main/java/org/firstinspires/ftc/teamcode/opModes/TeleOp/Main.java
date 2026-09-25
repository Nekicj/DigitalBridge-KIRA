package org.firstinspires.ftc.teamcode.opModes.TeleOp;

//import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;

import org.firstinspires.ftc.teamcode.robot.containers.TeleOp.RobotContainer;

//@Configurable
@TeleOp(name = "Main", group = "1")
public class Main extends CommandOpMode {

    @Override
    public void initialize() {
        new RobotContainer(hardwareMap, gamepad1, telemetry);
    }
}