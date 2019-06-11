/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

/**
 *
 * @author WIN64BIT
 */
public class clsMonHoc {
    String MAMONHOC;
    String TENMONHOC;
    String SOTINCHI;

    public clsMonHoc(String MAMONHOC, String TENMONHOC, String SOTINCHI) {
        this.MAMONHOC = MAMONHOC;
        this.TENMONHOC = TENMONHOC;
        this.SOTINCHI = SOTINCHI;
    }

    public String getMAMONHOC() {
        return MAMONHOC;
    }

    public void setMAMONHOC(String MAMONHOC) {
        this.MAMONHOC = MAMONHOC;
    }

    public String getTENMONHOC() {
        return TENMONHOC;
    }

    public void setTENMONHOC(String TENMONHOC) {
        this.TENMONHOC = TENMONHOC;
    }

    public String getSOTINCHI() {
        return SOTINCHI;
    }

    public void setSOTINCHI(String SOTINCHI) {
        this.SOTINCHI = SOTINCHI;
    }
    
}
