package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

import dev.frozenmilk.dairy.cachinghardware.CachingDcMotorEx;
import dev.frozenmilk.dairy.cachinghardware.CachingServo;


public class IntakeController {
    private CachingDcMotorEx intakeMotor = null;
    private CachingDcMotorEx intakeMotor2 = null;

    public void initialize(HardwareMap hardwareMap,String intakeMotorName,String intakeMotorName2){

//        intakeMotor = hardwareMap.get(DcMotor.class, intakeMotorName);
//        intakeMotor2 = hardwareMap.get(DcMotor.class, intakeMotorName2);

        intakeMotor = new CachingDcMotorEx(hardwareMap.get(DcMotorEx.class,intakeMotorName));
        intakeMotor2 = new CachingDcMotorEx(hardwareMap.get(DcMotorEx.class,intakeMotorName2));

        intakeMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        intakeMotor2.setDirection(DcMotorSimple.Direction.REVERSE);

        intakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intakeMotor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void setIntakePower(double intakePower){
        intakeMotor.setPower(intakePower);
        intakeMotor2.setPower(intakePower);
    }
    public void update(){
    }

    public double getIntakePower(){
        return intakeMotor.getPower();
    }

    public void showTelemetry(Telemetry telemetry){
        telemetry.addData("leftIntake power",intakeMotor.getPower());
        telemetry.addData("leftIntake amper",intakeMotor.getCurrent(CurrentUnit.AMPS));
        telemetry.addData("leftIntake current",intakeMotor.getCurrentAlert(CurrentUnit.AMPS));
    }


}
