package Connection;

import javax.swing.*;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class InternetConnectionChecker implements Runnable {
    private volatile boolean isConnected = false;

    @Override
    public void run() {
        while (true) {
            boolean currentStatus = isInternetAvailable();

            if (currentStatus != isConnected) {
                isConnected = currentStatus;

                SwingUtilities.invokeLater(() -> {
                    if (isConnected) {
                        System.out.println("Internet is connected.");
                    } else {
                        System.out.println("Internet is not connected.");
                    }
                });
            }

            try {
                Thread.sleep(5000); // Check every 5 seconds
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public boolean startChecking() {
        return isInternetAvailable(); // Immediate check
    }

    public void startBackgroundCheck() {
        Thread thread = new Thread(this);
        thread.setDaemon(true);
        thread.start();
    }

    private boolean isInternetAvailable() {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress("8.8.8.8", 53), 1500); // Google Public DNS
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
