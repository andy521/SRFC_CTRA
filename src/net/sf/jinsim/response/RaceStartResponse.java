package net.sf.jinsim.response;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

import net.sf.jinsim.PacketType;
import net.sf.jinsim.Track;
import net.sf.jinsim.Weather;
import net.sf.jinsim.Wind;



public class RaceStartResponse extends InSimResponse {
	
	private static int CAN_VOTE = 1;
	private static int CAN_SELECT = 2;
	private static int CAN_MIDRACE_JOIN = 32;
	private static int MUST_PIT = 64;
	private static int CAN_RESET = 128;
	private static int FORCE_COCKPIT_VIEW = 256;
	private static int CAN_CRUISE;

	private byte raceLaps;
	private byte qualifingMinutes;
	private byte numberOfPlayers;
	private Track track;
	private Weather weather;
	private Wind wind;
	private int raceFlags;
	private int numberOfNodes;
	private int finishLineNodeIndex;
	private int split1NodeIndex;
	private int split2NodeIndex;
	private int split3NodeIndex;
	
	public RaceStartResponse() {
		super(PacketType.RACE_START);
	}

	
	public int getFinishLineNodeIndex() {
		return finishLineNodeIndex;
	}


	public int getNumberOfNodes() {
		return numberOfNodes;
	}


	public byte getNumberOfPlayers() {
		return numberOfPlayers;
	}


	public byte getQualifingMinutes() {
		return qualifingMinutes;
	}


	public int getRaceFlags() {
		return raceFlags;
	}


	public byte getRaceLaps() {
		return raceLaps;
	}


	public int getSplit1NodeIndex() {
		return split1NodeIndex;
	}


	public int getSplit2NodeIndex() {
		return split2NodeIndex;
	}


	public int getSplit3NodeIndex() {
		return split3NodeIndex;
	}


	public Track getTrack() {
		return track;
	}


	public Weather getWeather() {
		return weather;
	}


	public Wind getWind() {
		return wind;
	}


	@Override
	public void construct(ByteBuffer buffer) throws BufferUnderflowException {
		super.construct(buffer);
		buffer.position(buffer.position()+1);
		raceLaps = buffer.get();
		qualifingMinutes = buffer.get();
		numberOfPlayers = buffer.get();
		buffer.position(buffer.position()+1);
		String trackname = getString(buffer, 6);
		track = Track.getTrackByShortName(trackname);
		weather = Weather.getWeather(buffer.get());
		wind = Wind.getWind(buffer.get());
		raceFlags = buffer.getShort();
		numberOfNodes = buffer.getShort();
		finishLineNodeIndex = buffer.getShort();
		split1NodeIndex = buffer.getShort();
		split2NodeIndex = buffer.getShort();
		split3NodeIndex = buffer.getShort();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString() +
		", raceLaps: " + raceLaps +
		", qualifingMinutes: "+ qualifingMinutes +
		", numberOfPlayers: " + numberOfPlayers +
		", track: " + track +
		", weather: " + weather +
		", wind: " + wind +
		", raceFlags: " + raceFlags +
		", numberOfNodes: " + numberOfNodes + 
		", finishLineNodeIndex: " + finishLineNodeIndex + 
		", split1NodeIndex: " + split1NodeIndex + 
		", split2NodeIndex: " + split2NodeIndex + 
		", split3NodeIndex: " + split3NodeIndex;
	}

	public boolean canVote() {
		return (raceFlags & CAN_VOTE) > 0;
	}
	
	public boolean canSelect() {
		return (raceFlags & CAN_SELECT) > 0;
	}
	
	public boolean canMidraceJoin() {
		return (raceFlags & CAN_MIDRACE_JOIN) > 0;
	}
	public boolean mustPit() {
		return (raceFlags & MUST_PIT) > 0;
	}
	public boolean canReset() {
		return (raceFlags & CAN_RESET) > 0;
	}
	public boolean forceCockpitView() {
		return (raceFlags & FORCE_COCKPIT_VIEW) > 0;
	}
	public boolean canCruise() {
		return (raceFlags & CAN_CRUISE) > 0;
	}


	
}
