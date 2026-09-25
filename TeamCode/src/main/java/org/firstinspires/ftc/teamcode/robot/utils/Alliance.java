package org.firstinspires.ftc.teamcode.robot.utils;


public enum Alliance {
    RED, BLUE;

    private static Alliance selected = BLUE;

    public static void set(Alliance alliance) { selected = alliance; }
    public static Alliance get() { return selected; }

    public static double mirrorX(double x) { return selected == BLUE ? x : -x; }
    public static double mirrorY(double y) { return selected == BLUE ? y : -y; }
    public static double mirrorHeadingDeg(double headingDeg) {
        return selected == BLUE ? headingDeg : headingDeg + 180;
    }
}
