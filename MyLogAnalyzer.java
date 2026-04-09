import java.io.*;
import java.util.*;

public class MyLogAnalyzer {

    public static void main(String[] args) {

        String fileName = "logins.txt";

        // HashMap to track failed login counts for users
        HashMap<String, Integer> failedCount = new HashMap<>();

        // This will track the last country and time of successful login for each user
        HashMap<String, String> lastCountry = new HashMap<>();
        HashMap<String, Long> lastTime = new HashMap<>();

        // This will track locked accounts
        HashSet<String> lockedUsers = new HashSet<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;

            while ((line = br.readLine()) != null) {

                if (line.trim().isEmpty()) continue;

                String[] parts = line.split("\\|");

                String timestamp = parts[0].trim();
                String user = parts[1].trim();
                String status = parts[2].trim();
                String country = parts[3].trim();

                long time = parseTime(timestamp);

                // Check if account is locked
                if (lockedUsers.contains(user)) {
                    System.out.println("ALERT: Login attempt blocked. Account is LOCKED for user: " + user);
                    continue;
                }

                // Brute force attack detection
                if (status.equals("FAILED")) {

                    int count = failedCount.getOrDefault(user, 0) + 1;
                    failedCount.put(user, count);

                    if (count >= 3) {
                        System.out.println(" HIGH RISK ALERT: Possible BRUTE FORCE ATTACK detected for user: "
                                + user + " (" + count + " failed attempts)");
                    }

                    if (count == 5) {
                        lockedUsers.add(user);
                        System.out.println(" CRITICAL: Account LOCKED for user: " + user
                                + " due to repeated failed login attempts (Brute Force Attack)");
                    }

                } else {
                    // Reset on success or other events
                    failedCount.put(user, 0);
                }

                // Impossible Travel Detection
                if (status.equals("SUCCESS")) {

                    if (lastCountry.containsKey(user)) {

                        String prevCountry = lastCountry.get(user);
                        long prevTime = lastTime.get(user);

                        long diff = time - prevTime;

                        if (!prevCountry.equals(country) && diff <= 3600000) {
                            System.out.println(" SUSPICIOUS ACTIVITY: IMPOSSIBLE TRAVEL detected for user: "
                                    + user + " (Login from " + prevCountry + " to " + country + " within 1 hour)");
                        }
                    }

                    // Update last login
                    lastCountry.put(user, country);
                    lastTime.put(user, time);
                }
            }

            br.close();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Converting time to milliseconds for easier comparison
    public static long parseTime(String timestamp) {
        try {
            return java.time.LocalDateTime.parse(
                    timestamp.replace(" ", "T")
            ).atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli();
        } catch (Exception e) {
            return 0;
        }
    }
}
