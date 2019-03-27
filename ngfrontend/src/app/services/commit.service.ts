import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CommitService {

  constructor(public http: HttpClient) { }
  addUrl = "/api/addProduct"
  addProduct(formData:FormData){
    return this.http.post(this.addUrl, formData);
  }
}
