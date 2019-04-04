import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Product } from '../entity/product';
import { Payload } from '../entity/payload';
import { ProductPage } from '../entity/product-page';

@Injectable({
  providedIn: 'root'
})
export class SearchService {

  constructor(private httpClient: HttpClient) { }

  queryUrl:string = '/api/search';
  detailUrl:string = '/api/search/detail';
  search(query:string, currentPage:number):Observable<Payload<ProductPage<Product[]>>>{
    console.log(`${this.queryUrl}?wd=${query}&page=${currentPage}`)
    let url = `${this.queryUrl}?wd=${query}&page=${currentPage}`;
    return this.httpClient.get<Payload<ProductPage<Product[]>>>(url);
  }
}
