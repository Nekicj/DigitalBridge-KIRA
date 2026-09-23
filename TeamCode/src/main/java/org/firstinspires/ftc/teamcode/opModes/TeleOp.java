package org.firstinspires.ftc.teamcode.opModes;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.controllers.BaseController;
import org.firstinspires.ftc.teamcode.controllers.IntakeController;
import org.firstinspires.ftc.teamcode.controllers.ShooterController;
import org.firstinspires.ftc.teamcode.controllers.StopperController;
import org.firstinspires.ftc.teamcode.RobotConstants;

@Configurable
@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "teleOp", group = "1")
public class TeleOp extends OpMode {

    private BaseController baseController = null;
    private ShooterController shooterController = null;
    private IntakeController intakeController = null;
    private StopperController stopperController;

    private ElapsedTime stopperTimer = null;

    private boolean isShooting = false;
    private boolean isStopper = true;
    private double intakePower = 0;
    public static double shooterSpeed = RobotConstants.shooterSpeedBorder;

    @Override
    public void init(){
        stopperTimer = new ElapsedTime();
        baseController = new BaseController();
        intakeController = new IntakeController();
        shooterController = new ShooterController();
        stopperController = new StopperController();

        baseController.initialize(hardwareMap,true);
        intakeController.initialize(hardwareMap,"intake1","intake2");
        shooterController.initialize(hardwareMap,"shooter_l","shooter_r");
        stopperController.initialize(hardwareMap,"stopper",true);

        baseController.resetHeading();
    }


    @Override
    public void loop(){
        baseController.update(gamepad1.left_stick_x, -gamepad1.left_stick_y, gamepad1.right_stick_x, 0.75  , false, false);
        shooterController.update();


        buttons();
        conditions();
        showTelemetry();
    }

    private void buttons(){
        if(gamepad1.xWasPressed()){
            isShooting = !isShooting;
        }

        if(gamepad1.right_bumper){
            intakePower = -1;
        }else if(gamepad1.left_bumper){
            intakePower = 1;
        }else if(gamepad1.right_trigger > 0.15){
            intakePower = -0.6;
        } else{
            intakePower = 0;
        }

        if(gamepad1.aWasPressed()){
            isStopper = !isStopper;
        }

        if(gamepad1.backWasPressed()){
            baseController.resetHeading();
        }
    }

    private void conditions(){
        if(isShooting){
            shooterController.setShooterVelocity(shooterSpeed);
        }else{
            shooterController.setShooterVelocity(600);
        }

        if(isStopper){
            stopperController.setStopper(true);
        }else{
            stopperController.setStopper(false);
        }

        intakeController.setIntakePower(intakePower);
    }

    public void showTelemetry(){
        shooterController.showTelemetry(telemetry);

    }
}

