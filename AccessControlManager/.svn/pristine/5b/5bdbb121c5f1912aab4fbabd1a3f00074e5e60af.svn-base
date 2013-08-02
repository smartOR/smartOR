
package de.smartor.manager.accesscontrolmanager;

import org.ws4d.java.security.CredentialInfo;
import org.ws4d.java.service.InvocationException;
import org.ws4d.java.service.InvokeDelegate;
import org.ws4d.java.service.Operation;
import org.ws4d.java.service.parameter.ParameterValue;
import org.ws4d.java.service.parameter.ParameterValueManagement;
import org.ws4d.java.types.QName;

public class CheckHeartbeatDelegate implements InvokeDelegate
{
	
	// is called whenever the operation is invoked
	@Override
	public ParameterValue invokeImpl(Operation operation, ParameterValue parameterValue, CredentialInfo credentialInfo)
			throws InvocationException 
	{
    	System.out.println("HeartbeatRequest called");
    	
    	ParameterValue outVal = operation.createOutputValue();
    	ParameterValueManagement.setString(outVal, "ResultState", "MDC_RESULT_ACCEPTED");
        return outVal;
	}

}
