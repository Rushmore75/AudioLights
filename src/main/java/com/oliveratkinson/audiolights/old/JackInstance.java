/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oliveratkinson.audiolights.old;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jaudiolibs.audioservers.AudioClient;
import org.jaudiolibs.audioservers.AudioServerProvider;
import org.jaudiolibs.jnajack.*;
import org.jtransforms.fft.FloatFFT_1D;
import pl.edu.icm.jlargearrays.FloatLargeArray;

/**
 *
 * @author oliver
 */
@Deprecated
public class JackInstance {

    private Jack jack = null;
    private static JackInstance audioClient = null;

    private JackInstance() {
        try {
            this.jack = Jack.getInstance();
        } catch (JackException ex) {
            Logger.getLogger(JackInstance.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Create or get AudioClient instances
     *
     * @return AudioClient - instance
     */
    public static JackInstance getOrCreate() {
        if (audioClient == null) {
            // constuctor
            audioClient = new JackInstance();
        }

        return audioClient;
    }

    public Jack getJack() {
        return jack;
    }
    
    public ClientPortBundle openClient() {
        FloatFFT_1D fft = new FloatFFT_1D(512);
        try {
            EnumSet<JackOptions> clientOptions = EnumSet.of(JackOptions.JackUseExactName);
            // all the status options are failures?
            EnumSet<JackStatus> clientStatus = null;
            // open client
            JackClient client = getJack().openClient("Tim the wizard", clientOptions, clientStatus);
            JackPort port = client.registerPort("Porty McPortster", JackPortType.AUDIO, JackPortFlags.JackPortIsInput);

            client.setProcessCallback((jClient, nframes) -> {
                FloatBuffer buffer = port.getFloatBuffer();
                System.out.println(buffer.hasArray());
//                float[] e = port.getFloatBuffer().array();
//                fft.realForward();
//                System.out.println(e);
                return true;
            });

            client.activate();

            System.out.println("activated");

            return new ClientPortBundle(client, port);
        } catch (JackException ex) {
            Logger.getLogger(JackInstance.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
