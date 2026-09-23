package org.firstinspires.ftc.teamcode.utils;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;



public class FieldPositions {

    private static final PoseFactory p = PoseFactory.degrees();

    private static final Pose START_RED = p.of(24, 24, 0);
    private static final Pose SCORE_RED = p.of(48, 24, 90);
    private static final Pose PARK_RED  = p.of(72, 48, 90);
    private static final Pose FLOWER_RED= p.of(72,72,90);

    public static Pose start() { return mirror(START_RED); }
    public static Pose score() { return mirror(SCORE_RED); }
    public static Pose park()  { return mirror(PARK_RED); }
    public static Pose flower() {return mirror(FLOWER_RED);}

    private static Pose mirror(Pose bluePose) {
        double headingDeg = Math.toDegrees(bluePose.heading());
        return p.of(
            Alliance.mirrorX(bluePose.x()),
            Alliance.mirrorY(bluePose.y()),
            Alliance.mirrorHeadingDeg(headingDeg)
        );
    }
}
