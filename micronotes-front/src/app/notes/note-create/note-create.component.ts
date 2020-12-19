import { Router } from '@angular/router';
import { FormGroup, FormBuilder, FormControl } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { Note } from '../note';
import { NoteService } from '../note.service';

@Component({
  selector: 'app-note-create',
  templateUrl: './note-create.component.html',
  styleUrls: ['./note-create.component.css']
})
export class NoteCreateComponent implements OnInit {

  createNoteForm: FormGroup;
  note: Note;
  quillEditorStyle= {
    height: '300px',
    backgroundColor: 'white',
    fontSize: '100%'
  }

  constructor(private formBuilder: FormBuilder, private noteService: NoteService, private router: Router) { 

    this.createNoteForm = this.formBuilder.group({
      title: '',
      text: ''
    });
    
    this.note = {
      id: null,
      title: '',
      text: '',
      dateCreated: null,
      dateEdited: null,
      ownerId: null,
    };
  }





  ngOnInit() {
  }

  onSubmit(){
    this.note.title=this.createNoteForm.get('title').value;
    this.note.text=this.createNoteForm.get('text').value;

    if(this.note.title.trim() == '' 
      || this.note.text == null
      || this.note.text.trim() == ''){
      alert("Title and text can't be empty");
      return;
    }


    this.createNote();
  }

  



  createNote(){
    this.noteService.createNote(this.note)
      .toPromise()
      .then((data) =>{
        console.log("note created");
        this.router.navigateByUrl('/notes');})
        .catch((err) =>{
          console.log(err);
          console.log('Create note failed!');
          this.goBack();});
  }
  




  goBack(){
    this.router.navigateByUrl('/notes');
  }


}
