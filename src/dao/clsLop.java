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
public class clsLop {
    private String MALOP;
    private String MAKH;
    private String MAHDT;
    private String MAKHOA;
    private String TENLOP;

    public clsLop(String MALOP, String MAKH, String MAHDT, String MAKHOA, String TENLOP) {
        this.MALOP = MALOP;
        this.MAKH = MAKH;
        this.MAHDT = MAHDT;
        this.MAKHOA = MAKHOA;
        this.TENLOP = TENLOP;
    }

    public String getMALOP() {
        return MALOP;
    }

    public void setMALOP(String MALOP) {
        this.MALOP = MALOP;
    }

    public String getMAKH() {
        return MAKH;
    }

    public void setMAKH(String MAKH) {
        this.MAKH = MAKH;
    }

    public String getMAHDT() {
        return MAHDT;
    }

    public void setMAHDT(String MAHDT) {
        this.MAHDT = MAHDT;
    }

    public String getMAKHOA() {
        return MAKHOA;
    }

    public void setMAKHOA(String MAKHOA) {
        this.MAKHOA = MAKHOA;
    }

    public String getTENLOP() {
        return TENLOP;
    }

    public void setTENLOP(String TENLOP) {
        this.TENLOP = TENLOP;
    }

}
