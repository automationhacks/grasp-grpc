package io.automationhacks.routeguide.perf;

import static io.automationhacks.routeguide.constants.Constants.LOCUST_MASTER_HOST;
import static io.automationhacks.routeguide.constants.Constants.LOCUST_MASTER_PORT;

import com.github.myzhan.locust4j.Locust;
import io.automationhacks.routeguide.perf.tasks.GetFeatureTask;

public class RouteGuideLoadGen {

  public void getFeatureLoad() {
    var locust = configureLocustMaster();

    locust.run(new GetFeatureTask(1));
  }

  private Locust configureLocustMaster() {
    var locust = Locust.getInstance();
    locust.setMasterHost(LOCUST_MASTER_HOST);
    locust.setMasterPort(LOCUST_MASTER_PORT);
    return locust;
  }

  public static void main(String[] args) {
    new RouteGuideLoadGen().getFeatureLoad();
  }
}
