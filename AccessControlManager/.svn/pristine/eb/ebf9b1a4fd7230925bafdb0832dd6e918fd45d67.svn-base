
package de.smartor.manager.accesscontrolmanager;

import org.ws4d.java.security.CredentialInfo;
import org.ws4d.java.security.SecurityKey;
import org.ws4d.java.service.InvocationException;
import org.ws4d.java.service.InvokeDelegate;
import org.ws4d.java.service.Operation;
import org.ws4d.java.service.parameter.ParameterValue;
import org.ws4d.java.service.parameter.ParameterValueManagement;
import org.ws4d.java.service.reference.ServiceReference;
import org.ws4d.java.structures.Iterator;
import org.ws4d.java.types.EndpointReference;
import org.ws4d.java.types.QName;
import org.ws4d.java.types.URI;

public class RegisterManagerDelegate implements InvokeDelegate
{
	
	// is called whenever the operation is invoked
	@Override
	public ParameterValue invokeImpl(Operation operation, ParameterValue parameterValue, CredentialInfo credentialInfo)
			throws InvocationException 
	{
		String name = ParameterValueManagement.getString(parameterValue, "name");
		String endpointReference = ParameterValueManagement.getString(parameterValue, "EndpointReference");
    	System.out.println("* * * New manager registered: " + name);
    	System.out.println("Endpoint reference: " + endpointReference);
    	
    	ParameterValue outVal = operation.createOutputValue();
    	
    	if (name != null && endpointReference != null && !name.isEmpty() && !endpointReference.isEmpty()) {
        	ParameterValueManagement.setString(outVal, "RegisterManagerResult", "OSCB_REGISTERMANAGER_RESULT_SUCCEEDED");
    	} else {
        	ParameterValueManagement.setString(outVal, "RegisterManagerResult", "OSCB_REGISTERMANAGER_RESULT_FAILED");
    	}
        
    	
    	if ("MDC_DEV_SERVICEMANAGER".equals(name))
    	{
    		//@TODO call ServiceManager.getDevices()
    		//      for calling RegisterManager(AccessControlManager) on all devices


//			// First retrieve information about the device
//			if(strDeviceReference != null)
//    		{
//    			// Get device and service references
//            	deviceReference = getDeviceReference(new EndpointReference(new URI(strDeviceReference)));
//            	Iterator serviceReferences = deviceReference.getDevice().getServiceReferences(SecurityKey.EMPTY_KEY);
//                // Get ID service
//                while(serviceReferences.hasNext())
//            	{
//            		ServiceReference nextRef = (ServiceReference)serviceReferences.next();
//            		if (nextRef.getServiceId().toString().contains("DeviceMasterService"))
//                	{
//            			deviceMasterService = nextRef.getService();
//            			requestAccessOperation = deviceMasterService.getOperation("http://www.smartOR.de/2012/AccessControlManager/requestAccessRequest");
//                	}
//            	}
//    		}
    		
    	}
    	
    	return outVal;
	}
	
}
