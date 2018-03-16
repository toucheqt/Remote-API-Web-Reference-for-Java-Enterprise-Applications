import { Project } from '../../model/project';
import { ProjectService } from '../../services/project.service';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators, AbstractControl } from '@angular/forms';
import { Router } from '@angular/router';
import { PopperContent } from 'ngx-popper';
import { debounceTime } from 'rxjs/operators/debounceTime';

@Component({
  selector: 'app-create-project-button',
  templateUrl: './create-project-button.component.html',
  styleUrls: ['./create-project-button.component.css']
})
export class CreateProjectButtonComponent implements OnInit {

  projectForm: FormGroup;
  project = new Project();

  isUnique = true;

  constructor(
    private router: Router,
    private projectService: ProjectService
  ) {}

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

  onCancel(): void {
    this.projectForm.reset();
  }

  onSubmit(): void {
    this.project.name = this.projectForm.get('name').value;
    this.project.source = this.projectForm.get('source').value;
    this.projectService.createProject(this.project).subscribe(project => {
      this.router.navigate([`dashboard/${project.id}`]);
    });
    this.onCancel();
  }

  get name() {
    return this.projectForm.get('name');
  }

  get source() {
    return this.projectForm.get('source');
  }

}



