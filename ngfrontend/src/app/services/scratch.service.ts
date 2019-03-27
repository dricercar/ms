import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ScratchService {

  constructor(public httpClient: HttpClient) { }
  scratchURL:string = "/api/scratch"
  scratchTypesURL:string = "/api/scratchtypes"
  scratch(formData: FormData){
    return this.httpClient.post(this.scratchURL, formData);
  }
  stopScratch(){
    return this.httpClient.delete(this.scratchURL);
  }
  scratchType():Observable<string[]>{
    return this.httpClient.get<string[]>(this.scratchTypesURL);
  }
}