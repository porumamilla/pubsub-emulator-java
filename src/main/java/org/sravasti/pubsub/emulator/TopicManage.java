package org.sravasti.pubsub.emulator;

import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.cloud.pubsub.v1.SubscriptionAdminSettings;
import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.cloud.pubsub.v1.TopicAdminClient.ListTopicsPagedResponse;
import com.google.cloud.pubsub.v1.TopicAdminSettings;
import com.google.pubsub.v1.ListTopicsRequest;
import com.google.pubsub.v1.ProjectName;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.google.pubsub.v1.ProjectTopicName;
import com.google.pubsub.v1.PushConfig;
import com.google.pubsub.v1.Subscription;
import com.google.pubsub.v1.Topic;

public class TopicManage {

	public Topic createTopic() throws Exception {
		try (TopicAdminClient topicAdminClient = TopicAdminClient
				.create(TopicAdminSettings.newBuilder().setTransportChannelProvider(Constants.channelProvider)
						.setCredentialsProvider(Constants.credentialsProvider).build())) {
			// projectId <= unique project identifier, eg. "my-project-id"
			// topicId <= "my-topic-id"
			ProjectTopicName topicName = ProjectTopicName.of(Constants.projectId, Constants.topicId);
			Topic topic = topicAdminClient.createTopic(topicName);
			return topic;
		}
	}

	public void deleteTopic() throws Exception {
		ProjectTopicName topicName = ProjectTopicName.of(Constants.projectId, Constants.topicId);
		Constants.topicAdminClient.deleteTopic(topicName);
	}

	public void listTopics() throws Exception {

		ListTopicsRequest listTopicsRequest = ListTopicsRequest.newBuilder()
				.setProject(ProjectName.format(Constants.projectId)).build();
		ListTopicsPagedResponse response = Constants.topicAdminClient.listTopics(listTopicsRequest);
		Iterable<Topic> topics = response.iterateAll();
		System.out.println("List of topics are ");
		for (Topic topic : topics) {
			// do something with the topic
			System.out.println("Topic = " + topic.getName());
		}

	}

	public void createSubscription() throws Exception {
		try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create(SubscriptionAdminSettings.newBuilder().setTransportChannelProvider(Constants.channelProvider)
				.setCredentialsProvider(Constants.credentialsProvider).build())) {
			// eg. projectId = "my-test-project", topicId = "my-test-topic"
			ProjectTopicName topicName = ProjectTopicName.of(Constants.projectId, Constants.topicId);
			// eg. subscriptionId = "my-test-subscription"
			ProjectSubscriptionName subscriptionName = ProjectSubscriptionName.of(Constants.projectId,
					Constants.subscriptionId);
			// create a pull subscription with default acknowledgement deadline
			Subscription subscription = subscriptionAdminClient.createSubscription(subscriptionName, topicName,
					PushConfig.getDefaultInstance(), 0);
		}
	}

	public static void main(String[] args) throws Exception {

		TopicManage topicManage = new TopicManage();
		topicManage.createTopic();
		topicManage.listTopics();
		topicManage.deleteTopic();
	}
}
