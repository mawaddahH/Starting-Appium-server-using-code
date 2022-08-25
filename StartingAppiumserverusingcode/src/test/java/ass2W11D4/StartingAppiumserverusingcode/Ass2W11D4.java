/**
 * @author Mawaddah Hanbali
 */
package ass2W11D4.StartingAppiumserverusingcode;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.time.Duration;
import java.util.HashMap;

import org.testng.annotations.Test;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

public class Ass2W11D4 {

	// set variable
	static String Appium_Node_Path = "C:\\Program Files\\nodejs\\node.exe";
	static String Appium_JS_Path = "C:\\Users\\lo0ol\\AppData\\Roaming\\npm\\node_modules\\appium";
	static AppiumDriverLocalService service;
	static String service_url;
	static int port =4976;

	@Test
	public static void appiumStart() throws Exception {

		// create file for writing the log information
		File fileInfo = new File(System.getProperty("user.dir") + "/target/build-logInfo.txt");
		fileInfo.setExecutable(true);
		fileInfo.setReadable(true);
		fileInfo.setWritable(true);
		
//      // create file for writing the log error
//		File fileError = new File(System.getProperty("user.dir") + "/target/build-logError.txt");
//		fileError.setExecutable(true);
//		fileError.setReadable(true);
//		fileError.setWritable(true);

		// set appiumServiceBuilder
		AppiumServiceBuilder appiumServiceBuilder = new AppiumServiceBuilder();
		appiumServiceBuilder.usingPort(port).withArgument(GeneralServerFlag.BASEPATH, "/wd/hub/")
				.withArgument(GeneralServerFlag.USE_PLUGINS, "images").withIPAddress("127.0.0.1")
				.usingDriverExecutable(new File(Appium_Node_Path)).withAppiumJS(new File(Appium_JS_Path))
				.withArgument(GeneralServerFlag.SESSION_OVERRIDE).withArgument(GeneralServerFlag.RELAXED_SECURITY)
				.withArgument(GeneralServerFlag.LOG_LEVEL, "info").withLogFile(fileInfo) // enabled
//              .withArgument(GeneralServerFlag.LOG_LEVEL, "error").withLogFile(fileError) //disabled
				.withTimeout(Duration.ofSeconds(60));

		
		//set environment
		HashMap<String, String> environment = new HashMap();
		environment.put("PATH", "/usr/local/bin:" + System.getenv("PATH"));
		appiumServiceBuilder.withEnvironment(environment);
		System.out.println("The environment = " + environment);

		
		// set service
		service = AppiumDriverLocalService.buildService(appiumServiceBuilder);
		
		// start service
		service.start();
		Thread.sleep(40000);
		System.out.println("Is the server running = " + checkIfServerIsRunnning(port));

		
		// get service_url
		service_url = service.getUrl().toString();
		System.out.println("The service_url = " + service_url);

		// stop service
		appiumStop();
		Thread.sleep(10000);
		System.out.println("Is the server running after used appiumStop() = " + checkIfServerIsRunnning(port));
	}

	/*
	*
	* Stop the service
	*/
	public static void appiumStop() {
		service.stop();
	}

	/*
	*
	* check If Server Is Runnning
	*/
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

}
