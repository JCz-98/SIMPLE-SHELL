
package SShell;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
/* References
By Alvin Alexander => https://alvinalexander.com/java/edu/pj/pj010016
By MichaelRoytman => https://github.com/MichaelRoytman/UnixShell
*/

public class SShell {

	public static void main(String[] args) throws java.io.IOException {

		String currDir = "";
		String homeDir = "";
		String dirSeparator = "";
		ArrayList <String > h = new ArrayList<String>();
		boolean flagw = true;
		ProcessBuilder homePath = null;
		int cont=0;

		// whats the OS?
		String wOS = System.getProperty("os.name").toLowerCase();

		if (wOS.startsWith("win")) {
			dirSeparator = "\\";
			System.out.println(dirSeparator + wOS);
			homePath = new ProcessBuilder("cmd.exe", "/c", "pwd");
		}

		if (wOS.startsWith("mac") || wOS.startsWith("nix")) {
			flagw = false;
			homePath = new ProcessBuilder("pwd");
			dirSeparator = "/";
		}

		Process homePathProcess = homePath.start();
		BufferedReader pathStdInput = new BufferedReader(new InputStreamReader(homePathProcess.getInputStream()));

		// read path argument
		currDir = pathStdInput.readLine();
		if (flagw == true)
			currDir = "C:" + currDir.substring(11);

		homeDir = currDir;
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
			h.add(commandLine);
				
			
			

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
							if (pathArgument.startsWith(dirSeparator)) {
								File checkPath = new File(pathArgument);
								if (checkPath.exists()) {
									System.out.println("path existe: " + checkPath.getAbsolutePath());
									if (pathArgument.startsWith("/..")) {
										currDir = dirSeparator;
									} else {
										currDir = checkPath.toString();
									}
								} else {
									System.out.println("Sorry! No such file or directory (/)");
								}
							}
							
							// check for path return
							if (pathArgument.startsWith("..")) {

								String[] parentSplit = pathArgument.split(Pattern.quote(dirSeparator));
								File wDir = new File(currDir);

								for (String word : parentSplit) {

									if (wDir.getAbsolutePath().equals(dirSeparator)) {
										System.out.println("Warning: 'root' parent reached!");
										break;
									}

									if (word.equals("..")) {
										wDir = wDir.getParentFile();
									} else {
										String actualPath = wDir.getAbsolutePath() + dirSeparator + word;
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
							if (!pathArgument.startsWith("..") && !pathArgument.startsWith(dirSeparator)) {

								String concatPath = currDir + dirSeparator + pathArgument;
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
					else if (currCommand.equals("history")){
						

						
						if (h.size() <= 20) {
							for(int a=0;a<h.size();a++) {
								System.out.println((a+1)+ "\t" + h.get(a));
							} 
						}
						else {
							int init = h.size() - 20;
							int cont2 = 1;
							for(int a=init;a<h.size();a++) {
								System.out.println(cont2+ "\t" + h.get(a));
								cont2++;
							} 
						}
						
					}
					else {
						ShellUtilities.runCommand(parameters, currDir);
					}
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
