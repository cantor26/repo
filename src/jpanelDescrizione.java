/*
 * Created on 21-feb-2006
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

import java.util.*;
import java.io.*;
import java.util.List;

import javax.swing.border.*;
import javax.swing.table.*;

import java.awt.*;
import java.awt.event.*;



public class jpanelDescrizione extends JPanel {
	
	ConnectionHsqlDB connection;
	JTextField txtPorzioni,txtNome,txtTempo,txtDifficolta,txtQuantita;
	JComboBox comboTipologia,comboSel;
	JTextArea txtDescrizione; 
	JScrollPane jsp;
	JPanel panelY;
	JTable table;
	Vector tableColumnsName;
	jpanelRicerca panelRicerca;
	TitledBorder border;
	jpanelLista panelLista;
	jpanelMenu panelMenu;
	JTabbedPane panelTab;
	String porzioni;
	int selRow;
	TableColumn column;
	String IDricetta;
	boolean isNuova=false;
	boolean isComboItemListener=false;
	
	public jpanelDescrizione(final ConnectionHsqlDB connection,final jpanelRicerca panelRicerca) {
		this.panelRicerca=panelRicerca;
		this.connection=connection;
		String sNome = panelRicerca.getNomeRicetta();
		String ID = connection.getCampo("Select ID from ricette where nomericetta='"+sNome+"'"); //$NON-NLS-1$ //$NON-NLS-2$
		setIDricetta(ID);
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		add(Box.createVerticalStrut(10));
		JPanel panelDati = new JPanel();
		panelDati.setLayout(new BoxLayout(panelDati,BoxLayout.Y_AXIS));
		JPanel panelTitle = new JPanel();
		panelTitle.setLayout(new BoxLayout(panelTitle,BoxLayout.X_AXIS));
		panelTitle.add(Box.createHorizontalStrut(10));
		//panelTitle.add(new JLabel(new ImageIcon("images/icone/Scodella.gif")));
		//panelTitle.add(Box.createHorizontalStrut(10));
		panelTitle.add(txtNome = new JTextField(sNome));
		panelTitle.add(Box.createHorizontalStrut(10));
		txtNome.setHorizontalAlignment(SwingConstants.CENTER);
		txtNome.setFont(new Font(null,Font.BOLD,20));
		txtNome.setForeground(new Color (255, 91, 13));
		panelDati.add(panelTitle);
		Vector v= connection.getVectorRecord("Select tempo,difficolta,descrizione from ricette " + //$NON-NLS-1$
				"where nomericetta ='"+sNome+"'"); //$NON-NLS-1$ //$NON-NLS-2$
		
		JPanel panelX = new JPanel();
		panelX.setLayout(new BoxLayout(panelX,BoxLayout.X_AXIS));
		panelX.add(Box.createHorizontalStrut(10));
		panelX.add(new JLabel(Messages.getString("jpanelDescrizione.Typology"))); //$NON-NLS-1$
		final Border border4 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,new Color(87,0,174),new Color (255,255,255));
		panelX.add(comboTipologia=new JComboBox(connection.getComboRecord("Select tipologia from tipologie"))); //$NON-NLS-1$
		comboTipologia.setSelectedItem(panelRicerca.getTipologia());
		panelX.add(Box.createHorizontalStrut(10));
		panelX.add(new JLabel(Messages.getString("jpanelDescrizione.Time"))); //$NON-NLS-1$
		panelX.add(txtTempo = new JTextField((String)v.get(0)));
		panelX.add(Box.createHorizontalStrut(10));
		panelX.add(new JLabel(Messages.getString("jpanelDescrizione.Difficulty"))); //$NON-NLS-1$
		panelX.add(txtDifficolta = new JTextField((String)v.get(1)));
		panelX.add(Box.createHorizontalStrut(10));
		panelX.add(new JLabel(Messages.getString("jpanelDescrizione.Portions"))); //$NON-NLS-1$
		panelX.add(txtPorzioni = new JTextField("4")); //$NON-NLS-1$
		
		final JButton 	butAggiungi = new JButton(Messages.getString("jpanelDescrizione.AddRow"),new ImageIcon("images/pulsanti/RowInsertAfter24.gif")); //$NON-NLS-1$ //$NON-NLS-2$
		butAggiungi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String s[] = {"","",""}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				((DefaultTableModel)table.getModel()).addRow(s);
				selRow =table.getRowCount()-1;
				table.editCellAt(selRow,0);
				comboSel.showPopup();
			}
			
		});
		txtPorzioni.addFocusListener(new FocusAdapter () {
			public void focusGained(FocusEvent e) {
				porzioni= txtPorzioni.getText();
			}
			public void focusLost(FocusEvent e) {
				butAggiungi.grabFocus();
			}
		});
		
		txtPorzioni.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				try {
									
				String sPorzioni= txtPorzioni.getText();
				if (!sPorzioni.equals("")) { //$NON-NLS-1$
					double d = Double.parseDouble(sPorzioni)/Double.parseDouble(porzioni);
					setTabella("Select articoli.descrizione,quantita,misura from ricette,ingredienti,articoli,misure "+ //$NON-NLS-1$
							"where ingredienti.IDarticolo= articoli.ID and " + //$NON-NLS-1$
							"articoli.IDmisura= misure.ID and " + //$NON-NLS-1$
							"ricette.ID= ingredienti.IDricetta and " + //$NON-NLS-1$
							"nomericetta= '"+ panelRicerca.getNomeRicetta() +"' order by descrizione",true,d); //$NON-NLS-1$ //$NON-NLS-2$
					//porzioni=sPorzioni;
				}
				border.setTitle(Messages.getString("jpanelDescrizione.IngredientsFor")+sPorzioni+ Messages.getString("Persons")); //$NON-NLS-1$ //$NON-NLS-2$
				
				
			} catch (NumberFormatException ex ) {
				System.out.println(Messages.getString("jpanelDescrizione.DigitOnlyNumbers"));} //$NON-NLS-1$
				
			}
		
			public void keyTyped(KeyEvent ke) { 
				char c = ke.getKeyChar ();
				if (!((Character.isDigit (c) || (c == KeyEvent.VK_BACK_SPACE)))) {
					getToolkit().beep ();
					ke.consume ();
					
      				}
			
			}
			
		});
		panelX.add(Box.createHorizontalStrut(10));
		
		panelDati.add(Box.createVerticalStrut(10));
		panelDati.add(panelX);
		JPanel panelX2 = new JPanel();
		panelX2.setLayout(new BoxLayout(panelX2,BoxLayout.X_AXIS));
		panelX2.add(Box.createHorizontalStrut(20));
		JButton butNuova,butCancella,butSalva,buttonSeleziona;;
		panelX2.add(butNuova=new JButton(Messages.getString("jpanelDescrizione.New"),new ImageIcon("images/pulsanti/New24.gif"))); //$NON-NLS-1$ //$NON-NLS-2$
		butNuova.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				isNuova=true;
				setNomeRicetta(""); //$NON-NLS-1$
				setTempo(""); //$NON-NLS-1$
				setDifficolta(""); //$NON-NLS-1$
				setDescrizione(""); //$NON-NLS-1$
				setPorzioni(""); //$NON-NLS-1$
				setTabella("Select articoli.descrizione,quantita,misura from ricette,ingredienti,articoli,misure "+ //$NON-NLS-1$
						"where ingredienti.IDarticolo= articoli.ID and " + //$NON-NLS-1$
						"articoli.IDmisura= misure.ID and " + //$NON-NLS-1$
						"ricette.ID= ingredienti.IDricetta and " + //$NON-NLS-1$
						"nomericetta='pippop' order by descrizione",true,1); //$NON-NLS-1$
				txtNome.grabFocus();
				
				
			}
		});	
		panelX2.add(Box.createHorizontalStrut(10));
		panelX2.add(butSalva=new JButton(Messages.getString("Save"),new ImageIcon("images/pulsanti/Save24.gif"))); //$NON-NLS-1$ //$NON-NLS-2$
		butSalva.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				if (getNomeRicetta().equals("")) { //$NON-NLS-1$
					JOptionPane.showMessageDialog(null,Messages.getString("jpanelDescrizione.DigitNameRecipe")); //$NON-NLS-1$
					txtNome.grabFocus();
					return;
				}
				if (getPorzioni().equals("")) { //$NON-NLS-1$
					JOptionPane.showMessageDialog(null,Messages.getString("jpanelDescrizione.DigitNumberPortions")); //$NON-NLS-1$
					txtPorzioni.grabFocus();
					return;
				}
				String tipologia=(String)comboTipologia.getSelectedItem();
				String ID = connection.getCampo("Select ID from tipologie where tipologia='"+tipologia.trim()+"'"); //$NON-NLS-1$ //$NON-NLS-2$
				String quantita;
				if (isNuova) {
				
					connection.esegui(
						"INSERT INTO ricette " + //$NON-NLS-1$
						"(nomericetta,tempo,difficolta,descrizione,porzioni,IDtipologia) VALUES (" + //$NON-NLS-1$
						"'"+framePrincipale.replace(getNomeRicetta().toLowerCase(),"'","''")+"',"+ //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
						"'"+framePrincipale.replace(txtTempo.getText(),"'","''")+"',"+ //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
						"'"+framePrincipale.replace(txtDifficolta.getText(),"'","''")+"',"+ //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
						"'"+framePrincipale.replace(txtDescrizione.getText(),"'","''")+"',"+ //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
						getPorzioni()+","+ID+")"); //$NON-NLS-1$ //$NON-NLS-2$
					long IDmassimo = connection.getMassimo("ID","ricette"); //$NON-NLS-1$ //$NON-NLS-2$
					
					for (int row=0; row<table.getRowCount();row++) {
						ID=connection.getCampo("Select ID from articoli where descrizione='"+((String)table.getValueAt(row,0)).trim()+"'"); //$NON-NLS-1$ //$NON-NLS-2$
						quantita=(String)table.getValueAt(row,1);
						if (quantita.equals("")) { //$NON-NLS-1$
							JOptionPane.showMessageDialog(null,Messages.getString("jpanelDescrizione.AfterInsertQuantityIngredient")+table.getValueAt(row,0)+ //$NON-NLS-1$
								Messages.getString("jpanelDescrizione.pressEnterAndSave")); //$NON-NLS-1$
						}
						else {
							quantita= quantita.replace(',','.');
							connection.esegui(
								"INSERT INTO ingredienti (IDarticolo,Quantita,IDricetta)"+ //$NON-NLS-1$
								"VALUES ("+ID+","+quantita+","+IDmassimo+")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
						}
						
					}
					isNuova=false;
				} else {
					
					connection.esegui(
						"UPDATE ricette SET " + //$NON-NLS-1$
						"nomericetta='"+framePrincipale.replace(getNomeRicetta().toLowerCase(),"'","''")+"',"+ //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
						"tempo='"+framePrincipale.replace(txtTempo.getText(),"'","''")+"',"+ //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
						"difficolta='"+framePrincipale.replace(txtDifficolta.getText(),"'","''")+"',"+ //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
						"descrizione='"+framePrincipale.replace(txtDescrizione.getText(),"'","''")+"',"+ //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
						"porzioni="+getPorzioni()+","+ //$NON-NLS-1$ //$NON-NLS-2$
						"IDtipologia="+ID+ //$NON-NLS-1$
						" WHERE ID="+IDricetta); //$NON-NLS-1$
					
					connection.esegui("DELETE FROM ingredienti WHERE IDricetta="+IDricetta); //$NON-NLS-1$
					for (int row=0; row<table.getRowCount();row++) {
						ID=connection.getCampo("Select ID from articoli where descrizione='"+((String)table.getValueAt(row,0)).trim()+"'"); //$NON-NLS-1$ //$NON-NLS-2$
						quantita=(String)table.getValueAt(row,1);
						
						if (quantita.equals("")) { //$NON-NLS-1$
							JOptionPane.showMessageDialog(null,Messages.getString("jpanelDescrizione.AfterInsertQuantyti")+table.getValueAt(row,0)+ //$NON-NLS-1$
							Messages.getString("jpanelDescrizione.pressEnterAndSave")); //$NON-NLS-1$
						}
						else {
							quantita= quantita.replace(',','.');
							connection.esegui(
								"INSERT INTO ingredienti (IDarticolo,Quantita,IDricetta)"+ //$NON-NLS-1$
								"VALUES ("+ID+","+quantita+","+IDricetta+")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
						}
					}
				}
				panelRicerca.aggiornaJListRicette();
			}
		});
		panelX2.add(Box.createHorizontalStrut(10));
		panelX2.add(butCancella=new JButton(Messages.getString("Delete"),new ImageIcon("images/pulsanti/Delete24.gif"))); //$NON-NLS-1$ //$NON-NLS-2$
		butCancella.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				int result =JOptionPane.showConfirmDialog(null,Messages.getString("jpanelDescrizione.AreYouSureDeleteRecipe")); //$NON-NLS-1$
				if (result==JOptionPane.YES_OPTION) {
					if (panelRicerca.getNumRicette()==1) {
						JOptionPane.showMessageDialog(null,Messages.getString("jpanelDescrizione.AtLeastOneRecipe")); //$NON-NLS-1$
						return;
					}
					connection.esegui("DELETE FROM ingredienti WHERE IDricetta="+IDricetta); //$NON-NLS-1$
					connection.esegui("DELETE FROM ricette WHERE ID="+IDricetta); //$NON-NLS-1$
					panelRicerca.aggiornaJListRicette();
				}
			}
		});
		panelX2.add(Box.createHorizontalStrut(10));
		panelX2.add(buttonSeleziona = new JButton(Messages.getString("jpanelDescrizione.Select"),new ImageIcon("images/pulsanti/Preferences24.gif"))); //$NON-NLS-1$ //$NON-NLS-2$
		buttonSeleziona.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!panelLista.isInElenco(txtNome.getText())) { 
					panelLista.addRicettaTable();
					panelLista.generaLista();
					panelMenu.setListRicette();
					panelTab.setSelectedIndex(1);
					
				}
				else 
				    JOptionPane.showMessageDialog(null,Messages.getString("jpanelDescrizione.RecipeAlredySelect"));	 //$NON-NLS-1$
					
			}
			
		});
		panelX2.add(Box.createHorizontalStrut(10));
		jbuttonStampa butStampa;
		panelX2.add(butStampa=new jbuttonStampa());
		butStampa.setPanelDescrizione(this);
		panelX2.add(Box.createHorizontalStrut(10));
		
		panelDati.add(Box.createVerticalStrut(10));
		panelDati.add(panelX2);
		
	  	border = BorderFactory.createTitledBorder(border4,
	  			Messages.getString("jpanelDescrizione.DataRecipe"),TitledBorder.LEFT,TitledBorder.TOP,Font.getFont("Arial"),new Color(87,0,174)); //Color(87,0,174) //$NON-NLS-1$ //$NON-NLS-2$
	  	panelDati.setBorder(border);
	  	add(panelDati);
	  	add(Box.createVerticalStrut(10));
	  	
	  	panelY = new JPanel();
	  	panelY.setLayout(new BoxLayout(panelY,BoxLayout.Y_AXIS));
	  	
		tableColumnsName = connection.getColumnsName("ingredienti,articoli,misure", //$NON-NLS-1$
  		"descrizione,quantita,misura"); //$NON-NLS-1$
		setTabella("Select articoli.descrizione,quantita,misura from ricette,ingredienti,articoli,misure "+ //$NON-NLS-1$
				"where ingredienti.IDarticolo= articoli.ID and " + //$NON-NLS-1$
				"articoli.IDmisura= misure.ID and " + //$NON-NLS-1$
				"ricette.ID= ingredienti.IDricetta and " + //$NON-NLS-1$
				"nomericetta= '"+ sNome +"' order by descrizione",false,1); //$NON-NLS-1$ //$NON-NLS-2$
		panelY.add (jsp=new JScrollPane(table));
		jsp.getViewport().setBackground(new Color(255,245,215));
	  	jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			
		
		JPanel panelX3 = new JPanel();
		panelX3.setLayout(new BoxLayout(panelX3,BoxLayout.X_AXIS));
		panelX3.add(Box.createHorizontalStrut(10));
		
		JButton butElimina = new JButton(Messages.getString("jpanelDescrizione.DeleteRow"),new ImageIcon("images/pulsanti/RowDelete24.gif")); //$NON-NLS-1$ //$NON-NLS-2$
		butElimina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow()!=-1) 
				((DefaultTableModel)table.getModel()).removeRow(table.getSelectedRow());
				else
					JOptionPane.showMessageDialog(null,Messages.getString("jpanelDescrizione.SelectRowToDelete")); //$NON-NLS-1$
					
			}
			
		});

		panelX3.add(butAggiungi);
		panelX3.add(Box.createHorizontalStrut(10));
		panelX3.add(butElimina);
		panelX3.add(Box.createHorizontalStrut(10));
		panelX3.add(createNuovoIngredienteButton());
		panelX3.add(Box.createHorizontalStrut(10));
		JButton butBackup = new JButton("Backup",new ImageIcon("images/pulsanti/SaveAll24.gif")); //$NON-NLS-1$ //$NON-NLS-2$
		butBackup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] options = {"Ok","Annulla"};  //$NON-NLS-1$ //$NON-NLS-2$
				int result = JOptionPane.showOptionDialog( 
			 		    null,                             // the parent that the dialog blocks 
			 		    Messages.getString("jpanelDescrizione.InsertFloppyandPressEnter"),                                    // the dialog message array  //$NON-NLS-1$
			 		    Messages.getString("jpanelDescrizione.InformationForUtent"), // the title of the dialog window  //$NON-NLS-1$
			 		    JOptionPane.DEFAULT_OPTION,                 // option type 
			 		    JOptionPane.INFORMATION_MESSAGE,            // message type 
			 		    null,                                       // optional icon, use null to use the default icon 
			 		    options,                                    // options string array, will be made into buttons 
			 		    options[1]                                  // option that should be made into a default button 
			 		); 
			 		switch(result) {
			 		   case 0:
			 		   if(!CopiaFile("ricette.script","A:/")) return; //$NON-NLS-1$ //$NON-NLS-2$
			 		   if(!CopiaFile("ricette.properties","A:/")) return; //$NON-NLS-1$ //$NON-NLS-2$
			 		   if(!CopiaFile("ricette.data","A:/")) return; //$NON-NLS-1$ //$NON-NLS-2$
				 	    break;
			 		  case 1:
			 		    break; 
			 		}    
				
			}
		});
		panelX3.add(butBackup);
		panelX3.add(Box.createHorizontalStrut(10));
		
		panelY.add(Box.createVerticalStrut(10));
		panelY.add(panelX3);
		border = BorderFactory.createTitledBorder(border4,
	  			Messages.getString("jpanelDescrizione.IngredientsFor4Persons"),TitledBorder.LEFT,TitledBorder.TOP,Font.getFont("Arial"),new Color(87,0,174)); //Color(87,0,174) //$NON-NLS-1$ //$NON-NLS-2$
		panelY.setBorder(border);
		add(panelY);
	  	add(Box.createVerticalStrut(10));
	  	JScrollPane scroll;
	  	add(scroll= new JScrollPane(txtDescrizione = new JTextArea((String)v.get(2))));
	  	scroll.setBorder(BorderFactory.createTitledBorder(border4,
	  			Messages.getString("jpanelDescrizione.Procedure"),TitledBorder.LEFT,TitledBorder.TOP,Font.getFont("Arial"),new Color(87,0,174))); //$NON-NLS-1$ //$NON-NLS-2$
	  	txtDescrizione.setFont(new Font(null,Font.PLAIN,16));
		txtDescrizione.setBorder( new EmptyBorder(5,5,5,5));
		txtDescrizione.setLineWrap(true);
		txtDescrizione.setWrapStyleWord(true);
		
	  	scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setPreferredSize(new Dimension(300,500));
	  	add(Box.createVerticalStrut(10));
	  	
	}

	public void setNomeRicetta(String s) {
		txtNome.setText(s);
	}
	
	public String getNomeRicetta() {
		return txtNome.getText();
	}
	
	public void setTipologia(String s) {
		comboTipologia.setSelectedItem(s);
	}
	
	public void setTempo(String s) {
		txtTempo.setText(s);
	}
	
	public void setDifficolta(String s) {
		txtDifficolta.setText(s);
	}
	
	public void setDescrizione(String s) {
		txtDescrizione.setText(s);
		txtDescrizione.setCaretPosition(0);
	}
	public void setPorzioni(String s) {
		txtPorzioni.setText(s);
		border.setTitle(Messages.getString("jpanelDescrizione.IngredientsFor")+s+Messages.getString("Persons")); //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	public String getPorzioni() {
		return txtPorzioni.getText();
	}
	
	public void setTabella(String query,boolean aggiorna,double fattore) {
		Vector tableData = connection.getTableDataIngredienti(query,fattore);
		table 	= new JTable(tableData, tableColumnsName);
		column = table.getColumn("DESCRIZIONE"); //$NON-NLS-1$
        comboSel = new JComboBox(connection.getComboRecord("Select descrizione from articoli order by descrizione")); //$NON-NLS-1$
        table.addMouseListener(new MouseAdapter() {
        	public void mousePressed(MouseEvent e) {
                selRow = table.rowAtPoint (new Point (e.getX(), e.getY()));
                int selColumn=table.getSelectedColumn();
                if(selRow != -1 ) {
                	switch (selColumn) {
                	 case 0:	
                	 	comboSel.setSelectedItem(table.getValueAt(selRow,0)+" "); //$NON-NLS-1$
                	 	break;
                	 case 1:
                	 	if (e.getClickCount()==2) isComboItemListener=false;
                	 	break;
                	} 
                }      
                
        
        }});
        //comboSel.addItemListener(comboItemListener);
        comboSel.addActionListener(comboActionListener);
        comboSel.addKeyListener(keyComboListener);
        column.setCellEditor(new DefaultCellEditor(comboSel));
        TableColumn column2 =table.getColumn("QUANTITA"); //$NON-NLS-1$
        txtQuantita= new JTextField();
        txtQuantita.addFocusListener(new FocusAdapter () {
        	int selRow ;
        	
        	public void focusGained(FocusEvent e) {
        		selRow =table.getSelectedRow();
        		
        	}
        	
			public void focusLost(FocusEvent e) {
			 if (!isComboItemListener) {	
				JTextField txt= (JTextField)e.getSource();
				String quantita =txt.getText();
				if (quantita.equals("")) { //$NON-NLS-1$
					txt.grabFocus();
				}
				else {
					table.setValueAt(quantita,selRow,1);
				}
			}	
			}
		
		});
        txtQuantita.addKeyListener(new KeyAdapter() {
        	public void keyTyped(KeyEvent ke) { 
				char c = ke.getKeyChar ();
				if (!((Character.isDigit (c) || (c == KeyEvent.VK_BACK_SPACE)))) {
					if (c==',') return;
					getToolkit().beep ();
					ke.consume ();
					
      				}
			
			}
        });
        column2.setCellEditor(new DefaultCellEditor(txtQuantita));
		
        TableColumn column3 =table.getColumn("MISURA"); //$NON-NLS-1$
        JTextField txtMisura= new JTextField();
        txtMisura.addKeyListener(new KeyAdapter() {
        	public void keyTyped(KeyEvent ke) {
        	  ke.consume ();	
        	  JOptionPane.showMessageDialog(null,Messages.getString("jpanelDescrizione.NotPossibleEdit"));	 //$NON-NLS-1$
			  
			}
        });
        column3.setCellEditor(new DefaultCellEditor(txtMisura));
		
		if (aggiorna) {
			//System.out.println(border.getTitle());
		
			panelY.remove(jsp);
			panelY.add (jsp=new JScrollPane(table),0);
			//jsp.getViewport().setBackground(new Color(255,211,168));
			jsp.getViewport().setBackground(new Color(255,245,215));
			jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			panelY.validate();
			panelY.repaint();
			
		}


	}
	
	public int getNumRigheTableIngr () {
		return table.getRowCount();
	}
	
	public List getListRigaTabellaIngr (int selRow) {
		Vector v = new Vector();
		v.add(table.getValueAt(selRow,0).toString());
		v.add(table.getValueAt(selRow,1).toString());
		v.add(table.getValueAt(selRow,2).toString());
		return v.subList(0,3);
	}
	
	public void setPanelLista (jpanelLista panelLista) {
		this.panelLista=panelLista;
	}
	
	public void setPanelMenu (jpanelMenu panelMenu) {
		this.panelMenu=panelMenu;
	}
	
	
	public void setTabbedPane (JTabbedPane panelTab) {
		this.panelTab=panelTab;
		
	}
	
	public void setIDricetta (String ID) {
		IDricetta=ID;
	}
	
	public void setNuovaRicetta(boolean isNuova) {
		this.isNuova=isNuova;
	}
	
	public Hashtable getHashDati () {
		Hashtable hash = new Hashtable();
		hash.put("nome",txtNome.getText()); //$NON-NLS-1$
		hash.put("tipologia",comboTipologia.getSelectedItem()); //$NON-NLS-1$
		hash.put("tempo",txtTempo.getText()); //$NON-NLS-1$
		hash.put("difficolta",txtDifficolta.getText()); //$NON-NLS-1$
		hash.put("porzioni",txtPorzioni.getText()); //$NON-NLS-1$
		hash.put("ingredienti",((DefaultTableModel)table.getModel()).getDataVector()); //$NON-NLS-1$
		hash.put("descrizione",txtDescrizione.getText()); //$NON-NLS-1$
		return hash;
	}
	
	private JButton createNuovoIngredienteButton() {
		Action a = new AbstractAction(Messages.getString("jpanelDescrizione.ManageIngredients")) { //$NON-NLS-1$
		    public void actionPerformed(ActionEvent e) {
		    	panelTab.setSelectedIndex(3);
		    }
		};
		JButton b = new JButton(a);
		b.setIcon(new ImageIcon("images/pulsanti/Edit24.gif")); //$NON-NLS-1$
		return b;
	    }
	
	public void aggiornaComboSel() {
		 comboSel = new JComboBox(connection.getComboRecord("Select descrizione from articoli order by descrizione")); //$NON-NLS-1$
		 comboSel.addActionListener(comboActionListener);
		 //comboSel.addItemListener(comboItemListener);
		 comboSel.addKeyListener(keyComboListener);
	     column.setCellEditor(new DefaultCellEditor(comboSel));
	}
	
	//ItemListener comboItemListener =new ItemListener() {
    //	public void itemStateChanged(ItemEvent e) {
	ActionListener comboActionListener = new ActionListener () {
    		public void actionPerformed(ActionEvent e) {	
    		if (comboSel.getSelectedIndex()!=-1){
    			String sDesc=((String)comboSel.getSelectedItem()).trim();
    			
    			table.setValueAt(connection.getCampo("Select misura from articoli,misure " + //$NON-NLS-1$
    					"where misure.ID=articoli.IDmisura and descrizione='"+ //$NON-NLS-1$
						sDesc+"'"), //$NON-NLS-1$
						selRow,2);
    			//table.setValueAt(" ",selRow,1);
    			table.setRowSelectionInterval(selRow,selRow);
    			table.editCellAt(selRow,1);
    			txtQuantita.grabFocus();
    			isComboItemListener=true;
    			
    		}
    	}
    };
    
    KeyAdapter keyComboListener = new KeyAdapter() {
    	public void keyPressed(KeyEvent ke) {
    		char c = ke.getKeyChar ();
    		System.out.println(c);
    		switch (c) {
    			case KeyEvent.VK_KP_DOWN:
    				comboSel.setSelectedIndex(comboSel.getSelectedIndex()+1);
    				break;	
    			case KeyEvent.VK_KP_UP:
    				comboSel.setSelectedIndex(comboSel.getSelectedIndex()-1);
    				break;
    		};
			
    	}
    };

    private boolean CopiaFile (String sPathIn, String sPathFolder) {
    	boolean isOK=true;
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
	       isOK=false;
	       
	    }
	      return isOK; 
	 }
}
