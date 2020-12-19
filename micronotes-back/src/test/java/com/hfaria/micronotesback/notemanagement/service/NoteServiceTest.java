package com.hfaria.micronotesback.notemanagement.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.hfaria.micronotesback.model.Note;
import com.hfaria.micronotesback.model.User;
import com.hfaria.micronotesback.notemanagement.exception.NoteException;
import com.hfaria.micronotesback.repository.NoteRepository;

class NoteServiceTest {
    
    
    @InjectMocks
    private NoteService noteService;
    
    @Mock
    private NoteRepository noteRepository;
    
    private User user1;
    private User user2;
    private Note note1;


    @BeforeAll
    static void setUpBeforeClass() throws Exception {
    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {
    }

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        
        
        User user1 = new User();
        user1.setId(1l);
        user1.setEmail("u1@u1.com");
        user1.setFirstName("u1");
        user1.setLastName("u1");
        user1.setEncriptedPassword("uuu1");
        
        User user2 = new User();
        user2.setId(2l);
        user2.setEmail("u2@u2.com");
        user2.setFirstName("u2");
        user2.setLastName("u2");
        user2.setEncriptedPassword("uuu2");

        Note note1 = new Note();
        note1.setId(1l);
        note1.setOwner(user1);
        note1.setTitle("A title");
        note1.setText("A text");
        note1.setDateCreated(new Date());
        note1.setDateEdited(new Date());
        
        
        this.user1 = user1;
        this.user2 = user2;
        this.note1 = note1;
    }

    @AfterEach
    void tearDown() throws Exception {
    }
    
    @Test
    void testgetNoteFromOwnerReturnsTheNoteWhenItBelongsToTheUser() {
        
        Optional<Note> noteOptional = Optional.of(note1);
        
        given(noteRepository.findById(1l)).willReturn(noteOptional);
        
        Note result;
        
        try {
            result = noteService.getNoteFromOwner(1l, user1);
        } catch (Exception e) {
            result = null;
        }
        
        assertNotNull(result);
        assertEquals(result, note1);
        
    }

    @Test
    void testgetNoteFromOwnerReturnsNullWhenItDoesntBelongToTheUser() {
        
        Optional<Note> noteOptional = Optional.of(note1);
        
        given(noteRepository.findById(1l)).willReturn(noteOptional);
        
        assertThrows(NoteException.class, 
                ()->{
            noteService.getNoteFromOwner(1l, user2);
        });
    }

}
