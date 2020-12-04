package com.time.tracker.mvc;

import com.time.tracker.model.ProjectEntry;
import com.time.tracker.mvc.commands.ProjectEntryCommand;
import com.time.tracker.mvc.filters.ProjectEntrySpecification;
import com.time.tracker.services.ProjectEntryService;
import org.hamcrest.core.Is;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProjectEntryControllerTest extends AbstractControllerTest {

    @MockBean
    private ProjectEntryService service;

    @Test
    public void testGetProjectEntryById() throws Exception {
        // given
        ProjectEntry mocked = mockProjectEntry();
        // when
        when(service.findById(1L)).thenReturn(Optional.of(mocked));
        // expected
        mvc.perform(get("/api/v1/entries/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.timeSpent", Is.is(3.33)))
                .andExpect(jsonPath("$.entryDate", IsEqual.equalTo(mocked.getEntryDate().toString())))
                .andExpect(jsonPath("$.description", IsEqual.equalTo(mocked.getDescription())))
                .andExpect(jsonPath("$.id", IsEqual.equalTo(1)));
        verify(service).findById(1L);
    }

    @Test
    public void testDeleteProjectEntryById() throws Exception {
        // when
        doNothing().when(service).deleteById(1L);
        // expected
        mvc.perform(delete("/api/v1/entries/1"))
                .andExpect(status().is2xxSuccessful());
        verify(service).deleteById(1L);
    }

    @Test
    public void testSearch() throws Exception {
        // given
        ProjectEntry mocked = mockProjectEntry();
        // when
        when(service.search(any(), any())).thenReturn(new PageImpl<>(List.of(mocked)));
        // expected
        mvc.perform(get("/api/v1/entries/search")
                .param("sort", "id")
                .param("page", "0")
                .param("size", "10")
                .param("projectName", "name"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPages", IsEqual.equalTo(1)))
                .andExpect(jsonPath("$.totalElements", IsEqual.equalTo(1)))
                .andExpect(jsonPath("$.last", IsEqual.equalTo(true)))
                .andExpect(jsonPath("$.content.length()", IsEqual.equalTo(1)))
                .andExpect(jsonPath("$.content[0].entryDate", IsEqual.equalTo(mocked.getEntryDate().toString())))
                .andExpect(jsonPath("$.content[0].timeSpent", IsEqual.equalTo(3.33)))
                .andExpect(jsonPath("$.content[0].id", IsEqual.equalTo(1)));

        ArgumentCaptor<ProjectEntrySpecification> specCaptor = ArgumentCaptor.forClass(ProjectEntrySpecification.class);
        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        verify(service).search(specCaptor.capture(), pageableCaptor.capture());

        ProjectEntrySpecification spec = specCaptor.getValue();
        PageRequest pageable = (PageRequest) pageableCaptor.getValue();

        Assertions.assertEquals(10, pageable.getPageSize());
        Assertions.assertEquals(0, pageable.getPageNumber());
        Assertions.assertEquals(spec.getProjectName(), "name");

        verify(service).search(any(), any());
    }

    @Test
    public void testGetAllEntriesByProjectId() throws Exception {
        // given
        ProjectEntry mocked = mockProjectEntry();
        // when
        when(service.getAllEntriesByProjectId(1L)).thenReturn(List.of(mocked));
        // expected
        mvc.perform(get("/api/v1/entries/project/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].timeSpent", Is.is(3.33)))
                .andExpect(jsonPath("$[0].entryDate", IsEqual.equalTo(mocked.getEntryDate().toString())))
                .andExpect(jsonPath("$[0].description", IsEqual.equalTo(mocked.getDescription())))
                .andExpect(jsonPath("$[0].id", IsEqual.equalTo(1)));
        verify(service).getAllEntriesByProjectId(1L);
    }

    @Test
    public void testSave() throws Exception {
        // given
        ProjectEntry mocked = mockProjectEntry();
        ProjectEntryCommand command = new ProjectEntryCommand();
        command.setProjectId(1L);
        command.setTimeSpent(3.33F);
        command.setEntryDate(LocalDate.now().minusDays(10));
        command.setDescription("DESC");
        // when
        when(service.createProjectEntry(any())).thenReturn(mocked);
        // expected
        mvc.perform(put("/api/v1/entries/save")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(command)))
                .andExpect(status().is2xxSuccessful());
        verify(service).createProjectEntry(any());
    }

    private ProjectEntry mockProjectEntry() {
        ProjectEntry mocked = new ProjectEntry();
        mocked.setId(1L);
        mocked.setEntryDate(LocalDate.now());
        mocked.setTimeSpent(3.33f);
        mocked.setDescription("desc");
        return mocked;
    }
}
