import { Header } from '../../../../model/header';
import { HeaderService } from '../../../../services/header.service';
import { Component, OnInit, EventEmitter, Output } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { debounceTime } from 'rxjs/operators/debounceTime';

declare var $: any;

@Component({
  selector: 'app-add-endpoint-header',
  templateUrl: './add-endpoint-header.component.html',
  styleUrls: ['./add-endpoint-header.component.css']
})
export class AddEndpointHeaderComponent implements OnInit {

  @Output() headerCreateEvent = new EventEmitter<Header>();

  endpointId: number;
  headerEntity: Header = new Header();
  headerForm: FormGroup;

  unique = true;

  constructor(
    private route: ActivatedRoute,
    private headerService: HeaderService
  ) {
    this.route.params.subscribe(pathVariable => this.endpointId = pathVariable.apiId);
  }

  ngOnInit() {
    this.headerForm = new FormGroup({
      'header': new FormControl(this.headerEntity.header, [
        Validators.required
      ]),
      'value': new FormControl(this.headerEntity.value, [
        Validators.required
      ])
    });

    this.onChanges();
  }

  onChanges(): void {
    this.headerForm.get('header').valueChanges
      .pipe(
        debounceTime(400)
      ).subscribe(header => {
        if (this.headerForm.get('header').valid) {
          this.headerService.findByHeader(this.endpointId, header).subscribe(result => this.unique = result === null);
        }
      });
  }

  onCancel(): void {
    this.headerForm.reset();
  }

  onSubmit(): void {
    if (this.headerForm.valid) {
      this.headerEntity.header = this.headerForm.get('header').value;
      this.headerEntity.value = this.headerForm.get('value').value;
      this.headerService.createHeader(this.endpointId, this.headerEntity).subscribe(header => {
        this.headerEntity.id = header.id;
        this.headerCreateEvent.emit(this.headerEntity);
      });

      this.onCancel();
      $('#addHeaderModal').modal('hide');
    }
  }

  get header() {
    return this.headerForm.get('header');
  }

  get value() {
    return this.headerForm.get('value');
  }

}
