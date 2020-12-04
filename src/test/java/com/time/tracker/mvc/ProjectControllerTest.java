package com.time.tracker.mvc;

import com.time.tracker.model.Project;
import com.time.tracker.mvc.commands.ProjectCommand;
import com.time.tracker.mvc.filters.ProjectSpecification;
import com.time.tracker.services.ProjectService;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProjectControllerTest extends AbstractControllerTest {

    public static final LocalDate NOW = LocalDate.now();

    @MockBean
    private ProjectService service;

    @Test
    public void testGetProjectById() throws Exception {
        // given
        Project mocked = mockProject();
        // when
        when(service.findById(1L)).thenReturn(Optional.of(mocked));
        // expected
        mvc.perform(get("/api/v1/projects/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", IsEqual.equalTo("name")))
                .andExpect(jsonPath("$.startDate", IsEqual.equalTo(mocked.getStartDate().toString())))
                .andExpect(jsonPath("$.endDate", IsEqual.equalTo(mocked.getEndDate().toString())))
                .andExpect(jsonPath("$.id", IsEqual.equalTo(1)));
        verify(service).findById(1L);
    }

    @Test
    public void testDeleteProjectById() throws Exception {
        // when
        doNothing().when(service).deleteById(1L);
        // expected
        mvc.perform(delete("/api/v1/projects/1"))
                .andExpect(status().is2xxSuccessful());
        verify(service).deleteById(1L);
    }

    @Test
    public void testSearch() throws Exception {
        // given
        Project mocked = mockProject();
        // when
        when(service.search(any(), any())).thenReturn(new PageImpl<>(List.of(mocked)));
        // expected
        mvc.perform(get("/api/v1/projects/search")
                .param("sort", "id")
                .param("page", "0")
                .param("size", "10")
                .param("name", "name"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPages", IsEqual.equalTo(1)))
                .andExpect(jsonPath("$.totalElements", IsEqual.equalTo(1)))
                .andExpect(jsonPath("$.last", IsEqual.equalTo(true)))
                .andExpect(jsonPath("$.content.length()", IsEqual.equalTo(1)))
                .andExpect(jsonPath("$.content[0].startDate", IsEqual.equalTo(mocked.getStartDate().toString())))
                .andExpect(jsonPath("$.content[0].endDate", IsEqual.equalTo(mocked.getEndDate().toString())))
                .andExpect(jsonPath("$.content[0].id", IsEqual.equalTo(1)));

        ArgumentCaptor<ProjectSpecification> specCaptor = ArgumentCaptor.forClass(ProjectSpecification.class);
        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        verify(service).search(specCaptor.capture(), pageableCaptor.capture());

        ProjectSpecification spec = specCaptor.getValue();
        PageRequest pageable = (PageRequest) pageableCaptor.getValue();

        Assertions.assertEquals(10, pageable.getPageSize());
        Assertions.assertEquals(0, pageable.getPageNumber());
        Assertions.assertEquals(spec.getName(), "name");

        verify(service).search(any(), any());
    }

    @Test
    public void testSave() throws Exception {
        // given
        Project mocked = mockProject();
        ProjectCommand command = new ProjectCommand();
        command.setName("name");
        command.setStartDate(LocalDate.now().minusDays(30));
        command.setEndDate(LocalDate.now().minusDays(10));
        // when
        when(service.createProject(any())).thenReturn(mocked);
        // expected
        mvc.perform(post("/api/v1/projects/save")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(command)))
                .andExpect(status().is2xxSuccessful());
        verify(service).createProject(any());
    }

    @Test
    public void testUpdate() throws Exception {
        // given
        Project mocked = mockProject();
        ProjectCommand command = new ProjectCommand();
        command.setName("name");
        command.setStartDate(LocalDate.now().minusDays(30));
        command.setEndDate(LocalDate.now().minusDays(10));
        // when
        when(service.updateProject(anyLong(), any())).thenReturn(mocked);
        // expected
        mvc.perform(put("/api/v1/projects/1/update")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(command)))
                .andExpect(status().is2xxSuccessful());
        verify(service).updateProject(anyLong(), any());
    }

    private Project mockProject() {
        Project mocked = new Project();
        mocked.setId(1L);
        mocked.setEndDate(LocalDate.now());
        mocked.setName("name");
        mocked.setStartDate(NOW.minusDays(5));
        return mocked;
    }
}
