/*
 * Created on 4-mar-2006
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




public class jpanelMenu extends JPanel {
	
	JComboBox comboMesi,comboSettimana;
	final GregorianCalendar cal;
	JTable tablePranzo,tableCena;
	Vector vColonna;
	String sDay[] = {Messages.getString("jpanelMenu.Sunday"),Messages.getString("jpanelMenu.Monday"),Messages.getString("jpanelMenu.Tuesday"),Messages.getString("jpanelMenu.Wednesday"),Messages.getString("jpanelMenu.Thursday"),Messages.getString("jpanelMenu.Friday"),Messages.getString("jpanelMenu.Saturday")}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$
	JList listRicette;
	Vector vRicette = jpanelLista.vRicette;
	jpanelRicette panelRicette;
	JPanel panelCalendar;
	
	public jpanelMenu (final jpanelRicette panelRicette) {
		this.panelRicette=panelRicette;
		setLayout(new BorderLayout());
		panelCalendar = new JPanel();
		panelCalendar.setLayout(new BoxLayout(panelCalendar,BoxLayout.X_AXIS));
		panelCalendar.add(Box.createHorizontalStrut(10));
		cal = new GregorianCalendar();
		long oggi = cal.getTimeInMillis();
		panelCalendar.add(comboMesi = getComboMesi(cal.get(Calendar.YEAR)));
		comboMesi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sostituisciComboSettimana();
			}
		});
		//System.out.println(comboMesi.getFont());
		comboMesi.setFont(new Font(null,Font.BOLD,16));
		panelCalendar.add(Box.createHorizontalStrut(10));
		panelCalendar.add(comboSettimana = getComboSettimane(cal));
		add(panelCalendar,BorderLayout.NORTH);
		JPanel panelTable = new JPanel();
		panelTable.setLayout(new BoxLayout(panelTable,BoxLayout.Y_AXIS));
		final Border border4 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,new Color(87,0,174),new Color (255,255,255));
		TitledBorder border = BorderFactory.createTitledBorder(border4,
	  			Messages.getString("jpanelMenu.lunch"),TitledBorder.LEFT,TitledBorder.TOP,Font.getFont("Arial"),new Color(87,0,174)); //Color(87,0,174) //$NON-NLS-1$ //$NON-NLS-2$
		JScrollPane scrollPranzo = new JScrollPane(tablePranzo=new JTable(new Vector(),getVectorColonna()));
		JScrollPane scrollCena = new JScrollPane(tableCena=new JTable(new Vector(),getVectorColonna()));
		scrollPranzo.getViewport().setBackground(new Color(255,245,215));
		scrollPranzo.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPranzo.setBorder(border);
		scrollCena.getViewport().setBackground(new Color(255,245,215));
		scrollCena.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		border = BorderFactory.createTitledBorder(border4,
	  			Messages.getString("jpanelMenu.Dinner"),TitledBorder.LEFT,TitledBorder.TOP,Font.getFont("Arial"),new Color(87,0,174)); //Color(87,0,174) //$NON-NLS-1$ //$NON-NLS-2$
		scrollCena.setBorder(border);
		panelTable.add(Box.createVerticalStrut(10));
		panelTable.add(scrollPranzo);
		panelTable.add(Box.createVerticalStrut(30));
		panelTable.add(scrollCena);
		panelTable.add(Box.createVerticalStrut(10));
		add(panelTable,BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		JPanel panelAggiungi = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton butAggiungi;
		final JComboBox comboTipo,comboGiorno;
		comboGiorno=new JComboBox(sDay);
		String s[] = {Messages.getString("jpanelMenu.lunch"),Messages.getString("jpanelMenu.Dinner")}; //$NON-NLS-1$ //$NON-NLS-2$
		comboTipo=new JComboBox(s);
		panelAggiungi.add(butAggiungi=new JButton(Messages.getString("Add"))); //$NON-NLS-1$
		butAggiungi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!vRicette.isEmpty()) {
					int giorno=comboGiorno.getSelectedIndex();
					String nomeRicetta =(String)listRicette.getSelectedValue();
					if (comboTipo.getSelectedIndex()==0) 
						setRicettaTable(tablePranzo,nomeRicetta,giorno);
					else 
						setRicettaTable(tableCena,nomeRicetta,giorno);
				}
			}	
		});
		
		panelAggiungi.add(new JLabel(Messages.getString("jpanelMenu.to"))); //$NON-NLS-1$
		panelAggiungi.add(comboTipo);
		panelAggiungi.add(new JLabel(Messages.getString("jpanelMenu.of"))); //$NON-NLS-1$
		panelAggiungi.add(comboGiorno);
		JPanel panelElimina = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		JButton butElimina;
		final JComboBox comboTipoDel,comboGiornoDel;
		comboGiornoDel=new JComboBox(sDay);
		comboTipoDel=new JComboBox(s);
		panelElimina.add(butElimina=new JButton(Messages.getString("Delete"))); //$NON-NLS-1$
		butElimina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (listRicette.getSelectedIndex()==-1) return;
				int giorno=comboGiornoDel.getSelectedIndex();
				String nomeRicetta =(String)listRicette.getSelectedValue();
				if (comboTipoDel.getSelectedIndex()==0) 
					deleteRicettaTable(tablePranzo,nomeRicetta,giorno);
				else 
					deleteRicettaTable(tableCena,nomeRicetta,giorno);
			}	
		});
		
		panelElimina.add(new JLabel(Messages.getString("jpanelMenu.from"))); //$NON-NLS-1$
		panelElimina.add(comboTipoDel);
		panelElimina.add(new JLabel(Messages.getString("jpanelMenu.of"))); //$NON-NLS-1$
		panelElimina.add(comboGiornoDel);
		panel.add(Box.createVerticalStrut(10));
		panel.add(panelAggiungi);
		panel.add(Box.createVerticalStrut(10));
		panel.add(panelElimina);
		panel.add(Box.createVerticalStrut(10));
		JPanel panelButton = new JPanel(new FlowLayout(FlowLayout.LEFT));
		jbuttonStampa button; 
		panelButton.add(button=new jbuttonStampa());
		panel.add(panelButton);
		panel.add(Box.createVerticalStrut(10));
		JPanel panelX = new JPanel();
		panelX.setLayout(new BoxLayout(panelX,BoxLayout.X_AXIS));
		panelX.add(Box.createHorizontalStrut(10));
		panelX.add(new JScrollPane(listRicette=new JList()));
		listRicette.setCellRenderer(new MyCellRenderer());
		panelX.add(Box.createHorizontalStrut(10));
		panelX.add(panel);
		border = BorderFactory.createTitledBorder(border4,
	  			Messages.getString("jpanelMenu.AddDeleteSelectedRecipes"),TitledBorder.LEFT,TitledBorder.TOP,Font.getFont("Arial"),new Color(87,0,174)); //Color(87,0,174) //$NON-NLS-1$ //$NON-NLS-2$
		panelX.setBorder(border);
		add(panelX,BorderLayout.SOUTH);
		panelRicette.getPanelDescrizione().setPanelMenu(this);
		setCalendarOggi(oggi);	
		button.setPanelMenu(this);
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
	
	private void setRicettaTable(JTable table,String nomeRicetta,int giorno) {
		String rigaVuota[] = {"","","","","","",""}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$
		int numRighe=table.getRowCount();
		if (numRighe==0) {
			((DefaultTableModel) table.getModel()).addRow(rigaVuota);
			table.setValueAt(nomeRicetta,0,giorno);
		}
		else if (table.getValueAt(numRighe-1,giorno).equals("")) { //$NON-NLS-1$
			for (int riga=0;riga<numRighe;riga++) {
				if (table.getValueAt(riga,giorno).equals("")) { //$NON-NLS-1$
					table.setValueAt(nomeRicetta,riga,giorno);
					return;
				}
			}
		}
		else {
			((DefaultTableModel) table.getModel()).addRow(rigaVuota);
			table.setValueAt(nomeRicetta,numRighe,giorno);
		}
		
	}
	
	private void deleteRicettaTable(JTable table,String nomeRicetta,int giorno) {
		int numRighe=table.getRowCount();
		for (int riga=0;riga<numRighe;riga++) {
				if (table.getValueAt(riga,giorno).equals(nomeRicetta)) {
					table.setValueAt("",riga,giorno); //$NON-NLS-1$
				}
			}
	}
	
	private void sostituisciComboSettimana() {
		panelCalendar.remove(comboSettimana);
		panelCalendar.add(comboSettimana = getComboSettimane(cal),3);
		panelCalendar.validate();
		vColonna=getVectorColonna();
		((DefaultTableModel) tablePranzo.getModel()).setColumnIdentifiers(vColonna);
		((DefaultTableModel) tableCena.getModel()).setColumnIdentifiers(vColonna);
	}
	
	private JComboBox getComboMesi(int anno) {
		Vector v = new Vector();
		String mesi [] ={Messages.getString("jpanelMenu.January"),Messages.getString("jpanelMenu.February"),Messages.getString("jpanelMenu.March"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				Messages.getString("jpanelMenu.April"),Messages.getString("jpanelMenu.May"),Messages.getString("jpanelMenu.June"),Messages.getString("jpanelMenu.July"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				Messages.getString("jpanelMenu.August"),Messages.getString("jpanelMenu.September"),Messages.getString("jpanelMenu.October"),Messages.getString("jpanelMenu.November"),Messages.getString("jpanelMenu.Dicember")}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
		
		for (int m=0; m<12;m++) {
			v.add(mesi[m]+" "+anno); //$NON-NLS-1$
		}
		return new JComboBox(v);
	}
	
	private JComboBox getComboSettimane(GregorianCalendar cal) {
		Vector v = new Vector();
		int giorno;
		int mese = comboMesi.getSelectedIndex();
		cal.set(cal.get(Calendar.YEAR),mese,1);
		cal.add(Calendar.DATE,-cal.get(Calendar.DAY_OF_WEEK)+1);
		for (int i=0;i<=4;i++) {
			giorno=cal.get(Calendar.DAY_OF_MONTH);
			mese =cal.get(Calendar.MONTH)+1;
			cal.add(Calendar.DATE,6);
			v.add(Messages.getString("jpanelMenu.WeekFrom")+giorno+"/"+ mese+ Messages.getString("jpanelMenu.to")+ //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
						cal.get(Calendar.DAY_OF_MONTH)+"/"+ //$NON-NLS-1$
						(cal.get(Calendar.MONTH)+1));
			cal.add(Calendar.DATE,1);
			
			
		}
		JComboBox combo = new JComboBox(v);
		combo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vColonna=getVectorColonna();
				((DefaultTableModel) tablePranzo.getModel()).setColumnIdentifiers(vColonna);
				((DefaultTableModel) tableCena.getModel()).setColumnIdentifiers(vColonna);
			}
		});
		combo.setFont(new Font(null,Font.BOLD,16));
		return combo;
	}
	
	private void setCalendarOggi(long dataOdierna) {
		int i,giorno,oggi;
		String s;
		cal.setTimeInMillis(dataOdierna);
		oggi=cal.get(Calendar.DAY_OF_MONTH);
		comboMesi.setSelectedIndex(cal.get(Calendar.MONTH));
		sostituisciComboSettimana();
		for (int n=0;n<5;n++) {
			s =(String)comboSettimana.getItemAt(n);
			i=s.indexOf("/"); //$NON-NLS-1$
			giorno = Integer.parseInt(s.substring(i-2,i).trim());
			if (giorno<=oggi && oggi<=giorno+6) {
				comboSettimana.setSelectedIndex(n);
				return;
			}	
		}
	}
	
	private Vector getVectorColonna() {
		String s =(String)comboSettimana.getSelectedItem();
		int i=s.indexOf("/"); //$NON-NLS-1$
		int giorno = Integer.parseInt(s.substring(i-2,i).trim());
		int mese = Integer.parseInt(s.substring(i+1,i+3).trim())-1;
		cal.set(cal.get(Calendar.YEAR),mese,giorno);
		Vector vColonne = new Vector();
		for (int g=0;g<7;g++) {
			vColonne.add(sDay[g]+" "+cal.get(Calendar.DAY_OF_MONTH)+"/"+(cal.get(Calendar.MONTH)+1)); //$NON-NLS-1$ //$NON-NLS-2$
			cal.add(Calendar.DATE,1);
		}
		return vColonne;
		
	}
	
	public void setListRicette () {
		//String nomeRicetta = panelRicette.getPanelDescrizione().getNomeRicetta();
		//vRicette.add(nomeRicetta);
		listRicette.setListData(vRicette);
		listRicette.setSelectedIndex(0);
	}
	
	public Hashtable getHashDati () {
		Hashtable hash = new Hashtable();
		hash.put("MeseAnno",(String)comboMesi.getSelectedItem()); //$NON-NLS-1$
		hash.put("Settimana",(String)comboSettimana.getSelectedItem()); //$NON-NLS-1$
		String giorni[] = {tableCena.getColumnName(0),
						   tableCena.getColumnName(1),
						   tableCena.getColumnName(2),
						   tableCena.getColumnName(3),
						   tableCena.getColumnName(4),
						   tableCena.getColumnName(5),
						   tableCena.getColumnName(6)};
		
		hash.put("giorni",giorni); //$NON-NLS-1$
		hash.put("vectorPranzo",((DefaultTableModel)tablePranzo.getModel()).getDataVector()); //$NON-NLS-1$
		hash.put("vectorCena",((DefaultTableModel)tableCena.getModel()).getDataVector()); //$NON-NLS-1$
		return hash;
	}
	
}
