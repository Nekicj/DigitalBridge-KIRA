package org.firstinspires.ftc.teamcode.robot.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.constants.RobotConstants;
import org.firstinspires.ftc.teamcode.robot.controllers.BaseController;

public class TeleOpDriveSubsystem extends SubsystemBase {

    private final BaseController base;

    public TeleOpDriveSubsystem(HardwareMap hardwareMap) {
        base = new BaseController();
        base.initialize(hardwareMap, true);
    }

    public void drive(double leftX, double leftY, double rightX) {
        base.update(leftX, leftY, rightX, RobotConstants.turnCoeff, false, false);
    }

    public void resetHeading() { base.resetHeading(); }
    public double getHeading() { return base.getHeading(); }

    @Override
    public void periodic() {

    }
}