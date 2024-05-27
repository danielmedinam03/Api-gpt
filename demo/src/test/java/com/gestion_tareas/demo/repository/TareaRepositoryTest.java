package com.gestion_tareas.demo.repository;

import com.gestion_tareas.demo.model.EstadoTarea;
import com.gestion_tareas.demo.model.Tarea;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class TareaRepositoryTest {

    @Autowired
    private TareaRepository tareaRepository;

    @Test
    public void testSaveAndFindById() {
        Tarea tarea = new Tarea();
        tarea.setNombre("Tarea de prueba");
        tarea.setDescripcion("Descripción de prueba");
        tarea.setEstado(EstadoTarea.PENDIENTE);

        Tarea savedTarea = tareaRepository.save(tarea);

        Optional<Tarea> foundTarea = tareaRepository.findById(savedTarea.getId());

        assertTrue(foundTarea.isPresent());
        assertEquals("Tarea de prueba", foundTarea.get().getNombre());
    }

    @Test
    public void testDeleteById() {
        Tarea tarea = new Tarea();
        tarea.setNombre("Tarea de prueba");
        tarea.setDescripcion("Descripción de prueba");
        tarea.setEstado(EstadoTarea.PENDIENTE);

        Tarea savedTarea = tareaRepository.save(tarea);

        tareaRepository.deleteById(savedTarea.getId());

        Optional<Tarea> foundTarea = tareaRepository.findById(savedTarea.getId());

        assertFalse(foundTarea.isPresent());
    }
}
