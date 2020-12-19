package com.hfaria.micronotesback.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hfaria.micronotesback.model.Note;
import com.hfaria.micronotesback.model.User;

public interface NoteRepository extends JpaRepository<Note, Long>{
    
    public Optional<List<Note>> findByOwner(User owner);
    public Optional<Note> findById(Long id);
    public void deleteById(Long id);
    
    
    

}
