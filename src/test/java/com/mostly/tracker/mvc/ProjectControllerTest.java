package com.mostly.tracker.mvc;

import com.mostly.tracker.model.Project;
import com.mostly.tracker.services.ProjectService;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    private Project mockProject() {
        Project mocked = new Project();
        mocked.setId(1L);
        mocked.setEndDate(LocalDate.now());
        mocked.setName("name");
        mocked.setStartDate(NOW.minusDays(5));
        return mocked;
    }
}
