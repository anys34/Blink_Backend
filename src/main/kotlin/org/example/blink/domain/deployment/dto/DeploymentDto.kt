package org.example.blink.domain.deployment.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.example.blink.domain.deployment.entity.Deployment
import org.example.blink.domain.deployment.entity.DeploymentStatus
import java.time.LocalDateTime

data class CreateDeploymentRequest(
    @field:NotBlank(message = "버전은 필수입니다.")
    @field:Size(min = 1, max = 50, message = "버전은 1자 이상 50자 이하여야 합니다.")
    val version: String,

    @field:NotNull(message = "프로젝트 ID는 필수입니다.")
    val projectId: Long,

    val dockerfile: String? = null, // null인 경우 프로젝트의 기본 Dockerfile 사용
    val port: Int? = null
)

data class UpdateDeploymentStatusRequest(
    @field:NotNull(message = "배포 상태는 필수입니다.")
    val status: DeploymentStatus
)

data class DeploymentResponse(
    val id: Long,
    val version: String,
    val status: String,
    val statusDescription: String,
    val projectId: Long,
    val projectName: String,
    val workspaceId: Long,
    val workspaceName: String,
    val imageName: String?,
    val containerName: String?,
    val port: Int?,
    val hasDockerfile: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        fun from(deployment: Deployment): DeploymentResponse = with(deployment) {
            DeploymentResponse(
                id = id,
                version = version,
                status = status.name,
                statusDescription = status.description,
                projectId = project.id,
                projectName = project.name,
                workspaceId = project.workspace.id,
                workspaceName = project.workspace.name,
                imageName = imageName,
                containerName = containerName,
                port = port,
                hasDockerfile = !dockerfile.isNullOrEmpty(),
                createdAt = createdAt,
                updatedAt = updatedAt
            )
        }
    }
}

data class DeploymentDetailResponse(
    val id: Long,
    val version: String,
    val status: String,
    val statusDescription: String,
    val dockerfile: String?,
    val buildLog: String?,
    val deploymentLog: String?,
    val imageName: String?,
    val containerName: String?,
    val port: Int?,
    val projectId: Long,
    val projectName: String,
    val workspaceId: Long,
    val workspaceName: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        fun from(deployment: Deployment): DeploymentDetailResponse = with(deployment) {
            DeploymentDetailResponse(
                id = id,
                version = version,
                status = status.name,
                statusDescription = status.description,
                dockerfile = dockerfile,
                buildLog = buildLog,
                deploymentLog = deploymentLog,
                imageName = imageName,
                containerName = containerName,
                port = port,
                projectId = project.id,
                projectName = project.name,
                workspaceId = project.workspace.id,
                workspaceName = project.workspace.name,
                createdAt = createdAt,
                updatedAt = updatedAt
            )
        }
    }
}

data class DeploymentLogResponse(
    val deploymentId: Long,
    val version: String,
    val buildLog: String?,
    val deploymentLog: String?,
    val lastUpdated: LocalDateTime
) {
    companion object {
        fun from(deployment: Deployment): DeploymentLogResponse = with(deployment) {
            DeploymentLogResponse(
                deploymentId = id,
                version = version,
                buildLog = buildLog,
                deploymentLog = deploymentLog,
                lastUpdated = updatedAt
            )
        }
    }
}

data class DeploymentStatusListResponse(
    val deployments: List<DeploymentStatusInfo>
)

data class DeploymentStatusInfo(
    val id: Long,
    val version: String,
    val status: String,
    val statusDescription: String,
    val projectName: String,
    val lastUpdated: LocalDateTime
) {
    companion object {
        fun from(deployment: Deployment): DeploymentStatusInfo = with(deployment) {
            DeploymentStatusInfo(
                id = id,
                version = version,
                status = status.name,
                statusDescription = status.description,
                projectName = project.name,
                lastUpdated = updatedAt
            )
        }
    }
} 