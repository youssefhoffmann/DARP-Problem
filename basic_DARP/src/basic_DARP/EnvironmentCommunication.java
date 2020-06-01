package basic_DARP;

import repast.simphony.context.Context;


public class EnvironmentCommunication {
	
	public static final int RADIUS = 10;
	public  static Context<Object> CONTEXT;

	public EnvironmentCommunication(Context<Object> context) {
		this.CONTEXT = context;
	}


	public void send(Vehicle vehicle, AgentMessage m) {
		for (Object obj : vehicle.getContext())
			if (obj instanceof Vehicle && vehicle.distance((Vehicle) obj) < RADIUS)
				if (((Vehicle) obj).getName().compareTo(vehicle.getName())!=0) 
				((Vehicle) obj).receive(m);
			}
	}
	
