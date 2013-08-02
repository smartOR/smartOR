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

import org.ws4d.java.service.DefaultService;
import org.ws4d.java.service.OperationStub;
import org.ws4d.java.structures.Iterator;
import org.ws4d.java.types.URI;

public class IdentificationService extends DefaultService
{
    public IdentificationService(URI wsdlFileURI) throws IOException
    {
            if(wsdlFileURI == null)
            {
                throw new IOException("WSDL file URI String empty!");
            }
            
            // Define yourself form the wsdl file
            this.define(wsdlFileURI,null);
            
            // Not needed, only for listing operation from the wsdl file
            Iterator it = this.getOperations();
            while(it.hasNext())
            {
                System.out.println("WSDL operation: " + ((OperationStub)it.next()).getInputAction());
            }
            
            this.setServiceId(new URI("IdentificationService"));
            
            // Define operations
            OperationStub opGetIdentification = (OperationStub) this.getOperation("http://www.smartOR.de/2012/IdentificationService/getIdentificationRequest");
            opGetIdentification.setDelegate(new GetIdentificationDelegate());

            // ???? not applicable for AccessControlManager
            OperationStub opRegisterManager = (OperationStub) this.getOperation("http://www.smartOR.de/2012/IdentificationService/registerManagerRequest");
            opRegisterManager.setDelegate(new RegisterManagerDelegate());
            
            
            OperationStub opCheckHeartbeat = (OperationStub) this.getOperation("http://www.smartOR.de/2012/IdentificationService/checkHeartbeatRequest");
            opCheckHeartbeat.setDelegate(new CheckHeartbeatDelegate());
    }
}
 

