
package de.smartor.manager.accesscontrolmanager;

import org.ws4d.java.security.CredentialInfo;
import org.ws4d.java.service.InvocationException;
import org.ws4d.java.service.InvokeDelegate;
import org.ws4d.java.service.Operation;
import org.ws4d.java.service.parameter.ParameterValue;
import org.ws4d.java.service.parameter.ParameterValueManagement;
import org.ws4d.java.types.QName;

public class GetIdentificationDelegate implements InvokeDelegate {
	// Attributes:
	public static String sDeviceTypeIDAttribute = "OSCB Manager";
	public static String sDeviceSubTypeIDAttribute = "OSCB Manager";
	public static String sUsesIEEECodesAttribute = "true";
	// any other attribute is also aloud and should be added ...

	// Elements:
	public static String sFriendlyName = "OSCB Access Control Manager";
	public static String sSerialNumber = "12345";
	public static String sFirmwareVersion = "0.1";
	public static String sHardwareVersion = "0.1";
	public static String sSoftwareVersion = "0.1";

	// DeviceParameterElement:
	// their are more then one DeviceParameter possible and should be
	// implemented later...
	public static String sNameDeviceParameterElement;
	public static Boolean bIsReadOnlyDeviceParameterElement;
	public static String sParameterValueDeviceParameterElement;
	public static String sValueTypeDeviceParameterElement;

	// is called whenever the operation is invoked
	@Override
	public ParameterValue invokeImpl(Operation operation, ParameterValue parameterValue, CredentialInfo credentialInfo)
			throws InvocationException {
		// Create the answer
		ParameterValue outVal = operation.createOutputValue();

		// Attributes
		outVal.setAttributeValue("DeviceTypeID", sDeviceTypeIDAttribute);
		outVal.setAttributeValue("DeviceSubTypeID", sDeviceSubTypeIDAttribute);
		outVal.setAttributeValue("UsesIEEECode", sUsesIEEECodesAttribute);

		ParameterValue outValDeviceInformation = outVal.createChild("DeviceInformation");
		// Elements
		ParameterValueManagement.setString(outValDeviceInformation, "FriendlyName", sFriendlyName);
		ParameterValueManagement.setString(outValDeviceInformation, "SerialNumber", sSerialNumber);
		ParameterValueManagement.setString(outValDeviceInformation, "FirmwareVersion", sFirmwareVersion);
		ParameterValueManagement.setString(outValDeviceInformation, "HardwareVersion", sHardwareVersion);
		ParameterValueManagement.setString(outValDeviceInformation, "SoftwareVersion", sSoftwareVersion);

		ParameterValue outValParameterDescription = outVal.createChild("ParameterDescription");
		ParameterValueManagement.setString(outValParameterDescription, "name", "name");
		ParameterValueManagement.setQName(outValParameterDescription, "valueType", new QName("test",
				"http://www.smartOR.de/2012/MedicalDeviceService"));
		ParameterValueManagement.setString(outValParameterDescription, "isSetable", "true");
		ParameterValueManagement.setString(outValParameterDescription, "acceptedCommand[0]", "CMD_TEST1");
		ParameterValueManagement.setString(outValParameterDescription, "acceptedCommand[1]", "CMD_Test2");

		ParameterValue outValParameterDescription1 = outVal.createChild("ParameterDescription");
		ParameterValueManagement.setString(outValParameterDescription1, "name", "name");
		ParameterValueManagement.setQName(outValParameterDescription1, "valueType", new QName("test",
				"http://www.smartOR.de/2012/MedicalDeviceService"));
		ParameterValueManagement.setString(outValParameterDescription1, "isSetable", "true");
		ParameterValueManagement.setString(outValParameterDescription1, "acceptedCommand[0]", "CMD_TEST1");
		ParameterValueManagement.setString(outValParameterDescription1, "acceptedCommand[2]", "CMD_Test2");

		return outVal;
	}

}
