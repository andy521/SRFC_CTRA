package net.sf.jinsim.response;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;


import net.sf.jinsim.Car;
import net.sf.jinsim.PacketType;
import net.sf.jinsim.types.Tyres;


public class NewPlayerResponse extends PlayerResponse {

	private byte connectionId;
	private byte playerType;
	private int playerFlags;
	private String playerName;
	private String numberPlate;
	private Car car;
	private String skinName;
	private Tyres tyres; 
	private byte addedMass;
	private byte intakeRestriction;
	private byte passengers;
	private byte numberInRace;
	private byte model;
	
	
	public NewPlayerResponse() {
		super(PacketType.NEW_PLAYER);
	}

	public byte getAddedMass() {
		return addedMass;
	}

	public Car getCar() {
		return car;
	}

	public byte getIntakeRestriction() {
		return intakeRestriction;
	}

	public byte getNumberInRace() {
		return numberInRace;
	}

	public String getNumberPlate() {
		return numberPlate;
	}

	public byte getPassengers() {
		return passengers;
	}

	public int getPlayerFlags() {
		return playerFlags;
	}


	public String getPlayerName() {
		return playerName;
	}

	public byte getPlayerType() {
		return playerType;
	}

	public String getSkinName() {
		return skinName;
	}

	public Tyres getTyres() {
		return tyres;
	}


	@Override
	public void construct(ByteBuffer buffer) throws BufferUnderflowException {
		super.construct(buffer);
		connectionId = buffer.get();
		playerType = buffer.get();;
		playerFlags = buffer.getShort();
		playerName = getString(buffer, 24);
		numberPlate = getString(buffer, 8);
		String carName = getString(buffer, 4);
		car = Car.getCarByName(carName);
		skinName = getString(buffer, 16);
		tyres = new Tyres(buffer); 
		addedMass = buffer.get();
		intakeRestriction = buffer.get();
		model = buffer.get();
		passengers = buffer.get();
		buffer.position(buffer.position()+5);
		numberInRace = buffer.get();
		buffer.position(buffer.position()+2);
	}

	@Override
	public String toString() {
		return super.toString() +
		", uniqueId: " + connectionId +
		", playerType: " + playerType+
		", playerFlags: " + playerFlags +
		", playerName: " + playerName +
		", numberPlate: " + numberPlate + 
		", car: " + car + 
		", skinName: " + skinName +
		", tyres: " + tyres +  
		", addedMass: " + addedMass +
		", intakeRestriction: " + intakeRestriction +
		", model: " + model +
		", passengers: " + passengers +
		", numberInRace: " + numberInRace;
	}

	public byte getConnectionId() {
		return connectionId;
	}

	public void setConnectionId(byte uniqueId) {
		this.connectionId = uniqueId;
	}

	public byte getModel() {
		return model;
	}
	
}
