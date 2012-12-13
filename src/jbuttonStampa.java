import java.awt.event.*;
import java.awt.Desktop;
import java.io.*;
import javax.swing.*;
import java.util.*;
import java.net.URI;
import java.net.URISyntaxException;


public class jbuttonStampa extends JButton {
	
	Hashtable hashDati;
    jpanelDescrizione panelDescrizione;
    jpanelLista panelLista;
    jpanelMenu panelMenu;
    boolean stampaLista,
	stampaRicetteConIngredienti,
	stampaRicetteSenzaIngredienti;
    private Desktop desktop;
    private Desktop.Action action;
    //Process p=null;
    //int exit=-1;
    
    public jbuttonStampa() {
    	super(Messages.getString("jbuttonStampa.Print"),new ImageIcon("images/pulsanti/Print24.gif"));
    	setEnabled(false);
    	addActionListener(new CreaReportAction());
    	if (Desktop.isDesktopSupported()) {
            desktop = Desktop.getDesktop();
            if (desktop.isSupported(Desktop.Action.BROWSE)) {
                setEnabled(true);
            }
        }
     }
    
    class CreaReportAction extends AbstractAction {
    	
    	protected CreaReportAction() {
            super();
        
      }
    	
    	public void actionPerformed(ActionEvent e) {
    		String s=null;
    		File f=null;
    		try {
    			
    			if (panelDescrizione!=null) {
    				s =System.getProperty("user.dir") + "/Ricetta.html";
    				f = new File(s);
        			f.delete();
    				CreaReportRicette(f);
    			}	
    			else if (panelLista!=null){
    				if (panelLista.isVectorRicetteEmpty()) {
    					JOptionPane.showMessageDialog(null,Messages.getString("jbuttonStampa.NothingToPrint"));
    					return;
    				}
    				s =System.getProperty("user.dir") + "/Lista.html";
    				f = new File(s);
        			f.delete();
    				CreaReportLista(f);
    			}	
    			else if (panelMenu!=null){
    				s =System.getProperty("user.dir") + "/Menu.htm";
    				f = new File(s);
        			f.delete();
    				CreaReportMenu(f);
    			}
    			
    			//s=framePrincipale.replace(s," ","%20");
    			URI uri = null;
    	        uri = f.toURI();
    	        desktop.browse(uri);
    	        
      	       	
            }	
            catch (Exception ex) {System.out.println(ex.toString());}
      }
    
    }
    
    private void setHashdati(Hashtable hash) {
    	hashDati=hash;
    }
    
    private void setStampaOpzioni (boolean b[]) {
	    this.stampaLista=b[2];
	    this.stampaRicetteConIngredienti=b[0];
	    this.stampaRicetteSenzaIngredienti=b[1];
    	
    }
    
    public void setPanelDescrizione(jpanelDescrizione panel) {
    	panelDescrizione=panel;
    }
    
    public void setPanelLista(jpanelLista panel) {
    	panelLista=panel;
    }
    
    public void setPanelMenu(jpanelMenu panel) {
    	panelMenu=panel;
    }
    
