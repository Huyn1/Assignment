package com.example.assignment.Model;

public class HoatDOng {
    private String ngay;
    private int soBuoc;
    private int soCalo;
    private long thoiGian;

    public HoatDOng() {
        // Default constructor required for calls to DataSnapshot.getValue(HoatDOng.class)
    }

    public HoatDOng(String ngay, int soBuoc, int soCalo, long thoiGian) {
        this.ngay = ngay;
        this.soBuoc = soBuoc;
        this.soCalo = soCalo;
        this.thoiGian = thoiGian;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public int getSoBuoc() {
        return soBuoc;
    }

    public void setSoBuoc(int soBuoc) {
        this.soBuoc = soBuoc;
    }

    public int getSoCalo() {
        return soCalo;
    }

    public void setSoCalo(int soCalo) {
        this.soCalo = soCalo;
    }

    public long getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(long thoiGian) {
        this.thoiGian = thoiGian;
    }
}
