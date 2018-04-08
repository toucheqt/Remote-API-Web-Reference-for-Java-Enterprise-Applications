import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-no-settings',
  templateUrl: './no-settings.component.html',
  styleUrls: ['./no-settings.component.css']
})
export class NoSettingsComponent implements OnInit {

  projectId: number;

  constructor(
    private route: ActivatedRoute
  ) {}

  ngOnInit() {
    this.route.params.subscribe(pathVariable => {
      this.projectId = pathVariable.id;
    });
  }

  toNewHeaderDetail() {}

}
