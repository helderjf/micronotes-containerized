import { Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { Note } from '../note';
import { NoteService } from '../note.service';

@Component({
  selector: 'app-note-dashboard',
  templateUrl: './note-dashboard.component.html',
  styleUrls: ['./note-dashboard.component.css']
})
export class NoteDashboardComponent implements OnInit {

  notesArray: Array<Note>

  quillEditorStyle= {
    fontSize: '100%',
    // border:'none',
    // backgroundColor: '#e8f2fc',
    borderRadius: '10px',
    borderColor: '#c8def4',
    marginBottom: '25px'
  }




  constructor(private noteService: NoteService, private router: Router) {
    this.getData();
  }





  ngOnInit() {
    // this.getData();
  }





  getData() {
    this.noteService.getAllNotes()
      .toPromise()
      .then((data) => {
        this.notesArray = data.sort((a, b) => {
          let dateA = new Date(a.dateEdited).getMilliseconds();
          let dateB = new Date(b.dateEdited).getMilliseconds();
          return dateA - dateB;
        })
      })
      .catch((err) => { console.log(err); });
  }




  
  view(id: number) {
    this.router.navigateByUrl('/note/' + id);
  }





  edit(id: number) {
    this.router.navigateByUrl('/note/edit/' + id);
  }





  delete(id: number) {
    if(confirm("Delete note?")){
      this.noteService.delete(id)
      .toPromise()
      .then(() => {
        this.getData();
      })
      .catch((err) => { console.log(err) });
    }
  }


}
