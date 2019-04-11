import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/entity/product';
import { HttpClient } from '@angular/common/http';
import { Router, ActivatedRoute } from '@angular/router';
import { SearchService } from 'src/app/services/search.service';
import { Payload } from 'src/app/entity/payload';

@Component({
  selector: 'app-product-detail',
  templateUrl: './product-detail.component.html',
  styleUrls: ['./product-detail.component.css']
})
export class ProductDetailComponent implements OnInit {

  product:Product = new Product();
  id:number;
  constructor(private route: ActivatedRoute,
              private searchService: SearchService) { }

  ngOnInit() {
    this.getId();
    this.getProduct();
  }
  getProduct(){
    this.searchService.getProduct(this.id).subscribe(payload =>{
      console.log(payload.code);
      console.log(payload.data);
      this.product = payload.data;
    });
  }

  getId(){
    this.id = +this.route.snapshot.paramMap.get("id");
  }

}
