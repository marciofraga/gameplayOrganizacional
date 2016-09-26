package org.architecture.servicelayer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class SocketService {

	//private InetAddress addr;
	private int port;
	private DatagramSocket datagram;
	private DatagramPacket packet;
	
	public SocketService(int port) throws IOException {
		//addr = InetAddress.getByName(host);
		this.port = port;
	}
	
	public String receive() {
		
		String resultado = null;
		try {
			datagram = new DatagramSocket(this.port);
			
			byte[] msg = new byte[256];
			packet = new DatagramPacket(msg, msg.length);
			
			datagram.receive(packet);
			
			resultado = new String(packet.getData());
			datagram.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return resultado;
	}
	
	/*public void send(byte[] msg) {
		
		try {
			packet = new DatagramPacket(msg, msg.length, addr, port);
			datagram = new DatagramSocket();
			
			datagram.send(packet);
			datagram.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/
}
