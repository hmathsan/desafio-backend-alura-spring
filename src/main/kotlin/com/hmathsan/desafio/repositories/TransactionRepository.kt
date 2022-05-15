package com.hmathsan.desafio.repositories

import com.hmathsan.desafio.entities.Transaction
import org.springframework.data.jpa.repository.JpaRepository

interface TransactionRepository : JpaRepository<Transaction, String>