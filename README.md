# grasp-grpc

## Load test

1. Start locust master

```commandline
locust -f locust_master.py --master --master-bind-host=127.0.0.1 --master-bind-port=5557
```

```commandline
/Users/gauravsingh/virtualenvs/grasp-grpc-xrv6ks68/lib/python3.11/site-packages/locust/util/deprecation.py:13: DeprecationWarning: Usage of User.task_set is deprecated since version 1.0. Set the tasks attribute instead (tasks = [DummyTask])
  warnings.warn(
[2024-05-25 22:24:11,494] Gauravs-Laptop/INFO/locust.main: Starting web interface at http://0.0.0.0:8089
[2024-05-25 22:24:11,498] Gauravs-Laptop/INFO/locust.main: Starting Locust 2.28.0
```

2. Ensure all relevant proto files are generated `./gradlew installDist`
3. Start gRPC server

```commandline
./build/install/grasp-grpc/bin/route-guide-server
```

```commandline
SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
SLF4J: Defaulting to no-operation (NOP) logger implementation
SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
May 25, 2024 10:39:06 PM io.automationhacks.routeguide.RouteGuideServer start
INFO: Server started, listening on 8980
```

4. Start locust slave by running the main function with locust simulator

https://github.com/automationhacks/grasp-grpc/blob/e41740a4b3544289e12ac5c51485923aeb73786d/src/test/java/io/automationhacks/routeguide/perf/RouteGuideLoadTest.java#L15

```java
public static void main(String[] args) {
    new RouteGuideLoadTest().getFeatureLoad();
  }
```