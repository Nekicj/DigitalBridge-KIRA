package org.firstinspires.ftc.teamcode.robot.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.constants.RobotConstants;
import org.firstinspires.ftc.teamcode.robot.controllers.IntakeController;

public class IntakeSubsystem extends SubsystemBase {

    private final IntakeController intakeController;

    public IntakeSubsystem(HardwareMap hardwareMap) {
        intakeController = new IntakeController();
        intakeController.initialize(hardwareMap, "intake1", "intake2");
    }
    public void setPower(double power){
        intakeController.setIntakePower(power);
    }

    public IntakeController getController() {
        return intakeController;
    }

    public void on()  { intakeController.setIntakePower(RobotConstants.intakePower); }
    public void off() { intakeController.setIntakePower(0); }
}