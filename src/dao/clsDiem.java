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
public class clsDiem {
    private String STT;
    private float HOCKY;
    private float DIEMLAN1;
    private float DIEMLAN2;

    public float getHOCKY() {
        return HOCKY;
    }

    public void setHOCKY(float HOCKY) {
        this.HOCKY = HOCKY;
    }

    public float getDIEMLAN1() {
        return DIEMLAN1;
    }

    public void setDIEMLAN1(float DIEMLAN1) {
        this.DIEMLAN1 = DIEMLAN1;
    }

    public float getDIEMLAN2() {
        return DIEMLAN2;
    }

    public void setDIEMLAN2(float DIEMLAN2) {
        this.DIEMLAN2 = DIEMLAN2;
    }
    private String MASV;
    private String MAMH;

    public String getMAMH() {
        return MAMH;
    }

    public void setMAMH(String MAMH) {
        this.MAMH = MAMH;
    }

    public String getSTT() {
        return STT;
    }

    public void setSTT(String STT) {
        this.STT = STT;
    }


    public String getMASV() {
        return MASV;
    }

    public void setMASV(String MASV) {
        this.MASV = MASV;
    }

    public clsDiem(String STT, float HOCKY, float DIEMLAN1, float DIEMLAN2, String TENSV, String MAMH) {
        this.STT = STT;
        this.HOCKY = HOCKY;
        this.DIEMLAN1 = DIEMLAN1;
        this.DIEMLAN2 = DIEMLAN2;
        this.MASV = TENSV;
        this.MAMH = MAMH;
    }
}
