package org.architecture.agents;

import jade.core.behaviours.CyclicBehaviour;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import jade.wrapper.StaleProxyException;
import ora4mas.nopl.GroupBoard;
import ora4mas.nopl.ORA4MASConstants;
import ora4mas.nopl.SchemeBoard;

import org.architecture.servicelayer.ArtifactService;
import org.architecture.servicelayer.CommandService;
import org.architecture.servicelayer.SocketService;

import cartago.ArtifactConfig;
import cartago.CartagoException;
import cartago.CartagoNode;
import cartago.CartagoService;
import cartago.CartagoWorkspace;
import cartago.ICartagoContext;
import cartago.security.AgentIdCredential;

/**
 * 
 * @author MarcioFraga
 *
 */
@SuppressWarnings("serial")
public class OrgGame extends OrgAgent{
	
	private CartagoWorkspace workspace;
	private ICartagoContext ora4masWS;
	
	private String orgName = "";
	private int qtd = 0;
	
	protected void setup() {
			
		initCartago();
		addBehaviour(new CyclicBehaviour() {
			@Override
			public void action() {
				
				try {
					
					SocketService socket = new SocketService(5150);	
					String resultado = socket.receive();
		
					switch(resultado.trim().substring(0, resultado.indexOf(":"))) {
						case "add_player":
							System.out.println("iniciando player...");
							initPlayer(resultado.trim().split(":"));
							break;
						case "destroy_player":
							System.out.println("removendo player...");
							destroyPlayer(resultado.trim().split(":"));
							break;
						case "init_organization":
							System.out.println("iniciando organizacao...");
							initOrganization(resultado.trim().split(":"));
							break;
						case "change_organization":
							System.out.println("substituindo organizacao...");
							changeOrganization(resultado.trim().split(":"));
							break;
						case "change_scheme":
							System.out.println("alterando o esquema...");
							changeScheme(resultado.trim().split(":"));
							break;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}	
			}
		});
	}

	private void initCartago() {

		try {
			CartagoService.startNode();
			CartagoService.installInfrastructureLayer("default");
			CartagoService.startInfrastructureService("default");
		} catch (CartagoException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * 
	 * @throws Exception
	 */
	protected void initOrganization(String[] parameter) throws Exception {

		orgName = parameter[1]+".xml";
		
		workspace = CartagoNode.getInstance().createWorkspace(ORA4MASConstants.ORA4MAS_WSNAME);
		ora4masWS = workspace.join(new AgentIdCredential("orgManager"),null);
		
		workspace.getKernel().makeArtifact(ora4masWS.getAgentId(), "g1",  GroupBoard.class.getName(), 
				new ArtifactConfig(CommandService.server.getPathOS() + orgName, ArtifactService.getNomeGrupo()));
	}
	
	protected void changeOrganization(String[] parameter) throws CartagoException {

		orgName = parameter[1] + ".xml";
		
		if(workspace.getKernel().getArtifact("g1") != null) {
			workspace.getKernel().disposeArtifact(ora4masWS.getAgentId(), workspace.getKernel().getArtifact("g1"));
			
			if(workspace.getKernel().getArtifact("s1") != null)
				workspace.getKernel().disposeArtifact(ora4masWS.getAgentId(), workspace.getKernel().getArtifact("s1"));
		}
		
		workspace.getKernel().makeArtifact(ora4masWS.getAgentId(), "g1", GroupBoard.class.getName(), 
				new ArtifactConfig(CommandService.server.getPathOS() + orgName, ArtifactService.getNomeGrupo()));
		
		System.out.println("organizacao substituida!");
		
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	public void initPlayer(String[] parameter) throws Exception {

		int qtd = Integer.parseInt(parameter[1]);
		String rol = parameter[2];
		
		for(int i=this.qtd; i < qtd + this.qtd; i++) {
			String name = String.join("_", rol, Integer.toString(i));
			
			AgentController ag = getContainerController().createNewAgent(name, OrgPlayer.class.getName(), new Object[]{name, rol});	
			ag.start();
			CommandService.instanciaPersonagem(rol);
		}
		this.qtd += qtd;
	}
	
	/**
	 * 
	 * @param parameter
	 * @throws ControllerException 
	 * @throws StaleProxyException 
	 */
	public void destroyPlayer(String[] parameter) throws StaleProxyException, ControllerException {
		
		if(getContainerController().getAgent(parameter[1]) != null) {
			getContainerController().getAgent(parameter[1]).kill();
			CommandService.destroiPersonagem(parameter[1]);
		}
		
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	protected void changeScheme(String[] esquema) throws Exception {
		
		if(workspace.getKernel().getArtifact("s1") != null)
			workspace.getKernel().disposeArtifact(ora4masWS.getAgentId(), workspace.getKernel().getArtifact("s1"));
		
		workspace.getKernel().makeArtifact(ora4masWS.getAgentId(), "s1", SchemeBoard.class.getName(), 
				new ArtifactConfig(CommandService.server.getPathOS() + CommandService.server.getFileOS(), esquema[1]));
		
		System.out.println("esquema mudado!");
	}
}