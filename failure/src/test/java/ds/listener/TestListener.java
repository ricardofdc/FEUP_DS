package ds.listener;

import ds.graph.Graph;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.junit.jupiter.api.Test;

public class TestListener {
    Listener listener = new Listener("test", new Graph());

    @Test
    public void testPositionMessageArrived() throws Exception {
        String message = "{\n" +
                "    \"machineID\": 1,\n" +
                "    \"sensorID\": \"position1\",\n" +
                "    \"sensorType\": \"POSITION\",\n" +
                "    \"values\": {\n" +
                "        \"x\": 3.0,\n" +
                "        \"y\": 1.0\n" +
                "    },\n" +
                "    \"readingTime\": \"31-12-2021 00:30:46.610169\"\n" +
                "}";
        MqttMessage mqttMessage = new MqttMessage(message.getBytes());
        listener.messageArrived("test", mqttMessage);
    }

    @Test
    public void testMachineVelocityMessageArrived() throws Exception {
        String message = "{\n" +
                "    \"machineID\": 1,\n" +
                "    \"sensorID\": machVelocity1,\n" +
                "    \"sensorType\": \"MACHINE_VELOCITY\",\n" +
                "    \"values\": {\n" +
                "        \"velocity\": 0.0\n" +
                "    },\n" +
                "    \"readingTime\": \"31-12-2021 00:30:46.610169\"\n" +
                "}";
        MqttMessage mqttMessage = new MqttMessage(message.getBytes());
        listener.messageArrived("test", mqttMessage);
    }

    @Test
    public void testMachineOrientationMessageArrived() throws Exception {
        String message = "{\n" +
                "    \"machineID\": 1,\n" +
                "    \"sensorID\": \"machOrientation1\",\n" +
                "    \"sensorType\": \"MACHINE_ORIENTATION\",\n" +
                "    \"values\": {\n" +
                "        \"orientation\": 45.0\n" +
                "    },\n" +
                "    \"readingTime\": \"31-12-2021 00:30:46.610169\"\n" +
                "}";
        MqttMessage mqttMessage = new MqttMessage(message.getBytes());
        listener.messageArrived("test", mqttMessage);
    }

    @Test
    public void testTemperatureMessageArrived() throws Exception {
        String message = "{\n" +
                "    \"machineID\": 1,\n" +
                "    \"sensorID\": \"temperature1\",\n" +
                "    \"sensorType\": \"TEMPERATURE\",\n" +
                "    \"values\": {\n" +
                "        \"temperature\": 48.2\n" +
                "    },\n" +
                "    \"readingTime\": \"31-12-2021 00:30:46.610169\"\n" +
                "}";
        MqttMessage mqttMessage = new MqttMessage(message.getBytes());
        listener.messageArrived("test", mqttMessage);
    }

    @Test
    public void testMVibrationMessageArrived() throws Exception {
        String message = "{\n" +
                "    \"machineID\": 1,\n" +
                "    \"sensorID\": \"vibration1\",\n" +
                "    \"sensorType\": \"VIBRATION\",\n" +
                "    \"values\": {\n" +
                "        \"vibration\": 3.2\n" +
                "    },\n" +
                "    \"readingTime\": \"31-12-2021 00:30:46.610169\"\n" +
                "}";
        MqttMessage mqttMessage = new MqttMessage(message.getBytes());
        listener.messageArrived("test", mqttMessage);
    }

    @Test
    public void testProductionSpeedMessageArrived() throws Exception {
        String message = "{\n" +
                "    \"machineID\": 1,\n" +
                "    \"sensorID\": \"prodSpeed1\",\n" +
                "    \"sensorType\": \"PRODUCTION_SPEED\",\n" +
                "    \"values\": {\n" +
                "        \"speed\": 23.5\n" +
                "    },\n" +
                "    \"readingTime\": \"31-12-2021 00:30:46.610169\"\n" +
                "}";
        MqttMessage mqttMessage = new MqttMessage(message.getBytes());
        listener.messageArrived("test", mqttMessage);
    }

    @Test
    public void testEnergyMessageArrived() throws Exception {
        String message = "{\n" +
                "    \"machineID\": 1,\n" +
                "    \"sensorID\": \"energy1\",\n" +
                "    \"sensorType\": \"ENERGY\",\n" +
                "    \"values\": {\n" +
                "        \"energy\": 23.4\n" +
                "    },\n" +
                "    \"readingTime\": \"31-12-2021 00:30:46.610169\"\n" +
                "}";
        MqttMessage mqttMessage = new MqttMessage(message.getBytes());
        listener.messageArrived("test", mqttMessage);
    }

    @Test
    public void testQrCodeInMessageArrived() throws Exception {
        String message = "{\n" +
                "    \"machineID\": 1,\n" +
                "    \"sensorID\": \"qrIn\",\n" +
                "    \"sensorType\": \"QR_CODE\",\n" +
                "    \"values\": {\n" +
                "        \"materialID\": 24,\n" +
                "        \"action\": \"IN\",\n" +
                "        \"defect\": false\n" +
                "    },\n" +
                "    \"readingTime\": \"31-12-2021 00:30:46.610169\"\n" +
                "}";
        MqttMessage mqttMessage = new MqttMessage(message.getBytes());
        listener.messageArrived("test", mqttMessage);
    }

    @Test
    public void testQrCodeOutMessageArrived() throws Exception {
        String message = "{\n" +
                "    \"machineID\": 1,\n" +
                "    \"sensorID\": \"qrOut\",\n" +
                "    \"sensorType\": \"QR_CODE\",\n" +
                "    \"values\": {\n" +
                "        \"materialID\": 24,\n" +
                "        \"action\": \"OUT\",\n" +
                "        \"defect\": true\n" +
                "    },\n" +
                "    \"readingTime\": \"31-12-2021 00:30:46.610169\"\n" +
                "}";
        MqttMessage mqttMessage = new MqttMessage(message.getBytes());
        listener.messageArrived("test", mqttMessage);
    }
}
