package net.sf.jinsim.request.relay;

import java.nio.ByteBuffer;

import net.sf.jinsim.PacketType;



public class HostListRequest extends InSimRelayRequest {

	public HostListRequest() {
		super(PacketType.RELAY_HOST_LIST, (byte)4);
	}

	@Override
	public void assemble(ByteBuffer byteBuffer) {
		super.assemble(byteBuffer);
		byteBuffer.position(byteBuffer.position()+1);
	}

}
