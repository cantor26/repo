/*
 * Created on 19-feb-2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author User
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class jpanelRicerca extends JPanel {
	
	ConnectionHsqlDB connection;
	JList listTipologia,listRicette;
	JTextField txtRicetta,txtIngr1,txtIngr2,txtIngr3;
	boolean seleziona=true;
	jpanelDescrizione panelDescrizione;
	
	public jpanelRicerca(final ConnectionHsqlDB connection) {
		
		this.connection=connection;
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		add(Box.createVerticalStrut(10));
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		JScrollPane scroll = new JScrollPane(listTipologia=new JList());
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		listTipologia.setCellRenderer(new MyCellRenderer());
		Vector v;
		v=connection.getComboRecord("Select tipologia from tipologie"); //$NON-NLS-1$
		v.add(0,Messages.getString("jpanelRicerca.All")); //$NON-NLS-1$
		listTipologia.setListData(v);
		listTipologia.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				seleziona=true;
			}
			public void mouseClicked(MouseEvent e) {
				seleziona=true;
			}
		});
		listTipologia.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				
				if (!listTipologia.isSelectionEmpty() && seleziona) {
					txtIngr1.setText(""); //$NON-NLS-1$
					txtIngr2.setText(""); //$NON-NLS-1$
					txtIngr3.setText(""); //$NON-NLS-1$
					txtRicetta.setText(""); //$NON-NLS-1$
					String sTipologia= (String)listTipologia.getSelectedValue();
					if (sTipologia.equals(Messages.getString("jpanelRicerca.All2"))) //$NON-NLS-1$
						listRicette.setListData(connection.getComboRecord("Select nomericetta from ricette order by nomericetta")); //$NON-NLS-1$
					else {
						sTipologia= sTipologia.substring(0,sTipologia.length()-1);
						String ID = connection.getCampo(
								"Select ID from tipologie where tipologia ='"+sTipologia+"'"); //$NON-NLS-1$ //$NON-NLS-2$
						listRicette.setListData(connection.getComboRecord("Select nomericetta from ricette " + //$NON-NLS-1$
							"where IDtipologia ="+ID+" order by nomericetta")); //$NON-NLS-1$ //$NON-NLS-2$
						}	
					if (listRicette.getFirstVisibleIndex()!=-1) listRicette.setSelectedIndex(0);
				}
			}
		});
	  	final Border border4 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,new Color(87,0,174),new Color (255,255,255));
	  	Border border = BorderFactory.createTitledBorder(border4,
	  			Messages.getString("jpanelRicerca.Search"),TitledBorder.LEFT,TitledBorder.TOP,Font.getFont("Arial"),new Color(87,0,174)); //Color(87,0,174) //$NON-NLS-1$ //$NON-NLS-2$
	  	panel.setBorder(border);
	  	panel.add(scroll);
	  	panel.add(Box.createVerticalStrut(10));
		JPanel panelOriz = new JPanel();
		panelOriz.setLayout(new BoxLayout(panelOriz,BoxLayout.X_AXIS));
		panelOriz.add(new JLabel(Messages.getString("jpanelRicerca.NameRecipe"))); //$NON-NLS-1$
		panelOriz.add(Box.createHorizontalStrut(10));
		panelOriz.add(txtRicetta=new JTextField(30));
		
		txtRicetta.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				listTipologia.clearSelection();
				txtIngr1.setText(""); //$NON-NLS-1$
				txtIngr2.setText(""); //$NON-NLS-1$
				txtIngr3.setText(""); //$NON-NLS-1$
				listRicette.setListData(connection.getComboRecord("Select nomericetta from ricette " + //$NON-NLS-1$
						"where nomericetta like '%"+txtRicetta.getText().toLowerCase()+"%' order by nomericetta")); //$NON-NLS-1$ //$NON-NLS-2$
				listRicette.setSelectedIndex(0);
				}
		});
		panel.add(panelOriz);
		panel.add(Box.createVerticalStrut(10));
		JPanel panelOriz2 = new JPanel();
		panelOriz2.setLayout(new BoxLayout(panelOriz2,BoxLayout.X_AXIS));
		panelOriz2.add(new JLabel(Messages.getString("jpanelRicerca.Ingredients"))); //$NON-NLS-1$
		panelOriz2.add(Box.createHorizontalStrut(10));
		panelOriz2.add(txtIngr1=new JTextField(10));
		panelOriz2.add(Box.createHorizontalStrut(10));
		panelOriz2.add(txtIngr2=new JTextField(10));
		panelOriz2.add(Box.createHorizontalStrut(10));
		panelOriz2.add(txtIngr3=new JTextField(10));
		txtIngr1.addKeyListener(keyTextField);
		txtIngr2.addKeyListener(keyTextField);
		txtIngr3.addKeyListener(keyTextField);
		panel.add(panelOriz2);
		panel.add(Box.createVerticalStrut(10));
		add(panel);
		JScrollPane scroll2 = new JScrollPane(listRicette=new JList());
	  	//final Border border4 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,new Color(87,0,174),new Color (255,255,255));
	  	Border border2 = BorderFactory.createTitledBorder(border4,
	  			Messages.getString("jpanelRicerca.SearchResult"),TitledBorder.LEFT,TitledBorder.TOP,Font.getFont("Arial"),new Color(87,0,174)); //Color(87,0,174) //$NON-NLS-1$ //$NON-NLS-2$
	  	scroll2.setBorder(border2);
	  	
		scroll2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		listRicette.setCellRenderer(new MyCellRenderer());
		add(scroll2);
		listRicette.setListData(connection.getComboRecord("Select nomericetta from ricette order by nomericetta")); //$NON-NLS-1$
		scroll2.setPreferredSize(new Dimension(300,500));
		listRicette.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (!listRicette.isSelectionEmpty()) {
					String sRicetta= (String)listRicette.getSelectedValue();
					sRicetta= sRicetta.substring(0,sRicetta.length()-1);
					seleziona=false;
					listTipologia.setSelectedValue(connection.getCampo("Select tipologia from ricette,tipologie " + //$NON-NLS-1$
							"where tipologie.ID=ricette.IDtipologia and " + //$NON-NLS-1$
							"nomericetta ='"+sRicetta+"'")+" ",false); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					listTipologia.ensureIndexIsVisible(listTipologia.getSelectedIndex());
					aggiornaDati();
				}	
			}
		});
		listRicette.setSelectedIndex(0);
	}

	KeyListener keyTextField = new KeyAdapter() {
		public void keyReleased(KeyEvent e) {
			 listTipologia.clearSelection();
			 txtRicetta.setText(""); //$NON-NLS-1$
			 boolean bIngr1=txtIngr1.getText().equals(""); //$NON-NLS-1$
			 boolean bIngr2=txtIngr2.getText().equals(""); //$NON-NLS-1$
			 boolean bIngr3=txtIngr3.getText().equals(""); //$NON-NLS-1$
			 Vector v1,v2,v3;
			 
			if (bIngr2 && bIngr3 && !bIngr1) {
				listRicette.setListData(v1=connection.getComboRecord(
						"Select nomericetta from ricette,ingredienti,articoli " + //$NON-NLS-1$
						"where ingredienti.IDricetta=ricette.ID and " + //$NON-NLS-1$
						"ingredienti.IDarticolo = Articoli.ID and " + //$NON-NLS-1$
						"articoli.descrizione like '"+txtIngr1.getText().toLowerCase()+"%' " + //$NON-NLS-1$ //$NON-NLS-2$
						"order by nomericetta")); //$NON-NLS-1$
				if (!v1.isEmpty()) listRicette.setSelectedIndex(0);
			}
			else if (bIngr1 && bIngr2 && !bIngr3) {
				listRicette.setListData(v1=connection.getComboRecord(
						"Select nomericetta from ricette,ingredienti,articoli " + //$NON-NLS-1$
						"where ingredienti.IDricetta=ricette.ID and " + //$NON-NLS-1$
						"ingredienti.IDarticolo = Articoli.ID and " + //$NON-NLS-1$
						"articoli.descrizione like '"+txtIngr3.getText().toLowerCase()+"%' " + //$NON-NLS-1$ //$NON-NLS-2$
						"order by nomericetta")); //$NON-NLS-1$
				if (!v1.isEmpty()) listRicette.setSelectedIndex(0);	
			}	
			else if (bIngr1 && bIngr3 && !bIngr2) {
				listRicette.setListData(v1=connection.getComboRecord(
						"Select nomericetta from ricette,ingredienti,articoli " + //$NON-NLS-1$
						"where ingredienti.IDricetta=ricette.ID and " + //$NON-NLS-1$
						"ingredienti.IDarticolo = Articoli.ID and " + //$NON-NLS-1$
						"articoli.descrizione like '"+txtIngr2.getText().toLowerCase()+"%' " + //$NON-NLS-1$ //$NON-NLS-2$
						"order by nomericetta")); //$NON-NLS-1$
				if (!v1.isEmpty()) listRicette.setSelectedIndex(0);
			}	
			else if (bIngr1 && bIngr2 && bIngr3) {
					listRicette.setListData(connection.getComboRecord("Select nomericetta from ricette order by nomericetta")); //$NON-NLS-1$
					listRicette.setSelectedIndex(0);
			}
			else if (!bIngr1 && !bIngr2 && !bIngr3) {
				v1= connection.getComboRecord(
						"Select nomericetta from ricette,ingredienti,articoli " + //$NON-NLS-1$
						"where ingredienti.IDricetta=ricette.ID and " + //$NON-NLS-1$
						"ingredienti.IDarticolo = Articoli.ID and " + //$NON-NLS-1$
						"articoli.descrizione like '"+txtIngr1.getText().toLowerCase()+"%' " + //$NON-NLS-1$ //$NON-NLS-2$
						"order by nomericetta"); //$NON-NLS-1$
				v2= connection.getComboRecord(
						"Select nomericetta from ricette,ingredienti,articoli " + //$NON-NLS-1$
						"where ingredienti.IDricetta=ricette.ID and " + //$NON-NLS-1$
						"ingredienti.IDarticolo = Articoli.ID and " + //$NON-NLS-1$
						"articoli.descrizione like '"+txtIngr2.getText().toLowerCase()+"%' " + //$NON-NLS-1$ //$NON-NLS-2$
						"order by nomericetta"); //$NON-NLS-1$
				v3 =connection.getComboRecord(
						"Select nomericetta from ricette,ingredienti,articoli " + //$NON-NLS-1$
						"where ingredienti.IDricetta=ricette.ID and " + //$NON-NLS-1$
						"ingredienti.IDarticolo = Articoli.ID and " + //$NON-NLS-1$
						"articoli.descrizione like '"+txtIngr3.getText().toLowerCase()+"%' " + //$NON-NLS-1$ //$NON-NLS-2$
						"order by nomericetta"); //$NON-NLS-1$
				v2.retainAll(v3.subList(0,v3.size()));
				v1.retainAll(v2.subList(0,v2.size()));
				listRicette.setListData(v1);
				if (!v1.isEmpty()) listRicette.setSelectedIndex(0);
			}	
			else if (bIngr1&& !bIngr2 && !bIngr3) {
				v2= connection.getComboRecord(
						"Select nomericetta from ricette,ingredienti,articoli " + //$NON-NLS-1$
						"where ingredienti.IDricetta=ricette.ID and " + //$NON-NLS-1$
						"ingredienti.IDarticolo = Articoli.ID and " + //$NON-NLS-1$
						"articoli.descrizione like '"+txtIngr2.getText().toLowerCase()+"%' " + //$NON-NLS-1$ //$NON-NLS-2$
						"order by nomericetta"); //$NON-NLS-1$
				v3 =connection.getComboRecord(
						"Select nomericetta from ricette,ingredienti,articoli " + //$NON-NLS-1$
						"where ingredienti.IDricetta=ricette.ID and " + //$NON-NLS-1$
						"ingredienti.IDarticolo = Articoli.ID and " + //$NON-NLS-1$
						"articoli.descrizione like '"+txtIngr3.getText().toLowerCase()+"%' " + //$NON-NLS-1$ //$NON-NLS-2$
						"order by nomericetta"); //$NON-NLS-1$
				v2.retainAll(v3.subList(0,v3.size()));
				listRicette.setListData(v2);
				if (!v2.isEmpty()) listRicette.setSelectedIndex(0);
				
			}	
			else if (bIngr2&& !bIngr1 && !bIngr3) {
				v1= connection.getComboRecord(
						"Select nomericetta from ricette,ingredienti,articoli " + //$NON-NLS-1$
						"where ingredienti.IDricetta=ricette.ID and " + //$NON-NLS-1$
						"ingredienti.IDarticolo = Articoli.ID and " + //$NON-NLS-1$
						"articoli.descrizione like '"+txtIngr1.getText().toLowerCase()+"%' " + //$NON-NLS-1$ //$NON-NLS-2$
						"order by nomericetta"); //$NON-NLS-1$
				v3 =connection.getComboRecord(
						"Select nomericetta from ricette,ingredienti,articoli " + //$NON-NLS-1$
						"where ingredienti.IDricetta=ricette.ID and " + //$NON-NLS-1$
						"ingredienti.IDarticolo = Articoli.ID and " + //$NON-NLS-1$
						"articoli.descrizione like '"+txtIngr3.getText().toLowerCase()+"%' " + //$NON-NLS-1$ //$NON-NLS-2$
						"order by nomericetta"); //$NON-NLS-1$
				v1.retainAll(v3.subList(0,v3.size()));
				listRicette.setListData(v1);
				if (!v1.isEmpty()) listRicette.setSelectedIndex(0);
				
			}	
			else if (bIngr3&& !bIngr2 && !bIngr1) {
				v1= connection.getComboRecord(
						"Select nomericetta from ricette,ingredienti,articoli " + //$NON-NLS-1$
						"where ingredienti.IDricetta=ricette.ID and " + //$NON-NLS-1$
						"ingredienti.IDarticolo = Articoli.ID and " + //$NON-NLS-1$
						"articoli.descrizione like '"+txtIngr1.getText().toLowerCase()+"%' " + //$NON-NLS-1$ //$NON-NLS-2$
						"order by nomericetta"); //$NON-NLS-1$
				v2 =connection.getComboRecord(
						"Select nomericetta from ricette,ingredienti,articoli " + //$NON-NLS-1$
						"where ingredienti.IDricetta=ricette.ID and " + //$NON-NLS-1$
						"ingredienti.IDarticolo = Articoli.ID and " + //$NON-NLS-1$
						"articoli.descrizione like '"+txtIngr2.getText().toLowerCase()+"%' " + //$NON-NLS-1$ //$NON-NLS-2$
						"order by nomericetta"); //$NON-NLS-1$
				v1.retainAll(v2.subList(0,v2.size()));
				listRicette.setListData(v1);
				if (!v1.isEmpty()) listRicette.setSelectedIndex(0);
				
			}
			}
	};

 class MyCellRenderer extends JLabel implements ListCellRenderer {
	     final ImageIcon iconFreccia =new ImageIcon("images/pulsanti/orgdiamd.gif"); //$NON-NLS-1$
	     	     // This is the only method defined by ListCellRenderer.
	     // We just reconfigure the JLabel each time we're called.

	     public Component getListCellRendererComponent(
	       JList list,
	       Object value,            // value to display
	       int index,               // cell index
	       boolean isSelected,      // is the cell selected
	       boolean cellHasFocus)    // the list and the cell have the focus
	     {
	         String s = value.toString();
	         setText(s);
	         setIcon(iconFreccia);
	   	   if (isSelected) {
	             setBackground(list.getSelectionBackground());
		       setForeground(list.getSelectionForeground());
		   }
	         else {
		       setBackground(list.getBackground());
		       setForeground(list.getForeground());
		   }
		   setEnabled(list.isEnabled());
		   setFont(list.getFont());
	         setOpaque(true);
	         return this;
	     }
	 }
	
	public String getNomeRicetta() {
		String nome= (String) listRicette.getSelectedValue();
		return nome.substring(0,nome.length()-1);
	}
	
	public String getTipologia() {
		return (String) listTipologia.getSelectedValue();
	}
	
	public int getNumRicette() {
		Vector vRicette=connection.getComboRecord("Select nomericetta from ricette order by nomericetta"); //$NON-NLS-1$
		return vRicette.size();
	}
	
	public void aggiornaJListRicette () {
		int index = listRicette.getSelectedIndex();
		Vector vRicette=connection.getComboRecord("Select nomericetta from ricette order by nomericetta"); //$NON-NLS-1$
		listRicette.setListData(vRicette);
		if (index!=-1 && index!=vRicette.size()) 
			listRicette.setSelectedIndex(index);
		else
			listRicette.setSelectedIndex(0);
		
	}
	
	public void setPanelDescrizione (jpanelDescrizione panelDescrizione) {
		this.panelDescrizione=panelDescrizione;
    }
	
	private void aggiornaDati() {
		if (panelDescrizione!=null) {
			String sNome =getNomeRicetta();
			panelDescrizione.setNomeRicetta(sNome);
			sNome=framePrincipale.replace(sNome,"'","''"); //$NON-NLS-1$ //$NON-NLS-2$
			String ID = connection.getCampo("Select ID from ricette where nomericetta='"+sNome+"'"); //$NON-NLS-1$ //$NON-NLS-2$
			panelDescrizione.setNuovaRicetta(false);
			panelDescrizione.setIDricetta(ID);
			Vector v= connection.getVectorRecord("Select tempo,difficolta,descrizione,porzioni from ricette " + //$NON-NLS-1$
					"where nomericetta ='"+sNome+"'"); //$NON-NLS-1$ //$NON-NLS-2$
			String sTemp=(String) v.get(0);
			panelDescrizione.setTempo(((sTemp==null)?"***":sTemp)); //$NON-NLS-1$
			sTemp=(String) v.get(1);
			panelDescrizione.setDifficolta(((sTemp==null)?"***":sTemp)); //$NON-NLS-1$
			panelDescrizione.setDescrizione((String) v.get(2));
			panelDescrizione.setTipologia(getTipologia());
			panelDescrizione.setTabella("Select articoli.descrizione,quantita,misura from ricette,ingredienti,articoli,misure "+ //$NON-NLS-1$
					"where ingredienti.IDarticolo= articoli.ID and " + //$NON-NLS-1$
					"articoli.IDmisura= misure.ID and " + //$NON-NLS-1$
					"ricette.ID= ingredienti.IDricetta and " + //$NON-NLS-1$
					"nomericetta= '"+ sNome +"' order by descrizione",true,1); //$NON-NLS-1$ //$NON-NLS-2$
			panelDescrizione.setPorzioni((String) v.get(3));
		}
	}
}
