import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D; // for drawing lines with varying thickness
import java.awt.BasicStroke; // for drawing lines with varying thickness
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import javax.swing.JComponent;
import java.util.Random;

/**
 * RecursiveTreePainting creates a GUI and uses recursion to paint trees
 * with several layers of branches interactively.
 * 
 * @author Xueying Xu (Shirley)
 * @version Assignment 3 (February 18, 2017) 
 **/


public class RecursiveTreePainting extends JComponent implements MouseListener {
	

		/** Number of generations to create branches **/
		public static final int NUM_GENERATIONS = 8; 
	 
		/** Number of children for each branch **/
		public static final int NUM_CHILDREN = 3; 
			
		/** Diameter of the blossoms **/
		public static final int BLOSSOM_DIAM = 4;
	 
		/** Golden ratio makes the child branches aesthetically appealing **/
		public static final double GOLDEN_RATIO = 1.618;
		
		/** Maximum branching angle of children from a parent stick **/
		public static final double MAX_BRANCHING_ANGLE = .5 * Math.PI;
		
		/** Thickness of the line for drawing branches **/
		public static final int BRANCH_THICKNESS = 1;
		
		/** Number of copies of the trunk **/
		public static final int TRUNK_TWINS = 4;
		
		private Point2D startingPoint;
		
		private Point2D endingPoint;
		
		
		/**
		 * Constructs a new RecursiveTreePainting object.
		 */
		public RecursiveTreePainting() {
			
			// this class should be a mouse listener for its own mouse events 
			addMouseListener(this);
			
		}
		
		
		// Record the starting point for the tree trunk
		private void startTrunk(int x, int y) {
			
			startingPoint = new Point2D.Double(x, y);
			
		}
		

		
		// Record the ending point for the tree trunk
		private void completeTrunk(int x, int y) {	
		    
			endingPoint = new Point2D.Double(x, y);	
		    
		    // invoke paint() to draw a new tree 
		    repaint();
		}
		

		
		/**
	     * Method for drawing on this component. 
	     * Overrides the paint method specified in JComponent (parent).
	     *
	     * @param g the Graphics object to paint the background and draw a single tree 
	     */
	    public void paint( Graphics g ) {

	       // paint the background  
	       paintBackground( g );

	        // if user has pressed and released mouse, draw a single tree
	       if (endingPoint != null)
	    	   
	    	  drawTree(g, startingPoint, endingPoint);
	    
	    }	
		
	    
	    
		// paint the background black
		protected void paintBackground( Graphics g ) {
			
			g.setColor(Color.BLACK);
			
			g.fillRect(0, 0, getWidth(), getHeight());
		}
	    

		
		// round a double value to the nearest integer
		private int round2Int( double a ) {
			
			return (int) Math.round(a);
		
		}
		
		
		// calculate the distance between two points 
		private double distance( double x1, double y1, double x2, double y2 ) {
			
			double dx = x2 - x1;
			double dy = y2 - y1;	
			return Math.sqrt(dx * dx + dy * dy);
		
		}

		
		/**
		 * Computes a random branch angle that is within 90 degrees 
		 * from the angle of the previous stick in both directions. 
		 * 
		 * @param prevAngle the angle of the previous stick
		 * @return a random branch angle
		 */
		protected double computeRandomBranchAngle( double prevAngle ) {
			
			Random rand = new Random(); 
			
			return prevAngle + (2 * MAX_BRANCHING_ANGLE * rand.nextDouble() - MAX_BRANCHING_ANGLE);
		}
		
		
		/** 
		 * Computes the point that is length away from point p at the specified angle.
		 * Uses cosine to get the new x coordinate, sine to get the new y coordinate.
		 *
		 * @param p the initial point of the branch 
		 * @param length the distance between the end point and the initial point of the branch
		 * @param angle the random branch angle 
		 * @return the end point (tip) of the branch
		 */
		protected Point2D computeEndpoint( Point2D p, double length, double angle ) {
	 	    
			return new Point2D.Double(p.getX() + length*Math.cos(angle), 
		                		      p.getY() + length*Math.sin(angle)); 
		}

		
		/**
		 * Draws a single tree, including the trunk, the branches, and the blossoms. 
		 * 
		 * @param g the Graphics object to draw a single tree
		 * @param trunkStart the start point of the trunk 
		 * @param trunkEnd the end point of the trunk 
		 */
		protected void drawTree( Graphics g, Point2D trunkStart, Point2D trunkEnd ) {
			
			// Compute the trunk length
			double trunkLength = distance(trunkStart.getX(), trunkStart.getY(), trunkEnd.getX(), trunkEnd.getY());
			
			// Compute the trunk angle
		    double theta = Math.atan2(trunkEnd.getY() - trunkStart.getY(), trunkEnd.getX() - trunkStart.getX());
		    
		    // Start the recursion multiple times to have a denser tree
		    for (int i = 0; i < TRUNK_TWINS; i++) {
		    
		    drawBranch(g, trunkStart, trunkLength, theta, NUM_GENERATIONS);
		    
		    }
								
		}		
		
		
		/** 
		 * Recursively draws several layers of branches.
		 * Draws a single blossom at the tip of each branch in the first generation.
		 * 
		 * @param g the Graphics object to draw the branches and blossoms.
		 * @param startPoint the start point of the branch
		 * @param length the length of the branch
		 * @param angle  the angle of the branch
		 * @param generation the generation of the branch
		 */	
		protected void drawBranch( Graphics g, Point2D startPoint, double length, double angle, int generation ) {
			
			// Base case
			if (generation == 0) {
				
				// Draw a blossom at the tip
				drawBlossom(g, startPoint);
					
			} else {
				
				// Draw a stick
			    Point2D endPoint = computeEndpoint(startPoint, length, angle);	   
			    drawStick(g, startPoint, endPoint);
			    
			    double childLength = (1/GOLDEN_RATIO) * distance(startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY());
			
			    // For a fixed number of children 
			    for (int i = 0; i < NUM_CHILDREN; i++) {
			    	
			    	double childAngle = computeRandomBranchAngle(angle);
			    	
			    	// Recursively draws several layers of branches
			    	drawBranch(g, endPoint, childLength, childAngle, generation-1);
			    		    	
			    }
					
			}
					
	}
		
