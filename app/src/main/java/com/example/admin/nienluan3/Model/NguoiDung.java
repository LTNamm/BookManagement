package com.example.admin.nienluan3.Model;

public class NguoiDung {
    public String userName;
    public String passWord;
    public String phone;
    public String hoVaTen;

/*
    Contructor => Hàm dựng
 */

    //hàm dựng có 3 tham số
    public NguoiDung(String userName, String phone, String hoVaTen) {           //tham số truyền vào của mỗi hàm dựng
        this.userName = userName;
        this.phone = phone;
        this.hoVaTen = hoVaTen;
    }

    //hàm dựng có 4 tham số
    public NguoiDung(String userName, String passWord, String phone, String hoVaTen) {
        this.userName = userName;
        this.passWord = passWord;
        this.phone = phone;
        this.hoVaTen = hoVaTen;
    }

    //hàm dựng có 2 tham số
    public NguoiDung(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
    }

}
