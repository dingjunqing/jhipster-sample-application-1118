import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IAIE } from '../aie.model';
import { AIEService } from '../service/aie.service';

@Component({
  standalone: true,
  templateUrl: './aie-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class AIEDeleteDialogComponent {
  aIE?: IAIE;

  protected aIEService = inject(AIEService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.aIEService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
