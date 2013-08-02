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

