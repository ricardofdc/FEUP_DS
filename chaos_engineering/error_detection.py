"""
This module POSTs a machine json with the location and the content of the errors
"""

from datetime import datetime


ERROR_LIST = ["Over_Pressure ",
              "Over_Heating.py",
              "Over_Vibration",
              "DDOS_Overload",
              "fake_data_reading.py",
              "incoherent_output_data.py",
              "negative_values.py",
              "deleted_data_reading"]

TIMESTR = datetime.now()


def error_detection(type_of_error, machine_json):
    """ Create function that detects errors """
    for type_of_error in ERROR_LIST:
        logs = open('logs.txt', 'a')
        logs.write("-" * 45 + '\n')
        logs.write(
            type_of_error +
            ' in ' +
            machine_json,
            '\t-> At\t' +
            str(TIMESTR))
