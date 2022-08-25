# Starting-Appium-server-using-code
Assignment 2 W11D4 - SDA - Software QA Bootcamp


# Table of contents
- [Question](#question)
- [Answer](#answer)
  - [Set up](#set-up)
  - [Start Work](#start-work)
    - [STEP 1: Create a Maven Project and Set Dependencies](#step-1-create-a-maven-project-and-set-dependencies)
    - [STEP 2: Create java class then set the code](#step-2-create-java-class-then-set-the-code)
- [Output Screenshots](#output-screenshots)
- [References](#references)

---
# Question
Create a simple program in java that starts an Appium server.

---

# Answer
Before running the code, there are some steps that need to take considered:

---
## Set up
### First:
Download and install:
- [JDK](https://www.oracle.com/java/technologies/downloads/) (Lastest)
- [Eclipse](https://www.eclipse.org/) (Lastest)
- [Appium Desktop](https://github.com/appium/appium-desktop/releases/tag/v1.22.3-4) (Lastest)
- [Node](https://nodejs.org/en/) (Lastest)



### Second:
Setup System Environment variables
- JAVA_HOME
- node
- nmp

#### Check this [Reference](https://www.baeldung.com/java-home-on-windows-7-8-10-mac-os-x-linux) to know how to use the Path

#### Your system path should have as shown below

<p align="center">

<img src="https://user-images.githubusercontent.com/48597284/185808789-558650e2-2ce9-490b-823d-112a1868264e.png" width=40% height=40%>
  
</p>

#### Install and start the Appium Server via Node

To install appium v2 run this command, window users please open your terminal in admin mode
```md
npm install -g appium@next
```

#### Check setup via below commands
```md
appium -v
```

```md
node -v
```

```md
java -version
```

```md
echo %JAVA_HOME%
```


```md
adb
```


<p align="center">

<img src="https://user-images.githubusercontent.com/48597284/186539886-1b9b851a-e2ac-41e7-bc3c-093276b50132.png" width=60% height=60%>
  
</p>


---
# Start Work
### STEP 1: Create a Maven Project and Set Dependencies
- _Eclipse > Create project -> New maven project -> maven-archetype-quickstart (1.4)(this gives you a maven template to begin work with-> Group ID n Artifact ID is a must for maven projects  (so appium can uniquely identify your project). The group ID is like a package name and Artifact Id is like a project name_.


Open pom.xml and add latest [Java Client](https://mvnrepository.com/artifact/io.appium/java-client) dependency.
remove junit and replace it with [TestNG](https://mvnrepository.com/artifact/org.testng/testng) dependency 

The Final dependencies looks like:
```md
  <dependencies>
<!-- https://mvnrepository.com/artifact/org.testng/testng -->
<dependency>
    <groupId>org.testng</groupId>
    <artifactId>testng</artifactId>
    <version>7.6.1</version>
</dependency>

<!-- https://mvnrepository.com/artifact/io.appium/java-client -->
<dependency>
    <groupId>io.appium</groupId>
    <artifactId>java-client</artifactId>
    <version>8.1.1</version>
</dependency>
  </dependencies>
```

<p align="center">
  <img src="https://user-images.githubusercontent.com/48597284/185945115-b011148d-9932-4994-8a8f-92c58028bcb6.png" width=50% height=50%>
</p>

---

### STEP 2: Create java class then set the code
1. Set variable
```md
static String Appium_Node_Path = "C:\\Program Files\\nodejs\\node.exe";
static String Appium_JS_Path = "C:\\Users\\lo0ol\\AppData\\Roaming\\npm\\node_modules\\appium";
static AppiumDriverLocalService service;
static String service_url;
```

2. Set the port number (you can chose any port)

```md
static int port =4976;
```

3. Check if the port is available or not from `cmd`
> Note: open it as an administrator
```md
netstat -ano|findstr "PID :4976"
```
<p align="center">
  <img src="https://user-images.githubusercontent.com/48597284/186540555-ff1b82ae-6a05-4b70-9d99-6debdec47ece.png" width=50% height=50%>
</p>

If not Then stop the port by the following the command
```md
taskkill /pid 14084 /f
```
<p align="center">
  <img src="https://user-images.githubusercontent.com/48597284/186541067-d170b5a4-8735-4957-8e5a-3fbbaa0eb430.png" width=50% height=50%>
</p>



4. Create file for writing the log information

```md
File fileInfo = new File(System.getProperty("user.dir") + "/target/build-logInfo.txt");
fileInfo.setExecutable(true);
fileInfo.setReadable(true);
fileInfo.setWritable(true);
```

5. set appiumServiceBuilder
```md
AppiumServiceBuilder appiumServiceBuilder = new AppiumServiceBuilder();
appiumServiceBuilder.usingPort(port).withArgument(GeneralServerFlag.BASEPATH, "/wd/hub/")
	.withArgument(GeneralServerFlag.USE_PLUGINS, "images").withIPAddress("127.0.0.1")
	.usingDriverExecutable(new File(Appium_Node_Path)).withAppiumJS(new File(Appium_JS_Path))
	.withArgument(GeneralServerFlag.SESSION_OVERRIDE).withArgument(GeneralServerFlag.RELAXED_SECURITY)
	.withArgument(GeneralServerFlag.LOG_LEVEL, "info").withLogFile(fileInfo) // enabled
//.withArgument(GeneralServerFlag.LOG_LEVEL, "error").withLogFile(fileError) //disabled
	.withTimeout(Duration.ofSeconds(60));
```

6. set environment
```md
HashMap<String, String> environment = new HashMap();
environment.put("PATH", "/usr/local/bin:" + System.getenv("PATH"));
appiumServiceBuilder.withEnvironment(environment);
```

7. set service
```md
service = AppiumDriverLocalService.buildService(appiumServiceBuilder);
```

8. create method for check If Server Is Runnning

```md
public static boolean checkIfServerIsRunnning(int port) {
  boolean isServerRunning = false;
	ServerSocket serverSocket;
	try {
			serverSocket = new ServerSocket(port);
			serverSocket.close();
	} catch (IOException e) {
			// If control comes here, then it means that the port is in use
			isServerRunning = true;
	} finally {
			serverSocket = null;
	}
		return isServerRunning;
}
```



9. start service

```md
service.start();
Thread.sleep(40000);
System.out.println("Is the server running = " + checkIfServerIsRunnning(port));
```


10. create method for stop the service

```md
public static void appiumStop() {
	service.stop();
}
```


11. stop the service

```md
appiumStop();
Thread.sleep(10000);
System.out.println("Is the server running after used appiumStop() = " + checkIfServerIsRunnning(port));
```

----

## Output Screenshots
<p align="center">
  <img src="https://user-images.githubusercontent.com/48597284/186543627-09ba4146-63aa-4a95-98a4-0aef28541e12.png" width=80% height=80%>

https://user-images.githubusercontent.com/48597284/186543569-e68086f3-f0a6-4d86-b460-2cdc150dd03f.mp4

</p>
----

## References
- [Start & stop Appium server with server arguments - PART 2](https://www.youtube.com/watch?v=5JMsszqD6gU)
- [How to check if appium server is already running using java](https://stackoverflow.com/questions/49362874/how-to-check-if-appium-server-is-already-running-using-java)
- [How to Start Appium Server Programmatically in Java](https://medium.com/geekculture/how-to-start-appium-server-programmatically-in-java-2ae2265cde10)
- [Starting an Appium Server Programmatically Using AppiumServiceBuilder](https://www.headspin.io/blog/starting-an-appium-server-programmatically-using-appiumservicebuilder)
- [How to stop and start Appium server programmatically using Java?](https://stackoverflow.com/questions/34745343/how-to-stop-and-start-appium-server-programmatically-using-java)
- [[SOLVED !] How can I start appium server programatically in parallel?](https://discuss.appium.io/t/solved-how-can-i-start-appium-server-programatically-in-parallel/25096)
- [How to start Appium server 2.0 Programmatically](https://stackoverflow.com/questions/69359123/how-to-start-appium-server-2-0-programmatically)
- [Could not start REST http interface listener. The requested port may already be in use.](https://stackoverflow.com/questions/49238539/could-not-start-rest-http-interface-listener-the-requested-port-may-already-be)
- [Appium Question. If "listen eaddrinuse: address already in use", how to stop it? why it didn't stop?](https://stackoverflow.com/questions/61122027/appium-question-if-listen-eaddrinuse-address-already-in-use-how-to-stop-it)
- [How to kill server when seeing “EADDRINUSE: address already in use”](https://levelup.gitconnected.com/how-to-kill-server-when-seeing-eaddrinuse-address-already-in-use-16c4c4d7fe5d)


