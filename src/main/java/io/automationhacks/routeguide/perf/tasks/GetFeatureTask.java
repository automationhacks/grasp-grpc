package io.automationhacks.routeguide.perf.tasks;

import static com.google.common.truth.Truth.assertWithMessage;
import static io.automationhacks.core.TimeUtils.getTime;
import static io.automationhacks.routeguide.constants.Constants.ROUTE_GUIDE_SERVER_HOST;
import static io.automationhacks.routeguide.constants.Constants.ROUTE_GUIDE_SERVER_PORT;

import com.github.myzhan.locust4j.AbstractTask;
import com.github.myzhan.locust4j.Locust;
import io.automationhacks.routeguide.Feature;
import io.automationhacks.routeguide.Point;
import io.automationhacks.routeguide.RouteGuideTestClient;

public class GetFeatureTask extends AbstractTask {
  private final int weight;

  RouteGuideTestClient client =
      new RouteGuideTestClient(ROUTE_GUIDE_SERVER_HOST, ROUTE_GUIDE_SERVER_PORT);

  public GetFeatureTask(int weight) {
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

    Point point = Point.newBuilder().setLatitude(latitude).setLongitude(longitude).build();

    // Record the startTime for the API call
    var startTime = getTime();
    // Add thread safe logic here to make the API call
    Feature response = client.getFeature(point);
    // Record the endTime for the API call
    var endTime = getTime();

    // Do not swallow any exceptions from the API, we should propagate any exceptions to locust
    assertWithMessage(
            "Could not find the feature at lat: %s long: %s".formatted(latitude, longitude))
        .that(response.getName())
        .isEqualTo("Patriots Path, Mendham, NJ 07945, USA");

    // Record success or failure to locust
    long responseTime = endTime - startTime;
    int contentLength = response.getName().length();

    Locust.getInstance()
        .recordSuccess("GetFeature", response.getName(), responseTime, contentLength);
  }
}
