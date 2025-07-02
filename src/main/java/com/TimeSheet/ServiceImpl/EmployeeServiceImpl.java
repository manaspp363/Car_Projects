package com.TimeSheet.ServiceImpl;

import com.TimeSheet.DTO.EmployeeDTO;
import com.TimeSheet.Entity.Employee;
import com.TimeSheet.Exception.ResourceNotFoundException;
import com.TimeSheet.Repository.EmployeeRepository;
import com.TimeSheet.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee dtoToEntity(EmployeeDTO dto) {
        return new Employee(dto.getName(), dto.getEmail(), dto.getNumber(), dto.getDepartment(), dto.getDesignation(), dto.getProjectCode());
    }

    private EmployeeDTO entityToDto(Employee emp) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setName(emp.getName());
        dto.setEmail(emp.getEmail());
        dto.setNumber(emp.getNumber());
        dto.setDepartment(emp.getDepartment());
        dto.setDesignation(emp.getDesignation());
        dto.setProjectCode(emp.getProjectCode());
        return dto;
    }

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO dto) {
        Employee emp = dtoToEntity(dto);
        Employee saved = employeeRepository.save(emp);
        return entityToDto(saved);
    }

    @Override
    public EmployeeDTO getEmployeeById(Long id) {
        Employee emp = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID " + id));
        return entityToDto(emp);
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll().stream().map(this::entityToDto).collect(Collectors.toList());
    }

    @Override
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO dto) {
        Employee emp = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID " + id));

        emp.setName(dto.getName());
        emp.setEmail(dto.getEmail());
        emp.setNumber(dto.getNumber());
        emp.setDepartment(dto.getDepartment());
        emp.setDesignation(dto.getDesignation());
        emp.setProjectCode(dto.getProjectCode());

        return entityToDto(employeeRepository.save(emp));
    }

    @Override
    public String deleteEmployee(Long id) {
        Employee emp = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID " + id));

        employeeRepository.delete(emp);
        return "Employee deleted successfully";
    }
}
