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
public class clsKhoaHoc {
    private String MAKHOAHOC;
    private String TENKHOAHOC;

    public String getMAKHOAHOC() {
        return MAKHOAHOC;
    }

    public void setMAKHOAHOC(String MAKHOAHOC) {
        this.MAKHOAHOC = MAKHOAHOC;
    }

    public String getTENKHOAHOC() {
        return TENKHOAHOC;
    }

    public void setTENKHOAHOC(String TENKHOAHOC) {
        this.TENKHOAHOC = TENKHOAHOC;
    }

    public clsKhoaHoc(String MAKHOAHOC, String TENKHOAHOC) {
        this.MAKHOAHOC = MAKHOAHOC;
        this.TENKHOAHOC = TENKHOAHOC;
    }

}
