package com.TimeSheet.ServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.TimeSheet.Exception.ProjectNotFound;
import com.TimeSheet.Exception.StatusAlreadyExists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import com.TimeSheet.DTO.ProjectDTO;
import com.TimeSheet.Entity.Project;
import com.TimeSheet.Exception.ResourceNotFoundException;
import com.TimeSheet.Repository.ProjectRepository;
import com.TimeSheet.Service.ProjectService;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public ProjectDTO createProject(ProjectDTO dto) {

        if (projectRepository.findByProjectCode(dto.getProjectCode()).isPresent()) {
            throw  new StatusAlreadyExists("Project with this code already exists in database, please check again");
        }
        else {
            Project project = projectRepository.save(convertToProject(dto));
            ProjectDTO dto1 = convertToDTO(project);
            return dto1;
        }


    }

    @Override
    public List<ProjectDTO> getAllProjects() {

        List<Project> project = projectRepository.findAll();
        if(project != null){
            List<ProjectDTO> dtoList = project.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            return dtoList;
        }
        else{
            throw (new ProjectNotFound("There are no project found."));
        }

    }

    @Override
    public ProjectDTO getProjectById(Long id) {

        Optional<Project> project = projectRepository.findById(id);
        if(project != null){
            ProjectDTO projectDTO = convertToDTO(project.get());
            return projectDTO;
        }
        else {

                throw  (new ResourceNotFoundException("Project not found with ID: " + id));
        }

    }

    @Override
    public ProjectDTO getProjectByProjectCode(String code) {
        Optional<Project> byProjectCode = projectRepository.findByProjectCode(code);
        if (byProjectCode.isPresent()){
            return convertToDTO(byProjectCode.get());
        }
        else if( !byProjectCode.isPresent()){
            throw new ResourceNotFoundException("Project with this code is not found");
        }
        else{
            throw new InternalError("Some issue with server");
        }
    }

    @Override
    public ProjectDTO updateProject(Long id, ProjectDTO dto) {

        Optional<Project> existingOptional = projectRepository.findById(id);

        if (existingOptional.isPresent()) {
            Project existing = existingOptional.get();
            existing.setName(dto.getName());
            existing.setProjectCode(dto.getProjectCode());
            existing.setDescription(dto.getDescription());
            existing.setStartDate(dto.getStartDate());
            existing.setEndDate(dto.getEndDate());
            existing.setStatus(dto.getStatus());
            existing.setClientName(dto.getClientName());
            existing.setDepartment(dto.getDepartment());

            Project savedProject = projectRepository.save(existing);
            return convertToDTO(savedProject);
        } else {
            throw new ResourceNotFoundException("Project with ID " + id + " not found");
        }
    }


    @Override
    public String deleteProject(Long id) {
        Optional<Project> pro = projectRepository.findById(id);

        if (!pro.isPresent()) {
            return "Project with ID " + id + " not found.";
        } else {
            projectRepository.deleteById(id);
            return "Project deleted successfully.";
        }
    }






    @Override
    public ProjectDTO patchStatus(Long id, String status) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFound("Project with ID " + id + " does not exist"));

        if (project.getStatus().equalsIgnoreCase(status)) {
            throw new StatusAlreadyExists("This project already holds this status");
        }

        project.setStatus(status);
        Project updatedProject = projectRepository.save(project);

        return convertToDTO(updatedProject);
    }


    public ProjectDTO convertToDTO(Project project) {
        ProjectDTO dto = new ProjectDTO();
        dto.setName(project.getName());
        dto.setProjectCode(project.getProjectCode());
        dto.setDescription(project.getDescription());
        dto.setStartDate(project.getStartDate());
        dto.setEndDate(project.getEndDate());
        dto.setStatus(project.getStatus());
        dto.setClientName(project.getClientName());
        dto.setDepartment(project.getDepartment());
        return dto;
    }
    public Project convertToProject(ProjectDTO projectDto) {
        Project project = new Project();
        project.setName(projectDto.getName());
        project.setProjectCode(projectDto.getProjectCode());
        project.setDescription(projectDto.getDescription());
        project.setStartDate(projectDto.getStartDate());
        project.setEndDate(projectDto.getEndDate());
        project.setStatus(projectDto.getStatus());
        project.setClientName(projectDto.getClientName());
        project.setDepartment(projectDto.getDepartment());
        return project;
    }
    public Page<Project> getAllProjects(Pageable pageable) {
        return projectRepository.findAll(pageable);
    }


}

