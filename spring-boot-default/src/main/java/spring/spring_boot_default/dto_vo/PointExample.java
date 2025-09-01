package spring.spring_boot_default.dto_vo;

public class PointExample {
    public static void main(String[] args) {
        Point p1 = new Point(0,0);
        Point p2 = new Point(3,4);
        System.out.println("p1 = "+p1);
        System.out.println("p2 = "+p2);
        System.out.println("두,, 점...과의... 거리...는..? "+p1.distanceTo(p2));

        Point p3 = new Point(3,4);
        System.out.println("p1(0,0) vs p2(3,4) = "+p1.equals(p2));
        System.out.println("p2(3,4) vs p2(3,4) = "+p1.equals(p3));

        System.out.println(p1.hashCode());
        System.out.println(p2.hashCode());
        System.out.println(p3.hashCode());
    }
}
