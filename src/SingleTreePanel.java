import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * A SingleTreePanel displays instructions and one basic RecursiveTreePainting.
 * 
 * @author Xueying Xu (Shirley)
 * @version Assignment 3 (February 18, 2017) 
 **/

public class SingleTreePanel extends JPanel {

	 private JLabel instructions1;	 
	 private JLabel instructions2;
	 
		
	/** 
	 * Constructor uses a border layout. The GUI is set up by
	 * putting two JLabels with instructions in the NORTH region, 
	 * and a new RecursiveTreePainting object in the CENTER region.
	 * 
	 * @param instructionsText the text displayed at the top
	 **/
	
	 public SingleTreePanel(String instructionsText1, String instructionsText2) {
	
		// use a BorderLayout
		super( new BorderLayout() );
		
		// create instructions and put at the top
		instructions1 = new JLabel(instructionsText1);
		instructions2 = new JLabel(instructionsText2);
		
		JPanel instructions = new JPanel();
		instructions.setLayout(new GridLayout(2,1));
		instructions.add(instructions1);
		instructions.add(instructions2);
		
		add(instructions, BorderLayout.NORTH);
		
		
		// use the rest of the panel for a RecursiveTreePainting object
		add( new RecursiveTreePainting(), BorderLayout.CENTER );	
	}
}