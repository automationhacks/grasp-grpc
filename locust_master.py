from locust import User, TaskSet, task


class DummyTask(TaskSet):
    @task(1)
    def dummy(self):
        pass


class Dummy(User):
    task_set = DummyTask
