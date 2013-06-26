import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyStoreException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.ws.security.WSPasswordCallback;

/** Clase necesaria para definir los parametros 'OutflowSecurity' de rampart. */
public class PWCBHandler implements CallbackHandler {
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
    	System.out.println(" **** PWCBHandler");
        for (int i = 0; i < callbacks.length; i++) {
            WSPasswordCallback pwcb = (WSPasswordCallback)callbacks[i];
            
            try {
            	setPrivateKey(pwcb);
            	System.out.println(" **** "+pwcb);
				}
            catch (KeyStoreException e) {
				throw new IOException(e);
				}
			catch (URISyntaxException e) {
				throw new IOException(e);
				}
        	}
    	}

    private void setPrivateKey(WSPasswordCallback p) throws IOException, URISyntaxException, KeyStoreException{
    	p.setPassword("changeit");
    }

}