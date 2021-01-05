package com.example.admin.nienluan3.Model;

public class HoaDonChiTiet {
    public int maHoaDonChiTiet;
    public String maHoaDon;
    public String maSach;
    public int soLuongMua;
    public String thanhTien;

    public HoaDonChiTiet() {
    }

    public HoaDonChiTiet(String maHoaDon, String maSach, int soLuongMua, String thanhTien) {
        this.maHoaDon = maHoaDon;
        this.maSach = maSach;
        this.soLuongMua = soLuongMua;
        this.thanhTien = thanhTien;
    }

    public HoaDonChiTiet(int maHoaDonChiTiet, String maHoaDon, String maSach, int soLuongMua, String thanhTien) {

        this.maHoaDonChiTiet = maHoaDonChiTiet;
        this.maHoaDon = maHoaDon;
        this.maSach = maSach;
        this.soLuongMua = soLuongMua;
        this.thanhTien = thanhTien;
    }
}
