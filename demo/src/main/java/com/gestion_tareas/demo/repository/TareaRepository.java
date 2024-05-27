package com.gestion_tareas.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.gestion_tareas.demo.model.Tarea;

@Repository
public interface TareaRepository extends JpaRepository<Tarea, Long> {
}
