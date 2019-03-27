import { Component, OnInit } from '@angular/core';
import { ScratchService } from 'src/app/services/scratch.service';
import { of } from 'rxjs';

@Component({
  selector: 'app-scratch',
  templateUrl: './scratch.component.html',
  styleUrls: ['./scratch.component.css']
})
export class ScratchComponent implements OnInit {
  URL:string;
  constructor(public scratchService: ScratchService) { }
  types: string[];
  type: string;

  ngOnInit() {
    this.scratchService.scratchType().subscribe( x => {
      console.log(x);
      this.types = x;
      this.type = this.types[0];
    });
    
  }

  comfirm(){
    alert(this.URL + ' ' + this.type);
    let formData = new FormData();
    formData.append("url", this.URL);
    formData.append("type", this.type);
    console.log(formData);
    this.scratchService.scratch(formData).subscribe( x =>{
      alert(JSON.stringify(x));
      console.log(x);
    });

  }
  stop(){
    alert("stop");
    this.scratchService.stopScratch().subscribe( x => {
      console.log(x);
    })
  }
}
