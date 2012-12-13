import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import java.text.*;


 
public class ConnectionHsqlDB
{
	private Connection connection;
	DecimalFormat df = new DecimalFormat("#.##"); //$NON-NLS-1$
		 
	public ConnectionHsqlDB (String driver, String url, String user,String password) 
	{
		try 
		{
        	        Class.forName(driver).newInstance(); 
			try 
			{
				connection = DriverManager.getConnection(url, user, password);
				LogFile logFile = new LogFile();
				logFile.addLine(Messages.getString("ConnectionHsqlDB.ConnectionRealized"));  //$NON-NLS-1$
				logFile.close();
				 
				
			}
	             	catch (SQLException exception) 
			{
				
				LogFile logFile = new LogFile();
				JOptionPane.showMessageDialog(null,exception.toString());
				logFile.addLine(Messages.getString("ConnectionHsqlDB.ConnectionError")); //$NON-NLS-1$
				logFile.addLine("SQLException: " + exception.getMessage()); //$NON-NLS-1$
	        	logFile.addLine("SQLState:     " + exception.getSQLState()); //$NON-NLS-1$
	        	logFile.addLine("VendorError:  " + exception.getErrorCode()); //$NON-NLS-1$
				logFile.close();
	             	}
                 }
            catch (Exception exception) 
			{
		 	
            	
            CopiaFile("driver/hsqldb.jar","./");	 //$NON-NLS-1$ //$NON-NLS-2$
            JOptionPane.showMessageDialog(null,Messages.getString("ConnectionHsqlDB.ErrorDriver")); //$NON-NLS-1$
		 	LogFile logFile = new LogFile();
			logFile.addLine("- DRIVER ERROR -"); //$NON-NLS-1$
            logFile.addLine(exception.toString());
			
			logFile.close();
			
			System.out.println(exception.toString());
                 }
	}
	
	 private void CopiaFile (String sPathIn, String sPathFolder) {
	    try {
	       File source=new File(sPathIn);
	       /*  File destFolder=new File("copia");
	       if(!destFolder.mkdir())  {
	          System.out.println("mkdir failed");
	       return;
	        } */
	       File dest=new File(sPathFolder,source.getName ());
	       dest.createNewFile();
	       
	       FileInputStream in = new FileInputStream(source);
	       FileOutputStream out = new FileOutputStream(dest);
	       int c;
	       while ((c = in.read()) != -1) {
	          out.write(c);
	       }
	       in.close();
	       out.close();
	       } catch(Exception ex) {
	       JOptionPane.showMessageDialog (null,ex.toString ());
	       System.out.println (ex.toString ());
	    }
	 }

	public void close()
	{
		try 
		{
	
			connection.close();
			LogFile logFile = new LogFile();
			logFile.addLine(Messages.getString("ConnectionHsqlDB.disconnectMessage"));  //$NON-NLS-1$
			logFile.close();
		}
	        catch (SQLException exception) 
		{
	
			LogFile logFile = new LogFile();
			logFile.addLine("- DISCONNECTION ERROR -");  //$NON-NLS-1$
			logFile.addLine("SQLException: " + exception.getMessage()); //$NON-NLS-1$
	       	logFile.addLine("SQLState:     " + exception.getSQLState()); //$NON-NLS-1$
	       	logFile.addLine("VendorError:  " + exception.getErrorCode()); //$NON-NLS-1$
			logFile.close();
		}
	}
	
	
	
	
	public Connection getConnection()
	{
		return connection;
	}
	
		
	
