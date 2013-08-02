package de.smartor.manager.accesscontrolmanager;

import java.util.HashMap;
import java.util.concurrent.*;

import org.ws4d.java.client.DefaultClient;
import org.ws4d.java.communication.CommunicationException;
import org.ws4d.java.security.CredentialInfo;
import org.ws4d.java.security.SecurityKey;
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
import org.ws4d.java.types.URI;

public class ServiceManagerClient extends DefaultClient
{	
	private String 			strDeviceReference;
	
	private DeviceReference deviceReference = null;
	private Service 		getDevicesService = null;
	
	private Operation 		getDevicesOperation = null;
	
	
	public ServiceManagerClient(String ServiceManagerDeviceReference)
	{
		strDeviceReference = ServiceManagerDeviceReference;		
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
                    		if (nextRef.getServiceId().toString().contains("GetDevicesService"))
                        	{
                    			getDevicesService = nextRef.getService();
                    			getDevicesOperation = getDevicesService.getOperation("http://www.smartOR.de/2012/ServiceManager/getDevicesRequest");
                        	}
                    	}
	        		}
					
					// Call getDevices from ServiceManager
					if( (getDevicesService != null) && (getDevicesOperation != null))
					{
						try
						{
							// Retrieve MedicalDeviceProfile
							ParameterValue inValIdent = getDevicesOperation.createInputValue();
							ParameterValue retValIdent = getDevicesOperation.invoke(inValIdent, CredentialInfo.EMPTY_CREDENTIAL_INFO);
							ListIterator lstRetValIdentDeviceInformation = retValIdent.getChildrenList();
							while(lstRetValIdentDeviceInformation.hasNext())
							{
								ParameterValue pv = (ParameterValue)lstRetValIdentDeviceInformation.next();
								if(pv.getName().getLocalPart().toString().equals("DeviceInformation"))
								{
									String friendlyName = ParameterValueManagement.getString(pv, "FriendlyName");
									String endpointReference = ParameterValueManagement.getString(pv, "EndpointReference");
									
									// Show information about the device
									System.out.println("  FriendlyName: " + friendlyName + " EndpointReference: " + endpointReference);
									System.out.println("  Registering as manager...");

									RegisterManagerClient client = new RegisterManagerClient(endpointReference);
									client.run();
								}
							}
							
							
						}
						catch(Exception ex)
						{
							System.err.println("Error registering RegisterManager on prefound Devices - Malformed service?");
						}
					}
					else
					{
						System.err.println("Error retrieving access - Device Master Service missing!");
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
}
