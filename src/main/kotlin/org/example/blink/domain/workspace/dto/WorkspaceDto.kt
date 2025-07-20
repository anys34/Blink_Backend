package org.example.blink.domain.workspace.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.example.blink.domain.workspace.entity.Workspace
import java.time.LocalDateTime

data class CreateWorkspaceRequest(
    @field:NotBlank(message = "워크스페이스 이름은 필수입니다.")
    @field:Size(min = 1, max = 100, message = "워크스페이스 이름은 1자 이상 100자 이하여야 합니다.")
    val name: String,

    @field:Size(max = 500, message = "설명은 500자 이하여야 합니다.")
    val description: String? = null
)

data class UpdateWorkspaceRequest(
    @field:NotBlank(message = "워크스페이스 이름은 필수입니다.")
    @field:Size(min = 1, max = 100, message = "워크스페이스 이름은 1자 이상 100자 이하여야 합니다.")
    val name: String,

    @field:Size(max = 500, message = "설명은 500자 이하여야 합니다.")
    val description: String? = null
)

data class WorkspaceResponse(
    val id: Long,
    val name: String,
    val description: String?,
    val projectCount: Int,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        fun from(workspace: Workspace): WorkspaceResponse = with(workspace) {
            WorkspaceResponse(
                id = id,
                name = name,
                description = description,
                projectCount = projects.size,
                createdAt = createdAt,
                updatedAt = updatedAt
            )
        }
    }
}

data class WorkspaceDetailResponse(
    val id: Long,
    val name: String,
    val description: String?,
    val projects: List<WorkspaceProjectResponse>,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        fun from(workspace: Workspace): WorkspaceDetailResponse = with(workspace) {
            WorkspaceDetailResponse(
                id = id,
                name = name,
                description = description,
                projects = projects.map { WorkspaceProjectResponse.from(it) },
                createdAt = createdAt,
                updatedAt = updatedAt
            )
        }
    }
}

data class WorkspaceProjectResponse(
    val id: Long,
    val name: String,
    val description: String?,
    val hasDockerfile: Boolean,
    val deploymentCount: Int,
    val createdAt: LocalDateTime
) {
    companion object {
        fun from(project: org.example.blink.domain.project.entity.Project): WorkspaceProjectResponse = with(project) {
            WorkspaceProjectResponse(
                id = id,
                name = name,
                description = description,
                hasDockerfile = !dockerfile.isNullOrEmpty(),
                deploymentCount = deployments.size,
                createdAt = createdAt
            )
        }
    }
} 