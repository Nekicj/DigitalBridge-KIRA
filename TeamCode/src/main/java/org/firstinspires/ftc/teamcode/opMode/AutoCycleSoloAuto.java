package org.firstinspires.ftc.teamcode.opMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import com.pedropathing.follower.Follower;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.util.ElapsedTime;

import static com.pedropathing.api.Paths.*;

import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.controllers.IntakeController;
import org.firstinspires.ftc.teamcode.controllers.ShooterController;
import org.firstinspires.ftc.teamcode.controllers.StopperController;
import org.firstinspires.ftc.teamcode.utils.FieldPositions;
import org.firstinspires.ftc.teamcode.utils.RobotConstants;

@Autonomous(name = "коч братан",group = "1")
public class AutoCycleSoloAuto extends OpMode {
    private ShooterController shooterController;
    private StopperController stopperController;
    private IntakeController intakeController;
    private int autoState = 0;
    private boolean isShooting = false;

    private ElapsedTime elapsedTime;

    private double shooterSpeed = RobotConstants.shooterSpeedBorder;

    private Follower follower;
    private final PoseFactory p = PoseFactory.degrees();

    private final Pose startPose = FieldPositions.start();
    private final Pose scorePose = FieldPositions.score();
    private final Pose flowerPose= FieldPositions.flower();
    private final Pose parkPose  = FieldPositions.park();

    private Path startToScore() {
        return line(startPose, scorePose).linear(startPose, scorePose);
    }
    private Path park() {return line(scorePose, parkPose).linear(scorePose, parkPose);}

    @Override
    public void init() {
        elapsedTime = new ElapsedTime();
        elapsedTime.reset();
        follower = Constants.create(hardwareMap);
        follower.setPose(startPose);

        follower.update();

        shooterController = new ShooterController();
        intakeController = new IntakeController();
        stopperController = new StopperController();

        shooterController.initialize(hardwareMap,"shooter_l","shooter_r");
        intakeController.initialize(hardwareMap,"intake1","intake2");
        stopperController.initialize(hardwareMap,"stopper",true);
    }

    private void pathUpdate(){
        switch (autoState){
            case 0:
                if(!follower.isBusy()){
                    follower.follow(startToScore());
                    setPathState(1);
                    elapsedTime.reset();
                }
                break;
            case 1:
                if(!follower.isBusy()){
                    elapsedTime.reset();
                    setPathState(2);
                }
                break;
            case 2:
                if(!follower.isBusy() && elapsedTime.milliseconds() > 500){
                    setPathState(3);
                }
                break;

        }
    }

    @Override
    public void loop() {
        follower.update();
        pathUpdate();
        conditions();
        showTelemetry();
    }
    @Override
    public  void init_loop(){
        follower.update();
        shooterController.update();
    }
    private void conditions(){
        if(isShooting){
            shooterController.setShooterVelocity(shooterSpeed);
        }else {
            shooterController.setShooterVelocity(600);
        }
    }
    private void showTelemetry(){
        telemetry.addData("X", follower.pose().x());
        telemetry.addData("Y", follower.pose().y());
        telemetry.addData("Heading", Math.toDegrees(follower.pose().heading()));
        telemetry.update();
    }
    private void setPathState(int pathState){
        autoState = pathState;
    }
}