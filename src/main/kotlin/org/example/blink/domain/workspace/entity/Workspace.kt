package org.example.blink.domain.workspace.entity

import jakarta.persistence.*
import org.example.blink.common.entity.BaseEntity
import org.example.blink.domain.project.entity.Project

@Entity
@Table(name = "workspaces")
class Workspace(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(nullable = false, unique = true, length = 100)
    var name: String,

    @Column(length = 500)
    var description: String? = null,

    @OneToMany(mappedBy = "workspace", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val projects: MutableList<Project> = mutableListOf()
) : BaseEntity() {

    fun updateName(name: String): Workspace = apply {
        this.name = name
    }

    fun updateDescription(description: String?): Workspace = apply {
        this.description = description
    }

    fun addProject(project: Project): Workspace = apply {
        projects.add(project)
        project.workspace = this
    }
} 