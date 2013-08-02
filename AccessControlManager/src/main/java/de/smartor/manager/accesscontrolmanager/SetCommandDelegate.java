/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.smartor.manager.accesscontrolmanager;

import org.ws4d.java.client.SearchManager;
import org.ws4d.java.communication.CommunicationException;
import org.ws4d.java.security.CredentialInfo;
import org.ws4d.java.security.SecurityKey;
import org.ws4d.java.service.*;
import org.ws4d.java.service.parameter.ParameterValue;
import org.ws4d.java.service.parameter.ParameterValueManagement;
import org.ws4d.java.service.reference.DeviceReference;
import org.ws4d.java.service.reference.ServiceReference;
import org.ws4d.java.structures.Iterator;
import org.ws4d.java.structures.List;
import org.ws4d.java.types.EndpointReference;
import org.ws4d.java.types.QName;
import org.ws4d.java.types.QNameSet;
import org.ws4d.java.types.URI;


/**
 *
 * @author findik
 */
public class SetCommandDelegate  implements InvokeDelegate
{

    @Override
    public ParameterValue invokeImpl(Operation operation, ParameterValue parameterValue, CredentialInfo credentialInfo) throws InvocationException
    {
        List commandList = parameterValue.getChildren("setCommand");        
        ParameterValue setCommandPV = (ParameterValue) commandList.get(0);
        
        
    	ParameterValue destinationPV = parameterValue.get("Destination");
		


		//TODO nachschauen wer der DeviceMaster der Destination ist
		
        // DeviceMaster hardcoded ChirugieWorkstation "b832b740-7033-11e1-b0c4-0800200c9b11"
    	DeviceMasterClient client = new DeviceMasterClient("b832b740-7033-11e1-b0c4-0800200c9b11", setCommandPV, destinationPV);
    	client.run();

		
		// Send response
		ParameterValue outVal = operation.createOutputValue();
		ParameterValueManagement.setString(outVal, "ResultState", "MDC_RESULT_ACCEPTED");
		return outVal;
    }
}
