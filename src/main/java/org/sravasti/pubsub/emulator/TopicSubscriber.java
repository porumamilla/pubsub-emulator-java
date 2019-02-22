package org.sravasti.pubsub.emulator;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.google.pubsub.v1.PubsubMessage;

public class TopicSubscriber {
	private static final BlockingQueue<PubsubMessage> messages = new LinkedBlockingDeque<>();

	  static class MessageReceiverExample implements MessageReceiver {

	    @Override
	    public void receiveMessage(PubsubMessage message, AckReplyConsumer consumer) {
	      messages.offer(message);
	      consumer.ack();
	    }
	  }
	  
	public static void main(String[] args) throws Exception {
	    ProjectSubscriptionName subscriptionName = ProjectSubscriptionName.of(
	        Constants.projectId, Constants.subscriptionId);
	    Subscriber subscriber = null;
	    try {
	      // create a subscriber bound to the asynchronous message receiver
	      subscriber =
	          Subscriber.newBuilder(subscriptionName, new MessageReceiverExample()).setChannelProvider(Constants.channelProvider)
	          .setCredentialsProvider(Constants.credentialsProvider).build();
	      subscriber.startAsync().awaitRunning();
	      // Continue to listen to messages
	      while (true) {
	        PubsubMessage message = messages.take();
	        System.out.println("Message Id: " + message.getMessageId());
	        System.out.println("Publish Time:" + message.getPublishTime());
	        System.out.println("Data: " + message.getData().toStringUtf8());
	      }
	    } finally {
	      if (subscriber != null) {
	        subscriber.stopAsync();
	      }
	    }
	}
}
