/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.smartor.manager.accesscontrolmanager;

import java.io.IOException;
import org.ws4d.java.security.CredentialInfo;
import org.ws4d.java.service.DefaultService;
import org.ws4d.java.service.OperationStub;
import org.ws4d.java.structures.Iterator;
import org.ws4d.java.types.URI;
import org.ws4d.java.wsdl.WSDL;

import de.smartor.manager.accesscontrolmanager.RegisterDeviceDelegate;

/**
 *
 * @author findik
 */
public class DeviceRegistrationService extends DefaultService
{
    public DeviceRegistrationService(URI wsdlFileURI) throws IOException
    {
        if(wsdlFileURI == null)
        {
            throw new IOException("WSDL file URI String empty!");
        }
        //Retrieve operations from WSDL file
        init(wsdlFileURI);
    }
    
    public void init(URI wsdlURI)
    {
        try
        {
            // Define yourself from the wsdl file
            this.define(wsdlURI,null);
        }
        catch (IOException e)
        {
            System.err.println(e.toString());
        }
        
        // Nicht benötigt nur zur Info alle Operation der WSDL auflisten
        Iterator it = this.getOperations();
        while(it.hasNext())
        {
            System.out.println("WSDL operation: " + ((OperationStub)it.next()).getInputAction());
        }

        // Get the operations and assign them to the right class
        
        OperationStub opRegisterDevice = (OperationStub) this.getOperation("http://www.smartOR.de/2012/ManagerDeviceRegistration/registerDeviceRequest");
        opRegisterDevice.setDelegate(new RegisterDeviceDelegate());
        
    }
}
