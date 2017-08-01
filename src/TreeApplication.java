import javax.swing.JFrame;

/** 
 * Main application for starting up the RecursiveTreePainting GUI.
 * 
 * @author Xueying Xu (Shirley)
 * @version Assignment 3 (February 18, 2017) 
 **/

public class TreeApplication {

  	// the text displayed at the top of the window
	public static final String INSTRUCTIONS_TEXT_1 = "Click and drag to start a tree painting."; 
	public static final String INSTRUCTIONS_TEXT_2 = "Be patient, as painting is a lot of work!";
	
	/**
	 * Creates a JFrame that holds the RecursiveTreePaintings.
	 * 
	 **/
	public static void main( String[] args ) {
		
	    // create a new JFrame to hold a RecursiveTreePainting object
		JFrame guiFrame = new JFrame( "Tree Painter" );
		
		// set size
		guiFrame.setSize( 600, 800 );

		// create a TreePanel and add it
		guiFrame.add( new SingleTreePanel(INSTRUCTIONS_TEXT_1, INSTRUCTIONS_TEXT_2) );
		

		// exit normally on closing the window
		guiFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

		// show frame
		guiFrame.setVisible( true );
	}
}