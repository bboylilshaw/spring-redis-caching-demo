package org.jasonxiao.demo.web.controller;

import org.jasonxiao.demo.AbstractControllerTest;
import org.jasonxiao.demo.model.User;
import org.jasonxiao.demo.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.ArrayList;
import java.util.Collection;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserRestControllerTest extends AbstractControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserRestController userRestController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        super.setUp(userRestController);
    }

    @Test
    public void testGetAllUsers() throws Exception {
        Collection<User> list = getEntityListStubData();

        // Stub the UserService.findAll method return value
        when(userService.findAll()).thenReturn(list);

        mvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Jason")));

        verify(userService, times(1)).findAll();

    }

    @Test
    public void testCreateUser() throws Exception {
        User u = getEntityStubData();
        when(userService.create(any(User.class))).thenReturn(u);

        String requestBody = "{\n" +
                "  \"name\": \"Jason\"\n" +
                "}";
        RequestBuilder request = post("/api/user")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody);

        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));

        verify(userService, times(1)).create(any(User.class));

    }

    @Test
    public void testGetUser() throws Exception {
        when(userService.findOne(anyLong())).thenReturn(getEntityStubData());

        mvc.perform(get("/api/user/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Jason")));

        verify(userService, times(1)).findOne(anyLong());
    }

    @Test
    public void testUpdateUser() throws Exception {
        when(userService.update(anyLong(), any(User.class))).thenReturn(getEntityStubData());

        String jsonRequest = "{\n" +
                "  \"name\": \"updateName\"\n" +
                "}";

        mvc.perform(put("/api/user/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Jason")));

        verify(userService, times(1)).update(anyLong(), any(User.class));
    }

    @Test
    public void testDeleteUser() throws Exception {
        doNothing().when(userService).delete(anyLong());

        mvc.perform(delete("/api/user/1")).andExpect(status().isNoContent());

        verify(userService, times(1)).delete(anyLong());
    }

    private Collection<User> getEntityListStubData() {
        Collection<User> list = new ArrayList<>();
        list.add(getEntityStubData());
        return list;
    }

    private User getEntityStubData() {
        User entity = new User();
        entity.setId(1L);
        entity.setName("Jason");
        return entity;
    }
}
