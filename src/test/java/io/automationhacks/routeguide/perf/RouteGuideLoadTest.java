package io.automationhacks.routeguide.perf;

import com.github.myzhan.locust4j.Locust;

public class RouteGuideLoadTest {

  public void getFeatureLoad() {
    var locust = Locust.getInstance();
    locust.setMasterHost("127.0.0.1");
    locust.setMasterPort(5557);

    locust.run(new GetRouteGuideLoadSimulation(20));
  }

  public static void main(String[] args) {
    new RouteGuideLoadTest().getFeatureLoad();
  }
}
