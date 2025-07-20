package org.example.blink.domain.environment.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.example.blink.domain.environment.entity.Environment
import java.time.LocalDateTime

data class CreateEnvironmentRequest(
    @field:NotBlank(message = "환경변수 키는 필수입니다.")
    @field:Size(min = 1, max = 100, message = "환경변수 키는 1자 이상 100자 이하여야 합니다.")
    val key: String,

    @field:NotBlank(message = "환경변수 값은 필수입니다.")
    val value: String,

    @field:NotNull(message = "프로젝트 ID는 필수입니다.")
    val projectId: Long,

    @field:Size(max = 200, message = "설명은 200자 이하여야 합니다.")
    val description: String? = null,

    val isSecret: Boolean = false
)

data class UpdateEnvironmentRequest(
    @field:NotBlank(message = "환경변수 값은 필수입니다.")
    val value: String,

    @field:Size(max = 200, message = "설명은 200자 이하여야 합니다.")
    val description: String? = null,

    val isSecret: Boolean = false
)

data class EnvironmentResponse(
    val id: Long,
    val key: String,
    val value: String,
    val description: String?,
    val isSecret: Boolean,
    val projectId: Long,
    val projectName: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        fun from(environment: Environment, maskSecret: Boolean = true): EnvironmentResponse = with(environment) {
            EnvironmentResponse(
                id = id,
                key = key,
                value = if (isSecret && maskSecret) "****" else value,
                description = description,
                isSecret = isSecret,
                projectId = project.id,
                projectName = project.name,
                createdAt = createdAt,
                updatedAt = updatedAt
            )
        }
    }
}

data class EnvironmentListResponse(
    val environments: List<EnvironmentResponse>,
    val totalCount: Int
) {
    companion object {
        fun from(environments: List<Environment>, maskSecrets: Boolean = true): EnvironmentListResponse {
            return EnvironmentListResponse(
                environments = environments.map { EnvironmentResponse.from(it, maskSecrets) },
                totalCount = environments.size
            )
        }
    }
} 