package application;

import java.io.File;
import org.apache.commons.io.FileUtils;
import logic.TranslatorLogic;

public class Main {

	public static void main(String[] args) {
		TranslatorLogic translator = new TranslatorLogic();
		
		if(args.length == 0) {
			FFKtranslatorStart.main(args);
		}else {
			String fileFXML = args[0];
			String path = args[1];
			
			try {
				File fileKV = translator.translateToKV(new File(fileFXML));
				
				if(path != null)
					FileUtils.copyFile(fileKV, new File(path + fileKV.getName() + "_KV.kv"));
				else
					FileUtils.copyFile(fileKV, new File(fileKV.getName() + "_KV.kv"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
	}

}