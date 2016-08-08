# AdCampaign

This project has developed using Sping Tool Suite 3.8.1 RELEASE . Following are the software requirements for this project.

1) JDK 1.8<br>
2) Spring Boot : v1.4.0.RELEASE<br>
3) Maven Build<br>
4) Spring Boot Tomcat Embedded Server


Instructions to Deploy:
----------------------------------

1) Import project from GIT to STS <br>
2) Verify JDK and JRE settings. <br>
3) Run --> Maven install<br>
4) Make sure all test cases completed successfully<br>
5) Run --> AdWebApplication.java as a SpringBoot/Java application. This must start the deployment process to embedded tomcat server<br>
6) Launch the application from http://localhost:8080/addemo/*


Testing
----------------

Two REST controllers created 

1) AdWebController - Holds data in-memory. End point addemo/* .<br>
2) AdWebCacheController - Cache the data using default EhCache. End point addemo/cache/* .

<b>SOAPUI test cases and results:  </b>

1) http://localhost:8080/addemo/postad

Request:

{

"partner_id":"45",
"duration":"60",
"ad_content":"This is my first ad !!"
	
}



Response: 

{
   "ad":    {
      "partner_id": "45",
      "duration": 60,
      "ad_content": "This is my first ad !!",
      "timeActive": 1470625759589
   },
   "adList": null,
   "error":    {
      "errorCd": 200,
      "errorMsg": null,
      "userMsg": "Ad campaign Saved Successfully !!"
   }
}



2) http://localhost:8080/

2.1 Request:  /addemo/ad/323


2.1 Response: 

{
   "ad": null,
   "adList": null,
   "error":    {
      "errorCd": 100,
      "errorMsg": null,
      "userMsg": "No ad campaigns exist for the pertner 323"
   }
}

2.2 Request:  /addemo/ad/45


2.2 Response: 

{
   "ad":    {
      "partner_id": "45",
      "duration": 60,
      "ad_content": "This is my first ad !!",
      "timeActive": 1470625956135
   },
   "adList": null,
   "error": null
}

2.3 Request:  /addemo/ad/45 (After 60 secs)


2.3 Response: 

{
   "ad": null,
   "adList": null,
   "error":    {
      "errorCd": 100,
      "errorMsg": null,
      "userMsg": "No active ad campaigns exist for the pertner 45"
   }
}


3) http://localhost:8080

Request: /addemo/ad


Response: 

{
   "ad": null,
   "adList":    [
            {
         "partner_id": "451",
         "duration": 60,
         "ad_content": "This is my first ad !!",
         "timeActive": 1470626052956
      },
            {
         "partner_id": "45",
         "duration": 60,
         "ad_content": "This is my first ad !!",
         "timeActive": 1470625956135
      },
            {
         "partner_id": "452",
         "duration": 60,
         "ad_content": "This is my first ad !!",
         "timeActive": 1470626061279
      }
   ],
   "error": null
}

