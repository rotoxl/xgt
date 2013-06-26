package sw;

import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axis2.client.async.AsyncResult;
import org.apache.axis2.client.async.Callback;

public class MyCallBack extends Callback {

	@Override
	public void onComplete(AsyncResult result) {
		@SuppressWarnings("unused")
		SOAPEnvelope envelope = result.getResponseEnvelope();
	}

	@Override
	public void onError(Exception arg0) {
		
	}

}
