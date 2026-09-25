package org.firstinspires.ftc.teamcode.robot.utils.auto;

import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;

import static com.pedropathing.api.Paths.*;

public final class AutoPaths {

    private static final Pose START = FieldPositions.start();
    private static final Pose SCORE_RIGHT = FieldPositions.score_right();
    private static final Pose SCORE_LEFT = FieldPositions.score_right();
    private static final Pose HUMAN = FieldPositions.human();
    private static final Pose HUMAN_TAKE = FieldPositions.human_take();
    private static final Pose FLOWER_BOTTOM = FieldPositions.flower_bottom();
    private static final Pose FLOWER_LEFT = FieldPositions.flower_left();
    private static final Pose PARK  = FieldPositions.park();
    private static final Pose SCAN = FieldPositions.scan();
    private static final Pose LEFT_BOTTOM = FieldPositions.left_bottom();


    private AutoPaths() {}

    public static Path startToScore() {return line(START, SCORE_RIGHT).linear(START, SCORE_RIGHT);}

    public static Path scoreToPark(boolean right) {
        if(right){
            return line(SCORE_RIGHT, PARK).linear(SCORE_RIGHT, PARK);
        }else{
            return line(SCORE_LEFT, PARK).linear(SCORE_LEFT, PARK);}
    }

    public static Path scoreToTakeHuman(){return line(SCORE_RIGHT,HUMAN_TAKE).linear(SCORE_RIGHT,HUMAN_TAKE);}

    public static Path scoreToHuman(){return line(HUMAN_TAKE,HUMAN).linear(HUMAN_TAKE,HUMAN);}

    public static Path humanToScore(){
        return line(HUMAN,SCORE_RIGHT).linear(HUMAN,SCORE_RIGHT);
    }

    public static Path humanToScan(){return line(HUMAN,SCAN).linear(HUMAN,SCAN);}

    public static Path scoreRightToFlowerBottom() { return  line(SCORE_RIGHT,FLOWER_BOTTOM).linear(SCORE_RIGHT,FLOWER_BOTTOM);}

    public static Path flowerBottomToScoreLeft(){return path(
            line(FLOWER_BOTTOM,LEFT_BOTTOM).linear(FLOWER_BOTTOM,LEFT_BOTTOM),
            line(LEFT_BOTTOM,SCORE_LEFT).linear(LEFT_BOTTOM,SCORE_LEFT));
    }

    public static Path scanToScore(boolean right){
        if(right){
            return line(SCAN,SCORE_RIGHT).linear(SCAN,SCORE_RIGHT);
        }else{
            return line(SCAN,SCORE_LEFT).linear(SCAN,SCORE_LEFT);
        }
    }
}