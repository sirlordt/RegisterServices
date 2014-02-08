package RegisterServicesManager;

import CommonClasses.ConstantsCommonConfigXMLTags;

public interface ConstantsServicesManager {

	public static final String _Main_File = "RegisterServicesManager";
	public static final String _Conf_File = _Main_File + ".conf";
	public static final String _Main_File_Log = _Main_File + ".log";
	public static final String _Security_Manager_Name = _Main_File + ".SecurityManager";
	
	public static final String _Register_Password = "register_pass"; 
	public static final int _Frequency_Check_Remove = 15000;
	public static final int _Remove_Registered_Timeout = 5000;
	
	public static final String _Services_Dir = "RegisterServices/"; 

	public static final String _Logger_Name = "RegisterServicesManagerLogger"; 

	public static final String _Response_Format = ConstantsCommonConfigXMLTags._ResponseFormat_JAVA_XML_WEBROWSET;
	public static final String _Response_Format_Version = "1.0";
	
}
