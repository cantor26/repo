/*
 * Created on 19-feb-2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

import javax.swing.*;




public class jpanelRicette extends JSplitPane {
	
	ConnectionHsqlDB connection;
	jpanelRicerca panelRicerca;
	jpanelDescrizione panelDescrizione;
	
	
	public jpanelRicette(ConnectionHsqlDB connection,final JTabbedPane tabPane) {
		try {
		this.connection=connection;
		panelRicerca = new jpanelRicerca(connection);
		panelDescrizione = new jpanelDescrizione(connection,panelRicerca);
		panelDescrizione.setTabbedPane(tabPane);
		panelRicerca.setPanelDescrizione(panelDescrizione);
		setLeftComponent(panelRicerca);
		setRightComponent(panelDescrizione);
	    setContinuousLayout(true);
		setOneTouchExpandable(true);
		if(framePrincipale.bRisoluzione800)
			setDividerLocation(200);
		else
			setDividerLocation(300);
		} catch (Exception ex){
			ex.printStackTrace();
		}
		}
	
	public jpanelDescrizione getPanelDescrizione() {
		return panelDescrizione;
	}
	
	
}
