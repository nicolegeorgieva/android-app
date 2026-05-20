package com.example.app.data.datasource.task

import arrow.core.Either
import com.example.app.data.datasource.model.TaskDto

interface TaskRemoteDataSource {
  suspend fun fetchTasks(): Either<Throwable, List<TaskDto>>
}