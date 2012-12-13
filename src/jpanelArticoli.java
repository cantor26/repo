/*
 * Created on 4-mag-2006
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
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.*;

public class jpanelArticoli extends JPanel {
	
	ConnectionHsqlDB connection;
	JTextField txtArticolo;
	JComboBox comboMisura,comboCategoria;
	JCheckBox chkDispensa;
	JTable table;
	jpanelDescrizione panelDescrizione;
	TitledBorder border;
	boolean isNuovo=true;
	
	public jpanelArticoli(final ConnectionHsqlDB connection) {
		this.connection=connection;
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		final Border border4 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,new Color(87,0,174),new Color (255,255,255));
		border = BorderFactory.createTitledBorder(border4,
	  			Messages.getString("jpanelArticoli.InsertionNewIngredient"),TitledBorder.LEFT,TitledBorder.TOP,Font.getFont("Arial"),new Color(87,0,174)); //Color(87,0,174) //$NON-NLS-1$ //$NON-NLS-2$
		setBorder(border);
		JPanel panelX1 = new JPanel();
		panelX1.setLayout(new BoxLayout(panelX1,BoxLayout.X_AXIS));
		panelX1.add(Box.createHorizontalStrut(30));
		panelX1.add(new JLabel(Messages.getString("item"))); //$NON-NLS-1$
		panelX1.add(Box.createHorizontalStrut(10));
		panelX1.add(txtArticolo= new JTextField());
		panelX1.add(Box.createHorizontalStrut(30));
		panelX1.add(new JLabel(Messages.getString("jpanelArticoli.category"))); //$NON-NLS-1$
		panelX1.add(Box.createHorizontalStrut(10));
		panelX1.add(comboCategoria= new JComboBox(connection.getComboRecord("Select categoria from categorie"))); //$NON-NLS-1$
		panelX1.add(Box.createHorizontalStrut(30));
		panelX1.add(new JLabel(Messages.getString("jpanelArticoli.unit"))); //$NON-NLS-1$
		panelX1.add(Box.createHorizontalStrut(10));
		panelX1.add(comboMisura= new JComboBox(connection.getComboRecord("Select misura from misure"))); //$NON-NLS-1$
		panelX1.add(Box.createHorizontalStrut(30));
		panelX1.add(chkDispensa= new JCheckBox(Messages.getString("inLarder"))); //$NON-NLS-1$
		panelX1.add(Box.createHorizontalStrut(30));
		add(Box.createVerticalStrut(10));
		add(panelX1);
		add(Box.createVerticalStrut(20));
		Vector tableColumnsName = connection.getColumnsName("articoli,misure,categorie", //$NON-NLS-1$
  		"descrizione,categoria,misura,dispensa"); //$NON-NLS-1$
		Vector tableData=connection.getTableDataArticoli(
				"Select articoli.descrizione,categoria,misura,dispensa from " + //$NON-NLS-1$
				"articoli,misure,categorie "+ //$NON-NLS-1$
				"where articoli.IDcategoria=categorie.ID and " + //$NON-NLS-1$
				"articoli.IDmisura= misure.ID " + //$NON-NLS-1$
				"order by descrizione"); //$NON-NLS-1$
		table 	= new JTable(tableData, tableColumnsName) {
			public boolean isCellEditable(int row,int column){
				return false;
			}
		};
		
		table.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				isNuovo=false;
				int selRow=table.rowAtPoint(new Point(e.getX(),e.getY()));
				aggiornaDati(selRow);
			}
		});
		JScrollPane jsp;
		add (jsp=new JScrollPane(table));
		jsp.getViewport().setBackground(new Color(255,245,215));
	  	jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	  	jsp.setPreferredSize(new Dimension(100,600));
	  	JPanel panelX2 = new JPanel();
	  	panelX2.setLayout(new BoxLayout(panelX2,BoxLayout.X_AXIS));
		panelX2.add(Box.createHorizontalStrut(20));
		JButton butNuovo,butSalva,butCancella;
		panelX2.add(butNuovo=new JButton(Messages.getString("jpanelArticoli.New"),new ImageIcon("images/pulsanti/New24.gif"))); //$NON-NLS-1$ //$NON-NLS-2$
		butNuovo.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				isNuovo=true;
				border.setTitle(Messages.getString("jpanelArticoli.InsertionNewIngredient")); //$NON-NLS-1$
				txtArticolo.setText(""); //$NON-NLS-1$
				chkDispensa.setSelected(false);
				repaint();
			}
		});	
		panelX2.add(Box.createHorizontalStrut(10));
		panelX2.add(butSalva=new JButton(Messages.getString("Save"),new ImageIcon("images/pulsanti/Save24.gif"))); //$NON-NLS-1$ //$NON-NLS-2$
		butSalva.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				String misura,IDmisura,IDcategoria;
				String articolo=txtArticolo.getText().toLowerCase();
				String categoria=((String)comboCategoria.getSelectedItem()).trim();
				int selRow;
				
				if (isNuovo) {
					 if (articolo.equals("")) { //$NON-NLS-1$
				   	 	JOptionPane.showMessageDialog(null,Messages.getString("jpanelArticoli.NoItemToAdd")); //$NON-NLS-1$
				   	 	return;
				   	 }
					 misura = ((String) comboMisura.getSelectedItem()).trim();
					 IDmisura = connection.getCampo("Select ID from misure where misura='"+misura+"'"); //$NON-NLS-1$ //$NON-NLS-2$
					 IDcategoria = connection.getCampo("Select ID from categorie where categoria='"+((String)comboCategoria.getSelectedItem()).trim()+"'"); //$NON-NLS-1$ //$NON-NLS-2$
				     if (connection.esegui("INSERT INTO articoli " + //$NON-NLS-1$
				     		"(descrizione,IDmisura,IDcategoria,dispensa) VALUES ('" +  //$NON-NLS-1$
							framePrincipale.replace(articolo,"'","''") +"',"+IDmisura+","+IDcategoria+","+chkDispensa.isSelected()+")"))  //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
				      {
					     String s[] = {"","","",""}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
						((DefaultTableModel)table.getModel()).insertRow(0,s);
						 //selRow=table.getRowCount()-1;
						 table.setValueAt(articolo,0,0);
						 table.setValueAt(categoria,0,1);
						 table.setValueAt(misura,0,2);
						 table.setValueAt((chkDispensa.isSelected()?Messages.getString("inLarder"):Messages.getString("ToBuy")),0,3); //$NON-NLS-1$ //$NON-NLS-2$
						 table.setRowSelectionInterval(0,0);
						 isNuovo=false;
						 border.setTitle(Messages.getString("jpanelArticoli.EditItem")+articolo); //$NON-NLS-1$
						 repaint();
				     }
				} 
				else {
					selRow = table.getSelectedRow();
					if (selRow==-1) {
						JOptionPane.showMessageDialog(null,Messages.getString("jpanelArticoli.noItemSelected")); //$NON-NLS-1$
						return;
					}
					misura =(String)table.getValueAt(selRow,2);
					if (!misura.equals(((String)comboMisura.getSelectedItem()).trim())) {
						JOptionPane.showMessageDialog(null,Messages.getString("jpanelArticoli.unitItemNoEditable")); //$NON-NLS-1$
				   	 	return;
					}
					
				   	IDmisura = connection.getCampo("Select ID from misure where misura='"+misura+"'"); //$NON-NLS-1$ //$NON-NLS-2$
				   	IDcategoria = connection.getCampo("Select ID from categorie where categoria='"+categoria+"'"); //$NON-NLS-1$ //$NON-NLS-2$
					
					connection.esegui("UPDATE articoli SET " + //$NON-NLS-1$
							"descrizione='"+framePrincipale.replace(articolo,"'","''")+ //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
							"',IDcategoria="+IDcategoria+"," + //$NON-NLS-1$ //$NON-NLS-2$
							"dispensa="+chkDispensa.isSelected()+" " + //$NON-NLS-1$ //$NON-NLS-2$
							"WHERE descrizione='"+framePrincipale.replace((String)table.getValueAt(selRow,0),"'","''")+"'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
					table.setValueAt(articolo,selRow,0);
					table.setValueAt(categoria,selRow,1);
					table.setValueAt((chkDispensa.isSelected()?Messages.getString("InLarder"):Messages.getString("ToBuy")),selRow,3); //$NON-NLS-1$ //$NON-NLS-2$
				}
				panelDescrizione.aggiornaComboSel();
			}
		});
		panelX2.add(Box.createHorizontalStrut(10));
		panelX2.add(butCancella=new JButton(Messages.getString("Delete"),new ImageIcon("images/pulsanti/Delete24.gif"))); //$NON-NLS-1$ //$NON-NLS-2$
		butCancella.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				String ID = connection.getCampo("Select ID from articoli where descrizione='"+framePrincipale.replace(txtArticolo.getText(),"'","''")+"'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				if (connection.esisteRecord("Select * from ingredienti where IDarticolo="+ID)) { //$NON-NLS-1$
					JOptionPane.showMessageDialog(null,Messages.getString("jpanelArticoli.IsNotPossibleToDelete")); //$NON-NLS-1$
					return;
				}
				else {
					connection.esegui("DELETE from articoli WHERE ID="+ID); //$NON-NLS-1$
					((DefaultTableModel)table.getModel()).removeRow(table.getSelectedRow());
					table.setRowSelectionInterval(0,0);
					aggiornaDati(0);
					panelDescrizione.aggiornaComboSel();
				}
			}
		});
		panelX2.add(Box.createHorizontalStrut(20));
		add(Box.createVerticalStrut(20));
		add(panelX2);
		add(Box.createVerticalStrut(30));
		
	}
	 
	public void setPanelDescrizione(jpanelDescrizione panelDescrizione) {
		this.panelDescrizione=panelDescrizione;
	}
	
	private void aggiornaDati(int selRow) {
		txtArticolo.setText((String)table.getValueAt(selRow,0));
		comboCategoria.setSelectedItem((String)table.getValueAt(selRow,1)+ " "); //$NON-NLS-1$
		comboMisura.setSelectedItem((String)table.getValueAt(selRow,2)+ " "); //$NON-NLS-1$
		boolean inDispensa =(((String)table.getValueAt(selRow,3)).equals(Messages.getString("inLarder"))?true:false); //$NON-NLS-1$
		chkDispensa.setSelected(inDispensa);
		border.setTitle(Messages.getString("jpanelArticoli.EditItem")+txtArticolo.getText()); //$NON-NLS-1$
		repaint();
	}
		
}
