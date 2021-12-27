/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oliveratkinson.audiolights;

import java.nio.FloatBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jaudiolibs.audioservers.AudioClient;
import org.jaudiolibs.audioservers.AudioConfiguration;
import org.jaudiolibs.audioservers.AudioServer;
import org.jaudiolibs.audioservers.AudioServerProvider;
import org.jaudiolibs.audioservers.ext.ClientID;
import org.jaudiolibs.audioservers.ext.Connections;
import org.jtransforms.fft.FloatFFT_1D;

/**
 *
 * @author olive
 */
public class SoundClient implements AudioClient {

    private float sampleRate;
    private int maxBufferSize;
    private static FloatFFT_1D fft;

    public static void startAudioClient() throws Exception {
        String lib = "JACK";

        AudioServerProvider provider = null;
        for (AudioServerProvider p : ServiceLoader.load(AudioServerProvider.class)) {
            if (lib.equals(p.getLibraryName())) {
                provider = p;
                break;
            }
        }
        if (provider == null) {
            throw new NullPointerException("No audio server found");
        }

        AudioConfiguration config = new AudioConfiguration(
                44100.0f, // sample rate -- jack will ignore
                1, // input channels
                0, // output channels
                512, // buffer size -- jack will ignore
                new ClientID("Audio Lights"),
                Connections.INPUT);

        final AudioServer server = provider.createServer(config, new SoundClient());

        Thread runner = new Thread(() -> {
            try {
                server.run();
            } catch (Exception ex) {
                Logger.getLogger(SoundClient.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        runner.setPriority(Thread.MAX_PRIORITY);

        runner.start();

    }

    public static void convertToFreq(FloatBuffer input) {
        System.out.println(Arrays.toString(input.array()));
        fft.realForward(input.array());
        System.out.println(Arrays.toString(input.array()));
    }

    @Override
    public void configure(AudioConfiguration context) throws Exception {
        // figure out what goes on here
        sampleRate = context.getSampleRate();
        maxBufferSize = context.getMaxBufferSize();
        fft = new FloatFFT_1D(maxBufferSize);
    }

    @Override
    public boolean process(long time, List<FloatBuffer> inputs, List<FloatBuffer> outputs, int nframes) {

        // putting stuff here breaks it
        convertToFreq(inputs.get(0));

        return true;
    }

    @Override
    public void shutdown() {
        // dont know what to put here, prob an error or something
    }
}
