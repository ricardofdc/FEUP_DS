"""
This module creates, sends and tests a negative value data reading in a machine topic
"""
import time
import random
from mqtt_handler.mqtt_handler import MQTTHandler
from print_color import print_color, TerminalColor


def test(mqtt, machine_id, test_number, **kwargs):
    """ Test machine sensor with negative values"""
    test_msg = f'Test #{test_number}: starting negative value reading test on {machine_id}'
    print_color(test_msg, TerminalColor.OKBLUE)

    machine_topic = f"machine/{machine_id}"
    #failure_topic = f"failure/{machine_id}"

    payload = {
        "machineID": random.randint(-100, 0),
        "readingTime": random.randint(-100, 0),
        "values": {
            "temperature": random.randint(-100, 0),
        },
        "sensorID": f'temperature{random.randint(-100, 0)}'
    }

    payload_msg = f'Test #{test_number}: publishing payload {payload} to {machine_topic}'
    print_color(payload_msg, TerminalColor.OKCYAN)

    mqtt.publish(machine_topic, payload)
    time.sleep(kwargs['delay'])

    print_color(
        f'Test #{test_number}: Cant yet verify this result', TerminalColor.WARNING
    )
    return -1


def main():
    """ Launch negative values test """
    mqtt = MQTTHandler(1883, False)
    machine_id = 'machine1'
    # mqtt starts the client in another thread
    mqtt.start()

    # machine1 starts being updated by messages in the background
    mqtt.subscribe(f"machine/{machine_id}")
    mqtt.subscribe(f"failure/{machine_id}")
    # publish a single message to machine_1

    test(mqtt, machine_id, 0, **{'delay': 2})

    # stop mqtt thread in the background
    mqtt.stop()


if __name__ == '__main__':
    main()
