package org.architecture.servicelayer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import moise.common.MoiseConsistencyException;
import moise.common.MoiseException;
import moise.os.OS;
import moise.os.fs.Goal;
import moise.os.fs.Mission;
import moise.os.fs.Scheme;
import moise.os.ns.Norm;
import moise.os.ss.Role;

/**
 * 
 * @author MarcioFraga
 *
 */
public class ArtifactService {

	private static OS organizacao;
	
	/**
	 * 
	 * @return
	 * @throws MoiseConsistencyException
	 */
	public static OS getOrganizacao() {
		organizacao = OS.loadOSFromURI(CommandService.server.getPathOS() + CommandService.server.getFileOS());
		return organizacao;
	}
	
	/**
	 * 
	 * @return
	 */
	public static String getNomeGrupo() {
		
		if(organizacao == null)
			organizacao = getOrganizacao();
		
		return organizacao.getSS().getRootGrSpec().getId();
	}
	
	/**
	 * 
	 * @return
	 */
	public static String getNomeEsquema() {
		
		if(organizacao == null)
			organizacao = getOrganizacao();
		
		return ((Scheme) organizacao.getFS().getSchemes().iterator().next()).getId();
	}
	
	/**
	 * 
	 * @return
	 */
	public static Collection<Role> getPapeis() {
		if(organizacao == null)
			organizacao = getOrganizacao();
		
		return organizacao.getSS().getRolesDef();
	}
	
	/**
	 * 
	 * @param papel
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Boolean isSuperPapel(Role papel) {
		
		if(organizacao == null)
			organizacao = getOrganizacao();
		
		Iterator it = organizacao.getSS().getRolesDef().iterator();
		while(it.hasNext()) {
			Role p = (Role) it.next();
			if(p.containsSuperRole(papel))
				return true;
		}
		
		return false;
	}
	
	public static String getPapelEsquema() {
		
		if(organizacao == null)
			organizacao = getOrganizacao();
		
		return ((Role) organizacao.getSS().getRolesDef().iterator().next()).getId();
	}
	
	/**
	 * 
	 * @param organizacao
	 * @param nomeAgente
	 * @return
	 * @throws MoiseException
	 */
	public static Collection<Scheme> getEsquemas() {
		if(organizacao == null)
			organizacao = getOrganizacao();
		
		return organizacao.getFS().getSchemes();
	}
	
	/**
	 * 
	 * @param organizacao
	 * @param nomeEsquema
	 * @return
	 * @throws MoiseException
	 */
	@SuppressWarnings("rawtypes")
	public static Collection<Goal> getMetasByEsquema(Scheme esquema) {
		
		if(organizacao == null)
			organizacao = getOrganizacao();
		
		Iterator it = organizacao.getFS().getSchemes().iterator();
		while(it.hasNext()) {
			Scheme s = (Scheme) it.next();
			if(s.getId().equals(esquema.getId()))
				return s.getGoals();
		}
		return null;
	}
	
