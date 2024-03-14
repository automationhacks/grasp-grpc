package io.automationhacks.routeguide;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.util.logging.Logger;

public class RouteGuideTestClient {

  private final Logger logger = Logger.getLogger(RouteGuideTestClient.class.getName());
  private final RouteGuideGrpc.RouteGuideBlockingStub blockingStub;

  public RouteGuideTestClient(String host, int port) {
    ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
    blockingStub = RouteGuideGrpc.newBlockingStub(channel);
  }

  public Feature getFeature(Point point) {
    return blockingStub.getFeature(point);
  }
}