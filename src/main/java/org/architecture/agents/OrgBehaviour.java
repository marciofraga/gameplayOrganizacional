package org.architecture.agents;	

import jade.core.behaviours.Behaviour;

import java.util.ArrayList;
import java.util.Collection;

import moise.os.fs.Goal;
import moise.os.fs.Mission;

import org.architecture.servicelayer.ArtifactService;
import org.architecture.servicelayer.BTService;

@SuppressWarnings("serial")
public class OrgBehaviour extends Behaviour{

	private ArrayList<Mission> missoesObrigatorias = null;
	private ArrayList<Mission> missoesPermitidas = null;
		
	@Override
	public void action() {
		BTService btree;
		try {
			
			if(((OrgPlayer) myAgent).getObsProperty("formationStatus").stringValue().equals("ok") && (missoesObrigatorias == null && missoesPermitidas == null))
					commitMissions();	
			
			Thread.sleep(100);
			if(missoesObrigatorias != null && missoesPermitidas != null) {
				
				((OrgPlayer) myAgent).focus(((OrgPlayer) myAgent).lookupArtifact("s1"));
				btree = new BTService(((OrgPlayer) myAgent).getObsProperty("name").stringValue());
				
				Collection<Goal> metas = ArtifactService.getAllMetas((String) ((OrgPlayer) myAgent).getObsProperty("name").getValue()); 
				for(Goal g : metas) {
					
					if(!ArtifactService.isMetaFromPapel(g.getId(),((OrgPlayer) myAgent).getRole()))
						continue;
						
					Object resultado = btree.executaBT(g.getId(),myAgent.getLocalName());
					if(resultado == null)
						continue;
					
					resultBehaviorTree(resultado,g);					
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void resultBehaviorTree(Object resultado,Goal g) throws Exception {
		
		switch((int)resultado) {
			case 1://b3.SUCCESS
				((OrgPlayer) myAgent).execOpSync("s1", "goalAchieved", g.getId());
				break;
			case 2://b3.FAILURE
				
				break;
			case 3://b3.RUNNING
				//((OrgPlayer) myAgent).wait();
				break;
			case 4://b3.ERROR
				break;
		}
	}
	private void commitMissions() throws Exception {
		
		// Compromete-se com as missoes que são obrigatorias e as permitidas
		//Missoes obrigatorias
		missoesObrigatorias = (ArrayList<Mission>) ArtifactService.getMissoesObrgatoriasByPapel(((OrgPlayer)myAgent).getRole());
		for(Mission m : missoesObrigatorias)
			((OrgPlayer)myAgent).execOpSync("s1", "commitMission", m.getId());
		//Missoes permitidas					
		missoesPermitidas = (ArrayList<Mission>) ArtifactService.getMissoesPermitidasByPapel(((OrgPlayer)myAgent).getRole());
		for(Mission m : missoesPermitidas)
			((OrgPlayer)myAgent).execOpSync("s1", "commitMission", m.getId());
	}

	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		return false;
	}

}
