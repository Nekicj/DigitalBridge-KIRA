package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.controllers.StopperController;

public class StopperSubsystem extends SubsystemBase {

    private final StopperController stopperController;

    public StopperSubsystem(HardwareMap hardwareMap) {
        stopperController = new StopperController();
        stopperController.initialize(hardwareMap, "stopper", true);
    }

    public StopperController getController() {
        return stopperController;
    }

    public void open()  { stopperController.setStopper(false);  }
    public void close() { stopperController.setStopper(true
    ); }
}