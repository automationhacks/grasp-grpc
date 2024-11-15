package io.automationhacks.testing;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import org.jacoco.core.data.ExecutionDataWriter;
import org.jacoco.core.runtime.RemoteControlReader;
import org.jacoco.core.runtime.RemoteControlWriter;

public class JacocoCoverageCollector {
  private static final String COVERAGE_FILE = "build/jacoco/server.exec";
  private static final String JACOCO_HOST = "localhost";
  private static final int JACOCO_PORT = 6300;

  public static void dumpCoverage() {
    try (Socket socket = new Socket(JACOCO_HOST, JACOCO_PORT);
        FileOutputStream localFile = new FileOutputStream(COVERAGE_FILE)) {
      // Create writer for the coverage file
      ExecutionDataWriter fileWriter = new ExecutionDataWriter(localFile);

      // Setup remote connection
      RemoteControlWriter writer = new RemoteControlWriter(socket.getOutputStream());
      RemoteControlReader reader = new RemoteControlReader(socket.getInputStream());

      // Register visitors to handle the data
      reader.setSessionInfoVisitor(fileWriter);
      reader.setExecutionDataVisitor(fileWriter);

      // Send dump command
      writer.visitDumpCommand(true, false);
      reader.read();

      System.out.println("Coverage data collected successfully");
    } catch (IOException e) {
      System.err.println("Failed to collect coverage data: " + e.getMessage());
      e.printStackTrace();
    }
  }
}
