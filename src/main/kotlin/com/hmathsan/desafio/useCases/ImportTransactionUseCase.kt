package com.hmathsan.desafio.useCases

import com.hmathsan.desafio.entities.Transaction
import com.hmathsan.desafio.entities.TransactionImportHistory
import com.hmathsan.desafio.model.ImportHistory
import com.hmathsan.desafio.repositories.TransactionImportHistoryRepository
import com.hmathsan.desafio.repositories.TransactionRepository
import com.hmathsan.desafio.services.SecurityService
import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.naming.AuthenticationException

@Service
class ImportTransactionUseCase(
    private val transactionRepository: TransactionRepository,
    private val transactionImportHistoryRepository: TransactionImportHistoryRepository,
    private val securityService: SecurityService
) {
    fun processFileAndSaveToDatabase(inputStream: InputStream) : List<ImportHistory> {
        val reader = BufferedReader(InputStreamReader(inputStream))
        val transactions: MutableList<Transaction> = mutableListOf()
        val transactionDate: LocalDateTime

        //2022-01-01T07:30:00
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

        val authenticatedUser = securityService.getAuthenticatedUser()
            ?: throw AuthenticationException("User not authenticated")

        while(reader.ready()) {
            val line = reader.readLine().split(",")

            val bancoOrigem = line[0]
            val agenciaOrigem = line[1]
            val contaOrigem = line[2]
            val bancoDestino = line[3]
            val agenciaDestino = line[4]
            val contaDestino = line[5]
            val valorTransacao = line[6].toDouble()
            val dataHoraTransacao = LocalDateTime.parse(
                line[7].replace("T", " "), formatter
            )

            val transaction = Transaction(
                null,
                bancoOrigem,
                agenciaOrigem,
                contaOrigem,
                bancoDestino,
                agenciaDestino,
                contaDestino,
                valorTransacao,
                dataHoraTransacao
            )

            transactionRepository.save(transaction)
            transactions.add(transaction)
        }

        transactionDate = transactions.first().dataHoraTransacao
        val transactionHistory = TransactionImportHistory(
            null,
            LocalDateTime.now(),
            transactionDate,
            transactions,
            authenticatedUser
        )

        transactionImportHistoryRepository.save(transactionHistory)
        return transactionImportHistoryRepository.findAll()
            .map(TransactionImportHistory::toModel)
    }

    fun getAllImportedTransactionHistory() : List<ImportHistory> {
        return transactionImportHistoryRepository.findAll()
            .map(TransactionImportHistory::toModel)
    }
}