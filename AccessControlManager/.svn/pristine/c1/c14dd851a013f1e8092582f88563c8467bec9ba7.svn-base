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
 

