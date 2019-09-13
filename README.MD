# How to build an Alexa skill 

This project is a demo to show how you can build an Amazon Alexa skill using Java and Maven on top of AWS lambdas. In first step we send back only a pre-defined message. After that we use an open api to get train stations in Sweden. 
### step-by-step guide
All words in quotation mark point to a tab, section or part of a webpage. 

#### Create Alexa skill 
1.  Log in to [Amazon developer portal](http://developer.amazon.com) and create a skill. 
2. Choose a name for "Skill Invocation Name". The default is the same as your skill name. 
<a name="indentname"> 3. Define an intent which will be invoked as an action after skill invocation name. </a>
4. Your custom intent should include some "Sample Utterances" which are the words that Alexa makes sense by building a model of them and uses language processing to expand related vocabularies. 
5. Go to the "Endpoint" section, choose "AWS Lambda ARN" to use lambda functions as a logic provider of your skill and copy the skill id. 


#### Create Lambda function  
1. Go to [Lambda developer page](https://aws.amazon.com/lambda/) and create a function. 
2. Open "Designer" and define Amazon Skill kit as a trigger. Paste the Alexa skill id to permit the skill the ability to invoke this function. 
3. Copy back the function ARN to the Skill Endpoint. 
4. Upload your program to the lambda function and define an entry point for the function.

 
#### Develop a Lambda function 
1. Create a maven project.
2. Make sure your runtime is java 8. Lambdas don’t accept newer versions. 
3. Set AWS-SDK in the project dependencies. 
4. Create a LOG4J configuration file in “resources”. AWS-SDK needs such a configuration file.
5. Define a class which extends SkillStreamHandler. 
6. AWS lambda needs an entry point to the function. the entry point is the first class which will be run when function invoked. The entry point will be the class that extends SkillStreamHandler.
7. Streamhandler should have a no-arg contractor.
8. All your “intent” should be included in the SkillStreamhandler to be known at runtime. 
9. There is a bunch of default intents that you should override to handle the different states of the skill when it runs, for example, launch phase, fallback phase, and so on. 
10. Your custom intent should implement RequestHandler from amazon dispatcher package. 
11. Make sure the indent name in “canhandle()” method matches the indent name you defined in [step 3 of creating Alexa skill](#indentname). 
12. The "handle()" method is the actual logic of your program. 
13. Build with maven and include all the dependencies in a single package.


##### Build the project and create a Jar file with all dependencies
You can use the following command to package the code and dependencies: 
```
mvn org.apache.maven.plugins:maven-assembly-plugin:2.6:assembly -DdescriptorId=jar-with-dependencies package -DskipTests
```

## Watch the video
[![Watch the video](https://img.youtube.com/vi/AvF0XWTSPzU/maxresdefault.jpg)](https://youtu.be/AvF0XWTSPzU)