	public Vector getColumnsName(String _table,String campi)
	{
		
		String table	= new String(_table);
		Vector resultat = new Vector();
		
		try
		{
			Statement getColumnsNameStatement = connection.createStatement();
			ResultSet getColumnsNameResultSet = getColumnsNameStatement.executeQuery("SELECT "+campi+" FROM " + table ); //$NON-NLS-1$ //$NON-NLS-2$
			ResultSetMetaData getColumnsNameResultSetMetaData = getColumnsNameResultSet.getMetaData();
						
			for(int cptCols = 1; cptCols <= getColumnsNameResultSetMetaData.getColumnCount(); cptCols++)
			{
				resultat.add(getColumnsNameResultSetMetaData.getColumnName(cptCols));
			}
			
			getColumnsNameResultSet.close();
			getColumnsNameStatement.close();
		}
		catch (SQLException exception)
		{
			LogFile logFile = new LogFile();
			logFile.addLine("getColumnsName(" + table + ") ERROR");  //$NON-NLS-1$ //$NON-NLS-2$
			logFile.addLine("SQLException: " + exception.getMessage()); //$NON-NLS-1$
        	logFile.addLine("SQLState:     " + exception.getSQLState()); //$NON-NLS-1$
        	logFile.addLine("VendorError:  " + exception.getErrorCode()); //$NON-NLS-1$
			logFile.close();
			
			resultat.add("- ERROR -"); //$NON-NLS-1$
		}
		
		return resultat;
	}
	
	public Vector getColumnsName(String query)
	{
		
		Vector resultat = new Vector();
		
		try
		{
			Statement getColumnsNameStatement = connection.createStatement();
			ResultSet getColumnsNameResultSet = getColumnsNameStatement.executeQuery(query+ " LIMIT 1"); //$NON-NLS-1$
			ResultSetMetaData getColumnsNameResultSetMetaData = getColumnsNameResultSet.getMetaData();
						
			for(int cptCols = 1; cptCols <= getColumnsNameResultSetMetaData.getColumnCount(); cptCols++)
			{
				resultat.add(getColumnsNameResultSetMetaData.getColumnName(cptCols));
			}
			
			getColumnsNameResultSet.close();
			getColumnsNameStatement.close();
		}
		catch (SQLException exception)
		{
			LogFile logFile = new LogFile();
			logFile.addLine("getColumnsName(" + query + ") ERROR");  //$NON-NLS-1$ //$NON-NLS-2$
			logFile.addLine("SQLException: " + exception.getMessage()); //$NON-NLS-1$
	        logFile.addLine("SQLState:     " + exception.getSQLState()); //$NON-NLS-1$
	        logFile.addLine("VendorError:  " + exception.getErrorCode()); //$NON-NLS-1$
			logFile.close();
			
			resultat.add("- ERROR -"); //$NON-NLS-1$
		}
		
		return resultat;
	}
	
	
	
	public Vector getTableData(String _table,String campi, String _condition)
	{
		String table	= new String(_table);
		String condition	= new String(_condition);
		Vector resultat = new Vector();
		
		try
		{
			Statement getTableDataStatement = connection.createStatement();
			ResultSet getTableDataResultSet = getTableDataStatement.executeQuery("SELECT "+campi+" FROM " + table+ " " +condition); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			ResultSetMetaData getTableDataResultSetMetaData = getTableDataResultSet.getMetaData();
			
			while(getTableDataResultSet.next())
			{
				Vector temp = new Vector();			
			
				for(int cptCols = 1; cptCols <= getTableDataResultSetMetaData.getColumnCount(); cptCols++)
				{
					temp.add(getTableDataResultSet.getString(cptCols));
										
				}
			
				resultat.add(temp);
			}
			
			getTableDataResultSet.close();
			getTableDataStatement.close();
		}
		catch (SQLException exception)
		{
			LogFile logFile = new LogFile();
			logFile.addLine("getTableData(" + table + ") ERROR");  //$NON-NLS-1$ //$NON-NLS-2$
			logFile.addLine("SQLException: " + exception.getMessage()); //$NON-NLS-1$
	        logFile.addLine("SELECT "+campi+" FROM " + table+ " " +condition);	 //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			logFile.close();
			
			resultat.add("- ERROR -"); //$NON-NLS-1$
		}
		
		return resultat;
	}
	
