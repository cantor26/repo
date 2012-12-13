import javax.swing.*;
import java.awt.*;
import java.awt.event.*;



public class framePrincipale extends JFrame {
	
	private static JWindow splashScreen = null;
	private static JLabel splashLabel = null;
	public static boolean bRisoluzione800=false;
	
	
	public static void main(String[] args)
	{
	try {
		avviaProgramma();
				
	} catch (Exception ex) {System.out.println(ex);}
	}
	
	
	
	
	
	private static void avviaProgramma() {
		splashLabel = new JLabel(new ImageIcon(Messages.getString("framePrincipale.Splash"))); //$NON-NLS-1$
 	    splashScreen = new JWindow();
 	    splashScreen.getContentPane().add(splashLabel);
 	    splashScreen.pack();
 	    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
 	    Rectangle screenRect =  ge.getMaximumWindowBounds();
 	    
 	    splashScreen.setLocation(
        screenRect.x + screenRect.width/2 - splashScreen.getSize().width/2,
 		screenRect.y + screenRect.height/2 - splashScreen.getSize().height/2);
		splashScreen.setVisible(true);
				
					
		framePrincipale frame = new framePrincipale();
		WindowListener l = new WindowAdapter() {
		    public void windowClosing(WindowEvent e) {
			      System.exit(0); }
		    };
		frame.addWindowListener(l);
	}
	
	
	public framePrincipale() {
			
		super();
		// Show the demo
		FormattaFrame();
		setExtendedState(Frame.MAXIMIZED_BOTH);

    }


     public void FormattaFrame() {
	
     	try {	
	    	setTitle(Messages.getString("framePrincipale.Title"));     //$NON-NLS-1$
    	  	jpanelPrincipale panel = new jpanelPrincipale();
	    	getContentPane().add(panel, BorderLayout.CENTER);
	    	pack();
	    	SwingUtilities.updateComponentTreeUI(this);
     		SwingUtilities.invokeLater(new Runnable() {
     		    public void run() {
     			setVisible(true);
     			hideSplash();
     		    }
     		});

     		
     	} catch (Exception ex) {System.out.println(ex);}
		

    }
   


     /**
      * pop down the spash screen
      */
      void hideSplash() {
 	
 	    splashScreen.setVisible(false);
 	    splashScreen = null;
 	    splashLabel = null;
 	 }
      
      public static String replace(String str, String pattern, String replace) {
   	  int s = 0;
   	  int e = 0;
   	  StringBuffer result = new StringBuffer();
   	  if(pattern == null || pattern.equals("")) return str; 
   	    while ((e = str.indexOf(pattern, s)) >= 0) {
   	      result.append(str.substring(s, e));
   	      result.append(replace);
   	      s = e+pattern.length();
   	      }
   	      result.append(str.substring(s));
   	      return result.toString();
   	    } 
      
  }  