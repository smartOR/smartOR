/********************************************************************************
 * Copyright (c) 2013 Muhammed-Ali Findik					*
 * "Richard Wolf Gmbh" spirit of excellence [http://www.richard-wolf.com]	*
 *										*
 * This file is part of smartOR AccessControlManager.				*
 * 										*
 * smartOR AccessControlManager is free software: you can redistribute 		*
 * it and/or modify it under the terms of the GNU General Public License 	*
 * as published by the Free Software Foundation, either version 3 of the	*
 * License, or (at your option) any later version.				*
 * 										*
 * This program is distributed in the hope that it will be useful,		*
 * but WITHOUT ANY WARRANTY; without even the implied warranty of		*
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the		*
 * GNU General Public License for more details.					*
 * 										*
 * You should have received a copy of the GNU General Public License		*
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.	*
 ********************************************************************************/

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

public class RegisterManagerClient extends DefaultClient
{	
	private String 			strDeviceReference;
	
	private DeviceReference deviceReference = null;
	private Service 		identService = null;
	
	private Operation 		identOperation = null;
	private Operation		registerManagerOperation = null;
	
	public String					FriendlyName;
	public String					EndpointReference;
	private java.util.List<String>	xAddresses;
	
	private boolean doRun = true;
	
	public RegisterManagerClient(String DeviceReference)
	{
		strDeviceReference = DeviceReference;
        
		
		xAddresses = new java.util.ArrayList();
		
	}
	
	public boolean isRunning()
	{
		return doRun;
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
                    		if (nextRef.getServiceId().toString().contains("IdentificationService"))
                        	{
                    			identService = nextRef.getService();
                    			identOperation = identService.getOperation("http://www.smartOR.de/2012/IdentificationService/getIdentificationRequest");
                    			registerManagerOperation = identService.getOperation("http://www.smartOR.de/2012/IdentificationService/registerManagerRequest");
                        	}
                    	}
	        		}
					
					// Register as manager
					if( (identService != null) && (identOperation != null) && (registerManagerOperation != null))
					{
						try
						{
							// Retrieve MedicalDeviceProfile
							ParameterValue inValIdent = identOperation.createInputValue();
							ParameterValue retValIdent = identOperation.invoke(inValIdent, CredentialInfo.EMPTY_CREDENTIAL_INFO);
							ListIterator lstRetValIdentDeviceInformation = retValIdent.getChildrenList();
							while(lstRetValIdentDeviceInformation.hasNext())
							{
								ParameterValue pv = (ParameterValue)lstRetValIdentDeviceInformation.next();
								if(pv.getName().getLocalPart().toString().equals("DeviceInformation"))
								{
									FriendlyName = ParameterValueManagement.getString(pv, "FriendlyName");
									EndpointReference = ParameterValueManagement.getString(pv, "EndpointReference");
									List lstXAddress = pv.getChildren("XAddress");
									for(Integer i = 0; i < lstXAddress.size(); i++)
									{
										String xAddress = ParameterValueManagement.getString(pv, "XAddress[" + i.toString() + "]");
										if(xAddress != null)
										{
											xAddresses.add(xAddress);
										}
									}
									break;
								}
							}
							
							// Show information about the device
							System.out.println("  FriendlyName: " + FriendlyName + " EndpointReference: " + EndpointReference);
							System.out.println("  Registering as manager...");
							
							// Register as event manager
							ParameterValue inVal = registerManagerOperation.createInputValue();
							ParameterValueManagement.setString(inVal,"name", "MDC_DEV_ACCESSCONTROLMANAGER");
							ParameterValueManagement.setString(inVal,"EndpointReference", AccessControlManager.sUUID);
							//ParameterValueManagement.setString(inVal,"xAdress", "");
	  						ParameterValue retval = registerManagerOperation.invoke(inVal, CredentialInfo.EMPTY_CREDENTIAL_INFO);
	  						if(ParameterValueManagement.getString(retval, "RegisterDeviceResult") == "OSCB_REGISTERMANAGER_RESULT_SUCCEEDED")
	  						{
	  							System.out.println("  Registering as manager successful!");
	  						}
						}
						catch(Exception ex)
						{
							System.err.println("Error retrieving MedicalDeviceProfile - Malformed service?");
							doRun = false;
						}
					}
					else
					{
						System.err.println("Error retrieving access - Device Master Service missing!");
						doRun = false;
					}

				}
				catch(Exception ex)
				{
					System.err.println(ex);
					doRun = false;
				}
			}
		});
		r.start();
    }
}
