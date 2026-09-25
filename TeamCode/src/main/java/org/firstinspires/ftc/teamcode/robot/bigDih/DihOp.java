package org.firstinspires.ftc.teamcode.robot.bigDih;

//import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@Disabled
@TeleOp(name = "servo", group = "1")
public class DihOp extends OpMode {
    private Servo servo = null;

    public static double dih2 = 0.5;

    @Override
    public void init() {
        servo = hardwareMap.get(Servo.class,"stopper");

    }

    @Override
    public void loop() {
        servo.setPosition(dih2);

        telemetry.addData("dihs",dih2);
        telemetry.update();
    }
}