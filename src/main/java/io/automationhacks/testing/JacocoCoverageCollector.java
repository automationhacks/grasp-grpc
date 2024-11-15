package io.automationhacks.testing;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;

import org.jacoco.core.data.ExecutionDataWriter;
import org.jacoco.core.runtime.RemoteControlReader;
import org.jacoco.core.runtime.RemoteControlWriter;

public class JacocoCoverageCollector {
  private static final String COVERAGE_FILE = "build/jacoco/server.exec";
  private static final String JACOCO_HOST = "localhost";
  private static final int JACOCO_PORT = 6300;
  private static final int TIMEOUT_MS = 1000;

  public static void dumpCoverage() {
    File coverageFile = new File(COVERAGE_FILE);
    File coverageDir = coverageFile.getParentFile();

    // Ensure directory exists
    if (!coverageDir.exists() && !coverageDir.mkdirs()) {
      throw new RuntimeException("Failed to create directory: " + coverageDir.getAbsolutePath());
    }

    try (Socket socket = new Socket(JACOCO_HOST, JACOCO_PORT)) {
      System.out.println("Connected to JaCoCo agent at " + JACOCO_HOST + ":" + JACOCO_PORT);

      // Set socket timeout
      socket.setSoTimeout(TIMEOUT_MS);

      try (FileOutputStream localFile = new FileOutputStream(coverageFile, true)) {
        System.out.println("Writing coverage data to: " + coverageFile.getAbsolutePath());

        ExecutionDataWriter fileWriter = new ExecutionDataWriter(localFile);
        RemoteControlWriter writer = new RemoteControlWriter(socket.getOutputStream());
        RemoteControlReader reader = new RemoteControlReader(socket.getInputStream());

        reader.setSessionInfoVisitor(fileWriter);
        reader.setExecutionDataVisitor(fileWriter);

        // Send dump command
        writer.visitDumpCommand(true, false);

        try {
          reader.read();
        } catch (SocketTimeoutException e) {
          throw new IOException("Timeout waiting for coverage data from agent", e);
        }

        System.out.println("Coverage data collected successfully");

        // Verify file was created and has content
        if (!coverageFile.exists() || coverageFile.length() == 0) {
          throw new IOException("Coverage file was not created or is empty: " + coverageFile.getAbsolutePath());
        }
      }
    } catch (IOException e) {
      String error = "Failed to collect coverage data: " + e.getMessage();
      System.err.println(error);
      e.printStackTrace();
      throw new RuntimeException(error, e);
    }
  }
}
