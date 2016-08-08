/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.tedkwan.cpp.nar;

/**
 *
 * @author devils
 */
public class DuffingOscil {
    
    static {
        NarSystem.loadLibrary();
    }

    /**
     * Pars has 5 fields:
     * 
     * gamma - index 0.
     * h - index 1.
     * tf - index 2.
     * x0 - index 3.
     * y0 - index 4.
     * 
     * 
     * @param pars
     * @return 
     */
    public native double[] rk4(double[] pars);
    
    public DuffingOscil(){
        
    }
    
}
