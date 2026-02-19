import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PropositionThese } from '../models/propositionThese.model';

@Injectable({
  providedIn: 'root'
})
export class PropositionService {

  private apiUrl = 'http://localhost:8080/api/propositions';

  constructor(private http: HttpClient) {}

  // ================= CREATE =================
  create(p: PropositionThese): Observable<PropositionThese> {
    return this.http.post<PropositionThese>(this.apiUrl, p);
  }

  // ================= UPDATE =================
  update(id: number, p: PropositionThese): Observable<PropositionThese> {
    return this.http.put<PropositionThese>(`${this.apiUrl}/${id}`, p);
  }

  // ================= GET ALL =================
  getAll(): Observable<PropositionThese[]> {
    return this.http.get<PropositionThese[]>(this.apiUrl);
  }

  // ================= GET BY ID =================
  getById(id: number): Observable<PropositionThese> {
    return this.http.get<PropositionThese>(`${this.apiUrl}/${id}`);
  }

  // ================= DELETE =================
  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  // ================= SUBMIT =================
  submit(id: number): Observable<PropositionThese> {
    return this.http.post<PropositionThese>(`${this.apiUrl}/${id}/submit`, {});
  }

  // ================= UPLOAD PDF =================
  uploadFile(id: number, file: File): Observable<PropositionThese> {

    const formData = new FormData();
    formData.append('file', file);

    return this.http.post<PropositionThese>(
      `${this.apiUrl}/${id}/upload`,
      formData
    );
  }
}
