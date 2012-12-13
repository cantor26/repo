import java.io.*;
import java.text.*;
import java.util.*;
import javax.swing.*;

public class LogFile
{
	private boolean	logFileOk;
	private PrintWriter logFile;
	
	public LogFile() 
	{
		try 
		{
			logFile = new PrintWriter(new FileWriter(System.getProperty("user.home") + "/.agriturismi-log", true), true); //$NON-NLS-1$ //$NON-NLS-2$
			logFileOk = true;
		}
		catch(IOException exception) 
		{
			JOptionPane.showMessageDialog(null, Messages.getString("LogFile.ErrorCreateFileLog")); //$NON-NLS-1$
			logFileOk = false;
		}	
		
	}
	
	public void close() 
	{
		logFile.close();
	}
	
	public void addLine(String _message) 
	{
		if(logFileOk == true) 
		{
			String message = new String(_message);
						
			
			SimpleDateFormat dateFormatter = new SimpleDateFormat (Messages.getString("LogFile.DateFormat")); //$NON-NLS-1$
        		Date currentTime = new Date();
        		String dateString = dateFormatter.format(currentTime);
			
			
			logFile.println(dateString + message);
		}
	}
}
