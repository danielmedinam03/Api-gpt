package com.gestion_tareas.demo.controller;

import com.gestion_tareas.demo.model.EstadoTarea;
import com.gestion_tareas.demo.model.Tarea;
import com.gestion_tareas.demo.service.TareaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TareaControllerTest {

    @Mock
    private TareaService tareaService;

    @InjectMocks
    private TareaController tareaController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(tareaController).build();
    }

    @Test
    public void testGetAllTareas() throws Exception {
        Tarea tarea1 = new Tarea(1L, "Tarea 1", "Descripción 1", EstadoTarea.PENDIENTE);
        Tarea tarea2 = new Tarea(2L, "Tarea 2", "Descripción 2", EstadoTarea.EN_PROGRESO);

        when(tareaService.getAllTareas()).thenReturn(Arrays.asList(tarea1, tarea2));

        mockMvc.perform(get("/api/tareas")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre", is("Tarea 1")))
                .andExpect(jsonPath("$[1].nombre", is("Tarea 2")));

        verify(tareaService, times(1)).getAllTareas();
        verifyNoMoreInteractions(tareaService);
    }

    @Test
    public void testGetTareaById() throws Exception {
        Tarea tarea = new Tarea(1L, "Tarea 1", "Descripción 1", EstadoTarea.PENDIENTE);

        when(tareaService.getTareaById(anyLong())).thenReturn(Optional.of(tarea));

        mockMvc.perform(get("/api/tareas/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is("Tarea 1")));

        verify(tareaService, times(1)).getTareaById(1L);
        verifyNoMoreInteractions(tareaService);
    }

    @Test
    public void testCreateTarea() throws Exception {
        Tarea tarea = new Tarea(1L, "Nueva Tarea", "Descripción de la nueva tarea", EstadoTarea.PENDIENTE);

        when(tareaService.createTarea(any(Tarea.class))).thenReturn(tarea);

        mockMvc.perform(post("/api/tareas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(tarea)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is("Nueva Tarea")));

        verify(tareaService, times(1)).createTarea(any(Tarea.class));
        verifyNoMoreInteractions(tareaService);
    }

    @Test
    public void testUpdateTarea() throws Exception {
        Tarea tarea = new Tarea(1L, "Tarea Actualizada", "Descripción Actualizada", EstadoTarea.EN_PROGRESO);

        when(tareaService.updateTarea(anyLong(), any(Tarea.class))).thenReturn(tarea);

        mockMvc.perform(put("/api/tareas/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(tarea)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is("Tarea Actualizada")));

        verify(tareaService, times(1)).updateTarea(anyLong(), any(Tarea.class));
        verifyNoMoreInteractions(tareaService);
    }

    @Test
    public void testDeleteTarea() throws Exception {
        doNothing().when(tareaService).deleteTarea(anyLong());

        mockMvc.perform(delete("/api/tareas/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(tareaService, times(1)).deleteTarea(1L);
        verifyNoMoreInteractions(tareaService);
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
