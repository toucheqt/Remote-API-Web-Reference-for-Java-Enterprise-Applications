import { Component, OnInit, ViewEncapsulation, Input } from '@angular/core';

@Component({
  selector: 'app-basic-content',
  templateUrl: './basic-content.component.html',
  styleUrls: ['./basic-content.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class BasicContentComponent implements OnInit {

 @Input() item: any;

  constructor() {
  }

  ngOnInit(): void {
  }

}
