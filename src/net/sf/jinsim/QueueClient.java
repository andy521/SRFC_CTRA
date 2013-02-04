package net.sf.jinsim;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.jinsim.request.InSimRequest;
import net.sf.jinsim.response.InSimListener;
import net.sf.jinsim.response.InSimResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class QueueClient extends Client {
	
	private static Log log = LogFactory.getLog(QueueClient.class);
	
	private static final int QUEUE_SIZE = 253;
	
	private ArrayList<InSimListener> listeners;
	
	public QueueClient() {
		listeners = new ArrayList<InSimListener>(QUEUE_SIZE);
	}
	
	public void setListeners(List<InSimListener> listeners) {
		this.listeners.addAll(listeners);
	}
	
	
	public void addListener(InSimListener listener) {
		// search for the next empty space in the array list
		int i=0;
		while( listeners.size() > i && listeners.get(i) != null) {
			i++;
		}
		if (i>QUEUE_SIZE) {
			throw new IndexOutOfBoundsException("Maximum number of listeners exceeded");
		}
		System.out.println("adding at index " + i);
		listeners.add(i, listener);
	}
	
	public void removeListener(InSimListener listener) {
		listeners.remove(listener);
	}
	
	@Override
	public void notifyListeners(InSimResponse packetData) {
		int requestInfo = packetData.getRequestInfo() & 0xFF;
		InSimListener listener = null;
		if (requestInfo > 0 && requestInfo < QUEUE_SIZE && requestInfo  <= listeners.size()) {
			listener = listeners.get(requestInfo-1);
		}
		if (listener != null) {
			listener.packetReceived(packetData);
		} else {
			for (InSimListener l : listeners) {
				l.packetReceived(packetData);
			}
		}
	}


	@Override
	public void send(InSimRequest packet) throws IOException {
		super.send(packet);
	}
	
	public void send(InSimRequest packet, InSimListener listener) throws IOException {
		int index = listeners.indexOf(listener);
		if (index > -1) {
			System.out.println("set request info: " + (index+1));
			packet.setRequestInfo((byte)(index+1));
		} else {
			packet.setRequestInfo((byte)255);
		}
		super.send(packet);
	}

}
