package com.hfaria.micronotesback.notemanagement.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hfaria.micronotesback.model.Note;
import com.hfaria.micronotesback.notemanagement.exception.NoteException;

public class NoteDTO {

	public String id;
	public String title;
	public String text;
	public String dateCreated;
	public String dateEdited;
	public String ownerId;
	
	public NoteDTO() {
	}
	
	public NoteDTO(Note note) {
	    id = note.getId().toString();
	    title = note.getTitle();
	    text = note.getText();
	    dateCreated = note.getDateCreated().toString();
	    dateEdited = note.getDateEdited().toString();
	    ownerId = note.getOwner().getId().toString();
	    
	}

    public static List<NoteDTO> toDtoList(List<Note> notes) {
        List<NoteDTO> noteDTOs = new ArrayList<NoteDTO>();
        notes.forEach((note) -> {noteDTOs.add(new NoteDTO(note));});
        return noteDTOs;
        
    }
    
    public static Note toNote(NoteDTO dto) throws NoteException{
        if(dto.title == null || dto.title.trim().equals("")
            || dto.text == null || dto.text.trim().equals("")) {
            throw new NoteException("Invalid parameters");
        }
        
        Note note = new Note();
        note.setId(Long.valueOf(dto.id));
        note.setTitle(dto.title);
        note.setText(dto.text);
        note.setDateCreated(new Date(dto.dateCreated));
        note.setDateEdited(new Date(dto.dateEdited));
        note.setOwner(null);

        return note;
    }

}
