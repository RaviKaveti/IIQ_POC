Feature: Test the IIQ Roles functionality

  Scenario Outline: Verifying the Role information
  
   Given User launch the ANZ IIQ application
   And enter Userid and Password and click on Submit button
   When User clicks on Setup tab and choose Roles option
   And enter <RoleName> in the search field
   Then verify the <Descr>,<Bus_Rln>,<Bus_DspNm> and <Owner> details
   And verify the <Jobcode>,<Org_Hrchy_Lvl6>,<Org>,<Bus_Unit>,<RBI>,<Tier_2> and <Lending_Fundamentals> details for the <RoleName> in RoleInformation section
   And click on Required Roles section and verify the IT and AD roles<IT_AD_Role>
   And click on Determine tab to verify application details<Determine_Rolename>,<Application>
   And User clicks on Logout button
    
   Examples:-
   |RoleName												|Determine_Rolename				  |Application			 |IT_AD_Role								 																	     																		 |Jobcode|Org_Hrchy_Lvl6			 |Org|Bus_Unit|RBI |Tier_2|Lending_Fundamentals|Bus_Rln													 |Bus_DspNm												 |Owner	|Descr|
   |ABN - Branch Manager (LOCAM)		|ABN AU Branch Manager LOCAM|RLS (iKnow Borrow)|ABN AU Branch Manager LOCAM;IQ Connect#AU RTCD SSO Users#Global;OSAS#AU OSAS Viewer Users (Wg)#Global|J00019 |Aust Branch Network	 |kk |ANZAU	 	|true|u65t	|true   		 			   |ABN - Branch Manager (LOCAM)	   |ABN - Branch Manager (LOCAM)	   |761550|This role captures the tasks performed by Branch Managers who are LOCAM accredited and responsible for the overall operation of a branch including sales, maintaining service excellence and ensuring the branch is compliant with ANZ's policies and procedures.|
   																	  
   																	  
   																	  
   #|ABN - Branch Manager (RBI Tier 2)|J00019 |Aust Branch Network	 |AU |ANZAU	  |true|true	|false   		 			   |ABN - Branch Manager (RBI Tier 2)|ABN - Branch Manager (RBI Tier 2)|761550|This role captures the tasks performed by Branch Managers who are Tier 2 and RBI accredited and responsible for the overall operation of a branch including sales| 
  
	 #|ABN - Branch Manager (RBI Tier 2)|J00019 |Aust Branch Network	 |AU |ANZAU	  |true|true	|false   		 			   |ABN - Branch Manager (RBI Tier 2)|ABN - Branch Manager (RBI Tier 2)|761550|This role captures the tasks performed by Branch Managers who are Tier 2 and RBI accredited and responsible for the overall operation of a branch including sales, maintaining service excellence and ensuring the branch is compliant with ANZ's policies and procedures.|
   
   
	