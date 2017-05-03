package se.liu.ida.jenli414.tddd78.sista_ordet.gamepanel;

import se.liu.ida.jenli414.tddd78.sista_ordet.ErrorLogger;
import javax.swing.*;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/** Utiliy class used by Game to check if the user word input might be valid by
 * checking if the word is in the database corresponding to the current
 * category.*/
public final class DatabaseConfirmationTool {

    private static final Logger ERROR_LOGGER = ErrorLogger.getInstance();
    /*Static String COLUMN_NAME exists because all column names in the
    worddatabases is "NAME". This can be changed if another database is
    added that doesn't comply to this.*/
    private static final String COLUMN_NAME = "NAME";
    private final ClassLoader cl = this.getClass().getClassLoader();

    public boolean confirmWordInDatabase(final String categoryDisplayName,
					 final String table, final String word)
    {
	StringBuilder queryStatementBuilder = new StringBuilder();
	queryStatementBuilder.append("SELECT * FROM ");
	queryStatementBuilder.append(table);
	queryStatementBuilder.append(" WHERE ");
	queryStatementBuilder.append(COLUMN_NAME);
	queryStatementBuilder.append(" LIKE ");
	queryStatementBuilder.append("\"");
	queryStatementBuilder.append(word);
	queryStatementBuilder.append("\"");
	queryStatementBuilder.append(";");

	String dbPath = "worddatabases/" + categoryDisplayName + ".db";
	if (cl.getResource(dbPath) == null) {
	    FileNotFoundException e = new FileNotFoundException(
					    "Couldn't find file: " + dbPath);
	    e.printStackTrace();
	    ERROR_LOGGER.log(Level.SEVERE, e.toString(), e);
	    JOptionPane.showMessageDialog(null, new JLabel(
		    "<html><center>Error!<br>Couldn't find file: " + dbPath +
		    					"</center></html>"));
	    System.exit(1);
	} else {
	    try {
		/*Datasource is another tool that has replaced DriverManager,
		but as of now we have no knowledge of JNDI name which makes it
		hard to use. getConnection works fine for this local database
		where we only need to know the local database name and not
		host, ports etc.*/
		try (Connection connection = DriverManager.getConnection(
				"jdbc:sqlite::resource:" + dbPath)) {
		    /*Connection.prepareStatement is called with non-constant
		    argument which is a potential security problem but in our
		    case it's ok since you can't do things like DROP TABLE etc.
		    (User input isn't executed directly from wordInput)*/
		    try (PreparedStatement preparedStatement = connection.
			prepareStatement(queryStatementBuilder.toString())) {
			try (ResultSet rs = preparedStatement.executeQuery()) {
			    return rs.next();
			}
		    }
		}
	    } catch (SQLException e) {
		e.printStackTrace();
		ERROR_LOGGER.log(Level.SEVERE, e.toString(), e);
		JOptionPane.showMessageDialog(null, new JLabel(
			"<html><center>Error!<br>SQLException occurred " +
			"in DatabaseConfirmationTool.</center></html>"));
		System.exit(1);
	    }
	}
	return false;
    }
}
