/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oliveratkinson.audiolights;

import com.oliveratkinson.audiolights.old.ClientPortBundle;
import com.oliveratkinson.audiolights.networking.Client;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jtransforms.fft.FloatFFT_1D;

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
        try {
            SoundClient.startAudioClient();
        } catch (Exception ex) { Logger.getLogger(AudioLights.class.getName()).log(Level.SEVERE, null, ex); }
        while (true) {            
            
        }
        //        JackInstance jack = JackInstance.getOrCreate();
        //        bundle = jack.openClient();
        //        System.out.println(bundle.getClient().getName());
        
//         -- send wifi credentials to esp8266 ---------------------------------
//        try {
//
//            Client client = new Client("192.168.4.1", 8080);
//            
//            client.callAndResponse();
//            
//            
//        } catch (IOException ex) {
//            Logger.getLogger(AudioLights.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
}
