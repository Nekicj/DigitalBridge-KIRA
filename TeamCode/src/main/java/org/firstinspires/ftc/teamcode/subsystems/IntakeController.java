package org.firstinspires.ftc.teamcode.Controllers;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

import dev.frozenmilk.dairy.cachinghardware.CachingDcMotorEx;
import dev.frozenmilk.dairy.cachinghardware.CachingServo;

@Config
public class IntakeController {
    private CachingDcMotorEx intakeMotor = null;
    private CachingDcMotorEx intakeMotor2 = null;

    private CachingServo leftServo = null;
    private CachingServo rightServo = null;

    public static double ledColor = 0.28;

    public static enum IntakeState{
        INTAKE(0.506),
        GATE(0.61),
        FOURTH(0.46);

        final double intakeState;
        IntakeState(double intakeState){this.intakeState = intakeState;}
    }



    public void initialize(HardwareMap hardwareMap,String intakeMotorName,String intakeMotorName2,String leftServoName,String rightServoName){
        leftServo = new CachingServo(hardwareMap.get(Servo.class,leftServoName));
        rightServo = new CachingServo(hardwareMap.get(Servo.class,rightServoName));

        leftServo.setDirection(Servo.Direction.REVERSE);

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

    public void setServosPos(IntakeState intakeState){
        switch (intakeState){
            case INTAKE:
                leftServo.setPosition(IntakeState.INTAKE.intakeState);
                rightServo.setPosition(IntakeState.INTAKE.intakeState);
                break;
            case GATE:
                leftServo.setPosition(IntakeState.GATE.intakeState);
                rightServo.setPosition(IntakeState.GATE.intakeState);
                break;
            case FOURTH:
                leftServo.setPosition(IntakeState.FOURTH.intakeState);
                rightServo.setPosition(IntakeState.FOURTH.intakeState);
                break;
        }
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
