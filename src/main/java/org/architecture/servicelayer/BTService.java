package org.architecture.servicelayer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.architecture.interfacelayer.InterfaceServer;
import org.architecture.servicelayer.CommandService;

/**
 * @author MarcioFraga
 * Classe respons�vel pelo carregamento do arquivo javascript que contem as Behaviour Tree, 
 * bem como pela execu��o  dos m�todos existentes no mesmo.
 */
public class BTService {

	//private static String CAMINHOPASTA = "./script/";
	private File arquivoJs;
	private ScriptEngineManager factory;
	private ScriptEngine engineJs;
	private Reader readerArquivoJs;
	
	/**
	 * Construtor da classe
	 * @param nomeArquivo
	 */
	public BTService(String nomeArquivo) { 
		carregaJavaScript(nomeArquivo);
	}
	
	/**
	 * M�todo que carrega o arquivo javascript contendo as Behaviour Tree que ser�o utilizadas pelo framework. 
	 * � utilizado a engine nashorn para tal fun��o
	 * @param nomeArquivo
	 */
	public void carregaJavaScript(String nomeArquivo) {
		try {
			InterfaceServer server = (InterfaceServer) CommandService.getAllInterfaceServerImpl();
			
			arquivoJs = new File(server.getPathBT() + nomeArquivo + ".js");
			readerArquivoJs = new FileReader(arquivoJs);
			
			factory = new ScriptEngineManager();
			engineJs = factory.getEngineByName("Nashorn");
			engineJs.eval(readerArquivoJs);
		} catch (FileNotFoundException | ScriptException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Chama a Behaviour Tree existente no arquivo javascript carregado no metodo carregaJavaScript. 
	 * O nome do metodo � o mesmo nome da meta ou miss�o existente na especifica��o organizacional
	 * 
	 * @param nomeBT - Nome da fun��o javascript a ser executada
	 * @param parametros - parametros que devem ser passados para a execu��o do metodo javascript
	 */
	public Object executaBT(String nomeBT, Object... parametros) throws NoSuchMethodException, ScriptException {

		Invocable invocable = (Invocable) engineJs;	
		Object resultado;
		
		if(parametros == null)
			resultado = invocable.invokeFunction(nomeBT);
		else
			resultado = invocable.invokeFunction(nomeBT, parametros);
		
        return resultado;
	}
}
