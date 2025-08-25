package spring.spring_boot_default.dto_vo;

public class BookEx {
    public static void main(String[] args) {
        BookDTO b1 = new BookDTO();
        b1.setTitle("onepiece");
        b1.setAuthor("oda");
        b1.setPrice(5000);

        BookDTO b2 = new BookDTO();
        b2.setTitle("naruto");
        b2.setAuthor("masashi");
        b2.setPrice(5000);

        System.out.println(b1.toString());
        System.out.println(b2.toString());
    }
}
