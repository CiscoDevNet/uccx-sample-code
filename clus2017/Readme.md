# Introduction
This folder contains the CCX scripts that were used for the session BRKCCT-2301 in Cisco Live US 2017.

Apart from the scripts, it includes a sample Java project that when built, creates an uber jar that can be uploaded to CCX for custom step implementation . This is used in the script webcallback.aef

## Scripts

### rewardpoints.aef

This script is used in the first demo, where the database steps are used to fetch information from an external SQL server and then a notification is sent out using a thirdparty notification service. 

Check out the script in detail on how this is done. 

#### Few Important Points

The script has Create JSON Document and Get JSON Document Data steps used and these are available from CCX v11.6 onwards. 

Further, the Make REST Call step has the flag 'Use Http Proxy' and has the ability to specify Http headers.  Both these capabilities too are available from CCX v11.6 onwards.

##### Get JSON Document Data step

This step needs a JSON Document as an input and then uses JSON path expressions to parse the content and extract data elements. 

The extracted data elements can be of the type:

* int
* int[]
* short
* short[]
* long 
* long[]
* float
* float[]
* double
* double[]
* boolean 
* boolean[]
* string
* string[]
* Document (instance of JSON Document)
* Document []  (Array of JSON Document instances)

Note that the editor supports the type Document, but the actual instance that this steps expect is JSONDocument. If its not an instance of JSONDocument, the step will fail.

Use Create JSON Document to create a JSON document instance

If the document is a valid document, but either the parsing fails or JSON path expressions does  not return any thing, then the following is the return value.

* For all array types, it will be an empty array
* For all other types, it will be null


This step supports the JSON path syntax as documented at https://github.com/json-path/JsonPath 

#### Create JSON Document
Use this step to create a JSON document. This step converts a TEXT document to JSON Document, if the content of TEXT document is a valid JSON.

If you have a string data type , such as the response of Make REST Call step, a TEXT document can be created by using  the expression TEXT[\<string value\>]

### webcallback.aef

This is the script used in the second demo, which gets invoked by an Http Trigger.

This script invokes CCX campaign contacts API to import contacts to an existing campaign and then  uses a custom step implementation to publish a message to the specified topic of an enterprise messaging system.

The custom step is a sequence of expressions wrapped in Do step.
The expressions use the custom class MsgProducer , which creates an instance of the producer, connects to a message broker and publishes the message to the specified topic.

The sample implementation of MsgProducer is included too.

## MsgProducer

This is a sample project that generates a JMS message producer jar , along with all its dependencies , which upon uploading to CCX and adding to system classpath, can be used in the Do step / other Java expressions in the script. 

### Building MsgProducer

You need to have maven 3.0.X installed with Java 7 (CCX v11.6 runs on Java 1.7) in order build the project.

Once the pre-requisites are met, go the folder msgproducer and run the command:

	mvn clean package

This will produce the jar "msgproducer-1.0-jar-with-dependencies.jar" under the target folder. 
This is the uber jar that needs to be uploaded to CCX.
 
### Uploading to Unified CCX


* Upload msgproducer-1.0-jar-with-dependencies.jar using ‘Applications->Document Management’ page of Unified CCX Administration interface
* Ensure the jar is in the list of ‘Selected Classpath Entries’ list in ‘System->Custom File Configruation’ page.


Once its uploaded and classpath is verified, the MsgProdcuer can be used in the Do step's Java expressions.
