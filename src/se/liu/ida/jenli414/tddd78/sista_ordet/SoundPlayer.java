package se.liu.ida.jenli414.tddd78.sista_ordet;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.util.Set;
import java.util.logging.Level;

/**Utility class that implements Runnable, with methods that will play certain
 * audiofiles for certain durations. FilePath and Duration is given by a Sound
 * Enum to the constructor. The methods will create a new Thread with a new
 * SoundPlayer object and call .start() on that Thread, invoking the run()
 * method in SoundPlayer that will play the sound with Clip while .sleep()
 * is called on the Thread for the given duration. If we want to stop a certain
 * sound, that can be done using the
 * interruptThreadsPlaying(Sound[] soundsToInterrupt) method.**/
public final class SoundPlayer implements Runnable {

    private static final ErrorLogger ERROR_LOGGER = ErrorLogger.getInstance();
    private final String filePath;
    private final int duration;
    private final ClassLoader cl = getClass().getClassLoader();

    private SoundPlayer(final Sound sound) {
	this.filePath = sound.getFilePath();
	this.duration = sound.getDuration();
    }

    @Override
    public void run() {
	Thread.currentThread().setName(filePath);
	URL soundURL = cl.getResource(filePath);
	if (soundURL == null) {
	    FileNotFoundException e =
		new FileNotFoundException("Couldn't find file: " + filePath);
	    e.printStackTrace();
	    ERROR_LOGGER.log(Level.SEVERE, e.toString(), e);
	    JOptionPane.showMessageDialog(
		null, new JLabel("<html><center>Error!" +
		 "<br>Couldn't find file: " + filePath + "</center></html>"));
	    System.exit(1);
	}

	int soundDurationMilliseconds = duration * 1000;
	try (Clip clip = AudioSystem.getClip()) {
	    clip.open(AudioSystem.getAudioInputStream(soundURL));
	    if (duration < 0) {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		Thread.sleep(Long.MAX_VALUE);
	    } else {
		clip.start();
		Thread.sleep(soundDurationMilliseconds);
	    }
	} catch (InterruptedException e) {
	    /*An interrupted thread most likely means that .interrupt() was
	    called on the thread, which is how we stop a sounds if we need to.
	    e is unused because we don't need to do anything in this case.
	    Doesn't get logged because it's not considered an error in our
	    case.*/
	} catch (IOException | LineUnavailableException |
			UnsupportedAudioFileException e) {
	    ERROR_LOGGER.log(Level.SEVERE, e.toString(), e);
	    e.printStackTrace();
	    JOptionPane.showMessageDialog(null,
		new JLabel("<html><center>Error!<br>IOException/" +
		    "LineUnavaliableException/UnsupportedAudioFileException" +
			   "<br>occurred in SoundPlayer.</center></html>"));
	}
    }

    private static void interruptThreadsPlaying(Sound[] soundsToInterrupt) {
	Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
	Thread[] threadArray = threadSet.toArray(new Thread[threadSet.size()]);
	for (Thread thread : threadArray) {
	    for (Sound sound : soundsToInterrupt) {
		if (thread.getName().equals(sound.getFilePath())) {
		    thread.interrupt();
		}
	    }
	}
    }

    public static void playStartGameSound() {
	Sound [] sounds = Sound.values();
	interruptThreadsPlaying(sounds);
	new Thread(new SoundPlayer(Sound.STARTGAME)).start();
    }

    public static void playTimerTickingSound() {
	Sound [] sounds = {Sound.TIMERTICKING};
	interruptThreadsPlaying(sounds);
	new Thread(new SoundPlayer(Sound.TIMERTICKING)).start();
    }

    public static void stopTimerTickingSound() {
	Sound [] sounds = {Sound.TIMERTICKING};
	interruptThreadsPlaying(sounds);
    }

    public static void playValidWordSound() {
	Sound [] sounds = {Sound.INVALIDWORD, Sound.VALIDWORD};
	interruptThreadsPlaying(sounds);
	new Thread(new SoundPlayer(Sound.VALIDWORD)).start();
    }

    public static void playInvalidWordSound() {
	Sound [] sounds = {Sound.VALIDWORD, Sound.INVALIDWORD};
	interruptThreadsPlaying(sounds);
	new Thread(new SoundPlayer(Sound.INVALIDWORD)).start();
    }

    public static void playTimesUpSound() {
	Sound [] sounds = {Sound.TIMERTICKING};
	interruptThreadsPlaying(sounds);
	new Thread(new SoundPlayer(Sound.TIMESUP)).start();
    }

    public static void playNewRoundSound() {
	new Thread(new SoundPlayer(Sound.NEWROUND)).start();
    }

    public static void stopAllSounds() {
	Sound[] sounds = Sound.values();
	interruptThreadsPlaying(sounds);
    }

}

