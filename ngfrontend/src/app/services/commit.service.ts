import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Payload } from '../entity/payload';

@Injectable({
  providedIn: 'root'
})
export class CommitService {

  constructor(public http: HttpClient) { }
  addUrl = "/api/addProduct"
  addProduct(formData:FormData):Observable<Payload<void>>{
    return this.http.post<Payload<void>>(this.addUrl, formData);
  }
}
