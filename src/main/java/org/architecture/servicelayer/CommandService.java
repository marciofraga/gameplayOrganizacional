package org.architecture.servicelayer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Set;
import java.util.concurrent.TimeoutException;




//import org.architecture.agents.OrgManager;
import org.architecture.interfacelayer.InterfaceServer;
import org.reflections.Reflections;

import com.github.koraktor.steamcondenser.exceptions.SteamCondenserException;

/**
 * @author MarcioFraga
 * Classe que envia os comandos dos nós folha da Behaviour Tree para o servidor do jogo
 */

public class CommandService {

	public static InterfaceServer server =(InterfaceServer) getAllInterfaceServerImpl();;
	private static RconService rcon = null;
	
	private static void init() {
		try {
			rcon = new RconService(server.getAdressRCon(), server.getPasswordRCon());
		} catch (UnknownHostException | SteamCondenserException | TimeoutException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @param name
	 * @return
	 */
	public String executaComando(String name) {
		
		if(rcon == null)
			init();
		
		if(!rcon.isConectado())
			rcon.conexaoRCon(server.getAdressRCon(), server.getPasswordRCon());
		
		return rcon.enviaComando(name);
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public Boolean executaComandoBooleano(String name) {
		
		if(rcon == null)
			init();
		
		if(!rcon.isConectado())
			rcon.conexaoRCon(server.getAdressRCon(), server.getPasswordRCon());
		
		return rcon.enviaComando(name) == "true" ? true: false;
	}
	
	/**
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Object getAllInterfaceServerImpl() {
		
		try {	
			Reflections ref = new Reflections("org.architecture.interfacelayer");
			Set<Class<? extends InterfaceServer>> classes = ref.getSubTypesOf(InterfaceServer.class);
			
			if(classes.size() > 1)
				return null;
			
			for(Class inf : classes)
				return inf.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	} 
	
	public static void instanciaPersonagem(String nomePersonagem) {
	//public static void instanciaPersonagem() {
		if(rcon == null)
			init();
		
		System.out.println(rcon.enviaComando("org_bot_addct " + nomePersonagem));
	}
	
	public static void destroiPersonagem(String nomePersonagem) {
		if(rcon == null)
			init();
		
		if(!rcon.isConectado())
			rcon.conexaoRCon(server.getAdressRCon(), server.getPasswordRCon());
		
		rcon.enviaComando("kick " + nomePersonagem);
	}
	
	/**
	 * 
	 * @return
	 */
	@SuppressWarnings("resource")
	public static String getSchemeSelected() {
		
		if(server == null)
			server =(InterfaceServer) getAllInterfaceServerImpl();
		
		String resultado = null;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(server.getPathOS() + server.getFileOS()));
			
			resultado = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return resultado;
	}
	
	/**
	 * 
	 * @param o
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Boolean isInterfaceServerImpl(Object o) {
		
		Class interfaceServer = o.getClass();
		
		for (Class interf : interfaceServer.getInterfaces()) {
			
			if(interf.getName().equals(InterfaceServer.class.getName())) {
				return true;
			}
		}
		return false;
	}
}
