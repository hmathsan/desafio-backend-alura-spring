package com.hmathsan.desafio.entities

import com.hmathsan.desafio.model.Roles
import org.hibernate.Hibernate
import org.hibernate.annotations.GenericGenerator
import java.util.*
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotNull

@Entity
@Table(name = "user_entity")
data class User(
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "VARCHAR(255)")
    val id: String?,

    @field:NotNull
    val name: String,

    @field:NotNull
    @field:Email
    val email: String,

    @field:NotNull
    val password: String,

    @field:NotNull
    @field:Enumerated(EnumType.STRING)
    val role: Roles
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as User

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , name = $name , email = $email , role = $role )"
    }
}