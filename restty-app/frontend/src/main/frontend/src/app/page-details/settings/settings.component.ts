import { SettingsService } from '../../services/headers.service';
import { Component, OnInit, ViewChild, TemplateRef, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute } from '@angular/router';


@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html'
})
export class SettingsComponent {

  projectId: number;

  constructor(private route: ActivatedRoute) {
    this.route.params.subscribe(pathVariable => this.projectId = pathVariable.id);
  }

}
