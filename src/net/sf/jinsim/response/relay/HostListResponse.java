package net.sf.jinsim.response.relay;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;


import net.sf.jinsim.PacketType;
import net.sf.jinsim.Track;
import net.sf.jinsim.types.HostInfo;


public class HostListResponse extends InSimRelayResponse {

	private ArrayList<HostInfo> hostList;
	
	public HostListResponse() {
		super(PacketType.RELAY_HOST_LIST_INFO);
	}

	@Override
	public void construct(ByteBuffer buffer) throws BufferUnderflowException {
		super.construct(buffer);
		
		int numberHosts = buffer.get();
		
		hostList = new ArrayList<HostInfo>(numberHosts);
		for(int i=0; i<numberHosts; i++) {
			hostList.add(createHostInfo(buffer));
		}
	}

	private HostInfo createHostInfo(ByteBuffer buffer) {
		String name = getString(buffer, 32);
		String track = getString(buffer, 6);
		byte flags = buffer.get();
		byte numberConnections = buffer.get();
		
		boolean requirePassword = (flags&1) > 0;
		boolean licensed = (flags&2) > 0;
		
		return new HostInfo(name, Track.getTrackByShortName(track), licensed, requirePassword, numberConnections);
	}
	
	public List<HostInfo> getHostList() {
		return hostList;
	}

}
