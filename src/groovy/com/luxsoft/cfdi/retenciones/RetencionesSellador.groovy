package com.luxsoft.cfdi.retenciones

import java.security.Signature

import org.bouncycastle.util.encoders.Base64
import org.apache.commons.lang.exception.ExceptionUtils
import org.apache.commons.logging.LogFactory

class RetencionesSellador {
	
	String algoritmo="SHA1withRSA"
	
	private static final log=LogFactory.getLog(this)
	
	String sellar(def privateKey,String cadenaOriginal){
		try {
			
			final byte[] input=cadenaOriginal.getBytes("UTF-8")
			Signature signature=Signature.getInstance(algoritmo,"BC");
			signature.initSign(privateKey)
			signature.update(input)
			
			final byte[] signedData=signature.sign()
			final byte[] encoedeData=Base64.encode(signedData)
			return new String(encoedeData,"UTF-8")
		} catch (Exception e) {
			e.printStackTrace()
			String msg="Error generando sello digital: "+ExceptionUtils.getRootCauseMessage(e);
			log.error(msg,e);
			throw new RuntimeException(msg,e)
		}
	}

}
