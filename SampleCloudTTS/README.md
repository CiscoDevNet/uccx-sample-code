# SampleCloudTTS.
Using Cloud TTS services with UCCX

**Introduction**

This folder contains the sample script sampleCloudTTS.aef and org-apache-commons-codec.jar. This JAR is used in sampleCloudTTS.aef to decode base64 output.

**Objective**

Leverage available cloud Text To Speech services to generate prompts for UCCX and play back the audio prompts on the fly. There are various advantages to using cloud TTS service:

1. Customer need not buy any TTS server which can be expensive at times.
2. No need to pre-record prompts or upload prompts to CCX.
3. Can change prompts on the fly as per business requirement.
4. Much cheaper than traditional TTS providers.

TTS service we have leveraged here is from voicerss and all information can be checked here: www.voicerss.org/ Using the TTS service from voicers we can covert any text to audio and use CCX ability to play back that audio as prompts.

**Script flow**

1. The script used here makes use of Make Rest call step to send request to voicerss TTS and stores the output in a variable.
2. The response here is base64 encoded which is used extracted and decoded into byte array using JAR which is uploaded to UCCX.
3. This byte array output is converted in input stream and further can be used by CCX media steps to play back as prompts.

**Script Usecase**

1. This sample script takes basic usecase of customer calling in a Bank.
2. The text for these prompts is completely editable from application page as we are using prompts text variable as parameter in script.
3. Make rest call step is used to create prompt for Welcome prompt / Debit queue / Credit queue / Loan queue leveraging cloud services.
4. The text entered in variable on application page are played back by CCX to customer. So these can be changed on fly anytime as per requirement. 

**Installation steps**

1. Create account on voicerss site and get the API access key which will be used to send request from CCX make call step.
2. Upload attached JAR into UCCX: System--Custom file configuration and move it to selected.
3. Restart Cisco Unified CCX Engine and Cisco Unified CCX Administration services on both the CCX nodes (in case of HA ).
4. Upload atached script and map it to some application. It will display variables available from script in application configuration.
5. Update the API key obtained in step 1 for the "key" variable of the script on application configuration page.
6. Update welcome text whatever you want and other text variable whatever you to play back to customer. The text updated in text
variables will be converted into prompts to be played back to customer.

For Example in application page we can enter Welcome text as _"Welcome to swiss bank, your black money is safe with us"_.
Same will be played back as prompt to customers who call in to this application.

Note: Make sure CCX have connectivity to internet so make rest call step can send request out of CCX. You can do this by enabling proxy
in system parameters on CCX appadmin and checking "Use proxy" in make rest call step.
