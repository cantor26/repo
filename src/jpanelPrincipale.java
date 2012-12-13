import javax.swing.*;
/*import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;*/

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;




public class jpanelPrincipale extends JPanel {
	
	JTabbedPane panelTab;
	jpanelRicette panelRicette;
	jpanelLista panelLista;
	jpanelMenu panelMenu;
	jpanelArticoli panelArticoli;
	
	
    public jpanelPrincipale() {
    	
    	ConnectionHsqlDB connection = new ConnectionHsqlDB("org.hsqldb.jdbcDriver","jdbc:hsqldb:ricette","sa","");; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
    	setLayout(new BorderLayout());
    	JPanel panelTitle = new JPanel();
    	//panelTitle.setLayout(new BoxLayout(panelTitle,BoxLayout.X_AXIS));
    	//panelTitle.setLayout(new FlowLayout(FlowLayout.RIGHT));
    	JButton buttonGuida,buttonLicenza;
		
		
		panelTitle.add(buttonGuida=new JButton(new ImageIcon("images/pulsanti/Information24.gif"))); //$NON-NLS-1$
		buttonGuida.setToolTipText(Messages.getString("jpanelPrincipale.Help")); //$NON-NLS-1$
		buttonGuida.addActionListener(new ActionListener(){
			public void actionPerformed (ActionEvent e) {
				//framePrincipale.mandaEmail("http://cucina.altervista.org/");
			}
		});
		JLabel labelTitle = new JLabel(new ImageIcon("images/titolo.jpg")); //$NON-NLS-1$
    	panelTitle.add(labelTitle);
		panelTitle.add(buttonLicenza=new JButton(new ImageIcon("images/pulsanti/Help24.gif"))); //$NON-NLS-1$
		buttonLicenza.addActionListener(new ActionListener(){
			public void actionPerformed (ActionEvent e) {
				Object[] message = new Object[2];
				message[0]=Messages.getString("jpanelPrincipale.Author" + " Gianluca Crocivera") + //$NON-NLS-1$
						Messages.getString("jpanelPrincipale.MailToAuthor"); //$NON-NLS-1$
				JTextArea txtArea = new JTextArea();
				txtArea.setText(Messages.getString("jpanelPrincipale.Licence") + //$NON-NLS-1$
						Messages.getString("jpanelPrincipale.NoWarranty") + //$NON-NLS-1$
						Messages.getString("jpanelPrincipale.NoWarranty2")); //$NON-NLS-1$
				txtArea.setWrapStyleWord(true);
				txtArea.setLineWrap(true);
				txtArea.setEditable(false);
				txtArea.setCaretPosition(0);
				JScrollPane scroll =new JScrollPane(txtArea);
				scroll.setPreferredSize(new Dimension(100,200));
				message[1]= scroll;
				String[] options = { 
			 		    Messages.getString("jpanelPrincipale.ContactAuthor"),Messages.getString("jpanelPrincipale.Undo")  //$NON-NLS-1$ //$NON-NLS-2$
			 		}; 
				int result = JOptionPane.showOptionDialog( 
			 		    null,                             // the parent that the dialog blocks 
			 		    message,                                    // the dialog message array 
			 		    Messages.getString("jpanelPrincipale.InformationOnAuthorAndLicence"), // the title of the dialog window  //$NON-NLS-1$
			 		    JOptionPane.DEFAULT_OPTION,                 // option type 
			 		    JOptionPane.INFORMATION_MESSAGE,            // message type 
			 		    null,                                       // optional icon, use null to use the default icon 
			 		    options,                                    // options string array, will be made into buttons 
			 		    options[0]                                  // option that should be made into a default button 
			 		); 
			 		switch(result) {
			 		   case 0:
			 		   //framePrincipale.mandaEmail("mailto:progjava2@yahoo.it?subject=software%20ricette");
			 			try {   
			 			Desktop desktop=Desktop.getDesktop();
			 			URI uriMailTo = new URI("mailto", "progjava2@yahoo.it?subject=ciao&body=ciao", null);
		                desktop.mail(uriMailTo); 
			 			}
			 			catch (Exception ex) {ex.printStackTrace();} 
			 		    break;
			 		   case 1: // connessione
			 		     break; 
			 		   
			 		} 
				
			}
		});
		buttonLicenza.setToolTipText(Messages.getString("jpanelPrincipale.InformationOnAuthorAndLicence")); //$NON-NLS-1$
		add(panelTitle,BorderLayout.NORTH);
		panelTab = new JTabbedPane ();
		panelTab.setFont(new Font(null,Font.PLAIN,16));
		panelRicette = new jpanelRicette(connection,panelTab);
		panelLista = new jpanelLista(panelRicette,connection);
		panelMenu = new jpanelMenu(panelRicette);
		panelArticoli = new jpanelArticoli(connection);
		panelArticoli.setPanelDescrizione(panelRicette.getPanelDescrizione());
		panelTab.addTab(Messages.getString("jpanelPrincipale.Recipes"),panelRicette); //$NON-NLS-1$
		panelTab.addTab(Messages.getString("jpanelPrincipale.ShoppingList"),panelLista); //$NON-NLS-1$
		panelTab.addTab(Messages.getString("jpanelPrincipale.WeeklyMenu"),panelMenu); //$NON-NLS-1$
		panelTab.addTab(Messages.getString("jpanelPrincipale.ManageIngredients"),panelArticoli); //$NON-NLS-1$
		add(panelTab,BorderLayout.CENTER);
		panelLista.setPanelMenu(panelMenu);
			
  
    }
    
     
}

