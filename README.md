# Java-LogAnalyser
MyLogAnalyzer is a Java program that functions as a security tool simulating a basic SIEM (Security Information and Event Management) functionality. It analyzes login log files to detect suspicious activities such as: Brute force attacks, Impossible travel detection and account lockouts to prevent attackers from gaining unauthorized access

MY LOGIN LOG ANALYZER PROGRAM (JAVA)
Author: Sinethemba Khumalo

HOW TO RUN THE PROGRAM
1. Java 11 installed on your system.
2. Create a folder and create the following two files in the folder:
A. MyLogAnalyzer.java- Write java program
B. logins.txt- data containing users
The logins.txt file must follow this format:
YYYY-MM-DD HH:MM:SS | username | status | country
3. Open Terminal in that folder.
4. Compile the program using the following command:
javac MyLogAnalyzer.java
5. Run the program using the following command:
java MyLogAnalyzer

Example Output
HIGH RISK ALERT: Possible BRUTE FORCE ATTACK detected for user: john (3 failed attempts)

WHAT THE PROGRAM DETECTS:
1. Brute Force Detection: Failed login attempts are tracked per user. In the event a user makes 3
failed attempts an alert: HIGH-RISK is generated, and 5 failed login attempts results in a locked
account: CRITICAL ALERT to prevent a brute force attack from being successful.
2. Account Lockout: A user reaching 5 failed login attempts triggers an account lock.
3. Impossible Travel Detection: A user logging in from different countries within 1 hour is
detected triggering an alert: SUSPICIOUS ACTIVITY.

HOW THE PROGRAM WORKS:
• The program reads a log file line by line.
• The program extracts: Timestamp, Username, Login status (SUCCESS / FAILED) and
Country
• HashMap stores failed login counts and login history
• HashSet stores users with locked accounts

TASK 3
What is ONE thing you would add to this log to make it more useful for detecting hackers?
One important thing I would add to the log is the IP address of the user. This would make the
system more effective in detecting hackers because attackers often use different IP addresses or
VPNs. By tracking IP addresses, it becomes easier to identify unusual login patterns, repeated
attempts from the same source, or connections from known malicious IPs. This would
significantly improve threat detection and response.

FUTURE IMPROVEMENTS
• Integration with SIEM tool
• IP Address capturing for better log analysis
