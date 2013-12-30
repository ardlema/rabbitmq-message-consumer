package ardlema.rabbitmqmessageconsumer

import com.rabbitmq.client.{QueueingConsumer, ConnectionFactory}

object MessageConsumer {
  def main(args: Array[String]) {
    val userName = "guest"
    val password = "guest"
    val hostName = "localhost"
    val portNumber = 5672
    val exchangeName = "testChannel"
    val routingKey = "routingKey"
    val factory = new ConnectionFactory()
    factory.setUsername(userName)
    factory.setPassword(password)
    //factory.setVirtualHost(virtualHost)
    factory.setHost(hostName)
    factory.setPort(portNumber)
    val conn = factory.newConnection()
    println("Connection created!!!!!!")

    val channel = conn.createChannel()
    channel.exchangeDeclare(exchangeName, "direct", true)
    val queueName = channel.queueDeclare().getQueue()
    channel.queueBind(queueName, exchangeName, routingKey)

    println("Waiting for messages...")

    val consumer  = new QueueingConsumer(channel)
    channel.basicConsume(queueName, true, consumer)

    while (true) {
      val delivery = consumer.nextDelivery()
      val message = new String(delivery.getBody())
      System.out.println(" [x] Received '" + message + "'")
    }
  }
}
