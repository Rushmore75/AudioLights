/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oliveratkinson.audiolights;

import org.jaudiolibs.jnajack.JackClient;
import org.jaudiolibs.jnajack.JackProcessCallback;

/**
 *
 * @author oliver
 */
public class ProcessCallback implements JackProcessCallback{

    @Override
    public boolean process(JackClient client, int nframes) {
        System.out.print(AudioLights.bundle.getPort().getFloatBuffer());
        System.out.println(" ~ " + nframes);
        
        return true;
    }
    
}
