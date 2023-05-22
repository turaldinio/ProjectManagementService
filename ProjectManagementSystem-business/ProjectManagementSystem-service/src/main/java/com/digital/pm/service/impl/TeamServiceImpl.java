package com.digital.pm.service.Impl;

import com.digital.pm.dto.employee.CreateEmployeeDto;
import com.digital.pm.dto.team.TeamDto;
import com.digital.pm.repository.TeamRepository;
import com.digital.pm.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;

    @Override
    public TeamDto addEmployee(CreateEmployeeDto createEmployeeDto) {
        
        return null;
    }

    @Override
    public void delete(Long employeeId) {

    }

    @Override
    public List<TeamDto> getAll() {
        return null;
    }
}
