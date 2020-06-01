package basic_DARP;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import repast.simphony.context.Context;

import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.query.space.grid.GridCell;
import repast.simphony.query.space.grid.GridCellNgh;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.Direction;
import repast.simphony.space.SpatialMath;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;
import repast.simphony.util.SimUtilities;

/**
 * Class that modelizes Vehicle agent
 * 
 *
 */
public class Vehicle {
	private String uuid;
	private boolean hasCustomer;
	private Context<Object> context;
	private Grid<Object> grid;
	private ContinuousSpace<Object> space;
	private String name;
	private double speed;
	private Client client;
	private ArrayList<AgentMessage> mailbox;
	private static final double THRESHOLD = 2;
	EnvironmentCommunication ec;

	private boolean moved = false;

	// Liste partagee
	private Map<Client, Map<Integer, Integer>> listePartagee;

	public Map<Client, Map<Integer, Integer>> getListePartagee() {
		return listePartagee;
	}

	public void setListePartagee(Map<Client, Map<Integer, Integer>> listePartagee) {
		this.listePartagee = listePartagee;
	}

	/**
	 * Constructor
	 * 
	 * @param context : Context<Object> which is the current context of the
	 *                simulation
	 * @param grid    : Grid<Object> which is the grid projection
	 * @param space   : ContinuousSpace<Object> which is the space projection
	 * @param name    : String that represents the name of this vehicle
	 * @param speed   : double that represents
	 */
	public Vehicle(Context<Object> context, Grid<Object> grid, ContinuousSpace<Object> space, String name,
			EnvironmentCommunication ec) {
		this.context = context;
		this.grid = grid;
		this.space = space;
		this.name = name;
		this.uuid = UUID.randomUUID().toString();
		this.hasCustomer = false;
		client = null;
		mailbox = new ArrayList<AgentMessage>();
		this.ec = ec;
	}
	// @ScheduledMethod(start = 1, duration = 1)
//	public void hello() {
//		send(new AgentMessage(this.name, "hello", "inform"));
//	}

	/**
	 * Method that makes the vehicle move
	 */
	@ScheduledMethod(start = 1, interval = 1)
	public void step() {

		AgentMessage m = read();
		GridPoint pt = grid.getLocation(this);

		while (m != null) {
			System.out.println(m);
			m = read();

		}

		if (!hasCustomer) {

			List<Object> clients = new ArrayList<Object>();

			// on recherche sur une liste de clients dont on n'apas encore repondu aux requetes

			for (Object obj : grid.getObjectsAt(pt.getX(), pt.getY())) {
				// for (Client obj : listePartagee.keySet()) {
				 if (obj instanceof Client) {

				// Informer les autres qu'on a trouve un client
				send(new AgentMessage(this.name, "found a client", "inform"));
				if (((Client) obj).isRideRequested()) {
					clients.add(obj);
				}
			}
		}

		if (clients.size() > 0) {
			// dans cette liste on prend celui le plus proche de notre taxi
			int index = 0, coordX = (int) space.getLocation(this).getX(), coordY = (int) space.getLocation(this).getY();
			int distance = Integer.MAX_VALUE;
			for (Object client : clients) {
				int clientDistance = (int) Math.pow(Math.pow(coordX - space.getLocation(client).getX(), 2)
						+ Math.pow(coordY - space.getLocation(client).getY(), 2), 0.5);
				if (clientDistance < distance) {
					index = clients.indexOf(client);
					distance = clientDistance;
				}
			}

			this.client = (Client) clients.get(index);

			context.remove(clients.get(index));
			hasCustomer = true;

		} else {
			// Direction.EAST, Direction.EAST ...
			space.moveByVector(this, 1, RandomHelper.nextDoubleFromTo(0, Math.PI * 2),
					RandomHelper.nextDoubleFromTo(0, Math.PI * 2));
			grid.moveTo(this, (int) space.getLocation(this).getX(), (int) space.getLocation(this).getY());
		}

	}else

	{
		if (Math.abs((pt.getX() - client.getMyRequest().getDestination().getX())) < THRESHOLD
				&& Math.abs((pt.getY() - client.getMyRequest().getDestination().getY())) < THRESHOLD) {
			client.arrived();
			send(new AgentMessage(this.name, "arrived", "inform"));
			context.add(client);
			space.moveTo(client, pt.getX(), pt.getY());
			grid.moveTo(client, (int) pt.getX(), (int) pt.getY());
			hasCustomer = false;
			client = null;

		} else {

			double angle = SpatialMath.calcAngleFor2DMovement(space, space.getLocation(this),
					client.getMyRequest().getDestination());
			space.moveByVector(this, 1, angle, 0);
			grid.moveTo(this, (int) space.getLocation(this).getX(), (int) space.getLocation(this).getY());
		}

	}
	}

	private void moveTowards(GridPoint pt) {
		// only move if we are not already in this grid location
		if (!pt.equals(grid.getLocation(this))) {
			NdPoint myPoint = space.getLocation(this);
			NdPoint otherPoint = new NdPoint(pt.getX(), pt.getY());
			double angle = SpatialMath.calcAngleFor2DMovement(space, myPoint, otherPoint);
			space.moveByVector(this, 1, angle, 0);
			myPoint = space.getLocation(this);
			grid.moveTo(this, (int) myPoint.getX(), (int) myPoint.getY());
			moved = true;

		}

	}

	protected void send(AgentMessage m) {
		ec.send(this, m);
	}

	public void receive(AgentMessage m) {
		mailbox.add(m);
	}

	private AgentMessage read() {
		if (mailbox.size() > 0)
			return mailbox.remove(0);
		return null;
	}

	@Override
	public String toString() {
		return "Vehicle [name=" + name + ", speed=" + speed + ", client=" + client + "]";
	}

	/**
	 * Getter
	 * 
	 * @return
	 */
	public boolean isHasCustomer() {
		return hasCustomer;
	}

	/**
	 * Setter
	 * 
	 * @param hasCustomer
	 */
	public void setHasCustomer(boolean hasCustomer) {
		this.hasCustomer = hasCustomer;
	}

	/**
	 * Method that calculates the travel time (estimation)
	 * 
	 * @param request : Request to compute
	 * @return int that represents the ticks number of the estimated travel time
	 */
	public int calcTravelTime(Request request) {
		NdPoint currentLocation = space.getLocation(this);
		NdPoint s1location = request.getOrigin();
		NdPoint s2location = request.getDestination();

		// Calc time between current position and first source
		double distance1 = this.space.getDistance(currentLocation, s1location);
		// Calc time between first source and second source
		double distance2 = this.space.getDistance(s1location, s2location);
		// Added times
		double totaldistance = distance1 + distance2;
		int totaltime = (int) (totaldistance / (this.speed * 0.5));

		return totaltime;
	}

	public Context<Object> getContext() {
		return context;
	}

	/**
	 * Getter
	 * 
	 * @return
	 */
	public String getName() {
		return this.name;
	}

	public double distance(Vehicle obj) {
		NdPoint currentLocation = space.getLocation(this);
		NdPoint otherLocation = space.getLocation(obj);
		return this.space.getDistance(currentLocation, otherLocation);
	}
}
