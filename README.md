# pubsub-emulator-java
This java project is created to explain how one can use the pubsub emulator on local machine for development. 

## Installing & starting pubsub emulator

To install please execute following commands in your local machine

gcloud components install pubsub-emulator
gcloud components update

To start please execute following command
gcloud beta emulators pubsub start

## Communicating with pubsub emaulator with Java API
In order to communicate with pubsub emulator you have to change your code little bit compared to the cloud pubsub code.

Here is the code you need to setup before calling Admin client

String hostport = "localhost:8085";
ManagedChannel channel = ManagedChannelBuilder.forTarget(hostport).usePlaintext(true).build();

channelProvider = FixedTransportChannelProvider.create(GrpcTransportChannel.create(channel));
		credentialsProvider = NoCredentialsProvider.create();
topicAdminClient = TopicAdminClient
					.create(TopicAdminSettings.newBuilder().setTransportChannelProvider(Constants.channelProvider)
							.setCredentialsProvider(Constants.credentialsProvider).build());
              

To create Topics & subscription look for the code in the class org.sravasti.pubsub.emulator.TopicManage
To publish the messages look for the code in the class org.sravasti.pubsub.emulator.TopicPublisher
To subscribe the messages look for the code in the class org.sravasti.pubsub.emulator.TopicSubscriber
