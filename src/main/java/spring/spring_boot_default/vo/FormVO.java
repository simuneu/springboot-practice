package spring.spring_boot_default.vo;

import lombok.Getter;

import java.util.List;

@Getter
public class FormVO {
    private String name;
    private String gender;
    private int year;
    private int month;
    private int day;
    private List<String> interest;
}
