# play-scala-starter-example

This is a simple application on which I'm learning how you use Scala with Play. I'm following tutorial from [Mark Lewis](https://youtu.be/FqMDHsFNlxQ?list=PLLMXbkbDbVt8tBiGc1y69BZdG8at1D7ZF)

## Running

Run this however you want - you can use "sbt run" or "Run Play 2 App" from IntelliJ IDEA - whatever you choose, it'll be fine.

And then go to <http://localhost:9000> to see the running web application.

## Controllers

- `HomeController.scala`:

  Shows how to handle simple HTTP requests.

- `TaskListOneController.scala`:

  Shows how to do simple login form which might give you access to some resources.

## Models

- `TaskListInMemoryModel.scala`:

  Model of a very simple task list accessible after logging in.

## Filters

- `ExampleFilter.scala`:

  A simple filter that adds a header to every response.
