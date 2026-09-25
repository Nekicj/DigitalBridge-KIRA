package org.firstinspires.ftc.teamcode.constants;

//import com.bylazar.configurables.annotations.Configurable;

import org.firstinspires.ftc.teamcode.robot.utils.Alliance;

//@Configurable
public class RobotConstants {
    public static double shooterSpeedBorder = 1430;
    public static double idleVelocity = 600;
    public static double intakeShootingMultiplier = 0.6;
    public static double intakePower       = -1.0;
    public static double outtakePower      = 1.0;
    public static double shootIntakeCoeff  = 0.6;
    public static double stopperOpenTimeMs = 200;
    public static double turnCoeff         = 0.75;
    public static double rumblePower       = 0.3;

    public enum AprilTagGroup {
        RED_RIGHT (new int[]{30, 31, 32, 33}),
        RED_LEFT  (new int[]{34, 35, 36, 37}),
        BLUE_LEFT(new int[]{38, 39, 40, 41}),
        BLUE_RIGHT (new int[]{42, 43, 44, 45});

        private final int[] ids;

        AprilTagGroup(int[] ids) { this.ids = ids; }

        public int[] getIds() { return ids; }

        public boolean contains(int id) {
            for (int tagId : ids) if (tagId == id) return true;
            return false;
        }

        public static AprilTagGroup[] own(boolean isRight) {
            return Alliance.get() == Alliance.RED
                    ? new AprilTagGroup[]{RED_RIGHT, RED_LEFT}
                    : new AprilTagGroup[]{BLUE_RIGHT, BLUE_LEFT};
        }
    }
}
