package org.example.blink.domain.project.entity

import jakarta.persistence.*
import org.example.blink.common.entity.BaseEntity
import org.example.blink.domain.deployment.entity.Deployment
import org.example.blink.domain.environment.entity.Environment
import org.example.blink.domain.workspace.entity.Workspace

@Entity
@Table(name = "projects")
class Project(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(nullable = false, length = 100)
    var name: String,

    @Column(columnDefinition = "TEXT")
    var dockerfile: String? = null,

    @Column(length = 500)
    var description: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id", nullable = false)
    var workspace: Workspace,

    @OneToMany(mappedBy = "project", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val deployments: MutableList<Deployment> = mutableListOf(),

    @OneToMany(mappedBy = "project", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val environments: MutableList<Environment> = mutableListOf()
) : BaseEntity() {

    fun updateName(name: String): Project = apply {
        this.name = name
    }

    fun updateDockerfile(dockerfile: String): Project = apply {
        this.dockerfile = dockerfile
    }

    fun updateDescription(description: String?): Project = apply {
        this.description = description
    }

    fun addDeployment(deployment: Deployment): Project = apply {
        deployments.add(deployment)
        deployment.project = this
    }

    fun addEnvironment(environment: Environment): Project = apply {
        environments.add(environment)
        environment.project = this
    }
} 