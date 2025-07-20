package org.example.blink.domain.environment.entity

import jakarta.persistence.*
import org.example.blink.common.entity.BaseEntity
import org.example.blink.domain.project.entity.Project

@Entity
@Table(
    name = "environments",
    uniqueConstraints = [UniqueConstraint(columnNames = ["project_id", "key"])]
)
class Environment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(nullable = false, length = 100, name = "`key`")
    var key: String,

    @Column(columnDefinition = "TEXT")
    var value: String,

    @Column(length = 200)
    var description: String? = null,

    @Column(nullable = false)
    var isSecret: Boolean = false,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    var project: Project
) : BaseEntity() {

    fun updateValue(value: String): Environment = apply {
        this.value = value
    }

    fun updateDescription(description: String?): Environment = apply {
        this.description = description
    }

    fun updateSecret(isSecret: Boolean): Environment = apply {
        this.isSecret = isSecret
    }
} 