	public Vector getTableData(String query)
	{
		
		
		Vector resultat = new Vector();
		
		try
		{
			Statement getTableDataStatement = connection.createStatement();
			ResultSet getTableDataResultSet = getTableDataStatement.executeQuery(query);
			ResultSetMetaData getTableDataResultSetMetaData = getTableDataResultSet.getMetaData();
			
			while(getTableDataResultSet.next())
			{
				Vector temp = new Vector();			
			
				for(int cptCols = 1; cptCols <= getTableDataResultSetMetaData.getColumnCount(); cptCols++)
				{
					temp.add(getTableDataResultSet.getString(cptCols));
										
				}
			
				resultat.add(temp);
			}
			
			getTableDataResultSet.close();
			getTableDataStatement.close();
		}
		catch (SQLException exception)
		{
			LogFile logFile = new LogFile();
			logFile.addLine("getTableData(" + query + ") ERROR");  //$NON-NLS-1$ //$NON-NLS-2$
			logFile.addLine("SQLException: " + exception.getMessage()); //$NON-NLS-1$
	        logFile.close();
			
			resultat.add("- ERROR -"); //$NON-NLS-1$
		}
		
		return resultat;
	}
	
	public Vector getTableDataArticoli(String query)
	{
		
		
		Vector resultat = new Vector();
		
		try
		{
			Statement getTableDataStatement = connection.createStatement();
			ResultSet getTableDataResultSet = getTableDataStatement.executeQuery(query);
			ResultSetMetaData getTableDataResultSetMetaData = getTableDataResultSet.getMetaData();
			
			while(getTableDataResultSet.next())
			{
				Vector temp = new Vector();			
				int numColonne=getTableDataResultSetMetaData.getColumnCount();
				for(int cptCols = 1; cptCols <= numColonne; cptCols++)
				{
					if (cptCols==numColonne) {
						Boolean inDispensa = Boolean.valueOf(getTableDataResultSet.getString(cptCols));
						temp.add((inDispensa.booleanValue()?Messages.getString("inLarder"):Messages.getString("ToBuy"))); //$NON-NLS-1$ //$NON-NLS-2$
					} 
					else temp.add(getTableDataResultSet.getString(cptCols));
										
				}
			
				resultat.add(temp);
			}
			
			getTableDataResultSet.close();
			getTableDataStatement.close();
		}
		catch (SQLException exception)
		{
			LogFile logFile = new LogFile();
			logFile.addLine("getTableDataArticoli(" + query + ") ERROR");  //$NON-NLS-1$ //$NON-NLS-2$
			logFile.addLine("SQLException: " + exception.getMessage()); //$NON-NLS-1$
	        logFile.close();
			
			resultat.add("- ERROR -"); //$NON-NLS-1$
		}
		
		return resultat;
	}
	
	public Vector getTableDataIngredienti(String query,double fattore)
	{
		
		
		Vector resultat = new Vector();
		double dQuantita;
		try
		{
			Statement getTableDataStatement = connection.createStatement();
			ResultSet getTableDataResultSet = getTableDataStatement.executeQuery(query);
			ResultSetMetaData getTableDataResultSetMetaData = getTableDataResultSet.getMetaData();
			
			while(getTableDataResultSet.next())
			{
				Vector temp = new Vector();			
			
				for(int cptCols = 1; cptCols <= getTableDataResultSetMetaData.getColumnCount(); cptCols++)
				{
					if (cptCols==2) {
						dQuantita = Double.parseDouble(getTableDataResultSet.getString(cptCols));
						temp.add(df.format(dQuantita*fattore));
					}
					else
						temp.add(getTableDataResultSet.getString(cptCols));
										
				}
			
				resultat.add(temp);
			}
			
			getTableDataResultSet.close();
			getTableDataStatement.close();
		}
		catch (SQLException exception)
		{
			LogFile logFile = new LogFile();
			logFile.addLine("getTableData(" + query + ") ERROR");  //$NON-NLS-1$ //$NON-NLS-2$
			logFile.addLine("SQLException: " + exception.getMessage()); //$NON-NLS-1$
	        logFile.close();
			
			resultat.add("- ERROR -"); //$NON-NLS-1$
		}
		
		return resultat;
	}
	
	
	
	
	
