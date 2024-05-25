package io.automationhacks.routeguide.perf;

import static com.google.common.truth.Truth.assertWithMessage;

import com.github.myzhan.locust4j.AbstractTask;
import com.github.myzhan.locust4j.Locust;
import io.automationhacks.routeguide.Feature;
import io.automationhacks.routeguide.Point;
import io.automationhacks.routeguide.RouteGuideTestClient;

public class GetRouteGuideLoadSimulation extends AbstractTask {
  private final int weight;
  String HOST = "localhost";
  int PORT = 8980;
  RouteGuideTestClient client = new RouteGuideTestClient(HOST, PORT);

  public GetRouteGuideLoadSimulation(int weight) {
    this.weight = weight;
  }

  @Override
  public int getWeight() {
    return weight;
  }

  public String getName() {
    return "RouteGuide.GetFeature";
  }

  public void execute() {

    int latitude = 407838351;
    int longitude = -746143763;

    var start = System.nanoTime();
    Point point = Point.newBuilder().setLatitude(latitude).setLongitude(longitude).build();
    Feature response = client.getFeature(point);
    var end = System.nanoTime();

    assertWithMessage(
            "Could not find the feature at lat: %s long: %s".formatted(latitude, longitude))
        .that(response.getName())
        .isEqualTo("Patriots Path, Mendham, NJ 07945, USA");
    Locust.getInstance()
        .recordSuccess("GetFeature", response.getName(), end - start, response.getName().length());
  }
}
