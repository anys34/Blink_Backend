package org.example.blink.domain.project.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.example.blink.domain.project.entity.Project
import java.time.LocalDateTime

data class CreateProjectRequest(
    @field:NotBlank(message = "프로젝트 이름은 필수입니다.")
    @field:Size(min = 1, max = 100, message = "프로젝트 이름은 1자 이상 100자 이하여야 합니다.")
    val name: String,

    @field:NotNull(message = "워크스페이스 ID는 필수입니다.")
    val workspaceId: Long,

    @field:Size(max = 500, message = "설명은 500자 이하여야 합니다.")
    val description: String? = null,

    val dockerfile: String? = null
)

data class UpdateProjectRequest(
    @field:NotBlank(message = "프로젝트 이름은 필수입니다.")
    @field:Size(min = 1, max = 100, message = "프로젝트 이름은 1자 이상 100자 이하여야 합니다.")
    val name: String,

    @field:Size(max = 500, message = "설명은 500자 이하여야 합니다.")
    val description: String? = null
)

data class UpdateDockerfileRequest(
    @field:NotBlank(message = "Dockerfile 내용은 필수입니다.")
    val dockerfile: String
)

data class ProjectResponse(
    val id: Long,
    val name: String,
    val description: String?,
    val workspaceId: Long,
    val workspaceName: String,
    val hasDockerfile: Boolean,
    val deploymentCount: Int,
    val environmentCount: Int,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        fun from(project: Project): ProjectResponse = with(project) {
            ProjectResponse(
                id = id,
                name = name,
                description = description,
                workspaceId = workspace.id,
                workspaceName = workspace.name,
                hasDockerfile = !dockerfile.isNullOrEmpty(),
                deploymentCount = deployments.size,
                environmentCount = environments.size,
                createdAt = createdAt,
                updatedAt = updatedAt
            )
        }
    }
}

data class ProjectDetailResponse(
    val id: Long,
    val name: String,
    val description: String?,
    val dockerfile: String?,
    val workspaceId: Long,
    val workspaceName: String,
    val deployments: List<ProjectDeploymentResponse>,
    val environments: List<ProjectEnvironmentResponse>,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        fun from(project: Project): ProjectDetailResponse = with(project) {
            ProjectDetailResponse(
                id = id,
                name = name,
                description = description,
                dockerfile = dockerfile,
                workspaceId = workspace.id,
                workspaceName = workspace.name,
                deployments = deployments.map { ProjectDeploymentResponse.from(it) },
                environments = environments.map { ProjectEnvironmentResponse.from(it) },
                createdAt = createdAt,
                updatedAt = updatedAt
            )
        }
    }
}

data class ProjectDeploymentResponse(
    val id: Long,
    val version: String,
    val status: String,
    val imageName: String?,
    val containerName: String?,
    val port: Int?,
    val createdAt: LocalDateTime
) {
    companion object {
        fun from(deployment: org.example.blink.domain.deployment.entity.Deployment): ProjectDeploymentResponse = with(deployment) {
            ProjectDeploymentResponse(
                id = id,
                version = version,
                status = status.name,
                imageName = imageName,
                containerName = containerName,
                port = port,
                createdAt = createdAt
            )
        }
    }
}

data class ProjectEnvironmentResponse(
    val id: Long,
    val key: String,
    val value: String,
    val description: String?,
    val isSecret: Boolean,
    val createdAt: LocalDateTime
) {
    companion object {
        fun from(environment: org.example.blink.domain.environment.entity.Environment): ProjectEnvironmentResponse = with(environment) {
            ProjectEnvironmentResponse(
                id = id,
                key = key,
                value = if (isSecret) "****" else value, // 시크릿 값은 마스킹
                description = description,
                isSecret = isSecret,
                createdAt = createdAt
            )
        }
    }
} 