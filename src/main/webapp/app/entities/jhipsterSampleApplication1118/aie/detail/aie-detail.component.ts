import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { IAIE } from '../aie.model';

@Component({
  standalone: true,
  selector: 'jhi-aie-detail',
  templateUrl: './aie-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class AIEDetailComponent {
  aIE = input<IAIE | null>(null);

  previousState(): void {
    window.history.back();
  }
}
