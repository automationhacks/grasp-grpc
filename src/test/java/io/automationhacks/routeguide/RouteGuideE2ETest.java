package io.automationhacks.routeguide;

import static com.google.common.truth.Truth.assertWithMessage;

import org.testng.annotations.Test;

public class RouteGuideE2ETest {

  private static final String HOST = "localhost";
  private static final int PORT = 8980;

  @Test
  public void testGetFeature() {
    System.out.println("Executed testGetFeature()");
    RouteGuideTestClient client = new RouteGuideTestClient(HOST, PORT);

    int latitude = 407838351;
    int longitude = -746143763;

    Point point = Point.newBuilder().setLatitude(latitude).setLongitude(longitude).build();
    Feature response = client.getFeature(point);

    assertWithMessage(
            "Could not find the feature at lat: %s long: %s".formatted(latitude, longitude))
        .that(response.getName())
        .isEqualTo("Patriots Path, Mendham, NJ 07945, USA");
  }
}
