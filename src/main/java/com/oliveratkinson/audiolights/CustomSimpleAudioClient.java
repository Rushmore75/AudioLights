/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oliveratkinson.audiolights;

import java.nio.FloatBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jaudiolibs.jnajack.JackException;
import org.jaudiolibs.jnajack.util.SimpleAudioClient;

/**
 *
 * @author oliver
 */
public class CustomSimpleAudioClient implements org.jaudiolibs.jnajack.util.SimpleAudioClient.Processor {

    public static SimpleAudioClient createNew() {
        try {
            // this is a deprecated method but it works soooo..... :shrug:
            SimpleAudioClient client = SimpleAudioClient.create(
                    "test tube child",                          // name
                    new String[]{"input-L", "input-R"},         // inputs
                    null,                                       // output
//                    new String[]{"output-L", "output-R"},    
                    true,                                       // autoconnect
                    true,                                       // startServer
                    new CustomSimpleAudioClient());             // processor

            client.activate();
            return client;

        } catch (JackException ex) {
            Logger.getLogger(CustomSimpleAudioClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void setup(float sampleRate, int bufferSize) {

    }

    @Override
    public void process(FloatBuffer[] inputs, FloatBuffer[] outputs) {

        // un-comment if you want to go deaf
        // screeches
//        outputs[0].put(inputs[0]);
//        outputs[1].put(inputs[1]);
    }

    @Override
    public void shutdown() {

    }
}
