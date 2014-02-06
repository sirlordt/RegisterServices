package SystemRegisterManager;

public interface ConstantsSystemRegisterManager {

	public static final String _Request_Context            = "Context";
	public static final String _Request_Context_Type       = "Varchar";
	public static final String _Request_Context_Length     = "128";

	public static final String _Request_ManagerURL         = "ManagerURL";
	public static final String _Request_ManagerURL_Type    = "Varchar";
	public static final String _Request_ManagerURL_Length  = "1024";
	
	public static final String _Request_Weight             = "Weight";
	public static final String _Request_Weight_Type        = "Integer";
	public static final String _Request_Weight_Length      = "0";

	public static final String _Request_Load               = "Load";
	public static final String _Request_Load_Type          = "Integer";
	public static final String _Request_Load_Length        = "0";

	public static final String _Main_File = "SystemRegisterManager";
	public static final String _Conf_File = _Main_File + ".conf";
	public static final String _Main_File_Log = _Main_File + ".log";
	
}