    private void CreaFileMetodo (){
 	   try {
 	    String percorso=System.getProperty("user.home") + "\\Lista.htm";
 	    RandomAccessFile file1 = new RandomAccessFile(percorso,"rw");
 	    FileOutputStream ostraim = new FileOutputStream("fileMetodo.txt");
 	    BufferedWriter putStream = new BufferedWriter(new OutputStreamWriter(ostraim));
 	    String inLine=null;
 	    putStream.write ("private void CreaReportRicette(File f){");
 	    putStream.newLine ();
 	    putStream.write ("try {");
 	    putStream.newLine ();
 	    putStream.write ("String lineSeparator =	(String) java.security.AccessController.doPrivileged("+
 	               "new sun.security.action.GetPropertyAction(\"line.separator\"));");
 	    putStream.newLine ();
 	    //String lineSeparator =	(String) java.security.AccessController.doPrivileged(
 	    //           new sun.security.action.GetPropertyAction("line.separator"));
 	    putStream.write ("RandomAccessFile file = new RandomAccessFile(f,\"rw\");");
 	    putStream.newLine ();
 	    while ((inLine = file1.readLine ()) !=null) {
 	       putStream.write ("file.writeBytes (\""+ framePrincipale.replace(inLine,"\"","\\\"") + "\" + lineSeparator);");
 	       putStream.newLine ();
 	    }
 	    putStream.write ("file.close();");
 	    putStream.newLine ();
 	    putStream.write ("} catch (Exception e) {e.printStackTrace ();} }");
 	    putStream.newLine ();

 	    file1.close ();
 	    putStream.flush ();
 	    ostraim.close (); //chiude il file
 	   } catch (Exception e) {JOptionPane.showMessageDialog (null,e.toString ());}
 	  }

 	 
 	  
 	  
 	 private void CreaReportRicette(File f){
 	 	try {
 	 	setHashdati(panelDescrizione.getHashDati());	
 	 	String lineSeparator =	(String) java.security.AccessController.doPrivileged(new sun.security.action.GetPropertyAction("line.separator"));
 	 	RandomAccessFile file = new RandomAccessFile(f,"rw");
 	 	file.writeBytes ("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">" + lineSeparator);
 	 	file.writeBytes ("<html>" + lineSeparator);
 	 	file.writeBytes ("<head>" + lineSeparator);
 	 	file.writeBytes ("<title>Ricetta</title>" + lineSeparator);
 	 	file.writeBytes ("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">" + lineSeparator);
 	 	file.writeBytes ("</head>" + lineSeparator);
 	 	file.writeBytes ("" + lineSeparator);
 	 	file.writeBytes ("<body>" + lineSeparator);
 	 	file.writeBytes ("<table width=\"100%\" border=\"1\">" + lineSeparator);
 	 	file.writeBytes ("  <tr align=\"center\"> " + lineSeparator);
 	 	file.writeBytes ("    <td colspan=\"5\"><h2><font color=\"#CC3333\" face=\"Verdana, Arial, Helvetica, sans-serif\">" +hashDati.get("nome")+ lineSeparator);
 	 	file.writeBytes ("</font></h2></td>" + lineSeparator);
 	 	file.writeBytes ("  </tr>" + lineSeparator);
 	 	file.writeBytes ("  <tr> " + lineSeparator);
 	 	file.writeBytes ("    <td><FONT SIZE=4 face=\"Verdana, Arial, Helvetica, sans-serif\"><B>Tipologia: " +hashDati.get("tipologia")+ lineSeparator);
 	 	file.writeBytes ("</B></FONT></td>" + lineSeparator);
 	 	file.writeBytes ("    <td><FONT SIZE=4 face=\"Verdana, Arial, Helvetica, sans-serif\"><B>Tempo: "+hashDati.get("tempo") + lineSeparator);
 	 	file.writeBytes ("</B></FONT></td>" + lineSeparator);
 	 	file.writeBytes ("    <td><FONT SIZE=4 face=\"Verdana, Arial, Helvetica, sans-serif\"><B>Difficolta': "+hashDati.get("difficolta")+"</B></FONT></td>" + lineSeparator);
 	 	file.writeBytes ("    <td><FONT SIZE=4 face=\"Verdana, Arial, Helvetica, sans-serif\"><B>Porzioni:</B></FONT></td>" + lineSeparator);
 	 	file.writeBytes ("    <td><font size=4 face=\"Verdana, Arial, Helvetica, sans-serif\">"+hashDati.get("porzioni")+"</font></td>" + lineSeparator);
 	 	file.writeBytes ("  </tr>" + lineSeparator);
 	 	file.writeBytes ("  <tr> " + lineSeparator);
 	 	file.writeBytes ("    <td colspan=\"5\">&nbsp;</td>" + lineSeparator);
 	 	file.writeBytes ("  </tr>" + lineSeparator);
 	 	file.writeBytes ("</table>" + lineSeparator);
 	 	file.writeBytes ("<table width=\"100%\" border=\"1\">" + lineSeparator);
 	 	file.writeBytes ("  <tr> " + lineSeparator);
 	 	file.writeBytes ("    <td colspan=\"3\" align=\"center\"><h2><font color=\"#CC3333\" face=\"Verdana, Arial, Helvetica, sans-serif\">Ingredienti</font></h2></td>" + lineSeparator);
 	 	file.writeBytes ("  </tr>" + lineSeparator);
 	 	file.writeBytes ("  <tr> " + lineSeparator);
 	 	file.writeBytes ("    <td width=\"33%\" align=\"center\"><FONT SIZE=4 face=\"Verdana, Arial, Helvetica, sans-serif\"><B>Prodotto</B></FONT><font face=\"Verdana, Arial, Helvetica, sans-serif\">&nbsp;</font></td>" + lineSeparator);
 	 	file.writeBytes ("    <td width=\"33%\" align=\"center\"><FONT SIZE=4 face=\"Verdana, Arial, Helvetica, sans-serif\"><B>Quantit&agrave;</B></FONT></td>" + lineSeparator);
 	 	file.writeBytes ("    <td width=\"33%\" align=\"center\"><FONT SIZE=4 face=\"Verdana, Arial, Helvetica, sans-serif\"><B>Misura</B></FONT><font face=\"Verdana, Arial, Helvetica, sans-serif\">&nbsp;</font></td>" + lineSeparator);
 	 	file.writeBytes ("  </tr>" + lineSeparator);
 	 	Vector vData = (Vector) hashDati.get("ingredienti");
 	 	for (int i=0; i<vData.size();i++) {
	 	 	file.writeBytes ("  <tr> " + lineSeparator);
	 	 	file.writeBytes ("    <td align=\"center\"><font face=\"Verdana, Arial, Helvetica, sans-serif\">"+((Vector)vData.elementAt(i)).elementAt(0) + lineSeparator);
	 	 	file.writeBytes ("      </font></td>" + lineSeparator);
	 	 	file.writeBytes ("    <td align=\"center\"><font face=\"Verdana, Arial, Helvetica, sans-serif\">"+((Vector)vData.elementAt(i)).elementAt(1) + lineSeparator);
	 	 	file.writeBytes ("      </font></td>" + lineSeparator);
	 	 	file.writeBytes ("    <td align=\"center\"><font face=\"Verdana, Arial, Helvetica, sans-serif\">"+((Vector)vData.elementAt(i)).elementAt(2) +  lineSeparator);
	 	 	file.writeBytes ("      </font></td>" + lineSeparator);
	 	 	file.writeBytes ("  </tr>" + lineSeparator);
 	 	}
 	 	file.writeBytes ("  <tr> " + lineSeparator);
 	 	file.writeBytes ("    <td colspan=\"3\" align=\"center\"><h2><font color=\"#CC3333\" face=\"Verdana, Arial, Helvetica, sans-serif\">Procedimento</font></h2></td>" + lineSeparator);
 	 	file.writeBytes ("  </tr>" + lineSeparator);
 	 	file.writeBytes ("  <tr>" + lineSeparator);
 	 	file.writeBytes ("    <td colspan=\"3\" align=\"center\"><font face=\"Verdana, Arial, Helvetica, sans-serif\">"+hashDati.get("descrizione")+"</font></td>" + lineSeparator);
 	 	file.writeBytes ("  </tr>" + lineSeparator);
 	 	file.writeBytes ("</table>" + lineSeparator);
 	 	file.writeBytes ("</body>" + lineSeparator);
 	 	file.writeBytes ("</html>" + lineSeparator);
 	 	file.close();
 	 	} catch (Exception e) {e.printStackTrace ();} }
 
 	
 	private void CreaReportLista(File f){
 		try {
 		int i;
 	 	Vector vRicette;
 	 	setStampaOpzioni(panelLista.getOpzioniStampa());
 	 	setHashdati(panelLista.getHashDati());	
 		String lineSeparator =	(String) java.security.AccessController.doPrivileged(new sun.security.action.GetPropertyAction("line.separator"));
 		RandomAccessFile file = new RandomAccessFile(f,"rw");
 		file.writeBytes ("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">" + lineSeparator);
 		file.writeBytes ("<html>" + lineSeparator);
 		file.writeBytes ("<head>" + lineSeparator);
 		file.writeBytes ("<title>Lista della spesa</title>" + lineSeparator);
 		file.writeBytes ("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">" + lineSeparator);
 		file.writeBytes ("</head>" + lineSeparator);
 		file.writeBytes ("" + lineSeparator);
 		file.writeBytes ("<body>" + lineSeparator);
 		if (stampaRicetteSenzaIngredienti) {
	 		file.writeBytes ("<table width=\"100%\" border=\"1\">" + lineSeparator);
	 		file.writeBytes ("  <tr>" + lineSeparator);
	 		file.writeBytes ("    <td align=\"center\"><h2><font color=\"#CC3333\" face=\"Verdana, Arial, Helvetica, sans-serif\">Elenco " + lineSeparator);
	 		file.writeBytes ("        ricette selezionate</font></h2></td>" + lineSeparator);
	 		file.writeBytes ("  </tr>" + lineSeparator);
	 		vRicette = (Vector) hashDati.get("ricetteSelezionate");
	 		for (i=0;i<vRicette.size();i++) {
		 		file.writeBytes ("  <tr>" + lineSeparator);
		 		file.writeBytes ("    <td align=\"center\"><font face=\"Verdana, Arial, Helvetica, sans-serif\">"+vRicette.elementAt(i)+ lineSeparator);
		 		file.writeBytes ("</font></td>" + lineSeparator);
		 		file.writeBytes ("  </tr>" + lineSeparator);
	 		}
	 		file.writeBytes ("</table>" + lineSeparator);
 		}
 		if (stampaRicetteConIngredienti) {
	 		file.writeBytes ("<table width=\"100%\" border=\"1\">" + lineSeparator);
	 		file.writeBytes ("  <tr align=\"center\"> " + lineSeparator);
	 		file.writeBytes ("    <td colspan=\"5\"><h2><font color=\"#CC3333\" face=\"Verdana, Arial, Helvetica, sans-serif\">Elenco " + lineSeparator);
	 		file.writeBytes ("        ricette selezionate con ingredienti</font></h2></td>" + lineSeparator);
	 		file.writeBytes ("  </tr>" + lineSeparator);
	 		file.writeBytes ("  <tr align=\"center\"> " + lineSeparator);
	 		file.writeBytes ("    <td><strong><font size=\"4\" face=\"Verdana, Arial, Helvetica, sans-serif\">Ricetta</font></strong></td>" + lineSeparator);
	 		file.writeBytes ("    <td><strong><font size=\"4\" face=\"Verdana, Arial, Helvetica, sans-serif\">Persone</font></strong></td>" + lineSeparator);
	 		file.writeBytes ("    <td><strong><font size=\"4\" face=\"Verdana, Arial, Helvetica, sans-serif\">Prodotto</font></strong></td>" + lineSeparator);
	 		file.writeBytes ("    <td><strong><font size=\"4\" face=\"Verdana, Arial, Helvetica, sans-serif\">Quantit&agrave;</font></strong></td>" + lineSeparator);
	 		file.writeBytes ("    <td><strong><font size=\"4\" face=\"Verdana, Arial, Helvetica, sans-serif\">Misura</font></strong></td>" + lineSeparator);
	 		file.writeBytes ("  </tr>" + lineSeparator);
	 		vRicette = (Vector) hashDati.get("ricetteSelezionateConIngredienti");
	 		for (i=0;i<vRicette.size();i++) {
		 		file.writeBytes ("  <tr align=\"center\"> " + lineSeparator);
		 		file.writeBytes ("    <td><font face=\"Verdana, Arial, Helvetica, sans-serif\">"+((Vector)vRicette.elementAt(i)).elementAt(0)+"</font></td>" + lineSeparator);
		 		file.writeBytes ("    <td><font face=\"Verdana, Arial, Helvetica, sans-serif\">"+((Vector)vRicette.elementAt(i)).elementAt(1)+"</font></td>" + lineSeparator);
		 		file.writeBytes ("    <td><font face=\"Verdana, Arial, Helvetica, sans-serif\">"+((Vector)vRicette.elementAt(i)).elementAt(2)+"</font></td>" + lineSeparator);
		 		file.writeBytes ("    <td><font face=\"Verdana, Arial, Helvetica, sans-serif\">"+((Vector)vRicette.elementAt(i)).elementAt(3)+"</font></td>" + lineSeparator);
		 		file.writeBytes ("    <td><font face=\"Verdana, Arial, Helvetica, sans-serif\">"+((Vector)vRicette.elementAt(i)).elementAt(4)+"</font></td>" + lineSeparator);
		 		file.writeBytes ("  </tr>" + lineSeparator);
	 		}
	 		file.writeBytes ("</table>" + lineSeparator);
 		}
 		if (stampaLista) {
	 		file.writeBytes ("<table width=\"100%\" border=\"1\">" + lineSeparator);
	 		file.writeBytes ("  <tr> " + lineSeparator);
	 		file.writeBytes ("    <td colspan=\"5\" align=\"center\"><h2><font color=\"#CC3333\" face=\"Verdana, Arial, Helvetica, sans-serif\">Lista " + lineSeparator);
	 		file.writeBytes ("        della spesa</font></h2></td>" + lineSeparator);
	 		file.writeBytes ("  </tr>" + lineSeparator);
	 		file.writeBytes ("  <tr> " + lineSeparator);
	 		file.writeBytes ("    <td width=\"20%\" align=\"center\"><FONT SIZE=4 face=\"Verdana, Arial, Helvetica, sans-serif\"><B>Prodotto</B></FONT><font face=\"Verdana, Arial, Helvetica, sans-serif\">&nbsp;</font></td>" + lineSeparator);
	 		file.writeBytes ("    <td width=\"20%\" align=\"center\"><FONT SIZE=4 face=\"Verdana, Arial, Helvetica, sans-serif\"><B>Quantit&agrave;</B></FONT></td>" + lineSeparator);
	 		file.writeBytes ("    <td width=\"20%\" align=\"center\"><FONT SIZE=4 face=\"Verdana, Arial, Helvetica, sans-serif\"><B>Misura</B></FONT><font face=\"Verdana, Arial, Helvetica, sans-serif\">&nbsp;</font></td>" + lineSeparator);
	 		file.writeBytes ("    <td width=\"20%\" align=\"center\"><FONT SIZE=4 face=\"Verdana, Arial, Helvetica, sans-serif\"><B>Categoria</B></FONT><font face=\"Verdana, Arial, Helvetica, sans-serif\">&nbsp;</font></td>" + lineSeparator);
	 		file.writeBytes ("    <td width=\"20%\" align=\"center\"><FONT SIZE=4 face=\"Verdana, Arial, Helvetica, sans-serif\"><B>Dispensa</B></FONT></td>" + lineSeparator);
	 		file.writeBytes ("  </tr>" + lineSeparator);
	 		vRicette = (Vector) hashDati.get("listaSpesa");
	 		for (i=0;i<vRicette.size();i++) {
		 		file.writeBytes ("  <tr> " + lineSeparator);
		 		file.writeBytes ("    <td align=\"center\"><font face=\"Verdana, Arial, Helvetica, sans-serif\">&nbsp;"+((Vector)vRicette.elementAt(i)).elementAt(0)+  lineSeparator);
		 		file.writeBytes ("      </font></td>" + lineSeparator);
		 		file.writeBytes ("    <td align=\"center\"><font face=\"Verdana, Arial, Helvetica, sans-serif\">&nbsp;"+((Vector)vRicette.elementAt(i)).elementAt(1)+ lineSeparator);
		 		file.writeBytes ("      </font></td>" + lineSeparator);
		 		file.writeBytes ("    <td align=\"center\"><font face=\"Verdana, Arial, Helvetica, sans-serif\">&nbsp;"+((Vector)vRicette.elementAt(i)).elementAt(2)+ lineSeparator);
		 		file.writeBytes ("      </font></td>" + lineSeparator);
		 		file.writeBytes ("    <td align=\"center\"><font face=\"Verdana, Arial, Helvetica, sans-serif\">&nbsp;"+((Vector)vRicette.elementAt(i)).elementAt(3)+ lineSeparator);
		 		file.writeBytes ("      </font></td>" + lineSeparator);
		 		file.writeBytes ("    <td align=\"center\"><font face=\"Verdana, Arial, Helvetica, sans-serif\">&nbsp;"+((Vector)vRicette.elementAt(i)).elementAt(4)+ lineSeparator);
		 		file.writeBytes ("      </font></td>" + lineSeparator);
		 		file.writeBytes ("  </tr>" + lineSeparator);
	 		}
	 		file.writeBytes ("</table>" + lineSeparator);
 		}
 		file.writeBytes ("</body>" + lineSeparator);
 		file.writeBytes ("</html>" + lineSeparator);
 		file.close();
 		} catch (Exception e) {e.printStackTrace ();} }
 
 	 
 	
 	
 	private void CreaReportMenu(File f){
 		try {
 		setHashdati(panelMenu.getHashDati());	
 		String lineSeparator =	(String) java.security.AccessController.doPrivileged(new sun.security.action.GetPropertyAction("line.separator"));
 		RandomAccessFile file = new RandomAccessFile(f,"rw");
 		file.writeBytes ("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">" + lineSeparator);
 		file.writeBytes ("<html>" + lineSeparator);
 		file.writeBytes ("<head>" + lineSeparator);
 		file.writeBytes ("<title>Menu' Settimanale</title>" + lineSeparator);
 		file.writeBytes ("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">" + lineSeparator);
 		file.writeBytes ("</head>" + lineSeparator);
 		file.writeBytes ("<body>" + lineSeparator);
 		file.writeBytes ("<table width=\"100%\" border=\"1\">" + lineSeparator);
 		file.writeBytes ("  <tr> " + lineSeparator);
 		file.writeBytes ("    <td colspan=\"5\" align=\"center\"><h2><font face=\"Verdana, Arial, Helvetica, sans-serif\">Men&ugrave; " + lineSeparator);
 		file.writeBytes ("        Settimanale</font></h2></td>" + lineSeparator);
 		file.writeBytes ("  </tr>" + lineSeparator);
 		file.writeBytes ("  <tr> " + lineSeparator);
 		file.writeBytes ("    <td colspan=\"3\"><h3><font color=\"#CC3333\" face=\"Verdana, Arial, Helvetica, sans-serif\">"+hashDati.get("MeseAnno")+"</font></h3></td>" + lineSeparator);
 		file.writeBytes ("    <td colspan=\"2\"><h3><font color=\"#CC3333\" face=\"Verdana, Arial, Helvetica, sans-serif\">"+hashDati.get("Settimana")+"</font></h3></td>" + lineSeparator);
 		file.writeBytes ("  </tr>" + lineSeparator);
 		file.writeBytes ("  <tr> " + lineSeparator);
 		file.writeBytes ("    <td width=\"21%\" rowspan=\"4\" align=\"center\"><h3><font color=\"#CC3333\" face=\"Verdana, Arial, Helvetica, sans-serif\">Pranzo</font></h3></td>" + lineSeparator);
 		String giorni []= (String []) hashDati.get("giorni");
 		file.writeBytes ("    <td width=\"20%\"><h3 align=\"center\"><font face=\"Verdana, Arial, Helvetica, sans-serif\">"+giorni[0]+"</font></h3></td>" + lineSeparator);
 		file.writeBytes ("    <td width=\"20%\"><h3 align=\"center\"><font face=\"Verdana, Arial, Helvetica, sans-serif\">"+giorni[1]+"</font></h3></td>" + lineSeparator);
 		file.writeBytes ("    <td width=\"19%\"><h3 align=\"center\"><font face=\"Verdana, Arial, Helvetica, sans-serif\">"+giorni[2]+"</font></h3></td>" + lineSeparator);
 		file.writeBytes ("    <td width=\"20%\"><h3 align=\"center\"><font face=\"Verdana, Arial, Helvetica, sans-serif\">"+giorni[3]+"</font></h3></td>" + lineSeparator);
 		file.writeBytes ("  </tr>" + lineSeparator);
 		
 		Vector v = (Vector) hashDati.get("vectorPranzo");
 		int i;
 		for (i=0;i<v.size();i++) {
	 		file.writeBytes ("  <tr> " + lineSeparator);
	 		file.writeBytes ("    <td><font face=\"Verdana, Arial, Helvetica, sans-serif\">"+((Vector) v.elementAt(i)).elementAt(0)+"&nbsp;</font></td>" + lineSeparator);
	 		file.writeBytes ("    <td><font face=\"Verdana, Arial, Helvetica, sans-serif\">"+((Vector) v.elementAt(i)).elementAt(1)+"&nbsp;</font></td>" + lineSeparator);
	 		file.writeBytes ("    <td><font face=\"Verdana, Arial, Helvetica, sans-serif\">"+((Vector) v.elementAt(i)).elementAt(2)+"&nbsp;</font></td>" + lineSeparator);
	 		file.writeBytes ("    <td><font face=\"Verdana, Arial, Helvetica, sans-serif\">"+((Vector) v.elementAt(i)).elementAt(3)+"&nbsp;</font></td>" + lineSeparator);
	 		file.writeBytes ("  </tr>" + lineSeparator);
 		}
 		file.writeBytes ("  <tr> " + lineSeparator);
 		file.writeBytes ("    <td><h3 align=\"center\"><font face=\"Verdana, Arial, Helvetica, sans-serif\">"+giorni[4]+"</font></h3></td>" + lineSeparator);
 		file.writeBytes ("    <td><h3 align=\"center\"><font face=\"Verdana, Arial, Helvetica, sans-serif\">"+giorni[5]+"</font></h3></td>" + lineSeparator);
 		file.writeBytes ("    <td><h3 align=\"center\"><font face=\"Verdana, Arial, Helvetica, sans-serif\">"+giorni[6]+"</font></h3></td>" + lineSeparator);
 		file.writeBytes ("    <td><font face=\"Verdana, Arial, Helvetica, sans-serif\">&nbsp;</font></td>" + lineSeparator);
 		file.writeBytes ("  </tr>" + lineSeparator);
 		for (i=0;i<v.size();i++) {
	 		file.writeBytes ("  <tr> " + lineSeparator);
	 		file.writeBytes ("    <td><font face=\"Verdana, Arial, Helvetica, sans-serif\">"+((Vector) v.elementAt(i)).elementAt(4)+"&nbsp;</font></td>" + lineSeparator);
	 		file.writeBytes ("    <td><font face=\"Verdana, Arial, Helvetica, sans-serif\">"+((Vector) v.elementAt(i)).elementAt(5)+"&nbsp;</font></td>" + lineSeparator);
	 		file.writeBytes ("    <td><font face=\"Verdana, Arial, Helvetica, sans-serif\">"+((Vector) v.elementAt(i)).elementAt(6)+"&nbsp;</font></td>" + lineSeparator);
	 		file.writeBytes ("    <td><font face=\"Verdana, Arial, Helvetica, sans-serif\">&nbsp;</font></td>" + lineSeparator);
	 		file.writeBytes ("  </tr>" + lineSeparator);
 		}
 		file.writeBytes ("</table>" + lineSeparator);
 		file.writeBytes ("<table width=\"100%\" border=\"1\">" + lineSeparator);
 		file.writeBytes ("  <tr> " + lineSeparator);
 		file.writeBytes ("    <td width=\"21%\" rowspan=\"4\" align=\"center\"><h3><font color=\"#CC3333\" face=\"Verdana, Arial, Helvetica, sans-serif\">Cena</font></h3></td>" + lineSeparator);
 		file.writeBytes ("    <td width=\"20%\"><h3 align=\"center\"><font face=\"Verdana, Arial, Helvetica, sans-serif\">"+giorni[0]+"</font></h3></td>" + lineSeparator);
 		file.writeBytes ("    <td width=\"20%\"><h3 align=\"center\"><font face=\"Verdana, Arial, Helvetica, sans-serif\">"+giorni[1]+"</font></h3></td>" + lineSeparator);
 		file.writeBytes ("    <td width=\"19%\"><h3 align=\"center\"><font face=\"Verdana, Arial, Helvetica, sans-serif\">"+giorni[2]+"</font></h3></td>" + lineSeparator);
 		file.writeBytes ("    <td width=\"20%\"><h3 align=\"center\"><font face=\"Verdana, Arial, Helvetica, sans-serif\">"+giorni[3]+"</font></h3></td>" + lineSeparator);
 		file.writeBytes ("  </tr>" + lineSeparator);
 		v = (Vector) hashDati.get("vectorCena");
 		for (i=0;i<v.size();i++) {
	 		file.writeBytes ("  <tr> " + lineSeparator);
	 		file.writeBytes ("    <td><font face=\"Verdana, Arial, Helvetica, sans-serif\">"+((Vector) v.elementAt(i)).elementAt(0)+"&nbsp;</font></td>" + lineSeparator);
	 		file.writeBytes ("    <td><font face=\"Verdana, Arial, Helvetica, sans-serif\">"+((Vector) v.elementAt(i)).elementAt(1)+"&nbsp;</font></td>" + lineSeparator);
	 		file.writeBytes ("    <td><font face=\"Verdana, Arial, Helvetica, sans-serif\">"+((Vector) v.elementAt(i)).elementAt(2)+"&nbsp;</font></td>" + lineSeparator);
	 		file.writeBytes ("    <td><font face=\"Verdana, Arial, Helvetica, sans-serif\">"+((Vector) v.elementAt(i)).elementAt(3)+"&nbsp;</font></td>" + lineSeparator);
	 		file.writeBytes ("  </tr>" + lineSeparator);
 		}
 		
 		file.writeBytes ("  <tr> " + lineSeparator);
 		file.writeBytes ("    <td><h3 align=\"center\"><font face=\"Verdana, Arial, Helvetica, sans-serif\">"+giorni[4]+"</font></h3></td>" + lineSeparator);
 		file.writeBytes ("    <td><h3 align=\"center\"><font face=\"Verdana, Arial, Helvetica, sans-serif\">"+giorni[5]+"</font></h3></td>" + lineSeparator);
 		file.writeBytes ("    <td><h3 align=\"center\"><font face=\"Verdana, Arial, Helvetica, sans-serif\">"+giorni[6]+"</font></h3></td>" + lineSeparator);
 		file.writeBytes ("    <td><font face=\"Verdana, Arial, Helvetica, sans-serif\">&nbsp;</font></td>" + lineSeparator);
 		file.writeBytes ("  </tr>" + lineSeparator);
 		for (i=0;i<v.size();i++) {
	 		file.writeBytes ("  <tr> " + lineSeparator);
	 		file.writeBytes ("    <td><font face=\"Verdana, Arial, Helvetica, sans-serif\">"+((Vector) v.elementAt(i)).elementAt(4)+"&nbsp;</font></td>" + lineSeparator);
	 		file.writeBytes ("    <td><font face=\"Verdana, Arial, Helvetica, sans-serif\">"+((Vector) v.elementAt(i)).elementAt(5)+"&nbsp;</font></td>" + lineSeparator);
	 		file.writeBytes ("    <td><font face=\"Verdana, Arial, Helvetica, sans-serif\">"+((Vector) v.elementAt(i)).elementAt(6)+"&nbsp;</font></td>" + lineSeparator);
	 		file.writeBytes ("    <td><font face=\"Verdana, Arial, Helvetica, sans-serif\">&nbsp;</font></td>" + lineSeparator);
	 		file.writeBytes ("  </tr>" + lineSeparator);
 		}
 		file.writeBytes ("</table>" + lineSeparator);
 		file.writeBytes ("</body>" + lineSeparator);
 		file.writeBytes ("</html>" + lineSeparator);
 		file.close();
 		} catch (Exception e) {e.printStackTrace ();} }



      
}    	