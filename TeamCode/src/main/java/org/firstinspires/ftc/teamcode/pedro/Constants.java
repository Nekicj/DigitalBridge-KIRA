package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.algorithm.Foresight;
import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Matrix;
import com.pedropathing.math.Vector2D;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.PinpointConfig;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Constants {
    public static MecanumConfig drivetrainConfig = new MecanumConfig(c -> {
        c.frontLeftName.set("lfd");
        c.frontRightName.set("rfd");
        c.backLeftName.set("lbd");
        c.backRightName.set("rbd");

        c.frontLeftDirection.set(DcMotorSimple.Direction.FORWARD);
        c.frontRightDirection.set(DcMotorSimple.Direction.REVERSE);
        c.backLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.backRightDirection.set(DcMotorSimple.Direction.FORWARD);
    });

    public static PinpointConfig localizerConfig = new PinpointConfig(c -> {
        c.name.set("pinpoint");
        c.podType.set(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        c.xPodOffset.set(4.928390923447497);
        c.yPodOffset.set(-5.831831083523007);
        c.xPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.yPodDirection.set(GoBildaPinpointDriver.EncoderDirection.REVERSED);
        c.globalDistanceUnit.set(DistanceUnit.INCH);
        c.offsetUnits.set(DistanceUnit.INCH);
    });

    public static ForesightConfig foresightConfig = new ForesightConfig(
            c -> {
                Controller primaryTranslationalForward = Controller.proportional(0.22419707478467996);
                Controller secondaryTranslationalForward = Controller.proportional(0.0828348284767695);
                Controller primaryTranslationalLateral = Controller.proportional(0.3023599840713053);
                Controller secondaryTranslationalLateral = Controller.proportional(0.11171393490677604);

                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                c.coast.set(Controller.proportionalFeedforward(0.011591424330560629));
                c.brake.set(Controller.proportionalFeedforward(0.009852710680976534));

                c.headingFeedback.set(Controller.proportional(3.2714237697939583));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.05541656169953065, 0.002450768354554018));

                c.linearBrakeCoefficients.set(Matrix.diag(0.09154375373556328, 0.07229027739171828));
                c.quadraticBrakeCoefficients.set(Matrix.diag(0.0010845074488468125, 0.0012174268884057337));

                c.maxAchievableForwardVelocity.set(86.1119580865803);
                c.maxAchievableStrafeVelocity.set(73.46003016225252);
                c.naturalForwardDeceleration.set(29.409556182764906);
                c.naturalStrafeDeceleration.set(60.79120416606112);
            }
    );

    public static Follower create(HardwareMap h) {
        return new Follower(
                new PinpointLocalizer(h, localizerConfig),
                new Mecanum(h, drivetrainConfig),
                new Foresight(foresightConfig)
        );
    }
}