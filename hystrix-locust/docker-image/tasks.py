import random

from locust import HttpLocust, TaskSet, task


class HystrixTaskSet(TaskSet):
    @task(10)
    def hello(self):
        self.client.get('/hello', name='Hello')

    @task(1)
    def crash(self):
        self.client.get('/crash', name='Crash')

    @task(1)
    def slow(self):
        self.client.get('/slow', name='Slow service')

    @task(1)
    def stores(self):
        self.client.get('/stores', name='Stores')


class HystrixLocust(HttpLocust):
    task_set = HystrixTaskSet
    min_wait = 1000
    max_wait = 1000
