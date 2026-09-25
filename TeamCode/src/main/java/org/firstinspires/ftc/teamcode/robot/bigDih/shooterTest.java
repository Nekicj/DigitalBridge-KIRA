
package org.firstinspires.ftc.teamcode.robot.bigDih;

//import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name = "shooterTest", group = "1")
public class shooterTest extends OpMode {
    private DcMotor left = null;
    private DcMotor right = null;

    public static double leftspeed = 0.5;
    public static double rightspeed = 0.5;

    @Override
    public void init() {
        left = hardwareMap.get(DcMotor.class,"shooter_l");
        right = hardwareMap.get(DcMotor.class,"shooter_r");

    }

    @Override
    public void loop() {
        left.setPower(leftspeed);
        right .setPower(rightspeed);

        if(gamepad1.rightBumperWasPressed()){
            rightspeed +=0.05;
        }else if(gamepad1.leftBumperWasPressed()){
            leftspeed += 0.05;
        }

        if(gamepad1.rightTriggerWasPressed()){
            rightspeed -=0.05;
        }else if(gamepad1.leftTriggerWasPressed()){
            leftspeed -= 0.05;
        }
        telemetry.addData("left",leftspeed);
        telemetry.addData("right",rightspeed);
        telemetry.update();
    }
}