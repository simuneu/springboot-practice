package spring.spring_boot_default.dto_vo;

import java.util.Objects;

public final class AddressVO {
    private String city;
    private String street;
    private int zipcode;

    public AddressVO(String city, String street, int zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public int getZipcode() {
        return zipcode;
    }

    @Override
    public String toString() {
        return "AddressVO{" +
                "city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", (zipcode)=" + zipcode +
                '}';
    }

    public boolean equals(Object o){
        if(this ==o ) return true;
        if(o==null ||getClass() != o.getClass()) return false;
        AddressVO a = (AddressVO) o;

        return  Objects.equals(city, a.city) &&
                Objects.equals(street, a.street) &&
                zipcode == a.zipcode;
    }

    @Override
    public int hashCode(){
        return Objects.hash(city, street, zipcode);
    }
}
