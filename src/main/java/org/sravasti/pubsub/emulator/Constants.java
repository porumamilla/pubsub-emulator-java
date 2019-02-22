package org.sravasti.pubsub.emulator;

import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.core.NoCredentialsProvider;
import com.google.api.gax.grpc.GrpcTransportChannel;
import com.google.api.gax.rpc.FixedTransportChannelProvider;
import com.google.api.gax.rpc.TransportChannelProvider;
import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.cloud.pubsub.v1.TopicAdminSettings;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class Constants {
	public static final String projectId = "springmlproject";
	public static final String topicId = "test";
	public static final String subscriptionId = "testSubscription";

	public static TransportChannelProvider channelProvider;
	public static CredentialsProvider credentialsProvider;
	public static TopicAdminClient topicAdminClient;
	static {
		String hostport = "localhost:8085";
		ManagedChannel channel = ManagedChannelBuilder.forTarget(hostport).usePlaintext(true).build();
		channelProvider = FixedTransportChannelProvider.create(GrpcTransportChannel.create(channel));
		credentialsProvider = NoCredentialsProvider.create();
		try {
			topicAdminClient = TopicAdminClient
					.create(TopicAdminSettings.newBuilder().setTransportChannelProvider(Constants.channelProvider)
							.setCredentialsProvider(Constants.credentialsProvider).build());
		} catch (Exception error) {
			error.printStackTrace();
		}

	}
}
