import { Component, OnInit, Input, SimpleChanges, ElementRef } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { SearchService } from 'src/app/services/search.service';
import { Product } from 'src/app/entity/product';
@Component({
  selector: 'app-search-page',
  templateUrl: './search-page.component.html',
  styleUrls: ['./search-page.component.css']
})
export class SearchPageComponent implements OnInit {

  query:string;
  totalPage: number;
  currentPage: number;
  counter: Object[];
  products: Product[];
  constructor(private router:Router,
            private route:ActivatedRoute,
            private searchService: SearchService) { }

  ngOnInit() {
    console.log("init");
    this.currentPage = 1;
    this.getQuery();
    this.search();
  }
  getQuery(){
    this.query = this.route.snapshot.paramMap.get("query");
  }
  search(){
    if(!this.query){
      this.router.navigate(["/search"])
    }else{
      this.router.navigate(['/search', this.query]);
      this.currentPage = 1;
      this.searchService.search(this.query, this.currentPage).subscribe(payload =>{
        console.log(payload.data);
        this.products = payload.data.products;
        this.currentPage = payload.data.currentPage;
        this.totalPage = payload.data.totalPage;
        this.counter = new Array(this.totalPage);
      });
    }
  }
  nextPage(){
    this.searchService.search(this.query, this.currentPage+1).subscribe(payload =>{
      console.log(payload.data);
      this.products = payload.data.products;
      this.currentPage = payload.data.currentPage;
    });
  }
  lastPage(){
    this.searchService.search(this.query, this.currentPage-1).subscribe(payload =>{
      console.log(payload.data);
      this.products = payload.data.products;
      this.currentPage = payload.data.currentPage;
    });
  }
  choosePage(event){
    console.log(event.srcElement.innerHTML);
    let toPage = event.srcElement.innerHTML;
    this.searchService.search(this.query, toPage).subscribe(payload =>{
      console.log(payload.data);
      this.products = payload.data.products;
      this.currentPage = payload.data.currentPage;
    });
  }
}
