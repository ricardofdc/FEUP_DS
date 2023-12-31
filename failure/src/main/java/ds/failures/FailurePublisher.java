package ds.failures;
import java.util.UUID;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;

import io.github.cdimascio.dotenv.Dotenv;

public class FailurePublisher {
    private final String brokerURI;
    private final String publisherId;
    private MqttClient publisher;
    private final String topic;

    public FailurePublisher(String topic){
        Dotenv dotenv = Dotenv.load();
        this.brokerURI = "tcp://" + dotenv.get("mosquitto_address") + ":1883";
        this.publisherId = UUID.randomUUID().toString();
        this.topic = topic;
    }

    public void init(){
        try {
            System.out.println("Connecting to MQTT Broker at " + this.brokerURI);

            // Connect to Broker
            this.publisher = new MqttClient(this.brokerURI, publisherId);
            this.publisher.connect(this.getMQTTOptions());

        } catch (MqttException e) {
            System.err.println("Error connecting to MQTT Broker at " + brokerURI + " - " + e);
        }
    }

    private MqttConnectOptions getMQTTOptions(){
        MqttConnectOptions mqttOptions = new MqttConnectOptions();
        mqttOptions.setCleanSession(true);
        mqttOptions.setConnectionTimeout(20);
        mqttOptions.setWill(this.publisher.getTopic(this.topic), "Failure publisher disconnected".getBytes(), 2, false);

        return mqttOptions;
    }

    public void publish(String rawMsg, String machineId){
        String machineTopic = this.topic + '/' + machineId;

        try {
            final MqttTopic topicObj = this.publisher.getTopic(machineTopic);

            MqttMessage msg = getMqttMessage(rawMsg);
            msg.setQos(2);
            topicObj.publish(msg);

        } catch (MqttException e) {
            System.err.println("Error publishing to " + machineTopic + " - " + e);
        }
    }

    public void publishUnknowFailure(String rawMsg){
        try {
            final MqttTopic topicObj = this.publisher.getTopic(this.topic);

            MqttMessage msg = getMqttMessage(rawMsg);
            msg.setQos(2);
            topicObj.publish(msg);
            System.out.println(msg.toString());

        } catch (MqttException e) {
            System.err.println("Error publishing to " + this.topic + " - " + e);
        }
    }
    
    private MqttMessage getMqttMessage(String message){
        byte[] payload = message.getBytes();        
        MqttMessage msg = new MqttMessage(payload); 
        msg.setQos(2);
        
        return msg;
    }
}