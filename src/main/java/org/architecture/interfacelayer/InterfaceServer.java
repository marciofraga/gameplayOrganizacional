package org.architecture.interfacelayer;

import java.util.ArrayList;
import java.util.List;

public interface InterfaceServer {
		
	public String getName();
	public void setName(String name);
	
	public String getPathBT();
	public void setPathBT(String urlBT);
	
	public String getPathOS();
	public void setPathOS(String urlOS);
	
	public String getFileOS();
	public void setFileOS(String fileOS);
	
	public String getFileBT();
	public void setFileBT(String fileBT);
	
	public byte[] getAdressRCon();
	public void setAdressRCon(byte[] address);
	
	public String getPasswordRCon();
	public void setPasswordRCon(String password);
	
	public ArrayList<String> getListActions();
	public void setListActions(ArrayList<String> actions);
	
	public ArrayList<String> getListMissions();
	public void setListMissions(ArrayList<String> missions);
	
	public List<String> getAllBT();
	
	public String getSchemeSelected();
	public void setSchemeSelected(String scheme);
	
	public String getMissionSelected();
	public void setMissionSelected(String mission);
	
	public Boolean isShowGui();
	public void setShowGui(Boolean showGui);
}
