package org.example.blink.common.exception

sealed class BusinessException(message: String) : RuntimeException(message)

class WorkspaceNotFoundException(message: String) : BusinessException(message)
class ProjectNotFoundException(message: String) : BusinessException(message)
class DeploymentNotFoundException(message: String) : BusinessException(message)
class DuplicateWorkspaceNameException(message: String) : BusinessException(message)
class DuplicateProjectNameException(message: String) : BusinessException(message)
class InvalidDockerfileException(message: String) : BusinessException(message) 