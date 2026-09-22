package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;


public class HeadingController {
    private GoBildaPinpointDriver pinpoint;
    private double currentHeadingRad = 0;
    private double previousRawHeading = 0;
    private double velX = 0;
    private double velY = 0;
    private double headingVelocity = 0;
    private boolean wasNan = false;
    private int nanCounter = 0;
    private static final int MAX_NAN_COUNT = 5;

    public HeadingController(HardwareMap hardwareMap) {
        pinpoint = hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");
        initializePinpoint(5.508, -0.412);
    }

    private void initializePinpoint(double offsetX, double offsetY) {
        pinpoint.initialize();
        pinpoint.resetDeviceConfigurationForOpMode();
        pinpoint.recalibrateIMU();
        pinpoint.resetPosAndIMU();
        pinpoint.setOffsets(offsetX, offsetY, DistanceUnit.INCH);
        pinpoint.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        pinpoint.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.REVERSED, GoBildaPinpointDriver.EncoderDirection.FORWARD);

        double initialHeading = pinpoint.getHeading(AngleUnit.RADIANS);
        if (Double.isFinite(initialHeading)) {
            currentHeadingRad = initialHeading;
            previousRawHeading = initialHeading;
        }
    }

    public void update() {
        pinpoint.update();
        velX = pinpoint.getVelX(DistanceUnit.INCH);
        velY = pinpoint.getVelY(DistanceUnit.INCH);
        headingVelocity = pinpoint.getHeadingVelocity(AngleUnit.RADIANS.getUnnormalized());

        double rawHeading = pinpoint.getHeading(AngleUnit.RADIANS);
        double deltaHeading = 0;

        if (!Double.isFinite(rawHeading)) {
            wasNan = true;
            nanCounter++;
            if (nanCounter > MAX_NAN_COUNT) {
                return;
            }
            rawHeading = previousRawHeading;
        } else {
            nanCounter = 0;
            deltaHeading = rawHeading - previousRawHeading;

            if (deltaHeading > Math.PI) {
                deltaHeading -= 2 * Math.PI;
            } else if (deltaHeading < -Math.PI) {
                deltaHeading += 2 * Math.PI;
            }
            previousRawHeading = rawHeading;
        }

        currentHeadingRad += deltaHeading;
        currentHeadingRad = normalizeAngle(currentHeadingRad);
    }

    private double normalizeAngle(double angle) {
        angle %= (2 * Math.PI);
        if (angle > Math.PI) {
            angle -= 2 * Math.PI;
        } else if (angle < -Math.PI) {
            angle += 2 * Math.PI;
        }
        return angle;
    }

    public void setPoseX(double X) {
        pinpoint.setPosX(X, DistanceUnit.INCH);
    }

    public void setPoseY(double Y) {
        pinpoint.setPosY(Y, DistanceUnit.INCH);
    }

//    public void setPose(Pose newPose) {
////        pinpoint.setPosition(new Pose2D(DistanceUnit.INCH,newPose.getX(),newPose.getY(),AngleUnit.RADIANS,newPose.getHeading()));
//        pinpoint.setPosX(newPose.getX(), DistanceUnit.INCH);
//        pinpoint.setPosY(newPose.getY(), DistanceUnit.INCH);
//        pinpoint.setHeading(newPose.getHeading(), AngleUnit.RADIANS);
//
//    }

    public void setHeading(double heading) {
        pinpoint.setHeading(heading, AngleUnit.RADIANS);
    }

    public double getCurrentHeading() {
        return currentHeadingRad;
    }

    public double getPosX() {
        return pinpoint.getPosX(DistanceUnit.INCH);
    }

    public double getPosY() {
        return pinpoint.getPosY(DistanceUnit.INCH);
    }

    public double getVelX() {
        return velX;
    }

    public double getVelY() {
        return velY;
    }

    public double getHeadingVelocity() {
        return headingVelocity;
    }

    public void resetPinpoint() {
        currentHeadingRad = 0;
        previousRawHeading = 0;
        velX = 0;
        velY = 0;
        headingVelocity = 0;
        pinpoint.resetPosAndIMU();
    }

    public void showTelemetry(Telemetry telemetry) {
        telemetry.addLine("=== HEADING CONTROLLER ===");
        telemetry.addData("current heading (rad)", currentHeadingRad);
        telemetry.addData("current heading (deg)", Math.toDegrees(currentHeadingRad));
        telemetry.addData("velocity X (in/s)", velX);
        telemetry.addData("velocity Y (in/s)", velY);
        telemetry.addData("heading velocity (rad/s)", headingVelocity);
        telemetry.addData("heading velocity (deg/s)", Math.toDegrees(headingVelocity));
        telemetry.addData("NaN Count", nanCounter);
        telemetry.addData("wasNaN", wasNan);
        telemetry.addData("X Pos", getPosX());
        telemetry.addData("Y Pos", getPosY());
        telemetry.addData("total speed", String.format("%.2f", Math.sqrt(velX * velX + velY * velY)));
    }
}