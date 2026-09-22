/**
 * @author David Tolegenov - 27674 Always Kiroshi
 */

package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import dev.frozenmilk.dairy.cachinghardware.CachingDcMotorEx;


    public class BaseController {
    CachingDcMotorEx Lfront = null;
    CachingDcMotorEx Rfront = null;
    CachingDcMotorEx Rback = null;
    CachingDcMotorEx Lback = null;

        HeadingController headingController = null;



        public void initialize(HardwareMap hardwareMap,boolean useBrakeMode){
            Lfront = new CachingDcMotorEx(hardwareMap.get(DcMotorEx.class,"lfd"));
            Rfront = new CachingDcMotorEx(hardwareMap.get(DcMotorEx.class,"rfd"));
            Lback = new CachingDcMotorEx(hardwareMap.get(DcMotorEx.class,"lbd"));
            Rback = new CachingDcMotorEx(hardwareMap.get(DcMotorEx.class,"rbd"));

            Lfront.setDirection(DcMotorSimple.Direction.FORWARD);
            Rfront.setDirection(DcMotorSimple.Direction.REVERSE);
            Lback.setDirection(DcMotorSimple.Direction.REVERSE);
            Rback.setDirection(DcMotorSimple.Direction.FORWARD);

    //        Lfront.setRunMode(Motor.RunMode.VelocityControl);
    //        Rfront.setRunMode(Motor.RunMode.VelocityControl);
    //        Lback.setRunMode(Motor.RunMode.VelocityControl);
    //        Rback.setRunMode(Motor.RunMode.VelocityControl);
    //
    //        Lfront.setFeedforwardCoefficients(kS,kV,kA);
    //        Rfront.setFeedforwardCoefficients(kS,kV,kA);
    //        Lback.setFeedforwardCoefficients(LBackkS,LBackkV,LBackkA);
    //        Rback.setFeedforwardCoefficients(kS,kV,kA);
    //
    //        Lfront.setVeloCoefficients(KP, KI, KD);
    //        Rfront.setVeloCoefficients(KP, KI, KD);
    //        Lback.setVeloCoefficients(LBackKP, LBackKI, LBackKD);
    //        Rback.setVeloCoefficients(KP, KI, KD);

            if(useBrakeMode){
                Lfront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                Rfront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                Lback.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                Rback.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            }else{
                Lfront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                Rfront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                Lback.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                Rback.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            }

            headingController = new HeadingController(hardwareMap);
        }

        public void update(double leftX,double leftY,double rightX, double turnCoeff,boolean headingToTarget,boolean isRobotCentric){
            headingController.update();
            if(isRobotCentric){
                driveFieldCentric(
                        leftX,
                        leftY,
                        rightX *turnCoeff,
                        0,
                        headingToTarget
                );
            }else{
                driveFieldCentric(
                        leftX,
                        leftY,
                        rightX *turnCoeff,
                        headingController.getCurrentHeading() ,
                        headingToTarget
                );
            }

        }



        public void driveFieldCentric(double strafeSpeed, double forwardSpeed,
                                      double turnSpeed, double gyroAngle,boolean headingLockEnabled) {

            strafeSpeed = clipRange(strafeSpeed);
            forwardSpeed = clipRange(forwardSpeed);
            turnSpeed = clipRange(turnSpeed);

            double rotatedStrafe = strafeSpeed * Math.cos(-gyroAngle) - forwardSpeed * Math.sin(-gyroAngle);
            double rotatedForward = strafeSpeed * Math.sin(-gyroAngle) + forwardSpeed * Math.cos(-gyroAngle);

            
            double theta = Math.atan2(rotatedForward, rotatedStrafe);
            double magnitude = Math.sqrt(rotatedStrafe * rotatedStrafe + rotatedForward * rotatedForward);

            double[] wheelSpeeds = new double[4];
            wheelSpeeds[0] = Math.sin(theta + Math.PI / 4);
            wheelSpeeds[1] = Math.sin(theta - Math.PI / 4);
            wheelSpeeds[2] = Math.sin(theta - Math.PI / 4);
            wheelSpeeds[3] = Math.sin(theta + Math.PI / 4);

            normalizeWithMagnitude(wheelSpeeds, magnitude);

            wheelSpeeds[0] += turnSpeed;
            wheelSpeeds[1] -= turnSpeed;
            wheelSpeeds[2] += turnSpeed;
            wheelSpeeds[3] -= turnSpeed;

            normalize(wheelSpeeds);

            driveWithMotorPowers(wheelSpeeds[0], wheelSpeeds[1], wheelSpeeds[2], wheelSpeeds[3]);
        }

        private double clipRange(double value) {
            return Math.max(-1.0, Math.min(1.0, value));
        }

        private void normalizeWithMagnitude(double[] wheelSpeeds, double magnitude) {
            double maxMagnitude = 0;
            for (double speed : wheelSpeeds) {
                maxMagnitude = Math.max(maxMagnitude, Math.abs(speed));
            }

            if (maxMagnitude > 0) {
                for (int i = 0; i < wheelSpeeds.length; i++) {
                    wheelSpeeds[i] = (wheelSpeeds[i] / maxMagnitude) * magnitude;
                }
            }
        }

        private void normalize(double[] wheelSpeeds) {
            double maxMagnitude = 0;
            for (double speed : wheelSpeeds) {
                maxMagnitude = Math.max(maxMagnitude, Math.abs(speed));
            }

            if (maxMagnitude > 1.0) {
                for (int i = 0; i < wheelSpeeds.length; i++) {
                    wheelSpeeds[i] /= maxMagnitude;
                }
            }
        }
        public void resetHeading(){
//            double X = headingController.getPosX();
//            double Y = headingController.getPosY();

            headingController.resetPinpoint();

//            headingController.setPoseX(X);
//            headingController.setPoseY(Y);


        }

        public double getHeading(){
            return headingController.getCurrentHeading();
        }

        public double getPosX(){
            return headingController.getPosX();
        }

        public double getPosY(){
            return headingController.getPosY();
        }

        public double getVelX(){return headingController.getVelX();}

        public double getVelY(){return headingController.getVelY();}

        public double getAngularVelY(){return headingController.getHeadingVelocity();}
;
        public void resetPinpoint(){
            headingController.resetPinpoint();
        }
//
//        public double getDistanceTo(Pose comparedPose){
//            double dx = comparedPose.getX() - headingController.getPosX();
//            double dy = comparedPose.getY() - headingController.getPosY();
//
//            return Math.sqrt(dx * dx + dy * dy);
//        }

        public void resetPinpointNSetPose(double X,double Y){
            double deltaHeading = headingController.getCurrentHeading();
            headingController.resetPinpoint();
            headingController.setPoseX(X);
            headingController.setPoseY(Y);
            headingController.setHeading(deltaHeading);

        }


        private void driveWithMotorPowers(double frontLeft, double frontRight,
                                          double backLeft, double backRight) {
            Lfront.setPower(frontLeft);
            Rfront.setPower(frontRight);
            Lback.setPower(backLeft);
            Rback.setPower(backRight);
        }

        public void showTelemetry(Telemetry telemetry){

            headingController.showTelemetry(telemetry);
        }
    }
