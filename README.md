# SIMPLE-SHELL
Simulation of a Simple Shell in Java

//
commands implemented:
(1) ls
(2) cd
(3) echo
(4) ping
(5) ipconfig (Windows)
(6) ifconfig (Linux y OS X)

cd Command: 
  Simulation of cd command and directory navigation using the class File from Java. 
Home directory is set to be the system property 'user.dir', which returns the path where the class ‘SShell.java’ is located.
Navigation supported to go forward and backward through the same path. 
Also, this cd command can navigate to a new directory if provided an absolute path.

ls Command:
	Lists all the contents of the current directory (managed by cd). 
Supports the options available to change the format of the information displayed.

