package advent2025.day9;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.util.List;

public class Part1J {

    public static void main(String[] args) {
        GeneralPath path = new GeneralPath();
        List<Point> points = lines.stream().map(l -> {
            var p = l.split(",");
            var x = Integer.parseInt(p[0]);
            var y = Integer.parseInt(p[1]);
            if (path.getCurrentPoint() == null) {
                path.moveTo(x, y);
            } else path.lineTo(x, y);
            return new Point(x, y);
        }).toList();

        Area area = new Area(path);
        Long max =  Long.MIN_VALUE;
        for (int i = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {
                Integer width = Math.abs(points.get(i).x - points.get(j).x) + 1;
                Integer height = Math.abs(points.get(i).y - points.get(j).y) + 1;
                Long newArea = width.longValue() * height.longValue();
                Rectangle rect = new Rectangle(Math.min(points.get(i).x, points.get(j).x), Math.min(points.get(i).y, points.get(j).y), width, height);
                if (area.contains(rect) && newArea > max)
                    max = newArea;
            }
        }
        System.out.println("max: " + max);//1665679194
    }

    static class Point {
        Integer x;
        Integer y;
        public Point(Integer x, Integer y) {this.x = x;this.y = y;}
    }

    static List<String> lines = """
            7,1
            11,1
            11,7
            9,7
            9,5
            2,5
            2,3
            7,3
            """.lines().map(String::trim).toList();
}
