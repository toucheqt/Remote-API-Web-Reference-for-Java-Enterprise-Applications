import { Project } from '../../../model/project';
import { ProjectService } from '../../../services/project.service';
import { Component, OnInit, Input, AfterContentInit, Output, EventEmitter } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-delete-project',
  templateUrl: './delete-project.component.html',
  styles: ['./delete-project.component.css']
})
export class DeleteProjectComponent implements OnInit {

  @Input() project: Project;
  @Output() projectDeleteEvent = new EventEmitter<number>();

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

  onSubmit = () => {
    this.projectService.deleteProject(this.project.id);
    this.projectDeleteEvent.emit(this.project.id);
    this.onCancel();
  }

  get name() {
    return this.nameForm.get('name');
  }

}