	public Vector getVectorRecord(String query)
	{
		
		
		Vector temp = new Vector();
		
		try
		{
			Statement getTableDataStatement = connection.createStatement();
			ResultSet getTableDataResultSet = getTableDataStatement.executeQuery(query);
			ResultSetMetaData getTableDataResultSetMetaData = getTableDataResultSet.getMetaData();
			
			while(getTableDataResultSet.next())
			{
							
			
				for(int cptCols = 1; cptCols <= getTableDataResultSetMetaData.getColumnCount(); cptCols++)
				{
					temp.add(getTableDataResultSet.getString(cptCols));
										
				}
			
				
			}
			
			getTableDataResultSet.close();
			getTableDataStatement.close();
		}
		catch (SQLException exception)
		{
			LogFile logFile = new LogFile();
			logFile.addLine("getVectorRecord(" + query + ") ERROR");  //$NON-NLS-1$ //$NON-NLS-2$
			logFile.addLine("SQLException: " + exception.getMessage()); //$NON-NLS-1$
	        logFile.close();
			
			temp.add("- ERROR -"); //$NON-NLS-1$
		}
		
		return temp;
	}
	
	
	public Vector getComboData(String _table,String campo, String _condition)
	{
		
		String table	= new String(_table);
		String condition	= new String(_condition);
		Vector resultat = new Vector();
		String sTemp=null;
		
		try
		{
			Statement getTableDataStatement = connection.createStatement();
			ResultSet getTableDataResultSet = getTableDataStatement.executeQuery("SELECT "+campo+" FROM " +table+ " " +condition); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			ResultSetMetaData getTableDataResultSetMetaData = getTableDataResultSet.getMetaData();
			
			while(getTableDataResultSet.next())
			{
						
				for(int cptCols = 1; cptCols <= getTableDataResultSetMetaData.getColumnCount(); cptCols++)
				{
					sTemp=getTableDataResultSet.getString(cptCols);
					if (sTemp!=null) resultat.add(sTemp);
										
				}
			
				
			}
			
			getTableDataResultSet.close();
			getTableDataStatement.close();
		}
		catch (SQLException exception)
		{
			LogFile logFile = new LogFile();
			logFile.addLine("getComboData(" + table + ") ERROR");  //$NON-NLS-1$ //$NON-NLS-2$
			logFile.addLine("SQLException: " + exception.getMessage()); //$NON-NLS-1$
	        logFile.addLine("SELECT * FROM " + table+ " " +condition);	 //$NON-NLS-1$ //$NON-NLS-2$
			logFile.close();
			
			resultat.add("- ERROR -"); //$NON-NLS-1$
		}
		
		return resultat;
	}
	
	public Vector getComboRecord(String query)
	{
		
		Vector resultat = new Vector();
		
		try
		{
			Statement getTableDataStatement = connection.createStatement();
			ResultSet rsRecord = getTableDataStatement.executeQuery(query);
			ResultSetMetaData getTableDataResultSetMetaData = rsRecord.getMetaData();
			StringBuffer sBuf;
			while(rsRecord.next())
			{
				sBuf =new StringBuffer();
			
				for(int cptCols = 1; cptCols <= getTableDataResultSetMetaData.getColumnCount(); cptCols++)
				{
					sBuf.append(rsRecord.getString(cptCols)+" "); //$NON-NLS-1$
										
				}
				resultat.add(sBuf.toString());
				
			}
			
			
			getTableDataStatement.close();
		}
		catch (SQLException exception)
		{
			LogFile logFile = new LogFile();
			logFile.addLine("getComboRecord(" + query + ") ERROR");  //$NON-NLS-1$ //$NON-NLS-2$
			logFile.addLine("SQLException: " + exception.getMessage()); //$NON-NLS-1$
	       	logFile.close();
			
			resultat.add("- ERROR -"); //$NON-NLS-1$
		}
		
		return resultat;
	}
	
