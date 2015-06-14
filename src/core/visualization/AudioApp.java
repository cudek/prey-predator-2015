package core.visualization;

import java.applet.*;
import javax.swing.*;
import java.io.*;
import java.net.*;

/*dodaje dźwięk zjadania owcy, jeszcze bardziej hardkodowane niż Visualizer*/

public class AudioApp extends JApplet {

    public AudioApp() {
        try {
            songPath = new URL("file:///C:/Users/Właściciel/Documents/"
                + "NetBeansProjects/Agents_prototype1/sounds/eating_sound.wav");
            song = Applet.newAudioClip(songPath); // Load the Sound
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private AudioClip song; // Sound player
    private URL songPath; // Sound path

    public void playSound() {
        song.loop(); // Play
    }

    public void stopSound() {
        song.stop(); // Stop
    }

    public void playSoundOnce() {
        song.play(); // Play only once
    }

}
