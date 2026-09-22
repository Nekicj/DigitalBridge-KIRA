package org.firstinspires.ftc.teamcode.bigDih.dihs;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;



public class FieldPositions {

    private static final PoseFactory p = PoseFactory.degrees();

    private static final Pose START_BLUE = p.of(24, 24, 0);
    private static final Pose SCORE_BLUE = p.of(48, 48, 90);
    private static final Pose PARK_BLUE  = p.of(72, 48, 90);

    public static Pose start() { return mirror(START_BLUE); }
    public static Pose score() { return mirror(SCORE_BLUE); }
    public static Pose park()  { return mirror(PARK_BLUE); }

    private static Pose mirror(Pose bluePose) {
        double headingDeg = Math.toDegrees(bluePose.heading());
        return p.of(
            Alliance.mirrorX(bluePose.x()),
            Alliance.mirrorY(bluePose.y()),
            Alliance.mirrorHeadingDeg(headingDeg)
        );
    }
}
