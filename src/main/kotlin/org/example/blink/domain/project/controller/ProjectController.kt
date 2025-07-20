package org.example.blink.domain.project.controller

import org.example.blink.common.response.ApiResponse
import org.example.blink.domain.project.dto.*
import org.example.blink.domain.project.service.ProjectService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class ProjectController(
    private val projectService: ProjectService
) : ProjectApi {

    override fun createProject(request: CreateProjectRequest): ResponseEntity<ApiResponse<ProjectResponse>> {
        return projectService.createProject(request)
            .let { project -> ApiResponse.success(project, "프로젝트가 성공적으로 생성되었습니다.") }
            .let { response -> ResponseEntity.status(HttpStatus.CREATED).body(response) }
    }

    override fun getProject(id: Long): ResponseEntity<ApiResponse<ProjectResponse>> {
        return projectService.getProjectById(id)
            .let { project -> ApiResponse.success(project) }
            .let { response -> ResponseEntity.ok(response) }
    }

    override fun getProjectDetail(id: Long): ResponseEntity<ApiResponse<ProjectDetailResponse>> {
        return projectService.getProjectDetailById(id)
            .let { project -> ApiResponse.success(project) }
            .let { response -> ResponseEntity.ok(response) }
    }

    override fun getProjectsByWorkspace(workspaceId: Long): ResponseEntity<ApiResponse<List<ProjectResponse>>> {
        return projectService.getProjectsByWorkspaceId(workspaceId)
            .let { projects -> ApiResponse.success(projects) }
            .let { response -> ResponseEntity.ok(response) }
    }

    override fun updateProject(id: Long, request: UpdateProjectRequest): ResponseEntity<ApiResponse<ProjectResponse>> {
        return projectService.updateProject(id, request)
            .let { project -> ApiResponse.success(project, "프로젝트가 성공적으로 수정되었습니다.") }
            .let { response -> ResponseEntity.ok(response) }
    }

    override fun updateDockerfile(id: Long, request: UpdateDockerfileRequest): ResponseEntity<ApiResponse<ProjectResponse>> {
        return projectService.updateDockerfile(id, request)
            .let { project -> ApiResponse.success(project, "Dockerfile이 성공적으로 수정되었습니다.") }
            .let { response -> ResponseEntity.ok(response) }
    }

    override fun deleteProject(id: Long): ResponseEntity<ApiResponse<Nothing>> {
        projectService.deleteProject(id)
        return ApiResponse.success<Nothing>(message = "프로젝트가 성공적으로 삭제되었습니다.")
            .let { response -> ResponseEntity.status(HttpStatus.NO_CONTENT).body(response) }
    }
} 