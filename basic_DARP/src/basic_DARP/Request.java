package basic_DARP;

import java.util.UUID;

import repast.simphony.space.continuous.NdPoint;

/**
 * Class that modelizes a Request
 * 
 *
 */
public class Request {
	String uuid;
	int clientId;
	NdPoint destination;
	NdPoint origin;
	int timewindow;
	int askedAt;
	int takenAt;
	int finishedAt;
	int estimatedTravel;
	String vehicleuuid;
	

	/**
	 * Constructor of the Request
	 * @param clientId : int which is the id of the source (Source Class)
	 * @param destination : int which is the id of the destination (Source Class)
	 * @param timewindow : int which is the time window for the request to be satisfied
	 * @param askedAt : int which is the tick number when the request was asked
	 */
	public Request(int clientId, NdPoint origin, NdPoint destination, int timewindow, int askedAt) {
		
		this.uuid = UUID.randomUUID().toString();
		this.clientId = clientId;
		
		this.origin = origin;
		this.destination = destination;
		
		this.timewindow = timewindow;
		this.askedAt = askedAt;
		this.takenAt = 0;
		this.finishedAt = 0;
		this.estimatedTravel = 0;
		this.vehicleuuid = "";

	}

	/**
	 * Getter of UUID
	 * @return String : uuid
	 */
	public String getUuid() {
		return uuid;
	}

	/**
	 * Setter of UUID
	 * @param uuid : String that represents the UUID
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	/**
	 * Getter of source id
	 * @return int that represents the source id
	 */
	public int getSource() {
		return clientId;
	}

	/**
	 * Setter of source id
	 * @param source : int that represents the source id
	 */
	public void setSource(int source) {
		this.clientId = source;
	}

	/**
	 * Getter of destination id
	 * @return int that represents the destination id
	 */
	public NdPoint getDestination() {
		return destination;
	}

	/**
	 * Setter of destination id
	 * @param destination : int that represents the destination id
	 */
	public void setDestination(NdPoint destination) {
		this.destination = destination;
	}

	/**
	 * Getter
	 * @return
	 */
	public int getAskedAt() {
		return askedAt;
	}

	/**
	 * Setter
	 * @param askedAt
	 */
	public void setAskedAt(int askedAt) {
		this.askedAt = askedAt;
	}

	/**
	 * Getter
	 * @return
	 */
	public int getFinishedAt() {
		return finishedAt;
	}

	/**
	 * Setter
	 * @param finishedAt
	 */
	public void setFinishedAt(int finishedAt) {
		this.finishedAt = finishedAt;
	}

	/**
	 * Getter
	 * @return
	 */
	public int getTimewindow() {
		return timewindow;
	}

	/**
	 * Setter
	 * @param timewindow
	 */
	public void setTimewindow(int timewindow) {
		this.timewindow = timewindow;
	}

	/**
	 * Getter
	 * @return
	 */
	public int getTakenAt() {
		return takenAt;
	}

	/**
	 * Setter
	 * @param takenAt
	 */
	public void setTakenAt(int takenAt) {
		this.takenAt = takenAt;
	}

	/**
	 * Getter
	 * @return
	 */
	public int getEstimatedTravel() {
		return estimatedTravel;
	}

	/**
	 * Setter
	 * @param estimatedTravel
	 */
	public void setEstimatedTravel(int estimatedTravel) {
		this.estimatedTravel = estimatedTravel;
	}

	/**
	 * Getter
	 * @return
	 */
	public String getVehicleuuid() {
		return vehicleuuid;
	}

	/**
	 * Setter
	 * @param vehicleuuid
	 */
	public void setVehicleuuid(String vehicleuuid) {
		this.vehicleuuid = vehicleuuid;
	}

	/**
	 * toString method
	 */
	@Override
	public String toString() {
		return "Request [uuid=" + uuid + ", source=" + clientId + ", destination=" + destination + ", timewindow="
				+ timewindow + ", askedAt=" + askedAt + ", takenAt=" + takenAt + ", finishedAt=" + finishedAt
				+ ", estimatedTravel=" + estimatedTravel + ", vehicleuuid=" + vehicleuuid + "]";
	}

	public NdPoint getOrigin() {
		
		return origin;
	}
}
