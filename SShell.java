
package SShell;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Pattern;

/* References
By Alvin Alexander => https://alvinalexander.com/java/edu/pj/pj010016
By MichaelRoytsman => https://github.com/MichaelRoytman/UnixShell
*/

public class SShell {

	public static void main(String[] args) throws java.io.IOException {

		String currDir = "";
		String homeDir = "";
		boolean flagw = true;
		ProcessBuilder homePath = null;
		try {
			 homePath= new ProcessBuilder("cmd.exe", "/c", "pwd");
			
		}catch (Exception e){
			homePath= new ProcessBuilder("pwd");
			flagw = false;
		}
		
		Process homePathProcess = homePath.start();
				
		BufferedReader pathStdInput = new BufferedReader(new InputStreamReader(homePathProcess.getInputStream()));
			
		// read path argument
		currDir = pathStdInput.readLine();
		if(flagw==true) {
			currDir = "C:" + currDir.substring(11);
			
		}
		homeDir = currDir;
		
		System.out.println("Debug");
		System.out.println("Curr dir: " + currDir);


		String commandLine;
		BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

		while (true) {

			if (currDir.equals(homeDir)) {
				System.out.print("Simple_Shell:~ >>");
			} else {
				System.out.print("Simple_Shell:" + currDir + " >>");
			}
			// read what the user entered
			commandLine = console.readLine();

			// if the user entered a return, just loop again
			if (commandLine.equals("")) {
				continue;
			} else if (commandLine.equalsIgnoreCase("exit")) {
				System.out.println("Goodbye");
				System.exit(0);
			}

			// split multiple commands
			String[] multicommands = commandLine.split(Pattern.quote(" ^ "));

			// iterate over all the commands
			for (String commandp : multicommands) {

				System.out.println("Command to exec --> " + commandp + ":");
				// split the string into a string array
				ArrayList<String> parameters = new ArrayList<String>();
				String[] lineSplit = commandp.split(" ");

				for (String word : lineSplit) {
					// System.out.println("word:" + word);
					parameters.add(word);
				}

				String currCommand = parameters.get(0);
				// check if command has been implemented
				if (ShellUtilities.acceptCommand(currCommand)) {

					// cd command
					if (currCommand.equals("cd")) {

						// check if path is provided
						if (parameters.size() != 1) {

							// extract path provided
							String pathArgument = parameters.get(1);

							// check if is an absolute path
							if (pathArgument.startsWith("/")) {
								File checkPath = new File(pathArgument);
								if (checkPath.exists()) {
									System.out.println("path existe: " + checkPath.getAbsolutePath());
									if (pathArgument.startsWith("/..")) {
										currDir = "/";
									} else {
										currDir = checkPath.toString();
									}
								} else {
									System.out.println("Sorry! No such file or directory (/)");
								}
							}

							// check for path return
							if (pathArgument.startsWith("..")) {

								String[] parentSplit = pathArgument.split("/");

								File wDir = new File(currDir);

								for (String word : parentSplit) {

									if (wDir.getAbsolutePath().equals("/")) {
										System.out.println("Warning: 'root' parent reached!");
										break;
									}

									if (word.equals("..")) {
										wDir = wDir.getParentFile();
									} else {
										String actualPath = wDir.getAbsolutePath() + "/" + word;
										wDir = new File(actualPath);
									}
								}

								File checkPath = new File(wDir.getAbsolutePath());
								if (checkPath.exists()) {
									currDir = wDir.toString();
								} else {
									System.out.println("Sorry! No such file or directory");
								}
							}

							// check for dir in current folder
							if (!pathArgument.startsWith("..") && !pathArgument.startsWith("/")) {

								String concatPath = currDir + "/" + pathArgument;
								File checkPath = new File(concatPath);

								if (checkPath.exists()) {
									currDir = checkPath.getAbsolutePath();
								} else {
									System.out.println("Sorry! No such file or directory");
								}
							}
						} else {
							currDir = homeDir;
						}

					}

					// command ls
					if (currCommand.equals("ls")) {
						ProcessBuilder processBuilder = new ProcessBuilder(parameters);

						processBuilder.directory(new File(currDir));
						Process process = processBuilder.start();

						String s = null;

						BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));

						BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

						// read the output from the command
						System.out.println();
						while ((s = stdInput.readLine()) != null) {
							System.out.println(s);
						}

						// read any errors from the attempted command
						while ((s = stdError.readLine()) != null) {
							System.out.println(s);
						}
					}

					// implement echo, ping, ipconfig, ifconfig

					// HERE

					///

					System.out.println("\n------------------------------------------");
					System.out.println();

				} else {
					System.out.println("Command \'" + currCommand + "\' not implemented");
					System.out.println("\n------------------------------------------");
					System.out.println();
				}
			}
		}
	}
}
