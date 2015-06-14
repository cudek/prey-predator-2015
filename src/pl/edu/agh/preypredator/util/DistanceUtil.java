package pl.edu.agh.preypredator.util;

public class DistanceUtil {

    public static int calculateDistance(Point point1, Point point2) {
        int distance = Math.abs(point1.getX() - point2.getX());
        distance += Math.abs(point1.getY() - point2.getY());
        return distance;
    }
}
