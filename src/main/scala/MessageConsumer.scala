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
    val queueName = "testqueue"
    val factory = new ConnectionFactory()
    factory.setHost("localhost")
    val connection = factory.newConnection()
    val channel = connection.createChannel()

    channel.queueDeclare(queueName, false, false, false, null);
    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

    val consumer  = new QueueingConsumer(channel)
    channel.basicConsume(queueName, true, consumer)

    while (true) {
      val delivery = consumer.nextDelivery()
      val message = new String(delivery.getBody())
      System.out.println(" [x] Received '" + message + "'")
    }
  }
}
