package com.mostly.tracker.mvc;

import com.mostly.tracker.model.ProjectSummary;
import com.mostly.tracker.services.ProjectSummaryService;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProjectSummaryControllerTest extends AbstractControllerTest {

    @MockBean
    private ProjectSummaryService service;

    @Test
    public void testGetProjectSummaryByProjectId() throws Exception {
        // given
        ProjectSummary mocked = mockProjectSummary();
        // when
        when(service.getProjectSummaryByProjectId(1L)).thenReturn(mocked);
        // expected
        mvc.perform(get("/api/v1/summary/project/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.averageTimeSpentPerDay", IsEqual.equalTo(22.8275)))
                .andExpect(jsonPath("$.totalDays", IsEqual.equalTo(20)))
                .andExpect(jsonPath("$.totalTimeSpent", IsEqual.equalTo(456.55)))
                .andExpect(jsonPath("$.id", IsEqual.equalTo(1)));
        verify(service).getProjectSummaryByProjectId(1L);
    }

    private ProjectSummary mockProjectSummary() {
        long totalDays = 20L;
        float totalTimeSpent = 456.55f;

        ProjectSummary summary = new ProjectSummary();
        summary.setId(1L);
        summary.setTotalTimeSpent(totalTimeSpent);
        summary.setTotalDays(totalDays);
        summary.setAverageTimeSpentPerDay(totalTimeSpent / totalDays);
        return summary;
    }
}
