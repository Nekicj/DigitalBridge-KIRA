package org.firstinspires.ftc.teamcode.robot.utils.auto;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;

import org.firstinspires.ftc.teamcode.robot.utils.Alliance;


public class FieldPositions {

    private static final PoseFactory p = PoseFactory.degrees();

    private static final Pose START = p.of(-24, 24, 0);
    private static final Pose SCORE_RIGHT = p.of(64, 90, 90);
    private static final Pose SCORE_LEFT = p.of(64, -90, 90);

    private static final Pose FLOWER_BOTTOM = p.of(72,72,90);
    private static final Pose FLOWER_LEFT = p.of(24,-70,0);

    private static final Pose HUMAN = p.of(-24,70,0);
    private static final Pose HUMAN_TAKE = p.of(-24,63,0);

    private static final Pose PARK_RED  = p.of( -24, -50, 90);

    private static final Pose SCAN = p.of(20,20,0);

    private static final Pose LEFT_BOTTOM = p.of(-24,-70,0);

    public static Pose scan(){return SCAN;}
    public static Pose score_left(){return SCORE_LEFT;}
    public static Pose flower_left(){return FLOWER_LEFT;}
    public static Pose start() { return START; }
    public static Pose score_right() { return SCORE_RIGHT; }
    public static Pose park()  { return PARK_RED; }
    public static Pose flower_bottom() {return FLOWER_BOTTOM;}
    public static Pose human(){return HUMAN;}
    public static Pose human_take(){return HUMAN_TAKE;}
    public static Pose left_bottom(){return LEFT_BOTTOM;}

    private static Pose mirror(Pose bluePose) {
        double headingDeg = Math.toDegrees(bluePose.heading());
        return p.of(
            Alliance.mirrorX(bluePose.x()),
            Alliance.mirrorY(bluePose.y()),
            Alliance.mirrorHeadingDeg(headingDeg)
        );
    }
}
