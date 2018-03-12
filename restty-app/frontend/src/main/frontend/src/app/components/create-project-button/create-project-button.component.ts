import { ProjectService } from '../../services/project.service';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators, AbstractControl } from '@angular/forms';
import { debounceTime } from 'rxjs/operators/debounceTime';

@Component({
  selector: 'app-create-project-button',
  templateUrl: './create-project-button.component.html',
  styleUrls: ['./create-project-button.component.css']
})
export class CreateProjectButtonComponent implements OnInit {

  projectForm: FormGroup;
  project = { name: '', source: ''};

  isUnique = true;

  constructor(private projectService: ProjectService) {}

  ngOnInit(): void {
    this.projectForm = new FormGroup({
      'name': new FormControl(this.project.name, [
        Validators.required,
        Validators.minLength(2),
        Validators.pattern('[a-z0-9]([-a-z0-9]*[a-z0-9])?')
      ]),
      'source': new FormControl(this.project.source, [
        Validators.required
      ])
    });

    this.onChanges();
  }

  onChanges(): void {
    this.projectForm.get('name').valueChanges
      .pipe(
        debounceTime(400)
      ).subscribe(name => {
        if (this.projectForm.get('name').valid) {
          this.projectService.findByName(name)
            .subscribe(result => this.isUnique = result == null);
        }
      });
  }

  get name() {
    return this.projectForm.get('name');
  }

  get source() {
    return this.projectForm.get('source');
  }

}



