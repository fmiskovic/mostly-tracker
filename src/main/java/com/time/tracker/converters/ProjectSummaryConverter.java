package com.time.tracker.converters;

import com.time.tracker.dto.ProjectSummaryDto;
import com.time.tracker.model.ProjectSummary;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ProjectSummaryConverter implements EntityConverter<ProjectSummary, ProjectSummaryDto> {

    @Override
    public ProjectSummary toEntity(ProjectSummaryDto dto) {
        ProjectSummary entity = new ProjectSummary();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }

    @Override
    public ProjectSummaryDto toDto(ProjectSummary entity) {
        ProjectSummaryDto dto = new ProjectSummaryDto();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }
}
