package org.architecture.agents;

import ora4mas.nopl.ORA4MASConstants;

import org.architecture.servicelayer.ArtifactService;
import org.architecture.servicelayer.CommandService;

import cartago.ArtifactId;
import cartago.CartagoException;
import cartago.Op;
import cartago.security.AgentIdCredential;
import cartago.util.agent.ActionFeedback;

/**
 * 
 * @author MarcioFraga
 *
 */
@SuppressWarnings("serial")
public class OrgPlayer extends OrgAgent{
	
	protected void setup() {
		
		try {
			initAgent();
			initRoleSchemes();	
			
			addBehaviour(new OrgBehaviour());
			
			//Percept p = waitPercept();
            //System.out.println(p + "\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 */
	private void initAgent() {
		
		try {
			super.init();
			joinWorkspace(ORA4MASConstants.ORA4MAS_WSNAME, new AgentIdCredential((String)getArguments()[0]));
		} catch (CartagoException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	private void initRoleSchemes() throws Exception {
		
		if(getLocalName().contains(ArtifactService.getPapelEsquema()) && CommandService.server.isShowGui()) {
			execOpSync("g1", "startGUI", "");
			execOpSync("s1", "startGUI", "");
			
			CommandService.server.setShowGui(false);
		}
		
		focus(lookupArtifact("g1"));
		execOpSync("g1", "adoptRole", getRole());
		
		//TODO: definir como generalizar essa parte
		if(getLocalName().contains(ArtifactService.getPapelEsquema()))
			execOpSync("g1", "addSchemeWhenFormationOk", "s1");
		
		/*
		if(getObsProperty("formationStatus").stringValue().equals("ok")) {
			focus(lookupArtifact("g1"));
			execOpSync("g1", "adoptRole", getRole());
		}*/
	
	}
	
	/**
	 * 
	 * @param art
	 * @param op
	 * @param args
	 * @throws Exception
	 */
	public void execOpSync(final String art, final String op, final String args) throws Exception {
        ArtifactId aid = lookupArtifact(art);
        if (args.length() == 0) {
            doAction(aid, new Op(op), -1);
        } else {
            doAction(aid, new Op(op, (Object[])args.split(" ")), -1);
        }
    }
	
	/**
	 * 
	 * @param art
	 * @param op
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public ActionFeedback execOpAsync(final String art, final String op, final String args) throws Exception {
        ArtifactId aid = lookupArtifact(art);
        if (args.length() == 0) {
            return doActionAsync(aid, new Op(op), -1);
        } else {
            return doActionAsync(aid, new Op(op, (Object[])args.split(" ")), -1);
        }
    }
}
