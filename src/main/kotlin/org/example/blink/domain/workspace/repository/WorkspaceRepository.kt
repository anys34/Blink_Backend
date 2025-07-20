package org.example.blink.domain.workspace.repository

import org.example.blink.domain.workspace.entity.Workspace
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface WorkspaceRepository : JpaRepository<Workspace, Long> {
    
    fun findByName(name: String): Workspace?
    
    fun existsByName(name: String): Boolean
    
    @Query("SELECT w FROM Workspace w LEFT JOIN FETCH w.projects WHERE w.id = :id")
    fun findByIdWithProjects(@Param("id") id: Long): Workspace?
    
    @Query("SELECT w FROM Workspace w ORDER BY w.createdAt DESC")
    fun findAllOrderByCreatedAtDesc(): List<Workspace>
} 