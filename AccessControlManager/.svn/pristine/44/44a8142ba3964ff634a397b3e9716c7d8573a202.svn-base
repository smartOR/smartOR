package de.smartor.manager.accesscontrolmanager;

import org.ws4d.java.JMEDSFramework;
import org.ws4d.java.service.Device;
import org.ws4d.java.service.parameter.ParameterValue;
import org.ws4d.java.service.parameter.ParameterValueManagement;


/**
*
* @author findik
*/
public class AccessControlManager
{
    //a hardcoded UUID is better for test purposes. especially when assigning certain right to the device.
    public static String sUUID = "7405b90e-0e09-4cc3-be30-0a8deb0ec747";

	public static String sDeviceTypeIDAttribute = "MDC_DEV_ACCESSCONTROLMANAGER";
	public static String sDeviceSubTypeIDAttribute = "MDC_DEV_ACCESSCONTROLMANAGER";
	public static String sUsesIEEECodesAttribute = "true";
	// any other attribute is also aloud and should be added ...

	// Elements:
	public static String sEPR = sUUID;
	public static String sFriendlyName = "OSCB AccessControlManager";
	public static String sManufacturer = "Richard Wolf GmbH";
	public static String sSerialNumber = "12345";
	public static String sFirmwareVersion = "0.1";
	public static String sHardwareVersion = "0.1";
	public static String sSoftwareVersion = "0.1";
	
	static ParameterValue GenerateDeviceInformation(ParameterValue outVal)
	{
		// Attributes
		outVal.setAttributeValue("DeviceTypeID", sDeviceTypeIDAttribute);
		outVal.setAttributeValue("DeviceSubTypeID", sDeviceSubTypeIDAttribute);
		outVal.setAttributeValue("UsesIEEECode", sUsesIEEECodesAttribute);
		// Elements
		ParameterValueManagement.setString(outVal, "EndpointReference", sEPR);
		ParameterValueManagement.setString(outVal, "FriendlyName", sFriendlyName);
		ParameterValueManagement.setString(outVal, "Manufacturer", sManufacturer);
		ParameterValueManagement.setString(outVal, "SerialNumber", sSerialNumber);
		ParameterValueManagement.setString(outVal, "FirmwareVersion", sFirmwareVersion);
		ParameterValueManagement.setString(outVal, "HardwareVersion", sHardwareVersion);
		ParameterValueManagement.setString(outVal, "SoftwareVersion", sSoftwareVersion);
		
		return outVal;
	}
    
    
    private static Device device;

    public AccessControlManager() {

        // Start framework first
        if(!JMEDSFramework.isRunning())
        {
            JMEDSFramework.start(null);
        }

        // Create the device
        if(device== null)
        {
            // Create the device
            if(device== null)
            {
                //hardcoded UUID can be replaced by a dynamic generated one at each start. see @medicalDevice
                device = new AccessControlManagerDevice(-1, sUUID);
                //device.setPortTypes(new QNameSet(new QName("MedicalDevice", "http://www.smartOR.de/2011/MedicalDeviceProfile")));
                //device. .setPortTypes(new QNameSet(new QName("MDC_WORKSTATION_ANAESTHESIA", "http://www.smartOR.de/2012/MedicalDeviceProfile")));

                // ... add a binding the device can be accessed over ...
                //device.addBinding(new HTTPBinding(IP, 8080, "/MedicalDevice/device"));

            }
        }
    }

    public static void main(String[] args) {

    	AccessControlManager accessControlManager = new AccessControlManager();

	}


}
