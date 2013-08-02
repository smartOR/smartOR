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
import org.ws4d.java.service.parameter.ParameterValue;
import org.ws4d.java.service.parameter.ParameterValueManagement;
import org.ws4d.java.structures.Iterator;
import org.ws4d.java.structures.List;

/**
 *
 * @author findik
 */
public class GetParameterDelegate  implements InvokeDelegate
{

    @Override
    public ParameterValue invokeImpl(Operation operation, ParameterValue parameterValue, CredentialInfo credentialInfo) throws InvocationException
    {
    	
    	
		String cmdSource = ParameterValueManagement.getString( parameterValue, "CmdSource");
		String cmdDestination = ParameterValueManagement.getString( parameterValue, "CmdDestination");
		
		ParameterValue pvGetParameter = parameterValue.get("getParameter");
			
		String parameter = ParameterValueManagement.getString(pvGetParameter, "getParameter");

		System.out.println("cmdSource: " + cmdSource);
		System.out.println("cmdDestination: " + cmdDestination);
		System.out.println("getParameter(\"" + parameter + "\")");

	
		// Create the answer
		ParameterValue outVal = operation.createOutputValue();

		switch (parameter) {
		case "MDC_PARAM_STRING": {
			ParameterValue outValDeviceParameter = outVal.createChild("DeviceParameter");
			ParameterValue outValDeviceParameterStringParameter = outValDeviceParameter.createChild("StringParameter");

			ParameterValueManagement.setString(outValDeviceParameterStringParameter, "name", "MDC_PARAM_STRING");
			ParameterValueManagement.setString(outValDeviceParameterStringParameter, "value", "Toller String");
		}
			break;
		case "MDC_PARAM_BOOL": {
			ParameterValue outValDeviceParameter = outVal.createChild("DeviceParameter");
			ParameterValue outValDeviceParameterStringParameter = outValDeviceParameter.createChild("BooleanParameter");

			ParameterValueManagement.setString(outValDeviceParameterStringParameter, "name", "MDC_PARAM_BOOL");
			ParameterValueManagement.setString(outValDeviceParameterStringParameter, "value", "true");
		}
		case "MDC_PARAM_PHYSICAL": {
			ParameterValue outValDeviceParameter = outVal.createChild("DeviceParameter");
			ParameterValue outValDeviceParameterStringParameter = outValDeviceParameter.createChild("PhysicalParameter");
			ParameterValueManagement.setString(outValDeviceParameterStringParameter, "name", "MDC_PARAM_PHYSICAL");
			ParameterValueManagement.setString(outValDeviceParameterStringParameter, "value", "50");
			ParameterValueManagement.setString(outValDeviceParameterStringParameter, "targetValue", "70");
			ParameterValueManagement.setString(outValDeviceParameterStringParameter, "unit", "mmHg");
		}
			break;
		default:
			ParameterValueManagement.setString(outVal, "getParameterFault", "MDC_RESULT_UNKNOWN");

		}

		return outVal;

    }
    
}
