package com.digital.pm.service.mapping;

import com.digital.pm.dto.team.CreateTeamDto;
import com.digital.pm.dto.team.TeamDto;
import com.digital.pm.model.employee.Employee;
import com.digital.pm.model.team.Team;

import java.util.List;
import java.util.stream.Collectors;

public class TeamMapper {
    public  Team create(CreateTeamDto createTeamDto) {
        return Team.builder().projectId(createTeamDto.getProjectId()).
                employeeId(createTeamDto.getEmployeeId()).
                role(createTeamDto.getRole()).
                build();
    }


    public TeamDto map(Team team) {
        return TeamDto.builder().
                employeeId(team.getEmployeeId()).
                projectId(team.getProjectId()).
                role(team.getRole()).
                build();
    }

    public List<TeamDto> map(List<Team> list) {
        return list.stream().map(project ->
                        TeamDto.builder().build()).
                collect(Collectors.toList());
    }
}
