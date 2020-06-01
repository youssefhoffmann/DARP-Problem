package basic_DARP;

import java.awt.Color;

import repast.simphony.visualizationOGL2D.DefaultStyleOGL2D;
import saf.v3d.scene.Position;

/**
 * Class that enable Custom agent style
 * 
 *
 */
public class VehicleAgentStyle2D extends DefaultStyleOGL2D {

	/**
	 * Method that changes the color of the agent when he has a customer
	 */
	@Override
	public Color getColor(Object o){
		if (o instanceof Vehicle)
		{
			if (((Vehicle) o).isHasCustomer())
			{
				return Color.RED;
			} else {
				return Color.GREEN;
			}
		}
		else if (o instanceof Client){
			if (((Client) o).isArrived())
				return Color.BLUE;
			else if (((Client) o).isRideRequested())
				return Color.RED;
			else
				return Color.BLACK;
		}	
		return Color.WHITE;
	}

	/**
	 * Method that changes the Label of the agent with his name
	 */
	@Override
	public String getLabel(Object agent){
		String label = "";
		if (agent instanceof Vehicle)
			label = ((Vehicle) agent).getName();
		return label;
	}
}
