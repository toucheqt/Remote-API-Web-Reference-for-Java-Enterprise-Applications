import { Project } from '../../../model/project';
import { ProjectService } from '../../../services/project.service';
import { Component, OnInit, Input, AfterContentInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-delete-project',
  templateUrl: './delete-project.component.html'
})
export class DeleteProjectComponent implements OnInit {

  @Input() project: Project;
  nameForm: FormGroup;

  matches = false;

  constructor(private projectService: ProjectService) {}

  ngOnInit() {
    this.nameForm = new FormGroup({
      'name': new FormControl('', [
        Validators.required
      ])
    });

    this.onChanges();
  }

  onChanges(): void {
    this.nameForm.get('name').valueChanges
      .subscribe(name => {
        if (this.project.name === name) {
          this.matches = true;
        }
      });
  }

  onCancel(): void {
    this.nameForm.reset();
  }

  onSubmit(): void {
    this.projectService.deleteProject(this.project.id);
    this.onCancel();
  }

  get name() {
    return this.nameForm.get('name');
  }

}


