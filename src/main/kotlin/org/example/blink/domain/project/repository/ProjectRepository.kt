package org.example.blink.domain.project.repository

import org.example.blink.domain.project.entity.Project
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ProjectRepository : JpaRepository<Project, Long> {
    
    fun findByWorkspaceIdAndName(workspaceId: Long, name: String): Project?
    
    fun existsByWorkspaceIdAndName(workspaceId: Long, name: String): Boolean
    
    fun findByWorkspaceId(workspaceId: Long): List<Project>
    
    @Query("SELECT p FROM Project p LEFT JOIN FETCH p.deployments WHERE p.id = :id")
    fun findByIdWithDeployments(@Param("id") id: Long): Project?
    
    @Query("SELECT p FROM Project p LEFT JOIN FETCH p.environments WHERE p.id = :id")
    fun findByIdWithEnvironments(@Param("id") id: Long): Project?
} 