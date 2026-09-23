package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.controllers.IntakeController;

public class IntakeSubsystem extends SubsystemBase {

    private final IntakeController intakeController;

    public IntakeSubsystem(HardwareMap hardwareMap) {
        intakeController = new IntakeController();
        intakeController.initialize(hardwareMap, "intake1", "intake2");
    }

    public IntakeController getController() {
        return intakeController;
    }
}