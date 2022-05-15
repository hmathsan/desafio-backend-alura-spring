package com.hmathsan.desafio.repositories

import com.hmathsan.desafio.entities.TransactionImportHistory
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface TransactionImportHistoryRepository : JpaRepository<TransactionImportHistory, String>