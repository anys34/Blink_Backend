package org.example.blink.domain.workspace.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.example.blink.common.response.ApiResponse as CommonApiResponse
import org.example.blink.domain.workspace.dto.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "Workspace API", description = "워크스페이스 관리 API")
@RequestMapping("/api/v1/workspaces")
interface WorkspaceApi {

    @Operation(summary = "워크스페이스 생성", description = "새로운 워크스페이스를 생성합니다.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "생성 성공"),
            ApiResponse(responseCode = "400", description = "잘못된 요청"),
            ApiResponse(responseCode = "409", description = "워크스페이스 이름 중복")
        ]
    )
    @PostMapping
    fun createWorkspace(
        @Valid @RequestBody request: CreateWorkspaceRequest
    ): ResponseEntity<CommonApiResponse<WorkspaceResponse>>

    @Operation(summary = "워크스페이스 목록 조회", description = "모든 워크스페이스 목록을 조회합니다.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "조회 성공")
        ]
    )
    @GetMapping
    fun getAllWorkspaces(): ResponseEntity<CommonApiResponse<List<WorkspaceResponse>>>

    @Operation(summary = "워크스페이스 조회", description = "ID로 워크스페이스 정보를 조회합니다.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "조회 성공"),
            ApiResponse(responseCode = "404", description = "워크스페이스를 찾을 수 없음")
        ]
    )
    @GetMapping("/{id}")
    fun getWorkspace(
        @Parameter(description = "워크스페이스 ID", required = true)
        @PathVariable id: Long
    ): ResponseEntity<CommonApiResponse<WorkspaceResponse>>

    @Operation(summary = "워크스페이스 상세 조회", description = "ID로 워크스페이스 상세 정보(프로젝트 포함)를 조회합니다.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "조회 성공"),
            ApiResponse(responseCode = "404", description = "워크스페이스를 찾을 수 없음")
        ]
    )
    @GetMapping("/{id}/detail")
    fun getWorkspaceDetail(
        @Parameter(description = "워크스페이스 ID", required = true)
        @PathVariable id: Long
    ): ResponseEntity<CommonApiResponse<WorkspaceDetailResponse>>

    @Operation(summary = "워크스페이스 수정", description = "워크스페이스 정보를 수정합니다.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "수정 성공"),
            ApiResponse(responseCode = "400", description = "잘못된 요청"),
            ApiResponse(responseCode = "404", description = "워크스페이스를 찾을 수 없음"),
            ApiResponse(responseCode = "409", description = "워크스페이스 이름 중복")
        ]
    )
    @PutMapping("/{id}")
    fun updateWorkspace(
        @Parameter(description = "워크스페이스 ID", required = true)
        @PathVariable id: Long,
        @Valid @RequestBody request: UpdateWorkspaceRequest
    ): ResponseEntity<CommonApiResponse<WorkspaceResponse>>

    @Operation(summary = "워크스페이스 삭제", description = "워크스페이스를 삭제합니다.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "204", description = "삭제 성공"),
            ApiResponse(responseCode = "404", description = "워크스페이스를 찾을 수 없음")
        ]
    )
    @DeleteMapping("/{id}")
    fun deleteWorkspace(
        @Parameter(description = "워크스페이스 ID", required = true)
        @PathVariable id: Long
    ): ResponseEntity<CommonApiResponse<Nothing>>
} 