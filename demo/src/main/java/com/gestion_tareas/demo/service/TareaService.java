package com.gestion_tareas.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gestion_tareas.demo.model.Tarea;
import com.gestion_tareas.demo.repository.TareaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TareaService {
    @Autowired
    private TareaRepository tareaRepository;

    public List<Tarea> getAllTareas() {
        return tareaRepository.findAll();
    }

    public Optional<Tarea> getTareaById(Long id) {
        return tareaRepository.findById(id);
    }

    public Tarea createTarea(Tarea tarea) {
        return tareaRepository.save(tarea);
    }

    public Tarea updateTarea(Long id, Tarea tarea) {
        Optional<Tarea> existingTarea = tareaRepository.findById(id);
        if (existingTarea.isPresent()) {
            Tarea tareaToUpdate = existingTarea.get();
            tareaToUpdate.setNombre(tarea.getNombre());
            tareaToUpdate.setDescripcion(tarea.getDescripcion());
            tareaToUpdate.setEstado(tarea.getEstado());
            return tareaRepository.save(tareaToUpdate);
        } else {
            return null;
        }
    }

    public void deleteTarea(Long id) {
        tareaRepository.deleteById(id);
    }
}
