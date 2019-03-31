import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Payload } from '../entity/payload'

@Injectable({
  providedIn: 'root'
})
export class ScratchService {

  constructor(public httpClient: HttpClient) { }
  scratchURL:string = "/api/scratch"
  scratchTypesURL:string = "/api/scratchtypes"
  scratch(formData: FormData):Observable<Payload<void>>{
    return this.httpClient.post<Payload<void>>(this.scratchURL, formData);
  }
  stopScratch():Observable<Payload<void>>{
    return this.httpClient.delete<Payload<void>>(this.scratchURL);
  }
  scratchType():Observable<string[]>{
    return this.httpClient.get<string[]>(this.scratchTypesURL);
  }
}