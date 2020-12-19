package com.hfaria.micronotesback.notemanagement.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hfaria.micronotesback.authentication.service.AuthenticationService;
import com.hfaria.micronotesback.model.Note;
import com.hfaria.micronotesback.model.User;
import com.hfaria.micronotesback.notemanagement.dto.NoteDTO;
import com.hfaria.micronotesback.notemanagement.exception.NoteException;
import com.hfaria.micronotesback.repository.NoteRepository;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private AuthenticationService authenticationService;

    public Note createNote(NoteDTO noteDTO, User owner) throws Exception {
        if (!checkIntegrityForCreate(noteDTO)) {
            throw new NoteException("Invalid parameters");
        }
        Note newNote = new Note();
        newNote.setTitle(noteDTO.title);
        newNote.setText(noteDTO.text);
        newNote.setDateCreated(new Date());
        newNote.setDateEdited(new Date());
        newNote.setOwner(owner);

        return noteRepository.save(newNote);
    }

    public List<Note> getUserNotes(User owner) throws Exception {

        List<Note> notes;
        notes = noteRepository.findByOwner(owner).orElse(new ArrayList<Note>());

        return notes;
    }



    private User getCurrentUser() {
        User owner = authenticationService.getCurrentUser()
                .orElseThrow(() -> new IllegalArgumentException("No user loggedin."));
        return owner;
    }

    public Note getNoteFromOwner(Long noteId, User owner) throws NoteException, Exception {
        Note note = noteRepository.findById(noteId).get();
        if (note.getOwner().getId().equals(owner.getId())) {
            return note;
        }
        throw new NoteException("User has no permission to access this note");
    }

    public boolean deleteNoteFromOwner(Long noteId, User owner) throws NoteException, Exception {
        Note note;
        note = noteRepository.findById(noteId).get();
        if (note.getOwner().getId().equals(owner.getId())) {
            noteRepository.deleteById(noteId);
            return true;
        } else {
            throw new NoteException("User has no permission to delete this note");
        }
    }

    public Note updateNote(NoteDTO updatedNoteDTO) throws NoteException, Exception {
        if (!checkIntegrityForUpdate(updatedNoteDTO)) {
            throw new NoteException("Invalid parameters");
        }
        Note note;
        note = noteRepository.findById(Long.parseLong(updatedNoteDTO.id)).get();
        User currentUser = getCurrentUser();

        if (currentUser.equals(note.getOwner())) {
            note.setTitle(updatedNoteDTO.title);
            note.setText(updatedNoteDTO.text);
            note.setDateEdited(new Date());
            noteRepository.save(note);
        } else {
            throw new NoteException("User has no permission to edit this note");
        }
        return note;
    }

    private boolean checkIntegrityForCreate(NoteDTO noteDTO) {
        if (noteDTO == null 
                || noteDTO.text == null 
                || noteDTO.title == null) {
            return false;
        } else if (noteDTO.text.trim().length() == 0 || noteDTO.title.trim().length() == 0) {
            return false;
        }
        return true;
    }
    
    
    private boolean checkIntegrityForUpdate(NoteDTO noteDTO) {
        if (noteDTO == null 
                || noteDTO.text == null 
                || noteDTO.title == null
                || noteDTO.id == null) {
            return false;
        } else if (noteDTO.text.trim().length() == 0 || noteDTO.title.trim().length() == 0) {
            return false;
        }
        return true;
    }

}
