package SystemListRegisteredManagers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import RegisterCommonClasses.CRegisterAbstractService;

import AbstractResponseFormat.CAbstractResponseFormat;
import AbstractService.CAbstractService;
import AbstractService.CInputServiceParameter;
import AbstractService.CServicePreExecuteResult;
import AbstractService.CInputServiceParameter.TParameterScope;
import CommonClasses.CAbstractConfigLoader;
import CommonClasses.CClassPathLoader;
import CommonClasses.CConfigServicesDaemon;
import CommonClasses.CMemoryRowSet;
import CommonClasses.CRegisteredManagerInfo;
import CommonClasses.CRegisteredManagersControl;
import CommonClasses.CServicePostExecuteResult;
import CommonClasses.ConstantsCommonClasses;
import CommonClasses.ConstantsMessagesCodes;

public class CSystemListRegisteredManagers extends CRegisterAbstractService {

	public CSystemListRegisteredManagers() {
		
		super();
		
	}
	
	@Override
	public boolean initializeService( CConfigServicesDaemon ServicesDaemonConfig, CAbstractConfigLoader OwnerConfig ) { // Alternate manual contructor

		boolean bResult = super.initializeService( ServicesDaemonConfig, OwnerConfig );

		try {
			
			this.bAuthRequired = true;
			this.strRunningPath = net.maindataservices.Utilities.getJarFolder( this.getClass() );
			this.strServiceName = "System.List.Registered.Managers";
			this.strServiceVersion = "0.0.0.1";

			this.setupService( ConstantsSystemListRegisteredManagers._Main_File_Log, this.strRunningPath + ConstantsCommonClasses._Langs_Dir + ConstantsSystemListRegisteredManagers._Main_File + "." + ConstantsCommonClasses._Lang_Ext ); //Init the Logger and Lang

			ServiceLogger.logMessage( "1", ServiceLang.translate( "Running dir: [%s]", this.strRunningPath ) );        
			ServiceLogger.logMessage( "1", ServiceLang.translate( "Version: [%s]", this.strServiceVersion ) );        

			CClassPathLoader ClassPathLoader = new CClassPathLoader();

			ClassPathLoader.loadClassFiles( this.strRunningPath + ConstantsCommonClasses._Pre_Execute_Dir, ConstantsCommonClasses._Lib_Ext, 2, ServiceLogger, ServiceLang  );

			this.loadAndRegisterServicePreExecute();

			ClassPathLoader.loadClassFiles( this.strRunningPath + ConstantsCommonClasses._Post_Execute_Dir, ConstantsCommonClasses._Lib_Ext, 2, ServiceLogger, ServiceLang  );

			this.loadAndRegisterServicePostExecute();

			this.strServiceDescription = ServiceLang.translate( "Lets unregister managers" );

			ArrayList< CInputServiceParameter > ServiceInputParameters = new ArrayList< CInputServiceParameter >();

			CInputServiceParameter InputParameter = new CInputServiceParameter( ConstantsCommonClasses._Request_ResponseFormat, false, ConstantsCommonClasses._Request_ResponseFormat_Type, ConstantsCommonClasses._Request_ResponseFormat_Length, TParameterScope.IN, ServiceLang.translate( "Response format name, example: XML-DATAPACKET, CSV, JSON" ) );

			ServiceInputParameters.add( InputParameter ); 	

			InputParameter = new CInputServiceParameter( ConstantsCommonClasses._Request_ResponseFormatVersion, false, ConstantsCommonClasses._Request_ResponseFormatVersion_Type, ConstantsCommonClasses._Request_ResponseFormatVersion_Length, TParameterScope.IN, ServiceLang.translate( "Response format version, example: 1.1" ) );

			ServiceInputParameters.add( InputParameter ); 	

			InputParameter = new CInputServiceParameter( ConstantsCommonClasses._Request_ServiceName, true, ConstantsCommonClasses._Request_ServiceName_Type, ConstantsCommonClasses._Request_ServiceName_Length, TParameterScope.IN, ServiceLang.translate( "Service Name" ) );

			ServiceInputParameters.add( InputParameter );

			InputParameter = new CInputServiceParameter( ConstantsCommonClasses._Request_SecurityTokenID, true, ConstantsSystemListRegisteredManagers._Request_SecurityToken_Type, ConstantsSystemListRegisteredManagers._Request_SecurityToken_Length, TParameterScope.IN, ServiceLang.translate( "Security token" ) );

			ServiceInputParameters.add( InputParameter );

			InputParameter = new CInputServiceParameter( ConstantsSystemListRegisteredManagers._Request_Context, true, ConstantsSystemListRegisteredManagers._Request_Context_Type, ConstantsSystemListRegisteredManagers._Request_Context_Length, TParameterScope.IN, ServiceLang.translate( "Context to list registered managers, use * for list all context and registered managers. sample: /DBServices" ) );

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
							
							String strContext = Request.getParameter( ConstantsSystemListRegisteredManagers._Request_Context );
							
							Vector<CRegisteredManagerInfo> RegisteredManagersList = RegisteredManagersControl.getListRegisteredManagersOnContext( strContext );

							CMemoryRowSet RegisteredManagers = new CMemoryRowSet( false );
							
							RegisteredManagers.addField( ConstantsSystemListRegisteredManagers._Response_Context, ConstantsSystemListRegisteredManagers._Response_Context_SQLType, ConstantsSystemListRegisteredManagers._Response_Context_Type, ConstantsSystemListRegisteredManagers._Response_Context_Length, ConstantsSystemListRegisteredManagers._Response_Context );
							RegisteredManagers.addField( ConstantsSystemListRegisteredManagers._Response_ManagerURL, ConstantsSystemListRegisteredManagers._Response_ManagerURL_SQLType, ConstantsSystemListRegisteredManagers._Response_ManagerURL_Type, ConstantsSystemListRegisteredManagers._Response_ManagerURL_Length, ConstantsSystemListRegisteredManagers._Response_ManagerURL );
							RegisteredManagers.addField( ConstantsSystemListRegisteredManagers._Response_Weight, ConstantsSystemListRegisteredManagers._Response_Weight_SQLType, ConstantsSystemListRegisteredManagers._Response_Weight_Type, ConstantsSystemListRegisteredManagers._Response_Weight_Length, ConstantsSystemListRegisteredManagers._Response_Weight );
							RegisteredManagers.addField( ConstantsSystemListRegisteredManagers._Response_StandardizedWeight, ConstantsSystemListRegisteredManagers._Response_StandardizedWeight_SQLType, ConstantsSystemListRegisteredManagers._Response_StandardizedWeight_Type, ConstantsSystemListRegisteredManagers._Response_StandardizedWeight_Length, ConstantsSystemListRegisteredManagers._Response_StandardizedWeight );
							RegisteredManagers.addField( ConstantsSystemListRegisteredManagers._Response_Load, ConstantsSystemListRegisteredManagers._Response_Load_SQLType, ConstantsSystemListRegisteredManagers._Response_Load_Type, ConstantsSystemListRegisteredManagers._Response_Load_Length, ConstantsSystemListRegisteredManagers._Response_Load );
							
							//Determine the max Weight for context for sample: "/DBServices" -> 1500, "/BPservices" -> 2300, "/RegisterServices" -> 1890
							LinkedHashMap<String,Integer> ContextsMaxWeight = new LinkedHashMap<String,Integer>(); 
							
							for ( CRegisteredManagerInfo RegisteredManagerInfo : RegisteredManagersList ) {

								Integer intCurrentMaxWeight = ContextsMaxWeight.get( RegisteredManagerInfo.strContext );
								
								if ( intCurrentMaxWeight == null ) {
									
									ContextsMaxWeight.put( RegisteredManagerInfo.strContext, RegisteredManagerInfo.intWeight );
									
								}
								else if ( RegisteredManagerInfo.intWeight > intCurrentMaxWeight ) {
									
									ContextsMaxWeight.put( RegisteredManagerInfo.strContext, RegisteredManagerInfo.intWeight );
									
								}
								
							}
							
							for ( CRegisteredManagerInfo RegisteredManagerInfo : RegisteredManagersList ) {
								
								RegisteredManagers.addData( ConstantsSystemListRegisteredManagers._Response_Context, RegisteredManagerInfo.strContext );
								RegisteredManagers.addData( ConstantsSystemListRegisteredManagers._Response_ManagerURL, RegisteredManagerInfo.strManagerURL );
								RegisteredManagers.addData( ConstantsSystemListRegisteredManagers._Response_Weight, RegisteredManagerInfo.intWeight );
								
								int intCurrentMaxWeight = ContextsMaxWeight.get( RegisteredManagerInfo.strContext ); 
								
								RegisteredManagerInfo.intStandardizedWeight = RegisteredManagerInfo.intWeight * 100 / intCurrentMaxWeight;

								RegisteredManagers.addData( ConstantsSystemListRegisteredManagers._Response_StandardizedWeight, RegisteredManagerInfo.intStandardizedWeight );
								
								RegisteredManagers.addData( ConstantsSystemListRegisteredManagers._Response_Load, RegisteredManagerInfo.intLoad );
								
							}
							
							Response.setContentType( ResponseFormat.getContentType() );
							Response.setCharacterEncoding( ResponseFormat.getCharacterEncoding() );
							
							String strResponseBuffer = ResponseFormat.formatMemoryRowSet( RegisteredManagers, strResponseFormatVersion, (String) OwnerConfig.sendMessage( ConstantsMessagesCodes._Global_DateTime_Format, null ), (String) OwnerConfig.sendMessage( ConstantsMessagesCodes._Global_Date_Format, null ), (String) OwnerConfig.sendMessage( ConstantsMessagesCodes._Global_Time_Format, null ), ServiceLogger, ServiceLang );

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
