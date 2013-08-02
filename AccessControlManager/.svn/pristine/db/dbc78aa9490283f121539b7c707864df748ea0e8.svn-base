/*
 *  (c) 2001 - 2010 LOCALITE GmbH
 *  Sankt Augustin, Germany.
 *  All rights reserved.
 */

package de.smartor.manager.accesscontrolmanager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.ws4d.java.JMEDSFramework;
import org.ws4d.java.client.DefaultClient;
import org.ws4d.java.client.SearchCallback;
import org.ws4d.java.client.SearchManager;
import org.ws4d.java.communication.CommunicationException;
import org.ws4d.java.service.Device;
import org.ws4d.java.service.reference.DeviceReference;
import org.ws4d.java.service.reference.ServiceReference;
import org.ws4d.java.types.HelloData;
import org.ws4d.java.types.QName;
import org.ws4d.java.types.QNameSet;
import org.ws4d.java.types.SearchParameter;

import java.util.concurrent.TimeoutException;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyVararg;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

/**
 * @author Sven Arnold
 */

public class AccessControlManagerTest {

    private final static org.apache.commons.logging.Log log = LogFactory.getLog(AccessControlManagerTest.class);
    private final static int TIMEOUT_MILLIS = 10000;

    private AccessControlManager accessControlManager;

    @Before
    public void setup() {

        System.out.println("START");
        accessControlManager = new AccessControlManager();

    }

    @Test
    public void testManagerIsRunning() {

        SearchParameter searchParameter = new SearchParameter();
        searchParameter.setDeviceTypes(new QNameSet(new QName("ManagerDevice", "http://www.smartOR.de/2012/AccessControlManager")));

        SearchCallback mockedSearchCallback = Mockito.mock(SearchCallback.class);
        SearchManager.searchDevice(searchParameter, mockedSearchCallback, null);
        verify(mockedSearchCallback, timeout(10000)).deviceFound((DeviceReference)anyObject(), eq(searchParameter));
        //verify(mockedSearchCallback).serviceFound((ServiceReference)anyObject(), (SearchParameter)anyObject());
    }


}

class ACMClient extends DefaultClient {

    private final static Log log = LogFactory.getLog(ACMClient.class);

    @Override
    public void helloReceived(HelloData helloData) {

        try {
            // get device
            Device device = getDeviceReference(helloData).getDevice();
            System.out.println("Hello received from: ");

            /* get metadata */

            // EndpointReference(String)
            System.out.println("EndpointReference\t" + (device.getEndpointReference() != null ? device.getEndpointReference().getAddress().toString() : null));

        } catch (CommunicationException e) {
            log.error(e);
        }
    }

    @Override
    public void deviceFound(DeviceReference deviceReference, SearchParameter searchParameter) {
        try {
            System.out.println("Found Device: " + deviceReference.getDevice());
        } catch (CommunicationException e) {
            log.error("Change Me!", e);
        }
    }

    @Override
    public void serviceFound(ServiceReference serviceReference, SearchParameter searchParameter) {
        try {
            System.out.println("Found Service: " + serviceReference.getService());
        } catch (CommunicationException e) {
            log.error("Change Me!", e);
        }
    }
}