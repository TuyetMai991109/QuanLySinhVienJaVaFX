/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Date;

/**
 *
 * @author WIN64BIT
 */
public class clsSinhVien {
   String MASV;
   String TENSV;                
   String GIOITINH;           
   Date NGAYSINH;          
   String QUEQUAN;
   String TENLOP;

    public clsSinhVien(String MASV, String TENSV, String GIOITINH, Date NGAYSINH, String QUEQUAN, String TENLOP) {
        this.MASV = MASV;
        this.TENSV = TENSV;
        this.GIOITINH = GIOITINH;
        this.NGAYSINH = NGAYSINH;
        this.QUEQUAN = QUEQUAN;
        this.TENLOP = TENLOP;
    }

    public String getMASV() {
        return MASV;
    }

    public void setMASV(String MASV) {
        this.MASV = MASV;
    }

    public String getTENSV() {
        return TENSV;
    }

    public void setTENSV(String TENSV) {
        this.TENSV = TENSV;
    }

    public String getGIOITINH() {
        return GIOITINH;
    }

    public void setGIOITINH(String GIOITINH) {
        this.GIOITINH = GIOITINH;
    }

    public Date getNGAYSINH() {
        return NGAYSINH;
    }

    public void setNGAYSINH(Date NGAYSINH) {
        this.NGAYSINH = NGAYSINH;
    }

    public String getQUEQUAN() {
        return QUEQUAN;
    }

    public void setQUEQUAN(String QUEQUAN) {
        this.QUEQUAN = QUEQUAN;
    }

    public String getTENLOP() {
        return TENLOP;
    }

    public void setTENLOP(String TENLOP) {
        this.TENLOP = TENLOP;
    }
   
}
