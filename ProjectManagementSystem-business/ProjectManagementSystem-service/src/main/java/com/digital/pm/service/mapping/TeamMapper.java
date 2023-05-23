package com.digital.pm.service.mapping;

import com.digital.pm.dto.team.CreateTeamDto;
import com.digital.pm.dto.team.TeamDto;
import com.digital.pm.model.team.Team;

import java.util.List;
import java.util.stream.Collectors;

public class TeamMapper {
    public static Team create(CreateTeamDto createTeamDto) {
        return Team.builder().build();
    }


    public TeamDto map(Team team) {
        return TeamDto.builder().build();
    }

    public List<TeamDto> map(List<Team> list) {
        return list.stream().map(project ->
                        TeamDto.builder().build()).
                collect(Collectors.toList());
    }
}
