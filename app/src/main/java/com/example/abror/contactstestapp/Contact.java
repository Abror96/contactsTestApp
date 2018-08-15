package com.example.abror.contactstestapp;

public class Contact {

    private String fullname;
    private String address;
    private String email;
    private String cellphone;
    private String phone;
    private String added_date;

    public Contact() {

    }

    public Contact(String fullname, String address, String email,
                   String cellphone, String phone, String added_date) {
        this.fullname = fullname;
        this.address = address;
        this.email = email;
        this.cellphone = cellphone;
        this.phone = phone;
        this.added_date = added_date;
    }

    public String getFullname() {
        return fullname;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getCellphone() {
        return cellphone;
    }

    public String getPhone() {
        return phone;
    }

    public String getAdded_date() {
        return added_date;
    }
}
