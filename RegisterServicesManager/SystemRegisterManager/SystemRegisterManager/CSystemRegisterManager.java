package SystemRegisterManager;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import AbstractResponseFormat.CAbstractResponseFormat;
import AbstractService.CAbstractService;
import AbstractService.CInputServiceParameter;
import AbstractService.CServicePreExecuteResult;
import AbstractService.CInputServiceParameter.TParameterScope;
import CommonClasses.CAbstractConfigLoader;
import CommonClasses.CClassPathLoader;
import CommonClasses.CConfigServicesDaemon;
import CommonClasses.CRegisteredManagerInfo;
import CommonClasses.CRegisteredManagersControl;
import CommonClasses.CServicePostExecuteResult;
import CommonClasses.ConstantsCommonClasses;
import CommonClasses.ConstantsMessagesCodes;
import RegisterCommonClasses.CRegisterAbstractService;
import SystemUnregisterManager.ConstantsSystemUnregisterManager;

public class CSystemRegisterManager extends CRegisterAbstractService {

	public CSystemRegisterManager() {
		
		super();
		
	}
	
	@Override
	public boolean initializeService( CConfigServicesDaemon ServicesDaemonConfig, CAbstractConfigLoader OwnerConfig ) { // Alternate manual contructor

		boolean bResult = super.initializeService( ServicesDaemonConfig, OwnerConfig );

		try {
			
			this.bAuthRequired = true;
			this.strRunningPath = net.maindataservices.Utilities.getJarFolder( this.getClass() );
			this.strServiceName = "System.Register.Manager";
			this.strServiceVersion = "0.0.0.1";

			this.setupService( ConstantsSystemRegisterManager._Main_File_Log, this.strRunningPath + ConstantsCommonClasses._Langs_Dir + ConstantsSystemRegisterManager._Main_File + "." + ConstantsCommonClasses._Lang_Ext ); //Init the Logger and Lang

			ServiceLogger.logMessage( "1", ServiceLang.translate( "Running dir: [%s]", this.strRunningPath ) );        
			ServiceLogger.logMessage( "1", ServiceLang.translate( "Version: [%s]", this.strServiceVersion ) );        

			CClassPathLoader ClassPathLoader = new CClassPathLoader();

			ClassPathLoader.loadClassFiles( this.strRunningPath + ConstantsCommonClasses._Pre_Execute_Dir, ConstantsCommonClasses._Lib_Ext, 2, ServiceLogger, ServiceLang  );

			this.loadAndRegisterServicePreExecute();

			ClassPathLoader.loadClassFiles( this.strRunningPath + ConstantsCommonClasses._Post_Execute_Dir, ConstantsCommonClasses._Lib_Ext, 2, ServiceLogger, ServiceLang  );

			this.loadAndRegisterServicePostExecute();

			this.strServiceDescription = ServiceLang.translate( "Lets register managers" );

			ArrayList< CInputServiceParameter > ServiceInputParameters = new ArrayList< CInputServiceParameter >();

			CInputServiceParameter InputParameter = new CInputServiceParameter( ConstantsCommonClasses._Request_ResponseFormat, false, ConstantsCommonClasses._Request_ResponseFormat_Type, ConstantsCommonClasses._Request_ResponseFormat_Length, TParameterScope.IN, ServiceLang.translate( "Response format name, example: XML-DATAPACKET, CSV, JSON" ) );

			ServiceInputParameters.add( InputParameter ); 	

			InputParameter = new CInputServiceParameter( ConstantsCommonClasses._Request_ResponseFormatVersion, false, ConstantsCommonClasses._Request_ResponseFormatVersion_Type, ConstantsCommonClasses._Request_ResponseFormatVersion_Length, TParameterScope.IN, ServiceLang.translate( "Response format version, example: 1.1" ) );

			ServiceInputParameters.add( InputParameter ); 	

			InputParameter = new CInputServiceParameter( ConstantsCommonClasses._Request_ServiceName, true, ConstantsCommonClasses._Request_ServiceName_Type, ConstantsCommonClasses._Request_ServiceName_Length, TParameterScope.IN, ServiceLang.translate( "Service Name" ) );

			ServiceInputParameters.add( InputParameter );

			InputParameter = new CInputServiceParameter( ConstantsCommonClasses._Request_SecurityTokenID, true, ConstantsSystemUnregisterManager._Request_SecurityToken_Type, ConstantsSystemUnregisterManager._Request_SecurityToken_Length, TParameterScope.IN, ServiceLang.translate( "Security token" ) );

			ServiceInputParameters.add( InputParameter );
			
			InputParameter = new CInputServiceParameter( ConstantsSystemRegisterManager._Request_Context, true, ConstantsSystemRegisterManager._Request_Context_Type, ConstantsSystemRegisterManager._Request_Context_Length, TParameterScope.IN, ServiceLang.translate( "Context to register. sample: /DBServices" ) );

			ServiceInputParameters.add( InputParameter );

			InputParameter = new CInputServiceParameter( ConstantsSystemRegisterManager._Request_ManagerURL, true, ConstantsSystemRegisterManager._Request_ManagerURL_Type, ConstantsSystemRegisterManager._Request_ManagerURL_Length, TParameterScope.IN, ServiceLang.translate( "URL where localize the manager. sample: http://192.168.1.140/DBServices" ) );

			ServiceInputParameters.add( InputParameter );

			InputParameter = new CInputServiceParameter( ConstantsSystemRegisterManager._Request_Weight, true, ConstantsSystemRegisterManager._Request_Weight_Type, ConstantsSystemRegisterManager._Request_Weight_Length, TParameterScope.IN, ServiceLang.translate( "Weight for manager, this value help to distribution of load. sample: 150" ) );

			ServiceInputParameters.add( InputParameter );

			InputParameter = new CInputServiceParameter( ConstantsSystemRegisterManager._Request_Load, true, ConstantsSystemRegisterManager._Request_Load_Type, ConstantsSystemRegisterManager._Request_Load_Length, TParameterScope.IN, ServiceLang.translate( "Current load for manager, this value help to distribution of load, min 0 and max 100. sample: 45" ) );

			ServiceInputParameters.add( InputParameter );
			
			GroupsInputParametersService.put( ConstantsCommonClasses._Default, ServiceInputParameters );
		
		}
		catch ( Exception Ex ) {

			bResult = false;
			
			if ( OwnerLogger != null )
        		OwnerLogger.logException( "-1010", Ex.getMessage(), Ex );
			
		}
        
		return bResult;
		
	}
	
