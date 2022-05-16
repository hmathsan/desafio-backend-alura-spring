package com.hmathsan.desafio.entities

import com.hmathsan.desafio.model.ImportHistory
import org.hibernate.Hibernate
import org.hibernate.annotations.GenericGenerator
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotNull
import kotlin.collections.HashSet

@Entity
data class TransactionImportHistory(
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "VARCHAR(255)")
    val id: String?,

    @field:NotNull
    val dataImportacao: LocalDateTime,

    @field:NotNull
    val dataTransacao: LocalDateTime,

    @field:NotNull
    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    val transaction: List<Transaction>,

    @field:NotNull
    @ManyToOne
    val importadoPor: User
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as TransactionImportHistory

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , dataImportacao = $dataImportacao , dataTransacao = $dataTransacao )"
    }

    fun toModel() : ImportHistory {
        return ImportHistory(this.id!!, this.dataTransacao.toLocalDate(), dataImportacao)
    }
}