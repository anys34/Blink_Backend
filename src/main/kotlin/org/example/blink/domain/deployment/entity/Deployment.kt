package org.example.blink.domain.deployment.entity

import jakarta.persistence.*
import org.example.blink.common.entity.BaseEntity
import org.example.blink.domain.project.entity.Project

@Entity
@Table(name = "deployments")
class Deployment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(nullable = false, length = 50)
    var version: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: DeploymentStatus = DeploymentStatus.PENDING,

    @Column(columnDefinition = "TEXT")
    var dockerfile: String,

    @Column(columnDefinition = "TEXT")
    var buildLog: String? = null,

    @Column(columnDefinition = "TEXT")
    var deploymentLog: String? = null,

    @Column(length = 200)
    var imageName: String? = null,

    @Column(length = 100)
    var containerName: String? = null,

    @Column
    var port: Int? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    var project: Project
) : BaseEntity() {

    fun updateStatus(status: DeploymentStatus): Deployment = apply {
        this.status = status
    }

    fun updateBuildLog(log: String): Deployment = apply {
        this.buildLog = log
    }

    fun updateDeploymentLog(log: String): Deployment = apply {
        this.deploymentLog = log
    }

    fun updateImageName(imageName: String): Deployment = apply {
        this.imageName = imageName
    }

    fun updateContainerInfo(containerName: String, port: Int?): Deployment = apply {
        this.containerName = containerName
        this.port = port
    }

    fun appendBuildLog(additionalLog: String): Deployment = apply {
        this.buildLog = if (this.buildLog.isNullOrEmpty()) {
            additionalLog
        } else {
            "${this.buildLog}\n$additionalLog"
        }
    }

    fun appendDeploymentLog(additionalLog: String): Deployment = apply {
        this.deploymentLog = if (this.deploymentLog.isNullOrEmpty()) {
            additionalLog
        } else {
            "${this.deploymentLog}\n$additionalLog"
        }
    }
} 