package com.TimeSheet.Controller;

import java.util.List;

import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.TimeSheet.DTO.ProjectDTO;
import com.TimeSheet.Entity.Project;
import com.TimeSheet.Service.ProjectService;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping
    public ResponseEntity<ProjectDTO> create(@RequestBody ProjectDTO dto) {
        return new ResponseEntity<>(projectService.createProject(dto), HttpStatus.CREATED);
    }

   /* @GetMapping
    public List<ProjectDTO> getAll() {

        return projectService.getAllProjects();
    }*/
    @GetMapping
    public Page<Project> getAllProjects(Pageable pageable) {
        return projectService.getAllProjects(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getProjectById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectDTO> update(@PathVariable Long id, @RequestBody ProjectDTO dto) {
        return ResponseEntity.ok(projectService.updateProject(id, dto));
    }
    @PatchMapping("/{id}")
    public ResponseEntity<ProjectDTO> updateStatus(@PathVariable Long id, @RequestParam String status) {
        ProjectDTO dto = projectService.patchStatus(id, status);
        return new ResponseEntity<>(dto, HttpStatus.ACCEPTED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        String message = projectService.deleteProject(id);
        return new ResponseEntity<>(message, HttpStatus.ACCEPTED);
    }
}
