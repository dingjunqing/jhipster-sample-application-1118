import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAIE } from '../aie.model';
import { AIEService } from '../service/aie.service';

const aIEResolve = (route: ActivatedRouteSnapshot): Observable<null | IAIE> => {
  const id = route.params.id;
  if (id) {
    return inject(AIEService)
      .find(id)
      .pipe(
        mergeMap((aIE: HttpResponse<IAIE>) => {
          if (aIE.body) {
            return of(aIE.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default aIEResolve;
