package net.sf.jinsim.response;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;


import net.sf.jinsim.PacketType;
import net.sf.jinsim.types.InSimTime;


/**
 * 
 * @author Rene Pirringer (brilwing@liveforspeed.at)
 *
 */
public class FinishedRaceResponse extends PlayerResponse {

	private InSimTime totalTime;
	private InSimTime bestLap;
	private byte numberOfStops;
	private byte confirmationFlags;
	private int lapsDone;
	private int playerFlags;
	
	
	public FinishedRaceResponse() {
		super(PacketType.FINISHED_RACE);
	}
	
	@Override
	public void construct(ByteBuffer buffer) throws BufferUnderflowException {
		super.construct(buffer);
		totalTime = new InSimTime(buffer);
		bestLap = new InSimTime(buffer);
		buffer.position(buffer.position()+1);
		numberOfStops = buffer.get();
		confirmationFlags = buffer.get();
		buffer.position(buffer.position()+1);
		lapsDone = buffer.getShort();
		playerFlags = buffer.getShort();
	}

	@Override
	public String toString() {
		return super.toString() + 
		", totalTime: " + totalTime +
		", bestLap: " + bestLap + 
		", numberOfStops: " + numberOfStops +
		", confirmationFlags: " + confirmationFlags +
		", lapsDone " + lapsDone +
		", playerFlags" + playerFlags;
		
	}

	public FinishedRaceResponse(PacketType type) {
		super(PacketType.FINISHED_RACE);
	}

	public void setBestLap(InSimTime bestLap) {
		this.bestLap = bestLap;
	}

	public void setConfirmationFlags(byte confirmationFlags) {
		this.confirmationFlags = confirmationFlags;
	}

	public void setLapsDone(int lapsDone) {
		this.lapsDone = lapsDone;
	}

	public void setNumberOfStops(byte numberOfStops) {
		this.numberOfStops = numberOfStops;
	}

	public void setPlayerFlags(int playerFlags) {
		this.playerFlags = playerFlags;
	}

	public void setTotalTime(InSimTime totalTime) {
		this.totalTime = totalTime;
	}
	
	

}
