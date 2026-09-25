package org.firstinspires.ftc.teamcode.robot.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.robot.controllers.StopperController;

public class StopperSubsystem extends SubsystemBase {

    private final StopperController stopperController;
    private boolean open = false;

    public StopperSubsystem(HardwareMap hardwareMap) {
        stopperController = new StopperController();
        stopperController.initialize(hardwareMap, "stopper", true);
        setOpen(false);
    }

    public StopperController getController() {
        return stopperController;
    }
    public void setOpen(boolean open) {
        this.open = open;
        stopperController.setStopper(open);
    }

    public void toggle() { setOpen(!open); }
    public boolean isOpen() { return open; }

    public void open()  { stopperController.setStopper(false);  }
    public void close() { stopperController.setStopper(true);
    }
}