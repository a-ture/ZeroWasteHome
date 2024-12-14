import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ShoppingListService {
  private apiUrl = 'http://localhost:8090/api/lista-spesa'; // URL del backend

  constructor(private http: HttpClient) {}

  generateShoppingList(email: string): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/generate`, email);
  }
}