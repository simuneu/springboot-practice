package spring.spring_boot_default.dto_vo;

public class AddressMain {
    public static void main(String[] args) {
        AddressVO a1 = new AddressVO("seoul", "gangnam", 06236);
        AddressVO a2 = new AddressVO("seoul", "gangnam", 06236);
        AddressVO a3 = new AddressVO("seoul", "mapo", 06200);

        System.out.println(a1);
        System.out.println(a2);
        System.out.println(a1.equals(a2));
        System.out.println(a1.equals(a2) ? "같은 주소입니다." : "다른 주소입니다.");

        System.out.println(a2);
        System.out.println(a3);
        System.out.println(a2.equals(a3));
        System.out.println(a2.equals(a3) ? "같은 주소입니다." : "다른 주소입니다.");

        System.out.println(a1);
        System.out.println(a3);
        System.out.println(a1.equals(a3));
        System.out.println(a1.equals(a3) ? "같은 주소입니다." : "다른 주소입니다.");

    }
}
