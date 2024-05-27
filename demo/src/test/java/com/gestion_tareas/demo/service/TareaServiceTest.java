package com.gestion_tareas.demo.service;

import com.gestion_tareas.demo.model.EstadoTarea;
import com.gestion_tareas.demo.model.Tarea;
import com.gestion_tareas.demo.repository.TareaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class TareaServiceTest {

    @Mock
    private TareaRepository tareaRepository;

    @InjectMocks
    private TareaService tareaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllTareas() {
        Tarea tarea1 = new Tarea(1L, "Tarea 1", "Descripción 1", EstadoTarea.PENDIENTE);
        Tarea tarea2 = new Tarea(2L, "Tarea 2", "Descripción 2", EstadoTarea.EN_PROGRESO);

        when(tareaRepository.findAll()).thenReturn(Arrays.asList(tarea1, tarea2));

        List<Tarea> result = tareaService.getAllTareas();

        assertEquals(2, result.size());
        verify(tareaRepository, times(1)).findAll();
    }

    @Test
    public void testGetTareaById() {
        Tarea tarea = new Tarea(1L, "Tarea 1", "Descripción 1", EstadoTarea.PENDIENTE);

        when(tareaRepository.findById(anyLong())).thenReturn(Optional.of(tarea));

        Optional<Tarea> result = tareaService.getTareaById(1L);

        assertTrue(result.isPresent());
        assertEquals("Tarea 1", result.get().getNombre());
        verify(tareaRepository, times(1)).findById(1L);
    }

    @Test
    public void testCreateTarea() {
        Tarea tarea = new Tarea(1L, "Nueva Tarea", "Descripción de la nueva tarea", EstadoTarea.PENDIENTE);

        when(tareaRepository.save(any(Tarea.class))).thenReturn(tarea);

        Tarea result = tareaService.createTarea(tarea);

        assertNotNull(result);
        assertEquals("Nueva Tarea", result.getNombre());
        verify(tareaRepository, times(1)).save(any(Tarea.class));
    }

    @Test
    public void testUpdateTarea() {
        Tarea tarea = new Tarea(1L, "Tarea Actualizada", "Descripción Actualizada", EstadoTarea.EN_PROGRESO);

        when(tareaRepository.findById(anyLong())).thenReturn(Optional.of(tarea));
        when(tareaRepository.save(any(Tarea.class))).thenReturn(tarea);

        Tarea result = tareaService.updateTarea(1L, tarea);

        assertNotNull(result);
        assertEquals("Tarea Actualizada", result.getNombre());
        verify(tareaRepository, times(1)).findById(1L);
        verify(tareaRepository, times(1)).save(any(Tarea.class));
    }

    @Test
    public void testDeleteTarea() {
        doNothing().when(tareaRepository).deleteById(anyLong());

        tareaService.deleteTarea(1L);

        verify(tareaRepository, times(1)).deleteById(1L);
    }
}