		/**
		 * Draws a single stick. 
		 * 
		 * @param g the Graphics object to draw a single stick
		 * @param startpoint the start point of the stick
		 * @param endpoint the end point of the stick
		 */
		protected void drawStick( Graphics g, Point2D startpoint, Point2D endpoint ) {
			
			// Use dark green color
			g.setColor(new Color(0, 100, 10));
			
			Graphics2D g2d = (Graphics2D) g.create();
			
			// Draw the lines with different styles 
			g2d.setStroke(new BasicStroke(BRANCH_THICKNESS, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
			
			g2d.drawLine(round2Int(startpoint.getX()), round2Int(startpoint.getY()), round2Int(endpoint.getX()), round2Int(endpoint.getY()));
		    
			
		}
		
		
		/**
		 * Draws a single blossom at the tip of the branch.
		 * 
		 * @param g the Graphics object to draw a blossom
		 * @param p the center of the blossom, which is the tip of the branch 
		 */
		protected void drawBlossom( Graphics g, Point2D p ) {
			
			// Create random colors for the blossoms
			Random rand = new Random();		
			int red = rand.nextInt(250);
			int green = rand.nextInt(50);
			int blue = rand.nextInt(30);
			
			g.setColor(new Color(red, green, blue));
					
			g.fillOval(round2Int(p.getX()-BLOSSOM_DIAM / 2), round2Int(p.getY()-BLOSSOM_DIAM / 2), BLOSSOM_DIAM, BLOSSOM_DIAM);
			
		}

			
		/**
		 * Invoked when the mouse is pressed down. Records the mouse location and invokes the startTrunk. 
		 * 
		 * @param e the current state of the mouse
		 */
	    public void mousePressed( MouseEvent e ) {
	    
		   startTrunk(e.getX(), e.getY());
	  }

	    
	    /**
		 * Invoked when the mouse is released. Records the mouse location and invokes the completeTrunk. 
		 * 
		 * @param e the current state of the mouse
		 */
	    public void mouseReleased( MouseEvent e ) { 
	   
		   completeTrunk(e.getX(), e.getY()); 	
	  }
	 
	    
	    /**
		 * Invoked when the mouse is clicked (pressed down and released).
		 * 
		 * @param e the current state of the mouse
		 */
	    public void mouseClicked( MouseEvent e ) {
	    	
	  }
	 
	    
	    /**
		 * Invoked when the mouse enters the component.
		 * 
		 * @param e the current state of the mouse
		 */
	    public void mouseEntered( MouseEvent e ) {
		
	  }

	    
	    /**
		 * Invoked when the mouse leaves the component.
		 * 
		 * @param e the current state of the mouse
		 */
	    public void mouseExited( MouseEvent e ) {
		
	  }

}
