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
