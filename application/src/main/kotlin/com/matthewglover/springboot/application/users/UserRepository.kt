package com.matthewglover.springboot.application.users

import com.matthewglover.springboot.application.NewUser
import com.matthewglover.springboot.application.User

interface UserRepository {
  suspend fun all(): List<User>

  suspend fun add(newUser: NewUser): User

  suspend fun get(userId: Int): User?

  suspend fun delete(userId: Int): Int

  suspend fun update(user: User): Int
}
