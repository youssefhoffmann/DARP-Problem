package basic_DARP;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.grid.Grid;


/**
 * Class that modelizes a Client agent
 *
 */
public class Client {
	private ContinuousSpace<Object> space;
	private int id;
	private boolean rideRequested;
	private boolean isArrived;
	

	public Request myRequest;	
	/**
	 * Constructor
	 * @param space : ContinousSpace<Object> that represents the space projection
	 * @param id : int that represents the id of this Source
	 */
	public Client(ContinuousSpace<Object> space,  int id) {
		super();
		this.space = space;
		this.id = id;
		this.rideRequested = false;
		myRequest = null;

		isArrived  = false;
	}

	
	@ScheduledMethod(start = 10, interval = 10)
	public void askARide(){
		
		if (!rideRequested && RandomHelper.nextIntFromTo(0, 100) > 80 && !isArrived)
			{
		
			myRequest = randomRequest();
			rideRequested = true;
			}
	}

	
	public boolean isRideRequested() {
		return rideRequested;
	}
	
	/**
	 * Getter
	 * @return
	 */
	public int getId() {
		return id;
	}

	/**
	 * Setter
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Method that watches if a vehicule is free and ask them to look at waiting requests
	 * @param vehicle : Vehicule which is free
	 */
/*	@Watch(watcheeClassName="basicDialARide.Vehicle", 
			watcheeFieldNames ="hasCustomer", 
			whenToTrigger = WatcherTriggerSchedule.LATER)
	public void requestProcess(Vehicle vehicle){
		if(!vehicle.isHasCustomer()) {
			if (this.rideRequested){
				this.rideRequested = false;
				this.rideRequested = true;
			}
		}
	}
*/
	/**
	 * Method that watches if a Vehicle has asked a request and attributes it if possible
	 * @param vehicle : Vehicle which asked a request
	 */
/*	@Watch(watcheeClassName="basicDialARide.Vehicle", 
			watcheeFieldNames ="askedrequest", 
			whenToTrigger = WatcherTriggerSchedule.LATER)
	public void requestAttribution(Vehicle vehicle){
		Request askedrequest = vehicle.getAskedrequest();
		if (askedrequest != null) {
			System.out.println("Source " + this.id + " is checking requests attribution.");
			if (this.requests.contains(askedrequest))
			{
				this.requests.remove(askedrequest);
				askedrequest.setVehicleuuid(vehicle.getUuid());
				this.lastAcceptedRequest = askedrequest;
				System.out.println("Source " + this.id + " accepted offer for request " + askedrequest);
			}
		}
	}
*/
	/**
	 * Getter
	 * @return
	 */
	public Request getMyRequest() {
		return myRequest;
	}


	/**
	 * Getter
	 * @return
	 */
	public NdPoint getLocation(){
		NdPoint location = space.getLocation(this);
		return location;
	}


	@Override
	public String toString() {
		return "Client [id=" + id + ", rideRequested=" + rideRequested + ", myRequest=" + myRequest + "]";
	}

	/**
	 * Method that construct a new random request
	 * @return Request : random request
	 */
	public Request randomRequest(){
		int destination = this.id;
		NdPoint origin = space.getLocation(this);
		double nX = RandomHelper.nextDoubleFromTo(0, space.getDimensions().getWidth());
		double nY = RandomHelper.nextDoubleFromTo(0, space.getDimensions().getHeight());
		
		int timewindow = ThreadLocalRandom.current().nextInt(50, 200 + 1);
		
		NdPoint dest = new NdPoint(nX,nY); 
		
		return new Request(this.id, origin, dest, timewindow,(int)RunEnvironment.getInstance().getCurrentSchedule().getTickCount());	
	}
	
	public void arrived() {
		myRequest = null;
		rideRequested = false;
		isArrived = true;
	}


	public boolean isArrived() {
		
		return isArrived;
	}

}
