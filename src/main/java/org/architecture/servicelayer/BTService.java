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
 * Classe responsável pelo carregamento do arquivo javascript que contem as Behaviour Tree, 
 * bem como pela execução  dos métodos existentes no mesmo.
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
	 * Método que carrega o arquivo javascript contendo as Behaviour Tree que serão utilizadas pelo framework. 
	 * É utilizado a engine nashorn para tal função
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
	 * O nome do metodo é o mesmo nome da meta ou missão existente na especificação organizacional
	 * 
	 * @param nomeBT - Nome da função javascript a ser executada
	 * @param parametros - parametros que devem ser passados para a execução do metodo javascript
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
