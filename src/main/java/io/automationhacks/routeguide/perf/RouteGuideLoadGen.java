package io.automationhacks.routeguide.perf;

import com.github.myzhan.locust4j.Locust;
import io.automationhacks.routeguide.perf.tasks.GetFeatureTask;

import static io.automationhacks.routeguide.constants.Constants.*;

public class RouteGuideLoadGen {

  public void getFeatureLoad() {
    var locust = configureLocustMaster();

    locust.run(new GetFeatureTask(1));
  }

  private Locust configureLocustMaster() {
    var locust = Locust.getInstance();

    locust.setMasterHost(LOCUST_MASTER_HOST);
    locust.setMasterPort(LOCUST_MASTER_PORT);
    locust.setMaxRPS(LOCUST_MAX_RPS);

    return locust;
  }

  public static void main(String[] args) {
    new RouteGuideLoadGen().getFeatureLoad();
  }
}