	@Override
	public int executeService( int intEntryCode, HttpServletRequest Request, HttpServletResponse Response, String strSecurityTokenID, HashMap<String, CAbstractService> RegisteredServices, CAbstractResponseFormat ResponseFormat, String strResponseFormatVersion ) {

		int intResultCode = -1000;
		
		if ( this.checkServiceInputParameters( GroupsInputParametersService.get( ConstantsCommonClasses._Default ), Request, Response, ResponseFormat, strResponseFormatVersion, (String) OwnerConfig.sendMessage( ConstantsMessagesCodes._Global_DateTime_Format, null ), (String) OwnerConfig.sendMessage( ConstantsMessagesCodes._Global_Date_Format, null ), (String) OwnerConfig.sendMessage( ConstantsMessagesCodes._Global_Time_Format, null ), this.ServiceLogger!=null?this.ServiceLogger:this.OwnerLogger, this.ServiceLang!=null?this.ServiceLang:this.OwnerLang ) == true ) {
			
			CServicePreExecuteResult ServicePreExecuteResult = this.runServicePreExecute( intEntryCode, Request, Response, strSecurityTokenID, RegisteredServices, ResponseFormat, strResponseFormatVersion );

			if ( ServicePreExecuteResult == null || ServicePreExecuteResult.bStopExecuteService == false ) {

				if ( strSecurityTokenID != null && strSecurityTokenID.isEmpty() == false ) {

					try { 
						
						CRegisteredManagersControl RegisteredManagersControl = CRegisteredManagersControl.getRegisteredManagersControl();
						
						if ( RegisteredManagersControl != null ) {
							
							String strManagerURL = Request.getParameter( ConstantsSystemRegisterManager._Request_ManagerURL );
							
							CRegisteredManagerInfo RegisteredManagerInfo = RegisteredManagersControl.getRegisteredManagerFromURL( strManagerURL );

							Response.setContentType( ResponseFormat.getContentType() );
							Response.setCharacterEncoding( ResponseFormat.getCharacterEncoding() );
							
							String strResponseBuffer = "";
							
							if ( RegisteredManagerInfo == null ) {
								
								RegisteredManagerInfo = new CRegisteredManagerInfo();
								
								int intLoad = net.maindataservices.Utilities.strToInteger( Request.getParameter( ConstantsSystemRegisterManager._Request_Load ), ServiceLogger );

								int intWeight = net.maindataservices.Utilities.strToInteger( Request.getParameter( ConstantsSystemRegisterManager._Request_Weight ), ServiceLogger );
								
								String strContext = Request.getParameter( ConstantsSystemRegisterManager._Request_Context );
								
								RegisteredManagerInfo.strContext = strContext;
								RegisteredManagerInfo.strManagerURL = strManagerURL;
								RegisteredManagerInfo.intLoad = intLoad;
								RegisteredManagerInfo.intWeight = intWeight;
								RegisteredManagerInfo.lngLastUpdate = System.currentTimeMillis();
								
								RegisteredManagersControl.registerManager( RegisteredManagerInfo ); //Add to list from registered managers
								
								strResponseBuffer = ResponseFormat.formatSimpleMessage( "", "", 1000, ServiceLang.translate( "Success to register manager [%s] in context: [%s]", strManagerURL, strContext ), false, strResponseFormatVersion, (String) OwnerConfig.sendMessage( ConstantsMessagesCodes._Global_DateTime_Format, null ), (String) OwnerConfig.sendMessage( ConstantsMessagesCodes._Global_Date_Format, null ), (String) OwnerConfig.sendMessage( ConstantsMessagesCodes._Global_Time_Format, null ), this.ServiceLogger!=null?this.ServiceLogger:this.OwnerLogger, this.ServiceLang!=null?this.ServiceLang:this.OwnerLang );
								
							}
							else {
								
								RegisteredManagerInfo.lngLastUpdate = System.currentTimeMillis();
								
								strResponseBuffer = ResponseFormat.formatSimpleMessage( "", "", 1001, ServiceLang.translate( "Success to update registered manager [%s]", strManagerURL ), false, strResponseFormatVersion, (String) OwnerConfig.sendMessage( ConstantsMessagesCodes._Global_DateTime_Format, null ), (String) OwnerConfig.sendMessage( ConstantsMessagesCodes._Global_Date_Format, null ), (String) OwnerConfig.sendMessage( ConstantsMessagesCodes._Global_Time_Format, null ), this.ServiceLogger!=null?this.ServiceLogger:this.OwnerLogger, this.ServiceLang!=null?this.ServiceLang:this.OwnerLang );
								
							}

							Response.getWriter().print( strResponseBuffer );
							
							intResultCode = 1;
							
						}
						
					}
					catch ( Exception Ex ) {

						if ( ServiceLogger != null )
							ServiceLogger.logException( "-1020", Ex.getMessage(), Ex ); 
						else if ( OwnerLogger != null )
							OwnerLogger.logException( "-1020", Ex.getMessage(), Ex );

					}

				}

			}
			else {

				intResultCode = ServicePreExecuteResult.intResultCode;

			}

			CServicePostExecuteResult ServicePostExecuteResult = this.runServicePostExecute( intEntryCode, Request, Response, strSecurityTokenID, RegisteredServices, ResponseFormat, strResponseFormatVersion );

			if ( ServicePostExecuteResult != null ) {

				intResultCode = ServicePostExecuteResult.intResultCode;

			}

		}
		
        return intResultCode;
		
	}

}
