package de.smartor.manager.accesscontrolmanager;

import org.ws4d.java.security.CredentialInfo;
import org.ws4d.java.service.InvocationException;
import org.ws4d.java.service.InvokeDelegate;
import org.ws4d.java.service.Operation;
import org.ws4d.java.service.Service;
import org.ws4d.java.service.parameter.ParameterValue;
import org.ws4d.java.service.parameter.ParameterValueManagement;
import org.ws4d.java.structures.Iterator;
import org.ws4d.java.structures.List;
import org.ws4d.java.structures.ListIterator;


public class RegisterDeviceDelegate implements InvokeDelegate
{
		
	@Override
    public ParameterValue invokeImpl(Operation operation, ParameterValue parameterValue, CredentialInfo credentialInfo) throws InvocationException
    {
    	String serviceRef = ParameterValueManagement.getString(parameterValue, "IdentificationDeviceRef");
    	
    	System.err.println(serviceRef.toString());
    	System.out.println("* * * Register device invoked - Retrieving MedicalDeviceProfile from new device... * * *");
    	
    	// register as Manager on Device
    	RegisterManagerClient client = new RegisterManagerClient(serviceRef);
    	client.run();
    	
    	
    	ParameterValue outVal = operation.createOutputValue();
    	ParameterValueManagement.setString(outVal, "RegisterDeviceResult", "OSCB_REGISTERDEVICE_RESULT_SUCCEEDED");
        return outVal;
    }
	
}

