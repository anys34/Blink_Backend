package org.example.blink.domain.workspace.service

import org.example.blink.common.exception.DuplicateWorkspaceNameException
import org.example.blink.common.exception.WorkspaceNotFoundException
import org.example.blink.domain.workspace.dto.*
import org.example.blink.domain.workspace.entity.Workspace
import org.example.blink.domain.workspace.repository.WorkspaceRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class WorkspaceService(
    private val workspaceRepository: WorkspaceRepository
) {

    @Transactional
    fun createWorkspace(request: CreateWorkspaceRequest): WorkspaceResponse {
        return request.run {
            // 워크스페이스 이름 중복 체크
            require(!workspaceRepository.existsByName(name)) {
                throw DuplicateWorkspaceNameException("이미 존재하는 워크스페이스 이름입니다: $name")
            }
            
            // 워크스페이스 생성 및 저장
            Workspace(
                name = name,
                description = description
            ).let { workspace ->
                workspaceRepository.save(workspace)
            }.let { workspace ->
                WorkspaceResponse.from(workspace)
            }
        }
    }

    fun getWorkspaceById(id: Long): WorkspaceResponse {
        return workspaceRepository.findByIdOrNull(id)
            ?.let { workspace -> WorkspaceResponse.from(workspace) }
            ?: throw WorkspaceNotFoundException("워크스페이스를 찾을 수 없습니다: $id")
    }

    fun getWorkspaceDetailById(id: Long): WorkspaceDetailResponse {
        return workspaceRepository.findByIdWithProjects(id)
            ?.let { workspace -> WorkspaceDetailResponse.from(workspace) }
            ?: throw WorkspaceNotFoundException("워크스페이스를 찾을 수 없습니다: $id")
    }

    fun getAllWorkspaces(): List<WorkspaceResponse> {
        return workspaceRepository.findAllOrderByCreatedAtDesc()
            .map { workspace -> WorkspaceResponse.from(workspace) }
    }

    @Transactional
    fun updateWorkspace(id: Long, request: UpdateWorkspaceRequest): WorkspaceResponse {
        return workspaceRepository.findByIdOrNull(id)
            ?.let { workspace ->
                // 이름이 변경되는 경우 중복 체크
                if (workspace.name != request.name && workspaceRepository.existsByName(request.name)) {
                    throw DuplicateWorkspaceNameException("이미 존재하는 워크스페이스 이름입니다: ${request.name}")
                }
                
                workspace.apply {
                    updateName(request.name)
                    updateDescription(request.description)
                }
            }
            ?.let { workspace -> workspaceRepository.save(workspace) }
            ?.let { workspace -> WorkspaceResponse.from(workspace) }
            ?: throw WorkspaceNotFoundException("워크스페이스를 찾을 수 없습니다: $id")
    }

    @Transactional
    fun deleteWorkspace(id: Long) {
        workspaceRepository.findByIdOrNull(id)
            ?.let { workspace -> workspaceRepository.delete(workspace) }
            ?: throw WorkspaceNotFoundException("워크스페이스를 찾을 수 없습니다: $id")
    }
} 