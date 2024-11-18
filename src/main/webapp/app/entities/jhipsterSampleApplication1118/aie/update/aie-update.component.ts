import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IAIE } from '../aie.model';
import { AIEService } from '../service/aie.service';
import { AIEFormGroup, AIEFormService } from './aie-form.service';

@Component({
  standalone: true,
  selector: 'jhi-aie-update',
  templateUrl: './aie-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AIEUpdateComponent implements OnInit {
  isSaving = false;
  aIE: IAIE | null = null;

  protected aIEService = inject(AIEService);
  protected aIEFormService = inject(AIEFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AIEFormGroup = this.aIEFormService.createAIEFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ aIE }) => {
      this.aIE = aIE;
      if (aIE) {
        this.updateForm(aIE);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const aIE = this.aIEFormService.getAIE(this.editForm);
    if (aIE.id !== null) {
      this.subscribeToSaveResponse(this.aIEService.update(aIE));
    } else {
      this.subscribeToSaveResponse(this.aIEService.create(aIE));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAIE>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(aIE: IAIE): void {
    this.aIE = aIE;
    this.aIEFormService.resetForm(this.editForm, aIE);
  }
}
