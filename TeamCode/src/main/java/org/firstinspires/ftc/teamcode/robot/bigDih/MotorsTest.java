package org.firstinspires.ftc.teamcode.robot.bigDih;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@Disabled
@TeleOp(name = "Motor Test", group = "Test")
public class MotorsTest extends LinearOpMode {

    private DcMotorEx frontLeft;
    private DcMotorEx backLeft;
    private DcMotorEx frontRight;
    private DcMotorEx backRight;

    private static final double POWER = 0.4;

    @Override
    public void runOpMode() {

        frontLeft = hardwareMap.get(DcMotorEx.class, "lfd");
        backLeft = hardwareMap.get(DcMotorEx.class, "lbd");
        frontRight = hardwareMap.get(DcMotorEx.class, "rfd");
        backRight = hardwareMap.get(DcMotorEx.class, "rbd");

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();

        while (opModeIsActive()) {

            frontRight.setPower(gamepad1.right_bumper ? POWER : 0);
            backRight.setPower(gamepad1.right_trigger > 0.1 ? POWER : 0);

            frontLeft.setPower(gamepad1.left_bumper ? POWER : 0);
            backLeft.setPower(gamepad1.left_trigger > 0.1 ? POWER : 0);

            telemetry.addData("Front Right", gamepad1.right_bumper ? "ON" : "OFF");
            telemetry.addData("Back Right", gamepad1.right_trigger > 0.1 ? "ON" : "OFF");
            telemetry.addData("Front Left", gamepad1.left_bumper ? "ON" : "OFF");
            telemetry.addData("Back Left", gamepad1.left_trigger > 0.1 ? "ON" : "OFF");
            telemetry.update();
        }
    }
}