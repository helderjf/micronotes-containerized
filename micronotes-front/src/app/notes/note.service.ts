import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { LocalStorageService } from 'ngx-webstorage';
import { Observable, of } from 'rxjs';
import { Note } from './note';

@Injectable({
  providedIn: 'root'
})
export class NoteService {

  private url = 'http://localhost:8080/api/notes/';

  constructor(private httpClient: HttpClient, private localStoraqeService: LocalStorageService) { 
  }

  getAllNotes(): Observable<Array<Note>> {
    return this.httpClient.get<Array<Note>>(this.url + 'all');
  }

  createNote(note: Note): Observable<Note>{
    return this.httpClient.post<Note>(this.url + 'create', note);
  }

  getNote(id: number): Observable<Note>{
    return this.httpClient.get<Note>(this.url + id);
  }

  editNote(note: Note):Observable<Note>{
    return this.httpClient.put<Note>(this.url + note.id, note);
  }

  delete(id:number):Observable<any>{
    return this.httpClient.delete<any>(this.url + id);
  }


  }

