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
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;


/*import javax.swing.border.*;

import java.awt.*;
import java.awt.event.ActionEvent;*/



public class jpanelLista extends JPanel {

	JScrollPane jspSel;
	JTable tableSel,tableLista;
	TitledBorder border;
	Vector tableColumnsName;
	static Vector vRicette= new Vector();
	jpanelRicette panelRicette;
	jpanelMenu panelMenu;
	JList listRicette;
	JButton buttonElimina;
	JCheckBox chkRicetteIngr,chkRicetteSenzaIngr,chkLista;
	jbuttonStampa butStampa;
	ConnectionHsqlDB connection;
	Vector vColumns;
	JPanel panelX2;
	JScrollPane jspLista;
	
	public jpanelLista(final jpanelRicette panelRicette,final ConnectionHsqlDB connection) {
		this.panelRicette=panelRicette;
		this.connection=connection;
		final Border border4 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,new Color(87,0,174),new Color (255,255,255));
		border = BorderFactory.createTitledBorder(border4,
	  			Messages.getString("jpanelLista.selectedRecipesWithIngredients"),TitledBorder.LEFT,TitledBorder.TOP,Font.getFont("Arial"),new Color(87,0,174)); //Color(87,0,174) //$NON-NLS-1$ //$NON-NLS-2$
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		tableColumnsName = new Vector();
		tableColumnsName.add(Messages.getString("jpanelLista.recipe")); //$NON-NLS-1$
		tableColumnsName.add(Messages.getString("Persons")); //$NON-NLS-1$
		tableColumnsName.add(Messages.getString("item")); //$NON-NLS-1$
		tableColumnsName.add(Messages.getString("jpanelLista.Quantity")); //$NON-NLS-1$
		tableColumnsName.add(Messages.getString("jpanelLista.unit")); //$NON-NLS-1$
		tableSel = new JTable (new Vector(),tableColumnsName){
	  		public boolean isCellEditable(int row,int column) {
	  			return false;
	  			}
		};
		add (Box.createVerticalStrut(5));
		JPanel panelX = new JPanel();
		panelX.setLayout(new BoxLayout(panelX,BoxLayout.X_AXIS));
		panelX.add(Box.createHorizontalStrut(10));
		panelX.add (jspSel=new JScrollPane(tableSel));
		jspSel.getViewport().setBackground(new Color(255,245,215));
		jspSel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		jspSel.setBorder(border);
		JPanel panelY = new JPanel();
		panelY.setLayout(new BoxLayout(panelY,BoxLayout.Y_AXIS));
		JScrollPane scroll = new JScrollPane(listRicette=new JList());
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setPreferredSize(new Dimension(100,500));
		border = BorderFactory.createTitledBorder(border4,
	  			Messages.getString("jpanelLista.SelectedRecipes"),TitledBorder.LEFT,TitledBorder.TOP,Font.getFont("Arial"),new Color(87,0,174)); //Color(87,0,174) //$NON-NLS-1$ //$NON-NLS-2$
		listRicette.setCellRenderer(new MyCellRenderer());
		panelX.add(Box.createHorizontalStrut(10));
		JPanel panelbut = new JPanel();
		panelbut.add(buttonElimina = new JButton(Messages.getString("Delete"),new ImageIcon("images/pulsanti/Delete24.gif"))); //$NON-NLS-1$ //$NON-NLS-2$
		buttonElimina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteRicettaTable();
				panelMenu.setListRicette();
			}
			
		});
		panelY.add(panelbut);
		panelY.add(Box.createVerticalStrut(5));
		panelY.add(scroll);
		panelY.add(Box.createVerticalStrut(5));
		panelY.setBorder(border);
		panelX.add(panelY);
		add(panelX);
		add (Box.createVerticalStrut(5));
		
		panelX2= new JPanel();
		panelX2.setLayout(new BoxLayout(panelX2,BoxLayout.X_AXIS));
		panelX2.add(Box.createHorizontalStrut(10));
		
		vColumns = (Vector) connection.getColumnsName("lista", //$NON-NLS-1$
				"prodotto,quantita,misura,categoria,dispensa"); //$NON-NLS-1$
		
		
		tableLista = new JTable(new Vector(),vColumns);
		panelX2.add (jspLista=new JScrollPane(tableLista));
		jspLista.getViewport().setBackground(new Color(255,245,215));
		jspLista.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		border = BorderFactory.createTitledBorder(border4,
	  			Messages.getString("jpanelLista.ShoppingList"),TitledBorder.LEFT,TitledBorder.TOP,Font.getFont("Arial"),new Color(87,0,174)); //Color(87,0,174) //$NON-NLS-1$ //$NON-NLS-2$
		panelX2.add(Box.createHorizontalStrut(10));
		JPanel panelButton = new JPanel();
		panelButton.setLayout(new BoxLayout(panelButton,BoxLayout.Y_AXIS));
		//panelButton.setAlignmentX(JComponent.RIGHT_ALIGNMENT);
		panelButton.add(Box.createVerticalStrut(10));
		JButton butAggiungi = new JButton(Messages.getString("Add"),new ImageIcon("images/pulsanti/RowInsertAfter24.gif")); //$NON-NLS-1$ //$NON-NLS-2$
		butAggiungi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String s[] = {"","","","",""}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
				((DefaultTableModel)tableLista.getModel()).addRow(s);
			}
			
		});
		JButton butElimina = new JButton(Messages.getString("Delete"),new ImageIcon("images/pulsanti/RowDelete24.gif")); //$NON-NLS-1$ //$NON-NLS-2$
		butElimina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tableLista.getSelectedRow()!=-1) 
				((DefaultTableModel)tableLista.getModel()).removeRow(tableLista.getSelectedRow());
				else
					JOptionPane.showMessageDialog(null,Messages.getString("jpanelLista.SelectRowToDelete")); //$NON-NLS-1$
					
			}
			
		});
		butStampa = new jbuttonStampa();
		butStampa.setPanelLista(this);
		JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p1.add(butAggiungi);
		p1.add(butElimina);
		panelButton.add(p1);
		panelButton.add(Box.createVerticalStrut(10));
		
		
		
		JPanel p2 = new JPanel();
		p2.setLayout(new BoxLayout(p2,BoxLayout.Y_AXIS));
		p2.add(butStampa);
		//panelButton.add(p2);
		p2.setPreferredSize(new Dimension(220,150));
		p2.setBorder(border4);
		p2.add(chkRicetteIngr=new JCheckBox(Messages.getString("jpanelLista.selectedRecipesWithIngredients"))); //$NON-NLS-1$
		p2.add(chkRicetteSenzaIngr=new JCheckBox(Messages.getString("jpanelLista.selectedRecipesWithoutIngredients"))); //$NON-NLS-1$
		p2.add(chkLista=new JCheckBox(Messages.getString("jpanelLista.ShoppingList"))); //$NON-NLS-1$
		chkLista.setSelected(true);
		//panelButton.add(Box.createVerticalStrut(10));
		
		p1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p1.add(p2);
		panelButton.add(p1);
		if (framePrincipale.bRisoluzione800)
			panelButton.add(Box.createVerticalStrut(10));
		else
			panelButton.add(Box.createVerticalStrut(100));
		panelX2.add(panelButton);
		panelX2.add(Box.createHorizontalStrut(10));
		panelX2.setBorder(border);
		add(panelX2);
		add(Box.createVerticalStrut(10));
		
		panelRicette.getPanelDescrizione().setPanelLista(this);
		}
	
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
	
	public void addRicettaTable () {
		String nomeRicetta = panelRicette.getPanelDescrizione().getNomeRicetta();
		String porzioni = panelRicette.getPanelDescrizione().getPorzioni();
		for (int riga=0;riga<panelRicette.getPanelDescrizione().getNumRigheTableIngr();riga++)
		{
			Vector temp = new Vector();			
			temp.add(nomeRicetta);
			temp.add(porzioni);
			temp.addAll(panelRicette.getPanelDescrizione().getListRigaTabellaIngr(riga));
			((DefaultTableModel)tableSel.getModel()).addRow(temp);
		}
		vRicette.add(nomeRicetta);
		listRicette.setListData(vRicette);
		
		
		 
	}
	
	private void deleteRicettaTable () {
		if (listRicette.getSelectedIndex()!=-1) {
			String nomeRicetta=listRicette.getSelectedValue().toString();
	 
			 int numRigheDaCancellare=0;
			 int numPrimaRiga=0;
			 boolean isPrima=true;
			 aggiornaLista(nomeRicetta);
			 DefaultTableModel tableModel =(DefaultTableModel)tableSel.getModel();
			for (int riga=0; riga < tableSel.getRowCount(); riga++) {
					if (tableSel.getValueAt(riga,0).equals(nomeRicetta)) {
						if (isPrima) {
							numPrimaRiga=riga;
							isPrima=false;
						}
						numRigheDaCancellare++;
					}	
	
			}
			
			for (int n=0; n<numRigheDaCancellare;n++) {
				tableModel.removeRow(numPrimaRiga);
			}
			vRicette.remove(nomeRicetta);
			listRicette.setListData(vRicette);
		}
		else
			JOptionPane.showMessageDialog(null,Messages.getString("jpanelLista.SelectRowToDelete")); //$NON-NLS-1$
	}
	
	public void generaLista () {
		Vector v = new Vector();
		int riga=tableLista.getRowCount();
		connection.esegui("DELETE FROM lista"); //$NON-NLS-1$
		
		for (riga=0; riga<tableSel.getRowCount();riga++) {
			v.add(tableSel.getValueAt(riga,2));
		}
		HashSet h = new HashSet(v.subList(0,v.size()));
		Iterator i = h.iterator();
		while (i.hasNext()) {
			String prodotto = (String) i.next();
			String misura=""; //$NON-NLS-1$
			double quantita=0;
			for (riga=0; riga<tableSel.getRowCount();riga++) {
				if (prodotto.equals(tableSel.getValueAt(riga,2))) {
					misura=(String)tableSel.getValueAt(riga,4);
					quantita+=Double.parseDouble(((String)tableSel.getValueAt(riga,3)).replace(',','.'));
				}
			}
			String campi = connection.getStringRecord(
					"Select categoria,dispensa from articoli,categorie " + //$NON-NLS-1$
					"where articoli.IDcategoria=categorie.ID and " + //$NON-NLS-1$
					"descrizione='"+prodotto+"'"); //$NON-NLS-1$ //$NON-NLS-2$
			int index = campi.substring(0,campi.length()-1).lastIndexOf(" "); //$NON-NLS-1$
			String inDispensa = (campi.substring(index+1).equals("false ")?Messages.getString("ToBuy"):Messages.getString("inLarder")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			connection.esegui("INSERT INTO lista " + //$NON-NLS-1$
					"(prodotto,quantita,misura,categoria,dispensa) VALUES " + //$NON-NLS-1$
					"('"+prodotto+"',"+ //$NON-NLS-1$ //$NON-NLS-2$
					Double.toString(quantita)+",'"+ //$NON-NLS-1$
					misura+"','"+ //$NON-NLS-1$
					campi.substring(0,index)+"','"+ //$NON-NLS-1$
					inDispensa+"')"); //$NON-NLS-1$
			
		}
		Vector tableData=connection.getTableDataIngredienti("Select prodotto,quantita,misura,categoria,dispensa from lista order by categoria",1); //$NON-NLS-1$
		tableLista = new JTable(tableData,vColumns);
		panelX2.remove(jspLista);
		panelX2.add (jspLista=new JScrollPane(tableLista),1);
		jspLista.getViewport().setBackground(new Color(255,245,215));
		jspLista.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		panelX2.validate();
	}
	
	
	
	public boolean isInElenco(String nomeRicetta) {
		return vRicette.contains(nomeRicetta);
	}
	
	private void aggiornaLista(String nomeRicetta) {
		int rigaDaEliminare=-1;
		double quantita;
		for (int riga=0; riga<tableSel.getRowCount();riga++) {
			if (nomeRicetta.equals(tableSel.getValueAt(riga,0))) {
				String prodotto =(String)tableSel.getValueAt(riga,2);
				quantita=Double.parseDouble(((String)tableSel.getValueAt(riga,3)).replace(',','.'));
				for (int r=0;r<tableLista.getRowCount();r++) {
					if (prodotto.equals(tableLista.getValueAt(r,0))) {
						quantita= Double.parseDouble(((String)tableLista.getValueAt(r,1)).replace(',','.'))-quantita;
						tableLista.setValueAt((Double.toString(quantita)).replace('.',','),r,1);
						rigaDaEliminare=r;
					}
				}
				if (quantita==0) ((DefaultTableModel)tableLista.getModel()).removeRow(rigaDaEliminare);
			}
		}
		
	}
	
	public Hashtable getHashDati () {
		Hashtable hash = new Hashtable();
		hash.put("ricetteSelezionate",vRicette); //$NON-NLS-1$
		hash.put("ricetteSelezionateConIngredienti",((DefaultTableModel)tableSel.getModel()).getDataVector()); //$NON-NLS-1$
		hash.put("listaSpesa",((DefaultTableModel)tableLista.getModel()).getDataVector()); //$NON-NLS-1$
		return hash;
	}
	
	public boolean [] getOpzioniStampa() {
		boolean b[]={chkRicetteIngr.isSelected(),
					chkRicetteSenzaIngr.isSelected(),
					chkLista.isSelected()};
		return b;
	}
	
	public boolean isVectorRicetteEmpty() {
		return vRicette.isEmpty();
	}
	
	public void setPanelMenu(jpanelMenu panelMenu) {
		this.panelMenu=panelMenu;
	}
	
}	
