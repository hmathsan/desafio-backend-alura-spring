package com.hmathsan.desafio.entities

import com.sun.istack.NotNull
import org.hibernate.Hibernate
import org.hibernate.annotations.GenericGenerator
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
data class Transaction (
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "VARCHAR(255)")
    val id: String?,
    @field:NotNull
    val bancoOrigem: String,
    @field:NotNull
    val agenciaOrigem: String,
    @field:NotNull
    val contaOrigem: String,
    @field:NotNull
    val bancoDestino: String,
    @field:NotNull
    val agenciaDestino: String,
    @field:NotNull
    val contaDestino: String,
    @field:NotNull
    val valorTransacao: Double,
    @field:NotNull
    val dataHoraTransacao: LocalDateTime,
    @field:NotNull
    @ManyToOne
    val importadoPor: User
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Transaction

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    override fun toString(): String {
        return "Transaction(id=$id, bancoOrigem='$bancoOrigem', agenciaOrigem='$agenciaOrigem', contaOrigem='$contaOrigem', bancoDestino='$bancoDestino', agenciaDestino='$agenciaDestino', contaDestino='$contaDestino', valorTransacao=$valorTransacao, dataHoraTransacao=$dataHoraTransacao)"
    }
}