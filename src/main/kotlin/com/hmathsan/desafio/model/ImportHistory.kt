package com.hmathsan.desafio.model

import com.hmathsan.desafio.entities.TransactionImportHistory
import java.time.LocalDate
import java.time.LocalDateTime

data class ImportHistory(
    val historyId: String,
    val dataTrasacao: LocalDate,
    val dataImportacao: LocalDateTime
)