package org.example.blink.domain.project.service

import org.example.blink.common.exception.*
import org.example.blink.domain.project.dto.*
import org.example.blink.domain.project.entity.Project
import org.example.blink.domain.project.repository.ProjectRepository
import org.example.blink.domain.workspace.repository.WorkspaceRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ProjectService(
    private val projectRepository: ProjectRepository,
    private val workspaceRepository: WorkspaceRepository
) {

    @Transactional
    fun createProject(request: CreateProjectRequest): ProjectResponse {
        return request.run {
            // 워크스페이스 존재 확인
            val workspace = workspaceRepository.findByIdOrNull(workspaceId)
                ?: throw WorkspaceNotFoundException("워크스페이스를 찾을 수 없습니다: $workspaceId")
            
            // 프로젝트 이름 중복 체크 (워크스페이스 내에서)
            require(!projectRepository.existsByWorkspaceIdAndName(workspaceId, name)) {
                throw DuplicateProjectNameException("이미 존재하는 프로젝트 이름입니다: $name")
            }
            
            // Dockerfile 유효성 검사 (기본적인 체크)
            dockerfile?.let { dockerfileContent ->
                validateDockerfile(dockerfileContent)
            }
            
            // 프로젝트 생성 및 저장
            Project(
                name = name,
                description = description,
                dockerfile = dockerfile,
                workspace = workspace
            ).let { project ->
                projectRepository.save(project)
            }.let { project ->
                ProjectResponse.from(project)
            }
        }
    }

    fun getProjectById(id: Long): ProjectResponse {
        return projectRepository.findByIdOrNull(id)
            ?.let { project -> ProjectResponse.from(project) }
            ?: throw ProjectNotFoundException("프로젝트를 찾을 수 없습니다: $id")
    }

    fun getProjectDetailById(id: Long): ProjectDetailResponse {
        return projectRepository.findByIdOrNull(id)
            ?.let { project -> ProjectDetailResponse.from(project) }
            ?: throw ProjectNotFoundException("프로젝트를 찾을 수 없습니다: $id")
    }

    fun getProjectsByWorkspaceId(workspaceId: Long): List<ProjectResponse> {
        // 워크스페이스 존재 확인
        workspaceRepository.findByIdOrNull(workspaceId)
            ?: throw WorkspaceNotFoundException("워크스페이스를 찾을 수 없습니다: $workspaceId")
        
        return projectRepository.findByWorkspaceId(workspaceId)
            .map { project -> ProjectResponse.from(project) }
    }

    @Transactional
    fun updateProject(id: Long, request: UpdateProjectRequest): ProjectResponse {
        return projectRepository.findByIdOrNull(id)
            ?.let { project ->
                // 이름이 변경되는 경우 중복 체크 (워크스페이스 내에서)
                if (project.name != request.name && 
                    projectRepository.existsByWorkspaceIdAndName(project.workspace.id, request.name)) {
                    throw DuplicateProjectNameException("이미 존재하는 프로젝트 이름입니다: ${request.name}")
                }
                
                project.apply {
                    updateName(request.name)
                    updateDescription(request.description)
                }
            }
            ?.let { project -> projectRepository.save(project) }
            ?.let { project -> ProjectResponse.from(project) }
            ?: throw ProjectNotFoundException("프로젝트를 찾을 수 없습니다: $id")
    }

    @Transactional
    fun updateDockerfile(id: Long, request: UpdateDockerfileRequest): ProjectResponse {
        return projectRepository.findByIdOrNull(id)
            ?.let { project ->
                // Dockerfile 유효성 검사
                validateDockerfile(request.dockerfile)
                
                project.apply {
                    updateDockerfile(request.dockerfile)
                }
            }
            ?.let { project -> projectRepository.save(project) }
            ?.let { project -> ProjectResponse.from(project) }
            ?: throw ProjectNotFoundException("프로젝트를 찾을 수 없습니다: $id")
    }

    @Transactional
    fun deleteProject(id: Long) {
        projectRepository.findByIdOrNull(id)
            ?.let { project -> projectRepository.delete(project) }
            ?: throw ProjectNotFoundException("프로젝트를 찾을 수 없습니다: $id")
    }

    private fun validateDockerfile(dockerfile: String) {
        // 기본적인 Dockerfile 유효성 검사
        val trimmedDockerfile = dockerfile.trim()
        
        require(trimmedDockerfile.isNotEmpty()) {
            throw InvalidDockerfileException("Dockerfile이 비어있습니다.")
        }
        
        require(trimmedDockerfile.lines().any { line -> 
            line.trim().uppercase().startsWith("FROM")
        }) {
            throw InvalidDockerfileException("Dockerfile에 FROM 명령어가 없습니다.")
        }
    }
} 