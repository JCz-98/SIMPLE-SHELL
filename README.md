# SIMPLE-SHELL
Simulation of a Simple Shell in Java

commands implemented: (1) ls (2) cd (3) echo (4) ping (5) ipconfig (Windows) (6) ifconfig (Linux y OS X) (7) history
(8) ! -> (command in history)

cd Command:
Simulation of cd command and directory navigation using the class File from Java. Home directory is set to be the the path where the class ‘SShell.java’ is located and running. Navigation supported to go forward and backward through the same path. Also, this cd command can navigate to a new directory if provided an absolute path.

ls Command:
Lists all the contents of the current directory (managed by cd). Supports the options available to change the format of the information displayed.

echo Command:
Prints a string of the arguments provided.

ping Command:
Tests the reachability of a host on an Internet Protocol network

ipconfig (Windows):
Displays all current TCP/IP network configuration values and refresh Dynamic Host Configuration Protocol and Domain Name System settings

ifconfig (Linux y OS X):
Displays the current configuration of the network interfaces on your system.

history:
Displays the list (up to 20 entries) of commands the user has input so far.

!number or #:
Retrieves the command specified by the index from the history and runs it.

