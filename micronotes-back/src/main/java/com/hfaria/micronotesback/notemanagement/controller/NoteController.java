package com.hfaria.micronotesback.notemanagement.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hfaria.micronotesback.authentication.service.AuthenticationService;
import com.hfaria.micronotesback.model.Note;
import com.hfaria.micronotesback.model.User;
import com.hfaria.micronotesback.notemanagement.dto.NoteDTO;
import com.hfaria.micronotesback.notemanagement.exception.NoteException;
import com.hfaria.micronotesback.notemanagement.service.NoteService;

@RestController
@RequestMapping(path = "/api/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;
    @Autowired
    private AuthenticationService authenticationService;

    
    
    @PostMapping(path = "/create")
    public ResponseEntity<NoteDTO> createNote(@RequestBody NoteDTO noteDTO) {
        User owner = getCurrentUser();
        
        try {
            Note newNote = noteService.createNote(noteDTO, owner);
            return new ResponseEntity<NoteDTO>(new NoteDTO(newNote),HttpStatus.CREATED);
            
        } catch (NoteException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
        
    }

    
    
    
    
    @GetMapping(path = "/all")
    public ResponseEntity<List<NoteDTO>> getUserNotes() {
        User owner = getCurrentUser();
        List<Note> notes;
        List<NoteDTO> noteDTOs = new ArrayList<NoteDTO>();
        try {
            notes = noteService.getUserNotes(owner);
            noteDTOs=NoteDTO.toDtoList(notes);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
        return new ResponseEntity<List<NoteDTO>>(noteDTOs, HttpStatus.OK);
    }

    
    @GetMapping(path = "/{id}")
    public ResponseEntity<NoteDTO> getNote(@PathVariable @RequestBody Long id) {
        User owner = getCurrentUser();
        
        try {
            Note note = noteService.getNoteFromOwner(id, owner);
            NoteDTO dto = new NoteDTO(note);
            return new ResponseEntity<NoteDTO>(dto, HttpStatus.OK);
            
        } catch (NoteException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e ) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
        
    }

    
    
    
    
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Object> deleteNote(@PathVariable @RequestBody Long id) {
        User owner = getCurrentUser();

        try {
            if (noteService.deleteNoteFromOwner(id, owner)) {
                return new ResponseEntity<Object>(HttpStatus.OK);
            }
        } catch (NoteException e) {
            e.printStackTrace();
            return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Object>(HttpStatus.SERVICE_UNAVAILABLE);
        }
        return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
    }

    
    
    
    
    @PutMapping(path = "/{id}")
    public ResponseEntity<NoteDTO> editNote(@RequestBody NoteDTO editedNoteDTO) {
        
        try {
             NoteDTO updatedNoteDTO = new NoteDTO(noteService.updateNote(editedNoteDTO));
            return new ResponseEntity<NoteDTO>(updatedNoteDTO,HttpStatus.OK);
        } catch (NoteException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
        
    }

    
    
    
    
    private User getCurrentUser() {
        User user = authenticationService.getCurrentUser()
                .orElseThrow(() -> new IllegalArgumentException("No user logged in."));
        return user;
    }

}
