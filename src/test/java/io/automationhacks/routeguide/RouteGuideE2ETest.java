package io.automationhacks.routeguide;

import io.automationhacks.routeguide.Point;
import io.automationhacks.routeguide.RouteGuideTestClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class RouteGuideE2ETest {

  private static final String HOST = "localhost";
  private static final int PORT = 8980;

  @Test
  public void testGetFeature() throws Exception {
    System.out.println("Executed testGetFeature()");
    RouteGuideTestClient client = new RouteGuideTestClient(HOST, PORT);
    Point point = Point.newBuilder().setLatitude(409146138).setLongitude(-746198252).build();
    client.getFeature(point);
  }
}