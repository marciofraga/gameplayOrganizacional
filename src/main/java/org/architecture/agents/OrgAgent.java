package org.architecture.agents;

import jade.core.Agent;
import ora4mas.nopl.ORA4MASConstants;
import cartago.ArtifactId;
import cartago.CartagoException;
import cartago.IEventFilter;
import cartago.Op;
import cartago.WorkspaceId;
import cartago.security.AgentCredential;
import cartago.util.agent.ActionFailedException;
import cartago.util.agent.ActionFeedback;
import cartago.util.agent.ArtifactObsProperty;
import cartago.util.agent.CartagoBasicContext;
import cartago.util.agent.Percept;

/**
 * 
 * @author MarcioFraga
 *
 */
@SuppressWarnings("serial")
public class OrgAgent extends Agent{	

	private cartago.util.agent.Agent cartagoAgent;
	private CartagoBasicContext ctx;
	private String role;
	
	protected void init() {
		cartagoAgent = new cartago.util.agent.Agent((String)getArguments()[0]);
		ctx = new CartagoBasicContext(cartagoAgent.getName(), ORA4MASConstants.ORA4MAS_WSNAME);
		role = (String)getArguments()[1];
	}
	
	protected String getAgentName() {
		return cartagoAgent.getName();
	}

	protected ActionFeedback doActionAsync(Op op) throws CartagoException {
		return ctx.doActionAsync(op);
	}

	protected ActionFeedback doActionAsync(ArtifactId aid, Op op, long timeout) throws CartagoException {
		return ctx.doActionAsync(aid, op, timeout);
	}

	protected ActionFeedback doActionAsync(Op op, long timeout) throws CartagoException {
		return ctx.doActionAsync(op, timeout);
	}

	protected void doAction(Op op, long timeout) throws ActionFailedException, CartagoException {
		ctx.doAction(op, timeout);
	}

	protected void doAction(Op op) throws ActionFailedException, CartagoException {
		this.doAction(op, -1);
	}

	protected void doAction(ArtifactId aid, Op op, long timeout) throws ActionFailedException, CartagoException {
		ctx.doAction(aid, op, timeout);
	}

	protected void doAction(ArtifactId aid, Op op) throws ActionFailedException, CartagoException {
		this.doAction(aid,op,-1);
	}

	protected Percept fetchPercept() throws InterruptedException {
		return ctx.fetchPercept();
	}

	protected Percept waitPercept() throws InterruptedException {
		return ctx.waitForPercept();
	}

	protected Percept waitForPercept(IEventFilter filter) throws InterruptedException {
		return ctx.waitForPercept(filter);
	}

	protected void log(String msg){
		System.out.println("["+ctx.getName()+"] "+msg);
	}
	
	//Utility methods

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	protected WorkspaceId joinWorkspace(String wspName, AgentCredential cred) throws CartagoException {
		return ctx.joinWorkspace(wspName, cred);
	}

	protected WorkspaceId joinRemoteWorkspace(String wspName, String address, String roleName, AgentCredential cred)  throws CartagoException {
		return ctx.joinRemoteWorkspace(wspName, address, roleName, cred);
	}

	protected ArtifactId lookupArtifact(String artifactName) throws CartagoException {
		return ctx.lookupArtifact(artifactName);
	}

	protected ArtifactId makeArtifact(String artifactName, String templateName) throws CartagoException {
		return ctx.makeArtifact(artifactName, templateName);
	}

	protected ArtifactId makeArtifact(String artifactName, String templateName, Object[] params) throws CartagoException {
		return ctx.makeArtifact(artifactName, templateName, params);
	}

	protected void disposeArtifact(ArtifactId artifactId) throws CartagoException {
		ctx.disposeArtifact(artifactId);
	}

	protected void focus(ArtifactId artifactId) throws CartagoException {
		ctx.focus(artifactId);
	}

	protected void focus(ArtifactId artifactId, IEventFilter filter) throws CartagoException {
		ctx.focus(artifactId, filter);
	}

	protected void stopFocus(ArtifactId aid) throws CartagoException {
		ctx.stopFocus(aid);
	}
	
	protected ArtifactObsProperty getObsProperty(String name){
		return ctx.getObsProperty(name);
	}
}
