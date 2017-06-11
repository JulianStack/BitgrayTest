package test.julian.bitgray.Models;

import java.util.ArrayList;

/**
 * Created by JulianStack on 09/06/2017.
 */

public class User {
    int Id;
    String Name;
    String Username;
    String Email;
    UserAddress Address;
    String Phone;
    String Website;
    UserCompany Company;

    public User(int id, String name, String username, String email, UserAddress address, String phone, String website, UserCompany company) {
        Id = id;
        Name = name;
        Username = username;
        Email = email;
        Address = address;
        Phone = phone;
        Website = website;
        Company = company;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public UserAddress getAddress() {
        return Address;
    }

    public void setAddress(UserAddress address) {
        Address = address;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getWebsite() {
        return Website;
    }

    public void setWebsite(String website) {
        Website = website;
    }

    public UserCompany getCompany() {
        return Company;
    }

    public void setCompany(UserCompany company) {
        Company = company;
    }
}
