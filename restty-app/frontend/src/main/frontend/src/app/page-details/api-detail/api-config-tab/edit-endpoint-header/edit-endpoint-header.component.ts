import { Header } from '../../../../model/header';
import { HeaderService } from '../../../../services/header.service';
import { Component, OnInit, EventEmitter, Output, Input } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { debounceTime } from 'rxjs/operators/debounceTime';

declare var $: any;

@Component({
  selector: 'app-edit-endpoint-header',
  templateUrl: './edit-endpoint-header.component.html',
  styleUrls: ['./edit-endpoint-header.component.css']
})
export class EditEndpointHeaderComponent implements OnInit {

  @Input() headerEntity: Header;
  @Output() headerEditEvent = new EventEmitter<Header>();

  endpointId: number;
  originalHeaderName: string;
  headerForm: FormGroup;

  unique = true;

  constructor(
    private route: ActivatedRoute,
    private headerService: HeaderService
  ) {
    this.route.params.subscribe(pathVariable => this.endpointId = pathVariable.apiId);
  }

  ngOnInit() {
    this.originalHeaderName = this.headerEntity.header;
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
        if (this.headerForm.get('header').valid && this.headerForm.get('header').value !== this.originalHeaderName) {
          this.headerService.findByHeader(this.endpointId, header).subscribe(result => this.unique = result === null);
        }
      });
  }

  onCancel(): void {
    this.headerForm.reset();
  }

  onDelete(): void {
    this.headerService.deleteHeaders([this.headerEntity.id]).subscribe(result => {
      this.headerEntity.enabled = false;
      this.headerEditEvent.emit(this.headerEntity);
    });
  }

  onSubmit(): void {
    if (this.headerForm.valid) {
      this.headerEntity.header = this.headerForm.get('header').value;
      this.headerEntity.value = this.headerForm.get('value').value;
      this.headerService.updateHeader(this.headerEntity).subscribe(header => {
        this.headerEditEvent.emit(this.headerEntity);
      });

      this.onCancel();
      $('#editHeaderModal').modal('hide');
    }
  }

  get header() {
    return this.headerForm.get('header');
  }

  get value() {
    return this.headerForm.get('value');
  }
}
