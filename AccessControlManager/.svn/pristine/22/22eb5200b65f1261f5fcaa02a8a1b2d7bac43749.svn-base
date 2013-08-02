package de.smartor.manager.accesscontrolmanager;

import java.util.HashMap;
import java.util.concurrent.*;

import org.ws4d.java.client.DefaultClient;
import org.ws4d.java.client.SearchManager;
import org.ws4d.java.communication.CommunicationException;
import org.ws4d.java.security.CredentialInfo;
import org.ws4d.java.security.SecurityKey;
import org.ws4d.java.service.Device;
import org.ws4d.java.service.InvocationException;
import org.ws4d.java.service.Operation;
import org.ws4d.java.service.Service;
import org.ws4d.java.service.parameter.ParameterValue;
import org.ws4d.java.service.parameter.ParameterValueManagement;
import org.ws4d.java.service.reference.DeviceReference;
import org.ws4d.java.service.reference.ServiceReference;
import org.ws4d.java.structures.ArrayList;
import org.ws4d.java.structures.Iterator;
import org.ws4d.java.structures.List;
import org.ws4d.java.structures.ListIterator;
import org.ws4d.java.types.EndpointReference;
import org.ws4d.java.types.QName;
import org.ws4d.java.types.QNameSet;
import org.ws4d.java.types.URI;

public class DeviceMasterClient extends DefaultClient
{	
	private String 			strDeviceReference;
	
	private DeviceReference deviceReference = null;
	private Service 		deviceMasterService = null;
	
	private Operation 		requestAccessOperation = null;
	
	private String 					dstEndpointReference;
	private String friendlyNameSource, friendlyNameDestination;
	
	private String referenceIdStr, commandIdStr;	
	
    public static final String TARGET_NAMESPACE = "http://www.smartOR.de/2012/MedicalDeviceService";

    public static final String DEVICE_CONTROL_PORT_TYPE = "DeviceControlPortType";

    private QName deviceControlService = new QName(DEVICE_CONTROL_PORT_TYPE, TARGET_NAMESPACE);

	
	public DeviceMasterClient(String deviceMasterReference, ParameterValue setCommandPV, ParameterValue destinationPV)	
	{
		strDeviceReference = deviceMasterReference;
        
		ParseSetCommand(setCommandPV);
		
		ParseDestination(destinationPV);
		
		
//		this.cmdSource = cmdSource;
//		this.cmdDestination = cmdDestination;
//		this.dstEndpointReference = dstEndpointReference;
	}
	
	
	private void ParseDestination(ParameterValue destinationPV) {
		System.out.println("Destination: " + destinationPV);
		
		Iterator destinationIterator = destinationPV.children();
		String endpointReference = destinationIterator.next().toString();
		
		//TODO friendlyNameFrom Destination
		friendlyNameDestination = "OP-Tisch";
		
		
		dstEndpointReference = endpointReference;
		
	}


	private void ParseSetCommand(ParameterValue setCommandPV)
	{
        Iterator commandIterator = setCommandPV.children();
        ParameterValue referenceId = (ParameterValue)commandIterator.next();
        ParameterValue commandId = (ParameterValue)commandIterator.next();
        
        referenceIdStr = referenceId.toString();
        commandIdStr = commandId.toString();

		//TODO friendlyNameFrom Source
		friendlyNameSource = "AnästhesieWorkstation";
        
        System.out.println("referenceId: " + referenceIdStr);
        System.out.println("commandId: " + commandIdStr);		
	}