	/**
	 * 
	 * @param meta
	 * @param missao
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Boolean isMetaFromMissao(String meta, String missao) {
		
		if(organizacao == null)
			organizacao = getOrganizacao();
		
		Iterator itMetas = organizacao.getFS().findMission(missao).getGoals().iterator();
		while(itMetas.hasNext()) {
			Goal g = (Goal) itMetas.next();
			if(g.getId().equals(meta))
				return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param missao
	 * @param papel
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Boolean isMissaoFromPapel(String missao, String papel) {
		
		Iterator itNorm = organizacao.getNS().getNorms().iterator();
		while(itNorm.hasNext()) {
			Norm n = (Norm) itNorm.next();
			if(n.getRole().getId().equals(papel) && n.getMission().getId().equals(missao))
				return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param nomeMeta
	 * @param nomePapel
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Boolean isMetaFromPapel(String nomeMeta, String nomePapel) {
		
		if(organizacao == null)
			organizacao = getOrganizacao();
		
		Goal meta = organizacao.getFS().findGoal(nomeMeta);
		Iterator itMissao = organizacao.getFS().getAllMissions().iterator();
		while(itMissao.hasNext()) {
			Mission m = (Mission) itMissao.next();
			if(m.getGoals().contains(meta) && isMissaoFromPapel(m.getId(), nomePapel))
				return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Collection<Goal> getAllMetas(String esquema) {
		
		if(organizacao == null)
			organizacao = getOrganizacao();
		
		Collection<Goal> resultado = new ArrayList<Goal>();
		Iterator it = organizacao.getFS().getSchemes().iterator();
		
		while(it.hasNext()) {
			Scheme sch = (Scheme) it.next();
			if(sch.getId().equals(esquema)) {
				resultado = sch.getGoals();
				break;
			}
		}
		return resultado;
	}
	/**
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Collection<Goal> getMetasByMissao(Mission missao) {
		
		if(organizacao == null)
			organizacao = getOrganizacao();
		
		Collection<Goal> resultado = new ArrayList<Goal>();
		Iterator it = organizacao.getFS().getSchemes().iterator();
		while(it.hasNext()) {
			Scheme s = (Scheme) it.next();
			if(s.getMission(missao.getId()) != null) {
				resultado = s.getMission(missao.getId()).getGoals();
				break;
			}
		}
		return resultado;
	}
	
	/**
	 * 
	 * @return
	 */
	/*public static Collection<Goal> getAllMetasByPapel(String nomePapel) {
		
		if(organizacao == null)
			organizacao = getOrganizacao();
		
		Collection<Goal> resultado = new ArrayList<Goal>();
		for(Goal g : ((Scheme)organizacao.getFS().getSchemes().iterator().next()).getGoals()) {
			if(organizacao.getNS().get)
		}
		
		return resultado;
	}*/
	
	/**
	 * 
	 * @param papel
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Collection<Mission> getMissoesByPapel(Role papel) {
		
		if(organizacao == null)
			organizacao = getOrganizacao();
		
		Collection<Mission> resultado = new ArrayList<Mission>();
		Iterator it = organizacao.getNS().getNorms().iterator();
		while(it.hasNext()) {
			Norm n = (Norm) it.next();
			if(n.getRole().getId().equals(papel.getId()))
				resultado.add(n.getMission());
		}
		return resultado;
	}
	
	/**
	 * 
	 * @param papel
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Collection<Mission> getMissoesObrgatoriasByPapel(String papel) {
		
		if(organizacao == null)
			organizacao = getOrganizacao();
		
		Collection<Mission> resultado = new ArrayList<Mission>();
		Iterator it = organizacao.getNS().getNorms().iterator();
		while(it.hasNext()) {
			Norm n = (Norm) it.next();
			if(n.getRole().getId().equals(papel) && n.getType().name().equals("obligation"))
				resultado.add(n.getMission());
		}
		
		return resultado;
	}
	
	/**
	 * 
	 * @param papel
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Collection<Mission> getMissoesPermitidasByPapel(String papel) {
		
		if(organizacao == null)
			organizacao = getOrganizacao();
		
		Collection<Mission> resultado = new ArrayList<Mission>();
		Iterator it = organizacao.getNS().getNorms().iterator();
		while(it.hasNext()) {
			Norm n = (Norm) it.next();
			if(n.getRole().getId().equals(papel) && n.getType().name().equals("permission"))
				resultado.add(n.getMission());
		}
		return resultado;
	}
	
	/**
	 * 
	 * @param nomeMeta
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Mission getMissaoByMeta(String nomeMeta) {
		
		if(organizacao == null)
			organizacao = getOrganizacao();
		
		Iterator itMissao = organizacao.getFS().getAllMissions().iterator();
		while(itMissao.hasNext()) {
			Mission m = (Mission) itMissao.next();
			if(m.getGoals().contains(organizacao.getFS().findGoal(nomeMeta)))
				return m;
		}
		return null;
	}
}