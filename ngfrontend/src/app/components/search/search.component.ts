import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { routerNgProbeToken } from '@angular/router/src/router_module';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  query:string;
  constructor(private router: Router,
            private route: ActivatedRoute) { }

  ngOnInit() {
  }

  search(){
     if(this.query != null && this.query.length > 0){
       this.router.navigate(['/search', this.query]);
     }
  }
}
