/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

/**
 *
 * @author ASUS
 */
public class clsKhoa {
    private String MAKHOA;
    private  String TENKHOA;
    private  String DIACHI;
    private  String DIENTHOAI;

    public String getMAKHOA() {
        return MAKHOA;
    }

    public void setMAKHOA(String MAKHOA) {
        this.MAKHOA = MAKHOA;
    }

    public String getTENKHOA() {
        return TENKHOA;
    }

    public void setTENKHOA(String TENKHOA) {
        this.TENKHOA = TENKHOA;
    }

    public String getDIACHI() {
        return DIACHI;
    }

    public void setDIACHI(String DIACHI) {
        this.DIACHI = DIACHI;
    }

    public String getDIENTHOAI() {
        return DIENTHOAI;
    }

    public void setDIENTHOAI(String DIENTHOAI) {
        this.DIENTHOAI = DIENTHOAI;
    }

    public clsKhoa(String MAKHOA, String TENKHOA, String DIACHI, String DIENTHOAI) {
        this.MAKHOA = MAKHOA;
        this.TENKHOA = TENKHOA;
        this.DIACHI = DIACHI;
        this.DIENTHOAI = DIENTHOAI;
    }
}
