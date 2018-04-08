import { Project } from '../../../model/project';
import { ProjectService } from '../../../services/project.service';
import { Component, OnInit, Input, AfterViewInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { debounceTime } from 'rxjs/operators/debounceTime';

@Component({
  selector: 'app-edit-project',
  templateUrl: './edit-project.component.html',
  styleUrls: ['./edit-project.component.css']
})
export class EditProjectComponent implements OnInit {

  @Input() project: Project;
  nameForm: FormGroup;

  unique = true;

  constructor(private projectService: ProjectService) {}

  ngOnInit() {
    this.nameForm = new FormGroup({
      'name': new FormControl(this.project.name, [
        Validators.required,
        Validators.minLength(2),
        Validators.pattern('[a-z0-9]([-a-z0-9]*[a-z0-9])?')
      ])
    });

    this.onChanges();
  }

  onChanges(): void {
    this.nameForm.get('name').valueChanges
      .pipe(
        debounceTime(400)
      ).subscribe(name => {
        if (this.nameForm.get('name').valid && this.nameForm.get('name').value !== this.project.name) {
          this.projectService.findByName(name)
            .subscribe(result => this.unique = result == null);
        }
      });
  }

  onCancel(): void {
    this.nameForm.reset();
  }

  onSubmit(): void {
    this.project.name = this.nameForm.get('name').value;
    this.projectService.renameProject(this.project);
    this.onCancel();
  }

  get name() {
    return this.nameForm.get('name');
  }

}
