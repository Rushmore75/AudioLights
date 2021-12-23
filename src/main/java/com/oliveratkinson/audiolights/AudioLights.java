/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oliveratkinson.audiolights;


/**
 *
 * @author oliver
 */
public class AudioLights {
    public static ClientPortBundle bundle = null;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*
        need to do testing with this, 1D, 2D, or 3D?
        
        */
//        org.jtransforms.fft.FloatFFT_1D fft = new FloatFFT_1D(512);
                
        JackInstance jack = JackInstance.getOrCreate();
        bundle = jack.openClient();
        
        System.out.println(bundle.getClient().getName());
        
        
        while (true) {} 
    }
}
