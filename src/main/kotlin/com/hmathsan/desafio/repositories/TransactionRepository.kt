package com.hmathsan.desafio.repositories

import com.hmathsan.desafio.entities.Transaction
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface TransactionRepository : JpaRepository<Transaction, String>