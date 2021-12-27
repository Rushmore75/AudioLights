/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oliveratkinson.audiolights.old;

import com.oliveratkinson.audiolights.AudioLights;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Control;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.TargetDataLine;

/**
 *
 * @author olive
 */
@Deprecated
public class JavaAudio {
        /*
    below this line is extra code I didn't want to throw out - although unused
     */
    public static Mixer getMainMixer() throws LineUnavailableException {

        // get all mixers
        Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
        Line selectedLine = null;
        Mixer selectedMixer = null;

        // loop through all mixers
        for (Mixer.Info currentMixer : mixerInfo) {

            Mixer mixer = AudioSystem.getMixer(currentMixer);
            Line.Info[] targetLineInfo = mixer.getTargetLineInfo();
            // "A target data line is a type of DataLine from which audio data can be read."

            // make sure that targetLines are supported
            if (targetLineInfo.length > 0) {

                // print info about the mixer and the target lines
                System.out.println(currentMixer.getName());
                System.out.print(targetLineInfo.length + " target line(s) >> ");
                System.out.println(Arrays.toString(targetLineInfo));

                // loop through all targetLines
                for (Line.Info info : targetLineInfo) {
//                    showLineInfo(info);
//                    System.out.println(info.toString());
                    Line line = mixer.getLine(info);

                    if (line.getLineInfo().toString().equalsIgnoreCase("Master target port")) {
                        // selectedLine will be the line of the case here ^^^

                        selectedLine = line;
                        selectedMixer = mixer;
                        System.out.println("~~ found master - returning it's line ~~");

                        spectateLine(selectedMixer, selectedLine);

                        break;
                    }
                }
                System.out.print("\n \n");
            }
        }
        return selectedMixer;
    }

    private static void spectateLine(Mixer mixer, Line line) throws LineUnavailableException {
        System.out.print("\n \n");

        line.open();
        /*
        opened
         */

        Control[] lineControls = line.getControls();
        System.out.print(lineControls.length + " >> ");
        for (Control ctrl : lineControls) { System.out.println(ctrl.toString()); }
        
        
        
        /*
        closed
         */
        line.close();
    }
    
    private static void showLineInfo(Line.Info lineInfo) {

        System.out.println(lineInfo.toString());

        if (lineInfo instanceof DataLine.Info) {
            DataLine.Info dli = (DataLine.Info) lineInfo;

            AudioFormat[] formats = dli.getFormats();
            for (AudioFormat format : formats) {
                System.out.println(format.toString());
            }

        }

    }

    public static TargetDataLine targetDatalineFromMixer(Mixer mixer) {

        // normally returning null
        TargetDataLine tdLine = null;

        Line.Info[] targetLineInfo = mixer.getTargetLineInfo();

        // "A target data line is a type of DataLine from which audio data can be read."
        if (targetLineInfo.length > 0) {

            try {
                Line line = mixer.getLine(targetLineInfo[0]);

                if (line instanceof TargetDataLine) {

                    tdLine = (TargetDataLine) mixer.getLine(targetLineInfo[0]);

                }
            } catch (LineUnavailableException ex) {
                Logger.getLogger(AudioLights.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return tdLine;
    }

    public static void listenToMicrophone(TargetDataLine tdLine) throws Exception {
        /*
        this whole bit isn't prob needed but i didnt wanna throw it out
         */
        int duration = 3;

        if (tdLine == null) {
            throw new Exception("no line found");
        }

        AudioFormat af = new AudioFormat(44100, 8, 1, false, false);
        tdLine.open(af);
        tdLine.start();

        System.out.println(tdLine.getLevel());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[(int) af.getSampleRate() * af.getFrameSize()];
        long end = System.currentTimeMillis() + 1000 * duration;
        int len;
        while (System.currentTimeMillis() < end && ((len = tdLine.read(buffer, 0, buffer.length)) != -1)) {
            System.out.println(Arrays.toString(buffer));
            baos.write(buffer, 0, len);
        }

        tdLine.stop();
        tdLine.close();
        baos.close();
    }

}
