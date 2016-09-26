package org.architecture.interfacelayer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InterfaceServerImpl implements InterfaceServer{

	public String name = "Half-Life: DeathMatch";
	public String pathBT = "C:/organization/script/missions/";
	public String pathOS = "C:/organization/moise/";
	public String fileBT = "";
	public String fileOS = "team.xml";
	public String schemeSelected = "";
	public byte[] addressRCon = new byte[]{(byte) 192, (byte)168, (byte)9, (byte)5};
	public String passwordRCon = "123456";
	public ArrayList<String> listActions;
	public ArrayList<String> missions;
	
	
	public static String[] nomes = {"soldado","sargento"};
	private String pathFile = "C:/organization/log/vslog.txt";
	private String missionSelected = "";
	private Boolean showGui = true;
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getPathBT() {
		return pathBT;
	}

	@Override
	public void setPathBT(String pathBT) {
		this.pathBT = pathBT;
	}

	@Override
	public String getPathOS() {
		return pathOS;
	}

	@Override
	public void setPathOS(String pathOS) {
		this.pathOS = pathOS;
	}

	@Override
	public byte[] getAdressRCon() {
		return addressRCon;
	}

	@Override
	public void setAdressRCon(byte[] address) {
		this.addressRCon = address;
	}

	@Override
	public String getPasswordRCon() {
		return passwordRCon;
	}

	@Override
	public void setPasswordRCon(String passwordRCon) {
		this.passwordRCon = passwordRCon;		
	}

	@Override
	public ArrayList<String> getListActions() {
		return listActions;
	}

	@Override
	public void setListActions(ArrayList<String> listActions) {
		this.listActions = listActions;
	}

	@Override
	public String getFileBT() {
		return fileBT;
	}

	@Override
	public void setFileBT(String fileBT) {
		this.fileBT = fileBT;
	}

	@Override
	public String getFileOS() {
		return fileOS;
	}

	@Override
	public void setFileOS(String fileOS) {
		this.fileOS = fileOS;
	}

	@Override
	public ArrayList<String> getListMissions() {
		return missions;
	}

	@Override
	public void setListMissions(ArrayList<String> missions) {
		this.missions = missions;
	}

	@Override
	public List<String> getAllBT() {
		File directory = new File(getPathOS());
		
		ArrayList<String> resultado = new ArrayList<String>();
		File[] files = directory.listFiles();
		for(File f : files) {
			if(f.isFile())
				resultado.add(f.getName());
		}
		
		return resultado;
	}

	@Override
	@SuppressWarnings("resource")
	public String getSchemeSelected() {
		
		try {
			BufferedReader read = new BufferedReader(new FileReader(pathFile));
			String linha = read.readLine();
			
			schemeSelected = linha.substring(0,linha.indexOf("_"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
				
		return schemeSelected;
	}

	@Override
	public void setSchemeSelected(String scheme) {
		this.schemeSelected = scheme;
	}

	@Override
	@SuppressWarnings("resource")
	public String getMissionSelected() {
		
		try {
			BufferedReader read = new BufferedReader(new FileReader(pathFile));
			String linha = read.readLine();
			
			missionSelected = linha.substring(linha.indexOf("_") + 1);
			
			
		} catch(IOException e) {
			e.printStackTrace();
		}
		return missionSelected;
	}

	@Override
	public void setMissionSelected(String mission) {
		this.missionSelected = mission;
	}
	
	@Override
	public Boolean isShowGui() {
		return showGui;
	}
	
	@Override
	public void setShowGui(Boolean showGui) {
		this.showGui = showGui;
	}
}

