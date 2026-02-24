package com.example.app.data.datasource.task

import arrow.core.Either

interface TaskRemoteDataSource {
  suspend fun fetchTasks(): Either<Throwable, List<TaskDto>>
}