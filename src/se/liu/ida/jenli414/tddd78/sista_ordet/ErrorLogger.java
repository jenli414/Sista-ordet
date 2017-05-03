package se.liu.ida.jenli414.tddd78.sista_ordet;

import javax.swing.*;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**Logger that creates a FileHandler for itself and attempts to load
 * errorlogfile.log where it will log every exception that occurs in
 * the program.
 * This is a singleton because there will never be a reason for it to
 * change, it will be the same no matter if you want to for example
 * run several parallell games online in the future.**/
public final class ErrorLogger extends Logger {

    private static final ErrorLogger INSTANCE = new ErrorLogger();

    private ErrorLogger() {
	super("ErrorLogger", null);
	try {
	    FileHandler fileHandler =
		    new FileHandler("errorlog-thelastword.log");
	    SimpleFormatter simpleFormatter = new SimpleFormatter();
	    fileHandler.setFormatter(simpleFormatter);
	    setLevel(Level.ALL);
	    addHandler(fileHandler);
	} catch (IOException e) {
	    e.printStackTrace();
	    JOptionPane.showMessageDialog(null,
	      new JLabel("<html><center>Error!<br>IOException occurred in " +
	       "ErrorLogger while creating errorlog-thelastword.log." +
				"</center></html>"));
	    System.exit(1);
	}
    }

    public static ErrorLogger getInstance(){
        return INSTANCE;
    }
}
