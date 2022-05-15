package com.hmathsan.desafio.repositories

import com.hmathsan.desafio.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User, String> {
    fun findByEmail(email: String): Optional<User>
}