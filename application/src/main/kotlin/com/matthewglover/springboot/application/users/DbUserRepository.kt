package com.matthewglover.springboot.application

import com.matthewglover.springboot.application.users.UserRepository
import io.r2dbc.spi.Row
import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.reactive.awaitSingleOrNull
import org.springframework.r2dbc.core.DatabaseClient

data class NewUser(val name: String, val age: Int)

data class User(val id: Int, val name: String, val age: Int)

sealed class UserRepositoryError(message: String, val target: Any) : RuntimeException(message)

data class UserReadError(val row: Row) : UserRepositoryError("Error reading User", row)

class DbUserRepository(private val databaseClient: DatabaseClient) : UserRepository {

    private fun rowToUser(row: Row): User {
        return row.get("user_id", Integer::class.java)?.let { id ->
            row.get("name", String::class.java)?.let { name ->
                row.get("age", Integer::class.java)?.let { age ->
                    User(id as Int, name, age as Int)
                }
            }
        }
            ?: throw UserReadError(row)
    }

    override suspend fun all(): List<User> =
        databaseClient
            .sql("SELECT user_id, name, age FROM users")
            .map(::rowToUser)
            .all()
            .collectList()
            .awaitSingle()

    override suspend fun add(newUser: NewUser): User =
        databaseClient
            .sql("INSERT INTO users(name, age) VALUES (:name, :age) RETURNING user_id")
            .bind("name", newUser.name)
            .bind("age", newUser.age)
            .map { row ->
                row.get("user_id", Integer::class.java)?.let { userId ->
                    User(id = userId as Int, name = newUser.name, age = newUser.age)
                }
                    ?: throw UserReadError(row)
            }
            .one()
            .awaitSingle()

    override suspend fun get(userId: Int): User? =
        databaseClient
            .sql("SELECT user_id, name, age FROM users WHERE user_id = :userId")
            .bind("userId", userId)
            .map(::rowToUser)
            .one()
            .awaitSingleOrNull()

    override suspend fun delete(userId: Int): Int =
        databaseClient
            .sql("DELETE FROM users WHERE user_id = :userId")
            .bind("userId", userId)
            .fetch()
            .rowsUpdated()
            .awaitSingle()

    override suspend fun update(user: User): Int =
        databaseClient
            .sql("UPDATE users SET name = :name, age = :age WHERE user_id = :userId")
            .bind("userId", user.id)
            .bind("name", user.name)
            .bind("age", user.age)
            .fetch()
            .rowsUpdated()
            .awaitSingle()
}
