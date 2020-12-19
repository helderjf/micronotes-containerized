import { Component, OnInit } from '@angular/core';
import { Note } from '../note';
import { NoteService } from '../note.service';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Location } from '@angular/common';

@Component({
  selector: 'app-note-edit',
  templateUrl: './note-edit.component.html',
  styleUrls: ['./note-edit.component.css']
})
export class NoteEditComponent implements OnInit {

  note: Note;
  id: number;
  editNoteForm: FormGroup; 

  quillEditorStyle= {
    height: '300px',
    backgroundColor: 'white',
    fontSize: '100%'
  }


  constructor(private noteService: NoteService,
               private router:Router, private route: ActivatedRoute, 
               private formBuilder: FormBuilder, 
               private _location: Location) { 
                 
    this.id = +this.route.snapshot.paramMap.get('id');
    
    this.editNoteForm = this.formBuilder.group({
      title: '',
      text: ''
    });

    this.getData();
  }

  ngOnInit() {
  }

  getData(){
    this.noteService.getNote(this.id)
      .toPromise()
      .then((data: Note)=> {
      this.note=data
      this.editNoteForm = this.formBuilder.group({
        title: this.note.title,
        text: this.note.text});})
      .catch((err)=>{
        console.log(err);
        this.goBack();
        alert("Can't process your request");
    });
  }




  onSubmit(){
    this.note.title=this.editNoteForm.get('title').value;
    this.note.text=this.editNoteForm.get('text').value;

    if(this.note.title.trim() == '' 
      || this.note.text == null
      || this.note.text.trim() == ''){
      alert("Title and text can't be empty");
      return;
    }
    this.updateNote();
  }

  
updateNote(){
  this.noteService.editNote(this.note)
  .toPromise()
  .then(()=>{
    console.log("note updated");
    this.router.navigateByUrl('/notes');})
  .catch((err)=>{
    console.log('Update note failed!');
    alert(err);
    alert('Could not process your request');});
}



  goBack(){
    this._location.back();
  }

}