	public void run()
	{
		Thread r = new Thread(new Runnable()
		{
			public void run() 
			{
				try
				{
					// First retrieve information about the device
					if(strDeviceReference != null)
	        		{
	        			// Get device and service references
	                	deviceReference = getDeviceReference(new EndpointReference(new URI(strDeviceReference)));
	                	Iterator serviceReferences = deviceReference.getDevice().getServiceReferences(SecurityKey.EMPTY_KEY);
	                    // Get ID service
	                    while(serviceReferences.hasNext())
                    	{
                    		ServiceReference nextRef = (ServiceReference)serviceReferences.next();
                    		if (nextRef.getServiceId().toString().contains("DeviceMasterService"))
                        	{
                    			deviceMasterService = nextRef.getService();
                    			requestAccessOperation = deviceMasterService.getOperation("http://www.smartOR.de/2012/AccessControlManager/requestAccessRequest");
                        	}
                    	}
	        		}
					
					// Call requestAccess on DeviceMaster
					if( (deviceMasterService != null) && (requestAccessOperation != null))
					{
						try
						{
							// Retrieve MedicalDeviceProfile
							ParameterValue inValRequest = requestAccessOperation.createInputValue();
							
							ParameterValueManagement.setString(inValRequest, "CmdSource", friendlyNameSource);
							ParameterValueManagement.setString(inValRequest, "CmdDestination", friendlyNameDestination);
							ParameterValue retValRequest = requestAccessOperation.invoke(inValRequest, CredentialInfo.EMPTY_CREDENTIAL_INFO);
							String timeoutStr = ParameterValueManagement.getString(retValRequest, "grantAccess");
							
							int timeout = Integer.parseInt(timeoutStr);
							
							if (timeout > 0)
								CallSetCommandOnDestination();
							
							// Show information about the device
							System.out.println("  cmdSource: " + friendlyNameSource + " cmdDestination: " + friendlyNameDestination);
							System.out.println("  Access granted for " + timeout + " minutes...");
							
						}
						catch(Exception ex)
						{
							System.err.println("Error retrieving MedicalDeviceProfile - Malformed service?");
						}
					}
					else
					{
						System.err.println("Error retrieving MedicalDeviceProfile - Services missing!");
					}

				}
				catch(Exception ex)
				{
					System.err.println(ex);
				}
			}
		});
		r.start();
    }
	
	private void CallSetCommandOnDestination()
	{
		
		//TODO wieder entfernen
		// endpointReference = "26aae5b6-b8bb-4611-b39e-ed972f8a82b9";
		
		
	    EndpointReference cmdDestinationEndpointReference = new EndpointReference(new URI(dstEndpointReference));
	    DeviceReference destinationDeviceReference = SearchManager.getDeviceReference(cmdDestinationEndpointReference, SecurityKey.EMPTY_KEY, null, null);
	    try {
	        Device device =  destinationDeviceReference.getDevice();
	
	        //This should also work, but then everyone has to set the serviceID correctly. That is done manually at the moment.
	        //Maybe there is a way to set it in the wsdl-file?
	        //device.getServiceReference(new URI("MedicalDeviceDeviceControlService"), SecurityKey.EMPTY_KEY);
	
	        //Give me all device control services
	        for (Iterator serviceReferences = device.getServiceReferences(new QNameSet(deviceControlService), SecurityKey.EMPTY_KEY); serviceReferences.hasNext(); ) {
	            ServiceReference ref = (ServiceReference) serviceReferences.next();
	            Operation op = ref.getService().getAnyOperation(deviceControlService, "setCommand");
	
	            if (op != null) {
	                ParameterValue requestOfOp = op.createInputValue();
	
	                //This is not working. Why??
	                
	//                ParameterValueManagement.setString(requestOfOp, "referenceId", ParameterValueManagement.getString(commandToDelegate, "referenceId"));
	//                ParameterValueManagement.setString(requestOfOp, "commandId", ParameterValueManagement.getString(commandToDelegate, "commandId"));
	
	                //TODO: This is working but isn't nice and should be changed if we know how to make it better.
	//        		ParameterValue setCommand = parameterValue.get("setCommand");
	        		
	                
	                ParameterValueManagement.setString(requestOfOp, "referenceId", referenceIdStr);
	                ParameterValueManagement.setString(requestOfOp, "commandId", commandIdStr);
	
	     
	                
	                
	                ParameterValue sourcePV = requestOfOp.createChild("Source");
	                
	                AccessControlManager.GenerateDeviceInformation(sourcePV);
	                
	                // invoke operation with prepared input
	                ParameterValue result = op.invoke(requestOfOp, CredentialInfo.EMPTY_CREDENTIAL_INFO);
	
	                // get string value from answer
	                String reply = ParameterValueManagement.getString(result, "reply");
	                System.out.println(reply);
	            }
	        }
	    } catch (CommunicationException e) {
	        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
	    }  catch (InvocationException e) {
	    	e.printStackTrace();
		}
	}

}
