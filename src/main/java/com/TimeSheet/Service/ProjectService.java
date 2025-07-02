package com.TimeSheet.Service;

import java.util.List;

import com.TimeSheet.DTO.ProjectDTO;
import com.TimeSheet.Entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectService {
    ProjectDTO createProject(ProjectDTO projectDTO);
    List<ProjectDTO> getAllProjects();
    ProjectDTO getProjectById(Long id);
    ProjectDTO getProjectByProjectCode(String code);
    ProjectDTO updateProject(Long id, ProjectDTO dto);
    String deleteProject(Long id);
    ProjectDTO patchStatus(Long id, String status);

    Page<Project> getAllProjects(Pageable pageable);
}
