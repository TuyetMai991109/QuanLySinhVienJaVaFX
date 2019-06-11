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
public class clsHeDT {
    private String MAHEDT;
    private String TENHEDT;

    public clsHeDT(String MAHEDT, String TENHEDT) {
        this.MAHEDT = MAHEDT;
        this.TENHEDT = TENHEDT;
    }

    public String getMAHEDT() {
        return MAHEDT;
    }

    public void setMAHEDT(String MAHEDT) {
        this.MAHEDT = MAHEDT;
    }

    public String getTENHEDT() {
        return TENHEDT;
    }

    public void setTENHEDT(String TENHEDT) {
        this.TENHEDT = TENHEDT;
    }
    
}
