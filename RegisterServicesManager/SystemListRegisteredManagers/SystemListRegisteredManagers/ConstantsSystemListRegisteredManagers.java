package SystemListRegisteredManagers;

import java.sql.Types;

public interface ConstantsSystemListRegisteredManagers {

	public static final String _Request_Context         = "Context";
	public static final String _Request_Context_Type    = "Varchar";
	public static final String _Request_Context_Length  = "128";

	public static final String _Request_SecurityToken_Type = "Varchar";
	public static final String _Request_SecurityToken_Length = "50";
	
	public static final String _Response_Context = "Context"; 
	public static final int _Response_Context_SQLType = Types.VARCHAR; 
	public static final String _Response_Context_Type = "Varchar"; 
	public static final int _Response_Context_Length = 128; 
	
	public static final String _Response_ManagerURL = "ManagerURL"; 
	public static final int _Response_ManagerURL_SQLType = Types.VARCHAR; 
	public static final String _Response_ManagerURL_Type = "Varchar"; 
	public static final int _Response_ManagerURL_Length = 1024; 

	public static final String _Response_Weight = "Weight"; 
	public static final int _Response_Weight_SQLType = Types.INTEGER; 
	public static final String _Response_Weight_Type = "Integer"; 
	public static final int _Response_Weight_Length = 0; 

	public static final String _Response_StandardizedWeight = "StandardizedWeight"; 
	public static final int _Response_StandardizedWeight_SQLType = Types.INTEGER; 
	public static final String _Response_StandardizedWeight_Type = "Integer"; 
	public static final int _Response_StandardizedWeight_Length = 0; 

	public static final String _Response_Load = "Load"; 
	public static final int _Response_Load_SQLType = Types.INTEGER; 
	public static final String _Response_Load_Type = "Integer"; 
	public static final int _Response_Load_Length = 0;
	
	public static final String _Main_File = "SystemListRegisteredManagers";
	public static final String _Conf_File = _Main_File + ".conf";
	public static final String _Main_File_Log = _Main_File + ".log";

}
