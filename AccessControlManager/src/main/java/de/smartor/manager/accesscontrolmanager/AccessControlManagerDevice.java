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

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

import org.ws4d.java.communication.DPWSCommunicationManager;
import org.ws4d.java.communication.protocol.http.HTTPBinding;
import org.ws4d.java.service.DefaultDevice;
import org.ws4d.java.structures.Iterator;
import org.ws4d.java.types.EndpointReference;
import org.ws4d.java.types.URI;

import org.ws4d.java.communication.connection.ip.IPAddress;
import org.ws4d.java.communication.connection.ip.IPNetworkDetection;


/**
*
* @author findik
*/
public class AccessControlManagerDevice extends DefaultDevice
{
	private static IPAddress IP;
	
	/**
	 * constructor if no UUID was selected
	 */
	public AccessControlManagerDevice()
        {
		this(-1, new EndpointReference(new URI(UUID.randomUUID().toString())));
		init();
	}

	/**
	 * constructor with a selected uuid (String)
	 * @param configurationId
	 * @param endpoint
	 */
	public AccessControlManagerDevice(int configurationId, String endpoint)
	{
		super(configurationId);
		setEndpointReference(new EndpointReference(new URI(endpoint)));
		init();
	}
	
	/**
	 * constructor with a selected uuid (EndpointReference)
	 * @param configurationId
	 * @param endpoint
	*/
	public AccessControlManagerDevice(int configurationId, EndpointReference endpointReference) {
		super(configurationId);
		setEndpointReference(endpointReference);
		init();
	}
        
        /**
	 * parameter which should not be change at any time. 
	 * 
	 */
	void init()
	{
		//get the local ip address 
		try
		{
			String addr = InetAddress.getLocalHost().getHostAddress();
		}
		catch (UnknownHostException e)
		{
			e.printStackTrace();
		} 
		
		addFriendlyName("de-DE", "SmartOR Access Control Manager");
		addManufacturer("de-DE", "Richard Wolf GmbH");	
		addModelName("de-DE", "smartOR Manager");
		setFirmwareVersion("1.0");
		setManufacturerUrl("www.richard-wolf.de");
		setModelNumber("1.0");
		setSerialNumber("987654321");
		setModelUrl("http://www.smartor.de/");
		setPresentationUrl("http://www.smartor.de/");
		
		//IPAddress adr = IPNetworkDetection.getInstance().getIPAddressOfAnyLocalInterface("127.0.0.1", false);
		Iterator itAddr = IPNetworkDetection.getInstance().getAddresses(null, null);
		while(itAddr.hasNext())
		{
			IPAddress adr = (IPAddress)itAddr.next();
			System.out.println("Add Binding for address: " + adr.toString());
			this.addBinding(new HTTPBinding(adr, 8081, "ManagerDevice", DPWSCommunicationManager.COMMUNICATION_MANAGER_ID));
		}
		
		
		//this.addBinding(new HTTPBinding(IPNetworkDetection.getInstance().getIPAddressOfAnyLocalInterface("127.0.0.1", false), 8081, "ManagerDevice", DPWSCommunicationManager.COMMUNICATION_MANAGER_ID));
		
		try
        {
			String wsdlPath = "local:interfaces/wsdl/";
			
			// File exists does not work, so this is the other way
			System.out.println(AccessControlManagerDevice.class.getResource("AccessControlManagerDevice.class"));
			String s = AccessControlManagerDevice.class.getResource("AccessControlManagerDevice.class").toString();
			if(s.contains("jar:"))
			{
				wsdlPath = "local:../../../../wsdl/";
			}
			
        	// Add services, use relative path to common wsdl location
          	AccessControlService ns = new AccessControlService(new URI(wsdlPath + "AccessControlManagerDeviceControlService.wsdl"));
          	addService(ns);
          	addService(new DeviceRegistrationService(new URI(wsdlPath + "ManagerDeviceRegistrationService.wsdl")));

          	addService(new IdentificationService(new URI(wsdlPath + "MedicalDeviceIdentificationService.wsdl")));

            
            // Start the device
            start();
        }
        catch(java.io.IOException ex)
        {
            
        }
	}
}
