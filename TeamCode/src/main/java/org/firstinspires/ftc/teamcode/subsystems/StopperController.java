package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;



public class StopperController {
    private Servo stopper = null;

    public static double stopperOff = 0.27;
    public static double stopperOn = 0.37;

    public void initialize(HardwareMap hardwareMap,String stopperHardwareMapName,boolean initPose){
        stopper = hardwareMap.get(Servo.class,stopperHardwareMapName);

        if(initPose){
            stopper.setPosition(stopperOn);
        }
    }
    public void setStopper(boolean isStop){
        if(isStop){
            stopper.setPosition(stopperOn);

        }else{
            stopper.setPosition(stopperOff);
        }
    }
}