import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAIE, NewAIE } from '../aie.model';

export type PartialUpdateAIE = Partial<IAIE> & Pick<IAIE, 'id'>;

type RestOf<T extends IAIE | NewAIE> = Omit<T, 'createdAt'> & {
  createdAt?: string | null;
};

export type RestAIE = RestOf<IAIE>;

export type NewRestAIE = RestOf<NewAIE>;

export type PartialUpdateRestAIE = RestOf<PartialUpdateAIE>;

export type EntityResponseType = HttpResponse<IAIE>;
export type EntityArrayResponseType = HttpResponse<IAIE[]>;

@Injectable({ providedIn: 'root' })
export class AIEService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/aies', 'jhipstersampleapplication1118');

  create(aIE: NewAIE): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(aIE);
    return this.http.post<RestAIE>(this.resourceUrl, copy, { observe: 'response' }).pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(aIE: IAIE): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(aIE);
    return this.http
      .put<RestAIE>(`${this.resourceUrl}/${this.getAIEIdentifier(aIE)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(aIE: PartialUpdateAIE): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(aIE);
    return this.http
      .patch<RestAIE>(`${this.resourceUrl}/${this.getAIEIdentifier(aIE)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<RestAIE>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestAIE[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAIEIdentifier(aIE: Pick<IAIE, 'id'>): string {
    return aIE.id;
  }

  compareAIE(o1: Pick<IAIE, 'id'> | null, o2: Pick<IAIE, 'id'> | null): boolean {
    return o1 && o2 ? this.getAIEIdentifier(o1) === this.getAIEIdentifier(o2) : o1 === o2;
  }

  addAIEToCollectionIfMissing<Type extends Pick<IAIE, 'id'>>(aIECollection: Type[], ...aIESToCheck: (Type | null | undefined)[]): Type[] {
    const aIES: Type[] = aIESToCheck.filter(isPresent);
    if (aIES.length > 0) {
      const aIECollectionIdentifiers = aIECollection.map(aIEItem => this.getAIEIdentifier(aIEItem));
      const aIESToAdd = aIES.filter(aIEItem => {
        const aIEIdentifier = this.getAIEIdentifier(aIEItem);
        if (aIECollectionIdentifiers.includes(aIEIdentifier)) {
          return false;
        }
        aIECollectionIdentifiers.push(aIEIdentifier);
        return true;
      });
      return [...aIESToAdd, ...aIECollection];
    }
    return aIECollection;
  }

  protected convertDateFromClient<T extends IAIE | NewAIE | PartialUpdateAIE>(aIE: T): RestOf<T> {
    return {
      ...aIE,
      createdAt: aIE.createdAt?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restAIE: RestAIE): IAIE {
    return {
      ...restAIE,
      createdAt: restAIE.createdAt ? dayjs(restAIE.createdAt) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestAIE>): HttpResponse<IAIE> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestAIE[]>): HttpResponse<IAIE[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
