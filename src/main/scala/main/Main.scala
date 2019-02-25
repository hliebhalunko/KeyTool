package main

/**
  * The entry point of the app.
  */

object Main extends App {
  if (args.length == 0) {
    println("No arguments received!")
  } else if (args(0) == "generate-key") {
    println("Generating key...")
    println("Hostname: " + args(1))
    println("Expiration date: " + args(2))
    println(KeyUtil.generateKey(args(1), args(2)))
  } else if (args(0) == "validate-key") {
    println("Validating key " + args(1))
    println(KeyUtil.validateKey(args(1)))
  } else {
    println("Wrong input parameters!")
  }
}
