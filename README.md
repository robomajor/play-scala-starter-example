# play-scala-starter-example

This is a simple application on which I'm learning how you use Scala with Play. I'm following tutorial from [Mark Lewis](https://youtu.be/FqMDHsFNlxQ?list=PLLMXbkbDbVt8tBiGc1y69BZdG8at1D7ZF)

## Running

Run this however you want - you can use "sbt run" or "Run Play 2 App" from IntelliJ IDEA - whatever you choose, it'll be fine

And then go to <http://localhost:9000> to see the running web application.

## Controllers

- `HomeController.scala`:

  Shows how to handle simple HTTP requests.

- `AsyncController.scala`:

  Shows how to do asynchronous programming when handling a request.

- `CountController.scala`:

  Shows how to inject a component into a controller and use the component when
  handling requests.

## Components

- `Module.scala`:

  Shows how to use Guice to bind all the components needed by your application.

- `Counter.scala`:

  An example of a component that contains state, in this case a simple counter.

- `ApplicationTimer.scala`:

  An example of a component that starts when the application starts and stops
  when the application stops.

## Filters

- `Filters.scala`:

  Creates the list of HTTP filters used by your application.

- `ExampleFilter.scala`:

  A simple filter that adds a header to every response.
