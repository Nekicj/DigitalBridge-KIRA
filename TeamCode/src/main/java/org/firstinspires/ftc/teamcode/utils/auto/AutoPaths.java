package org.firstinspires.ftc.teamcode.utils.auto;

import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;

import static com.pedropathing.api.Paths.*;

public final class AutoPaths {

    private static final Pose START = FieldPositions.start();
    private static final Pose SCORE = FieldPositions.score();
    private static final Pose PARK  = FieldPositions.park();

    private AutoPaths() {}

    public static Path startToScore() {
        return line(START, SCORE).linear(START, SCORE);
    }

    public static Path scoreToPark() {
        return line(SCORE, PARK).linear(SCORE, PARK);
    }
}