package com.mediscreen.ui.unit;

import com.mediscreen.ui.controller.HomeController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith({SpringExtension.class})
@WebMvcTest({HomeController.class})
public class HomeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;

    public HomeControllerTest() {
    }

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @Test
    @Tag("ShowHomePage")
    @DisplayName("When showHomePage request, then display home page")
    public void whenShowHomePageRequest_thenDisplayHomePage() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/", new Object[0])).andExpect(MockMvcResultMatchers.view().name("home")).andExpect(MockMvcResultMatchers.status().isOk());
    }
}