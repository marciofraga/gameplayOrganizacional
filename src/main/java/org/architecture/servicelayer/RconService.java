package org.architecture.servicelayer;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import com.github.koraktor.steamcondenser.exceptions.SteamCondenserException;
import com.github.koraktor.steamcondenser.steam.servers.SourceServer;

public class RconService {

	private InetAddress serverIp;
	private SourceServer server;
	private byte[] address;
	private String password;
	
	public RconService(byte[] address, String password) throws UnknownHostException, SteamCondenserException, TimeoutException {
		
		this.address = address;
		this.password = password;
		conexaoRCon(address,password);
		
		/*serverIp = InetAddress.getByAddress(address);
		GoldSrcServer server = new GoldSrcServer(serverIp, 27015);
		server.initialize();
		System.out.println(server.getServerInfo());
		*/
	}
	
	public void conexaoRCon(byte[] address, String password) {
		
		try {
			//Fazer uma singleton para o controle da sessão do RCON
			serverIp = InetAddress.getByAddress(address);
			
			server = new SourceServer(serverIp);
			System.out.println("connecting " + serverIp.getHostAddress());
			server.rconAuth(password);
			
		}
		catch(SteamCondenserException | TimeoutException | UnknownHostException e) {
		  System.err.println("Could not authenticate with the game server.");
		}
	}
	
	public Boolean isConectado() {
		if(server == null)
			return false;
		
		return server.isRconAuthenticated();
	}
	
	public String enviaComando(String comando) {
		
		String resultado = null;
		try {
			
			resultado = server.rconExec(comando);
			
		} catch (TimeoutException | SteamCondenserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;
	}
}
