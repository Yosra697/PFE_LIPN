import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Doctorant } from '../models/doctorant.model';

@Injectable({
  providedIn: 'root'
})
export class DoctorantService {

  private apiUrl = 'http://localhost:8080/api/doctorants';

  constructor(private http: HttpClient) {}

  // ================= CREATE =================
  create(d: Doctorant): Observable<Doctorant> {
    return this.http.post<Doctorant>(this.apiUrl, d);
  }

  // ================= UPDATE =================
  update(id: number, d: Doctorant): Observable<Doctorant> {
    return this.http.put<Doctorant>(`${this.apiUrl}/${id}`, d);
  }

  // ================= GET ALL =================
  getAll(): Observable<Doctorant[]> {
    return this.http.get<Doctorant[]>(this.apiUrl);
  }

  // ================= GET BY ID =================
  getById(id: number): Observable<Doctorant> {
    return this.http.get<Doctorant>(`${this.apiUrl}/${id}`);
  }

  // ================= DELETE =================
  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  // ================= UPLOAD DOSSIER =================
  uploadDossier(id: number, file: File): Observable<Doctorant> {

    const formData = new FormData();
    formData.append('file', file);

    return this.http.post<Doctorant>(
      `${this.apiUrl}/${id}/upload`,
      formData
    );
  }
}
