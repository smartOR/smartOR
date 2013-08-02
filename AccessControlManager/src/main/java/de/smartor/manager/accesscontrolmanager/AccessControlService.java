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

import java.io.IOException;

import org.ws4d.java.security.CredentialInfo;
import org.ws4d.java.service.DefaultEventSource;
import org.ws4d.java.service.DefaultService;
import org.ws4d.java.service.EventDelegate;
import org.ws4d.java.service.EventSourceStub;
import org.ws4d.java.service.ServiceSubscription;
import org.ws4d.java.service.OperationStub;
import org.ws4d.java.types.URI;
import org.ws4d.java.service.parameter.ParameterValue;
import org.ws4d.java.service.parameter.ParameterValueManagement;
import org.ws4d.java.structures.Iterator;
import org.ws4d.java.types.URI;


/**
 *
 * @author findik
 */
public class AccessControlService extends DefaultService
{
		public AccessControlService(URI wsdlFileURI) throws IOException
		{
	        if(wsdlFileURI == null)
	        {
	            throw new IOException("WSDL file URI String empty!");
	        }
	        
	        // Define yourself form the wsdl file
	        this.define(wsdlFileURI,null);
	        
	        // Define operation setCommand
	        OperationStub opSetCommand = (OperationStub) this.getOperation("http://www.smartOR.de/2012/AccessControlService/setCommandRequest");
	        opSetCommand.setDelegate(new SetCommandDelegate());

	        // Define operation setParameter
	        OperationStub opSetParameter = (OperationStub) this.getOperation("http://www.smartOR.de/2012/AccessControlService/setParameterRequest");
	        opSetParameter.setDelegate(new SetParameterDelegate());
	        
	        /* getParameter not over AccessControlManager
	        // Define operation getParameter
	        OperationStub opGetParameter = (OperationStub) this.getOperation("http://www.smartOR.de/2012/AccessControlService/getParameterRequest");
	        opGetParameter.setDelegate(new GetParameterDelegate());
	        */
		}
	}
