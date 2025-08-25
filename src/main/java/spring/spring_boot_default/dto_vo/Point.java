package spring.spring_boot_default.dto_vo;

import java.util.Objects;

//2D좌표를 표현하는 vo클래스
//final
//final class는 상속 불가능 > 불변성 보장
public final class Point {
    //final field; : 한번 초기화되면 변경 불가능
    private final int x;
    private final int y;



    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    //getter : vo는 setter가 없어 불변성 보장
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    //비즈니스 로직:두 점 사이의 거리를 계산
    public double distanceTo(Point other){
        int dx = this.x - other.x;
        int dy = this.y - other.y;

        return Math.sqrt(dx*dy+dx*dy);
    }

    //equals():객체가 동등성을 정의(두 객체가 논리적으로 같은지)
    @Override
    public boolean equals(Object o){
        //현재 객체(this)와 비교대상 객체(매개변수 o)가 같은 참조를 가르킨다면 (==비교) true반환
        if(this ==o) return true;
        //비교대상 객체(매개변수 o)가 null이거나
        //현재 객체와 다른 클래스의 인스턴스라면 false를 반환
        if(o == null||getClass() != o.getClass()) return false;
        //비교대상 객체를(매개변수 o)를 Point타입으로 형변환
        Point point = (Point) o;
        
        //두 Point객체의 x,y값이 모두 같은지 여부 반환
        return x==point.x && y==point.y;
    }

    //equals()메서드로 같다고 판단된 두 객체는 같은 해시코드를 반환하도록 함
    @Override
    public int hashCode(){
        //Object 클래스의 hash메서드를 사용해서 x,y값을 기반으로 해시코드 작성
        //내부적으로 해시 알고리즘을 사용해 객체의 필드값이 결합한 해시코드
        //(같은 필드를 가지면 같은 해시코드를 리턴하도록)
        return Objects.hash(x,y);
    }


    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
