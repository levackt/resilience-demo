# resilience-demo
Cape Town Java Meetup demo - Resilience with [Kubernetes](http://kubernetes.io/) and [Hystrix](https://github.com/Netflix/Hystrix)

## Kubernetes setup
If you have kubectl installed and using a working cluster, skip this section.

Bring up a cluster using the official docs for your environment, or a quick dev environment as I did for the demo.

<!-- todo link to docker-machine docs -->
First create a docker-machine, adjust resources as needed;

<!-- todo code tags -->
   `docker-machine create --driver virtualbox --virtualbox-memory 6000 --virtualbox-cpu-count 2 hystrix;`

Then clone [Kubernetes in Docker](https://github.com/vyshane/kid)

And start your cluster

  `./kid up`

## Deploy the Hystrix Dashboard

The dashboard originates from the [Kubeflix samples](https://github.com/fabric8io/kubeflix) from Fabric8, and all apps use the awesome [maven plugin](http://fabric8.io/guide/mavenPlugin.html) to work with Kubernetes.

  `cd hystrix-dashboard`

  `mvn clean install fabric8:json fabric8:apply`

## Deploy the Hello World Hystrix application

  `cd hello-hystrix`

  Deploy to Kubernetes

  `mvn clean install docker:build fabric8:json fabric8:apply`

  or use Jetty to run;

  `mvn clean install jetty:run -Djetty.http.port=8087`

  The following endpoints should be working if running locally, adjust host:port as needed;

   [hello](http://localhost:8087/hello)

   [slow, times out](http://localhost:8087/slow)

   [readiness check for kubernetes](http://localhost:8087/ready)

   [crash, fails every other request](http://localhost:8087/crash)


## Deploy the [Spring Cloud Hystrix](http://cloud.spring.io/spring-cloud-netflix/spring-cloud-netflix.html) application

  `cd spring-cloud-hystrix`

  Deploy to Kubernetes

  `mvn clean install docker:build fabric8:json fabric8:apply`

  or use Spring Boot to run;

  `mvn clean install spring-boot:run -Dspring.profiles.active=dev`

  The following endpoints should be working if running locally, adjust host:port as needed;

   [hello, with fallback when the above Hello service fails](http://localhost:8333/hello)

   [slow, times out with fallback](http://localhost:8333/slow)

   [crash, fails every other request](http://localhost:8333/crash)


## [Camel Hystrix](http://camel.apache.org/hystrix-eip.html)

  `cd camel-hystrix`

  `mvn clean install docker:build fabric8:json fabric8:apply`

  or use Spring boot

  `mvn spring-boot:run`

  The following endpoints should be working if running locally, adjust host:port as needed;

  [demo](http://localhost:8081/demo)

  [delay for 2 seconds](http://localhost8081/demo?delay=2000)

  [fail](http://localhost:8081/demo?fail=true)

  Play around with Hystrix params in the Camel RouteBuilder 'DemoRouter.java'

## Locust

We used [Locust](http://locust.io/) to simulate user behaviour and stress the application, and monitor the changes to the Hystrix dashboards.

The sample Locust project is adapted from [GoogleCloudPlatform's Distributed Load Testing sample](https://github.com/GoogleCloudPlatform/distributed-load-testing-using-kubernetes), the change to the Dockerfile and project layout allows for faster build cycles.

Build the Docker image, or switch back to the image linked above.

  `cd hystrix-locust/docker-image`

  `docker build -t demo/locust-tasks:1.0 .`

Deploy the Locust master replication controller, and service;

  `cd hystrix-locust`

  `kubectl create -f kubernetes-config/locust-master-controller.yaml --validate=false`

  `kubectl create -f kubernetes-config/locust-master-service.yaml --validate=false`


Get the master's service IP;

  `kubectl get svc locust-master`

Get the spring-cloud-hystrix IP, we'll be stress testing this app which in turns relies on the hello-world service, providing fallbacks etc.

  `kubectl get svc locust-master`

Create the Locust worker  

  Update the locust worker config to point to the master, also set the host IP.

  In `hystrix-locust/kubernetes-config/locust-worker-controller.yaml`, update the following accordingly;

  ` - name: LOCUST_MASTER

      key: LOCUST_MASTER
      value: 10.0.0.163 #todo master cluster ip

    - name: TARGET_HOST

      key: TARGET_HOST
      value: http://10.0.0.58 #todo host ip`

Also edit the number of replicas as needed.

Now create the workers
  `kubectl create -f kubernetes-config/locust-worker-controller.yaml --validate=false`

Scale as needed

   `kubectl scale rc locust-worker --replicas=10`  

You can now port-forward to the master and start the load tests, eg

  `kubectl port-forward locust-master-4n2yo 8089:8089`

Open http://localhost:8089 and start swarming.

Whenever you edit the tasks in docker-images/tasks.py, you need to build a new Docker image

  `cd hystrix-locust/docker-image`

  `docker build -t demo/locust-tasks:1.0 .`
