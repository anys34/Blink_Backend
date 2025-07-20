package org.example.blink.domain.workspace.controller

import org.example.blink.common.response.ApiResponse
import org.example.blink.domain.workspace.dto.*
import org.example.blink.domain.workspace.service.WorkspaceService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class WorkspaceController(
    private val workspaceService: WorkspaceService
) : WorkspaceApi {

    override fun createWorkspace(request: CreateWorkspaceRequest): ResponseEntity<ApiResponse<WorkspaceResponse>> {
        return workspaceService.createWorkspace(request)
            .let { workspace -> ApiResponse.success(workspace, "워크스페이스가 성공적으로 생성되었습니다.") }
            .let { response -> ResponseEntity.status(HttpStatus.CREATED).body(response) }
    }

    override fun getAllWorkspaces(): ResponseEntity<ApiResponse<List<WorkspaceResponse>>> {
        return workspaceService.getAllWorkspaces()
            .let { workspaces -> ApiResponse.success(workspaces) }
            .let { response -> ResponseEntity.ok(response) }
    }

    override fun getWorkspace(id: Long): ResponseEntity<ApiResponse<WorkspaceResponse>> {
        return workspaceService.getWorkspaceById(id)
            .let { workspace -> ApiResponse.success(workspace) }
            .let { response -> ResponseEntity.ok(response) }
    }

    override fun getWorkspaceDetail(id: Long): ResponseEntity<ApiResponse<WorkspaceDetailResponse>> {
        return workspaceService.getWorkspaceDetailById(id)
            .let { workspace -> ApiResponse.success(workspace) }
            .let { response -> ResponseEntity.ok(response) }
    }

    override fun updateWorkspace(id: Long, request: UpdateWorkspaceRequest): ResponseEntity<ApiResponse<WorkspaceResponse>> {
        return workspaceService.updateWorkspace(id, request)
            .let { workspace -> ApiResponse.success(workspace, "워크스페이스가 성공적으로 수정되었습니다.") }
            .let { response -> ResponseEntity.ok(response) }
    }

    override fun deleteWorkspace(id: Long): ResponseEntity<ApiResponse<Nothing>> {
        workspaceService.deleteWorkspace(id)
        return ApiResponse.success<Nothing>(message = "워크스페이스가 성공적으로 삭제되었습니다.")
            .let { response -> ResponseEntity.status(HttpStatus.NO_CONTENT).body(response) }
    }
} 