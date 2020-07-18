import boto3
import json
import os
import requests

from datetime import datetime
from dateutil import tz

import logging
logger = logging.getLogger()
logging_level = os.environ.get("log_level", "INFO")
logger.setLevel(logging_level)

global zone
zone = tz.gettz('Europe/Stockholm')

def get_sunset_time(date):
    api = 'https://api.sunrise-sunset.org/json?lat={}&lng={}&date={}&formatted=0'.format(
        os.environ['lat'],
        os.environ['lng'],
        str(date))
    result = requests.get(api)
    content = json.loads(result.content)
    sunset_string = content["results"]["sunset"]
    sunset_time_utc = datetime.strptime(sunset_string, '%Y-%m-%dT%X%z')
    subset_time_se = sunset_time_utc.astimezone(zone)
    return subset_time_se

def get_end_of_day(now):
    return now.replace(
        hour=23,
        minute=59,
        second=59,
        microsecond=999999)

def publish(message):
    boto3.client('iot-data', region_name='eu-west-1').publish(
        topic='tradfri/group/power',
        qos=1,
        payload=json.dumps({"power": str(message)})
    )

def control(event, context):
    now_se = datetime.now().astimezone(zone)
    sunset_time_se = get_sunset_time(now_se.date())
    end_of_day_se = get_end_of_day(now_se)
    logger.info("Sunset time: {}, now: {}, end of day: {}".format(
        str(sunset_time_se), str(now_se), str(end_of_day_se)))

    if sunset_time_se < now_se < end_of_day_se:
        publish('on')
    else:
        publish('off')
