/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.smartor.manager.accesscontrolmanager;

import org.ws4d.java.security.CredentialInfo;
import org.ws4d.java.service.InvocationException;
import org.ws4d.java.service.InvokeDelegate;
import org.ws4d.java.service.Operation;
import org.ws4d.java.service.parameter.ParameterValue;
import org.ws4d.java.service.parameter.ParameterValueManagement;
import org.ws4d.java.structures.Iterator;
import org.ws4d.java.structures.List;
import org.ws4d.java.structures.ListIterator;

/**
 *
 * @author findik
 */
public class SetParameterDelegate  implements InvokeDelegate
{

	// is called whenever the operation is invoked
	@Override
	public ParameterValue invokeImpl(Operation operation, ParameterValue parameterValue, CredentialInfo credentialInfo)
			throws InvocationException {
		
		String cmdSource = ParameterValueManagement.getString( parameterValue, "CmdSource");
		String cmdDestination = ParameterValueManagement.getString( parameterValue, "CmdDestination");
		
		System.out.println("cmdSource: " + cmdSource);
		System.out.println("cmdDestination: " + cmdDestination);

		ParameterValue pvSetParameter = parameterValue.get("setParameter");
	
		ParameterValue pvDeviceParameter = pvSetParameter.get("DeviceParameter");
		

		// Nicht ben�tigt nur zur Info: Auflistung der Elemente von Choice
		Iterator itDeviceParameterChoiceElements = pvDeviceParameter.childrenFromType();
		while (itDeviceParameterChoiceElements.hasNext()) {
			System.out.println("itDeviceParameterChildren: " + itDeviceParameterChoiceElements.next().toString());
		}

		// Zugriff auf die Elemente un den Typ des choice
		Iterator itDeviceParameterChildren = pvDeviceParameter.children();
		while (itDeviceParameterChildren.hasNext()) {
			ParameterValue pvDeviceParameterChild = (ParameterValue) itDeviceParameterChildren.next();
			// Get type of the choice
			String strTypeOfChoice = pvDeviceParameterChild.getName().getLocalPart().toString();
			System.out.println("TypeOfChoice: " + strTypeOfChoice);
			String name = ParameterValueManagement.getString(pvDeviceParameterChild, "name");
			String value = ParameterValueManagement.getString(pvDeviceParameterChild, "value");
			System.out.println("DataOfChoice: Name: " + name + " Value: " + value);
		}
		// Was zurckgegeben wird muss noch festgelegt werden
		System.out.println("Sending out: " + pvDeviceParameter.toString());

		// Send response
		ParameterValue outVal = operation.createOutputValue();
		ParameterValueManagement.setString(outVal, "ResultState", "MDC_RESULT_ACCEPTED");
		return outVal;

//		ParameterValue outVal = operation.createOutputValue();
//		ParameterValueManagement.setString(outVal, "ResultState", "MDC_RESULT_UNKNOWN");
//		return outVal;
	}
    
}
