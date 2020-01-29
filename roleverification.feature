Feature: Test the IIQ Roles functionality

  Scenario Outline: Verifying the Role information
  
   Given user launch the ANZ IIQ application
   And enter Userid,Password and click on Submit button
   When user clicks on Setup tab and choose Roles option
   And enter <RoleName> in the search field
   Then verify the <Descr>,<Bus_Rln>,<Bus_DspNm> and <Owner> details
   And verify the <Jobcode>,<Org_Hrchy_Lvl6>,<Org>,<Bus_Unit>,<RBI>,<Tier_2> and <Lending_Fundamentals> details for the <RoleName> in RoleInformation section
   Then click on Required Roles section and verify the IT and AD roles<IT_AD_Role>
   And verify the Direct Entitlements section parameters<DirectEtmt_Rolename>,<Application>
   And user clicks on Logout button
    
   Examples:-
   |RoleName											|DirectEtmt_Rolename			  |Application			 |IT_AD_Role								 																	     																		 |Jobcode|Org_Hrchy_Lvl6			 |Org|Bus_Unit|RBI |Tier_2|Lending_Fundamentals|Bus_Rln													 |Bus_DspNm												 |Owner	|Descr|
   #|ABN - Branch Manager (LOCAM)	|ABN AU Branch Manager LOCAM|RLS (iKnow Borrow)|ABN AU Branch Manager LOCAM;Banker Desktop#AU Digital BD Read#Global;QlikView#AU QlikView - Home Loan Hub (ABN)#Global;eProtect#AU OTSPM - eProtect sales Users (Wg)#Oceania;Frontline Reports (Banker Dashboard)#AU PFSMB - HILM Smartlink User (Wg)#Global;IQ Connect#AU RTCD SSO Users#Global;IQ Connect#AU RTCD SSO Users#Global;QlikView#AU Qlikview - EW2B Dashboard#Global;QlikView#AU QlikView - Leads & Offers Dashboard (Wg)#Oceania;RBI Frontline Reports#AU RetailPerformanceInsights - Reporting Access#Global|J00019 |Aust Branch Network	 |AU |ANZAU	 	|true|true	|true   		 			   |ABN - Branch Manager (LOCAM)	   |ABN - Branch Manager (LOCAM)	   |761550|This role captures the tasks performed by Branch Managers who are LOCAM accredited and responsible for the overall operation of a branch including sales, maintaining service excellence and ensuring the branch is compliant with ANZ's policies and procedures.|
   |ABN - Branch Manager (LOCAM)	|ABN AU Branch Manager LOCAM|RLS (iKnow Borrow)|ABN AU Branch Manager LOCAM;IQ Connect#AU RTCD SSO Users#Global;OSAS#AU OSAS Viewer Users (Wg)#Global|J00019 |Aust Branch Network	 |kk |ANZAU	 	|true|u65t	|true   		 			   |ABN - Branch Manager (LOCAM)	   |ABN - Branch Manager (LOCAM)	   |761550|This role captures the tasks performed by Branch Managers who are LOCAM accredited and responsible for the overall operation of a branch including sales, maintaining service excellence and ensuring the branch is compliant with ANZ's policies and procedures.|
   																	  
   																	 