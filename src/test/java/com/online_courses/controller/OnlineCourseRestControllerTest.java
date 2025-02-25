package com.online_courses.controller;

import com.online_courses.entity.Instructor;
import com.online_courses.kafka.KafkaProducerService;
import com.online_courses.rest.OnlineCourseRestController;
import com.online_courses.service.OnlineCourseService;
import com.online_courses.service.OnlineCourseServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = OnlineCourseRestController.class)
@AutoConfigureMockMvc(addFilters = false)
@WithMockUser
public class OnlineCourseRestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OnlineCourseService onlineCourseService;
    @MockBean
    private KafkaProducerService kafkaProducerService;

    Instructor mockInstructor = new Instructor("John", "Doe", "john.doe@gmail.com");
    List<Instructor> mockInstructors = new ArrayList<>();

    String exampleInstructorJson = "{\"first_name\":\"John\",\"last_name\":\"Doe\",\"email\":\"john.doe@gmail.com\"}";

    @Test
    public void getInstructorById() throws Exception {
        Mockito.when(onlineCourseService.getInstructorById(Mockito.anyInt())).thenReturn(mockInstructor);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/courses/instructors/1").accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String expected = "{\"id\":0,\"firstName\":\"John\",\"lastName\":\"Doe\",\"email\":\"john.doe@gmail.com\",\"instructorDetail\":null,\"courses\":null}";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void getAllInstructors() throws Exception {
        mockInstructors.add(mockInstructor);

        Mockito.when(onlineCourseService.getAllInstructors()).thenReturn(mockInstructors);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/courses/instructors").accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String expected = "[{\"id\":0,\"firstName\":\"John\",\"lastName\":\"Doe\",\"email\":\"john.doe@gmail.com\",\"instructorDetail\":null,\"courses\":null}]";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void createInstructor() throws Exception {
        OnlineCourseServiceImpl onlineCourseServiceImpl = mock(OnlineCourseServiceImpl.class);
        Mockito.doCallRealMethod().when(onlineCourseServiceImpl).updateInstructor(Mockito.any(Instructor.class));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/courses/instructors")
                .accept(MediaType.APPLICATION_JSON)
                .content(exampleInstructorJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void updateInstructor() throws Exception {
        Mockito.when(onlineCourseService.updateInstructor(Mockito.any(Instructor.class))).thenReturn(mockInstructor);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/api/courses/instructors")
                .accept(MediaType.APPLICATION_JSON)
                .content(exampleInstructorJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void deleteInstructor() throws Exception {
        int id = 0;

        OnlineCourseServiceImpl onlineCourseServiceImpl = mock(OnlineCourseServiceImpl.class);

        Mockito.doCallRealMethod().when(onlineCourseServiceImpl).deleteInstructorById(id);
        Mockito.when(onlineCourseService.getInstructorById(Mockito.anyInt())).thenReturn(mockInstructor);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/api/courses/instructors/"+id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
}
