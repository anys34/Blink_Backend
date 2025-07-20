package org.example.blink.domain.project.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.example.blink.common.response.ApiResponse as CommonApiResponse
import org.example.blink.domain.project.dto.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "Project API", description = "프로젝트 관리 API")
@RequestMapping("/api/v1/projects")
interface ProjectApi {

    @Operation(summary = "프로젝트 생성", description = "새로운 프로젝트를 생성합니다.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "생성 성공"),
            ApiResponse(responseCode = "400", description = "잘못된 요청"),
            ApiResponse(responseCode = "404", description = "워크스페이스를 찾을 수 없음"),
            ApiResponse(responseCode = "409", description = "프로젝트 이름 중복")
        ]
    )
    @PostMapping
    fun createProject(
        @Valid @RequestBody request: CreateProjectRequest
    ): ResponseEntity<CommonApiResponse<ProjectResponse>>

    @Operation(summary = "프로젝트 조회", description = "ID로 프로젝트 정보를 조회합니다.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "조회 성공"),
            ApiResponse(responseCode = "404", description = "프로젝트를 찾을 수 없음")
        ]
    )
    @GetMapping("/{id}")
    fun getProject(
        @Parameter(description = "프로젝트 ID", required = true)
        @PathVariable id: Long
    ): ResponseEntity<CommonApiResponse<ProjectResponse>>

    @Operation(summary = "프로젝트 상세 조회", description = "ID로 프로젝트 상세 정보(배포, 환경변수 포함)를 조회합니다.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "조회 성공"),
            ApiResponse(responseCode = "404", description = "프로젝트를 찾을 수 없음")
        ]
    )
    @GetMapping("/{id}/detail")
    fun getProjectDetail(
        @Parameter(description = "프로젝트 ID", required = true)
        @PathVariable id: Long
    ): ResponseEntity<CommonApiResponse<ProjectDetailResponse>>

    @Operation(summary = "워크스페이스별 프로젝트 목록", description = "워크스페이스에 속한 모든 프로젝트를 조회합니다.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "조회 성공"),
            ApiResponse(responseCode = "404", description = "워크스페이스를 찾을 수 없음")
        ]
    )
    @GetMapping("/workspace/{workspaceId}")
    fun getProjectsByWorkspace(
        @Parameter(description = "워크스페이스 ID", required = true)
        @PathVariable workspaceId: Long
    ): ResponseEntity<CommonApiResponse<List<ProjectResponse>>>

    @Operation(summary = "프로젝트 정보 수정", description = "프로젝트 기본 정보를 수정합니다.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "수정 성공"),
            ApiResponse(responseCode = "400", description = "잘못된 요청"),
            ApiResponse(responseCode = "404", description = "프로젝트를 찾을 수 없음"),
            ApiResponse(responseCode = "409", description = "프로젝트 이름 중복")
        ]
    )
    @PutMapping("/{id}")
    fun updateProject(
        @Parameter(description = "프로젝트 ID", required = true)
        @PathVariable id: Long,
        @Valid @RequestBody request: UpdateProjectRequest
    ): ResponseEntity<CommonApiResponse<ProjectResponse>>

    @Operation(summary = "Dockerfile 수정", description = "프로젝트의 Dockerfile을 수정합니다.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "수정 성공"),
            ApiResponse(responseCode = "400", description = "잘못된 Dockerfile"),
            ApiResponse(responseCode = "404", description = "프로젝트를 찾을 수 없음")
        ]
    )
    @PutMapping("/{id}/dockerfile")
    fun updateDockerfile(
        @Parameter(description = "프로젝트 ID", required = true)
        @PathVariable id: Long,
        @Valid @RequestBody request: UpdateDockerfileRequest
    ): ResponseEntity<CommonApiResponse<ProjectResponse>>

    @Operation(summary = "프로젝트 삭제", description = "프로젝트를 삭제합니다.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "204", description = "삭제 성공"),
            ApiResponse(responseCode = "404", description = "프로젝트를 찾을 수 없음")
        ]
    )
    @DeleteMapping("/{id}")
    fun deleteProject(
        @Parameter(description = "프로젝트 ID", required = true)
        @PathVariable id: Long
    ): ResponseEntity<CommonApiResponse<Nothing>>
} 