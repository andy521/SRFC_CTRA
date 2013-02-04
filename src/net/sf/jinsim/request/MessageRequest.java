package net.sf.jinsim.request;

import java.nio.ByteBuffer;

import net.sf.jinsim.Colors;
import net.sf.jinsim.PacketType;



public class MessageRequest extends InSimRequest implements Colors {

	private String message;
	
	public MessageRequest() {
		super(PacketType.MESSAGE, (byte) 68);
	}
	
	public MessageRequest(String message) {
		this();
		this.message = message;
	}
	
	
	public void assemble(ByteBuffer buffer) {
        super.assemble(buffer);
        buffer.put((byte)0);
        assembleString(buffer, message, 64);
    }


	public void setMessage(String message) {
		this.message = message;
	}

}
