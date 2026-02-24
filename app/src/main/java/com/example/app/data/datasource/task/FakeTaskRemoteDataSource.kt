package com.example.app.data.datasource.task

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.example.app.data.datastore.SessionStorage
import javax.inject.Inject

class FakeTaskRemoteDataSource @Inject constructor(
  private val sessionStorage: SessionStorage,
) : TaskRemoteDataSource {

  override suspend fun fetchTasks(): Either<Throwable, List<TaskDto>> {
    val session = sessionStorage.get()

    return if (session != null) {
      listOf(
        TaskDto(
          task = "1",
          title = "Go for a walk",
          description = "Reach 9k in 1h",
          colorCode = "#FFA500"
        ),
        TaskDto(
          task = "2",
          title = "Work out",
          description = "Legs day",
          colorCode = "#000080"
        ),
        TaskDto(
          task = "3",
          title = "Buy groceries",
          description = "Tomatoes, cucumbers, cheese",
          colorCode = "#000080"
        ),
      ).right()
    } else {
      IllegalStateException("User not authenticated").left()
    }
  }
}