	public String getStringRecord(String query)
	{
		
		StringBuffer sBuf=null;
		try
		{
			Statement getTableDataStatement = connection.createStatement();
			ResultSet rsRecord = getTableDataStatement.executeQuery(query);
			ResultSetMetaData getTableDataResultSetMetaData = rsRecord.getMetaData();
			
			rsRecord.next();
			
			sBuf =new StringBuffer();
		
			for(int cptCols = 1; cptCols <= getTableDataResultSetMetaData.getColumnCount(); cptCols++)
			{
				sBuf.append(rsRecord.getString(cptCols)+" "); //$NON-NLS-1$
									
			}
			
			getTableDataStatement.close();
		}
		catch (SQLException exception)
		{
			LogFile logFile = new LogFile();
			logFile.addLine("getStringRecord(" + query + ") ERROR");  //$NON-NLS-1$ //$NON-NLS-2$
			logFile.addLine("SQLException: " + exception.getMessage()); //$NON-NLS-1$
	       	logFile.close();
			
			
		}
		
		return sBuf.toString();
	}
	
	public String getCampo(String query)
	{
		String campo=""; //$NON-NLS-1$
		
		try
		{
			Statement getTableDataStatement = connection.createStatement();
			ResultSet rsRecord = getTableDataStatement.executeQuery(query);
			rsRecord.next();
			campo = rsRecord.getString(1);
			getTableDataStatement.close();
		}
		catch (SQLException exception)
		{
			LogFile logFile = new LogFile();
			logFile.addLine("getCampo(" + query + ") ERROR");  //$NON-NLS-1$ //$NON-NLS-2$
			logFile.addLine("SQLException: " + exception.getMessage()); //$NON-NLS-1$
	       	logFile.close();
			
			
		}
		
		return campo;
	}
	
	
	
	
	
	public long getMassimo(String campo,String tabella) {
		long massimo=0;
		try {
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery("Select max("+campo+") from "+tabella); //$NON-NLS-1$ //$NON-NLS-2$
			rs.next();
			massimo =rs.getLong(1);
			st.close();
			}catch (SQLException exception)
			{
				LogFile logFile = new LogFile();
				logFile.addLine("getContatore() ERROR");  //$NON-NLS-1$
				logFile.addLine("SQLException: " + exception.getMessage()); //$NON-NLS-1$
		        logFile.close();
			}	
		return massimo;	
	}
	
	public boolean esegui (String query) {
		boolean eseguito=false;
		try {
		connection.createStatement().execute(query);
		eseguito=true;
		}catch (SQLException exception)
		{	
			LogFile logFile = new LogFile();
			logFile.addLine("esegui() ERROR");  //$NON-NLS-1$
			logFile.addLine("SQLException: " + exception.getMessage()); //$NON-NLS-1$
	        logFile.close();
			switch (exception.getErrorCode()){
				case -9:
					JOptionPane.showMessageDialog(null,Messages.getString("ConnectionHsqlDB.DuplicateArticles")); //$NON-NLS-1$
				break;	
				case -11:
					JOptionPane.showMessageDialog(null,Messages.getString("ConnectionHsqlDB.MissingData")); //$NON-NLS-1$
				break;	
				default:
					JOptionPane.showMessageDialog(null,Messages.getString("ConnectionHsqlDB.ThereIsError") +exception.getErrorCode()); //$NON-NLS-1$
				break;	
			}		
		}
		return eseguito;
	}
	
	public boolean esisteRecord(String query) {
		boolean b=false;
		try {
			Statement st=connection.createStatement();
			ResultSet rs = st.executeQuery(query);
			b= rs.next();
		}catch (SQLException exception)
		{exception.printStackTrace();}
		return b;
	}
	
	
}
