import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IAIE } from '../aie.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../aie.test-samples';

import { AIEService, RestAIE } from './aie.service';

const requireRestSample: RestAIE = {
  ...sampleWithRequiredData,
  createdAt: sampleWithRequiredData.createdAt?.toJSON(),
};

describe('AIE Service', () => {
  let service: AIEService;
  let httpMock: HttpTestingController;
  let expectedResult: IAIE | IAIE[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(AIEService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find('ABC').subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a AIE', () => {
      const aIE = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(aIE).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AIE', () => {
      const aIE = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(aIE).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AIE', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AIE', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a AIE', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAIEToCollectionIfMissing', () => {
      it('should add a AIE to an empty array', () => {
        const aIE: IAIE = sampleWithRequiredData;
        expectedResult = service.addAIEToCollectionIfMissing([], aIE);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(aIE);
      });

      it('should not add a AIE to an array that contains it', () => {
        const aIE: IAIE = sampleWithRequiredData;
        const aIECollection: IAIE[] = [
          {
            ...aIE,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAIEToCollectionIfMissing(aIECollection, aIE);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AIE to an array that doesn't contain it", () => {
        const aIE: IAIE = sampleWithRequiredData;
        const aIECollection: IAIE[] = [sampleWithPartialData];
        expectedResult = service.addAIEToCollectionIfMissing(aIECollection, aIE);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(aIE);
      });

      it('should add only unique AIE to an array', () => {
        const aIEArray: IAIE[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const aIECollection: IAIE[] = [sampleWithRequiredData];
        expectedResult = service.addAIEToCollectionIfMissing(aIECollection, ...aIEArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const aIE: IAIE = sampleWithRequiredData;
        const aIE2: IAIE = sampleWithPartialData;
        expectedResult = service.addAIEToCollectionIfMissing([], aIE, aIE2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(aIE);
        expect(expectedResult).toContain(aIE2);
      });

      it('should accept null and undefined values', () => {
        const aIE: IAIE = sampleWithRequiredData;
        expectedResult = service.addAIEToCollectionIfMissing([], null, aIE, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(aIE);
      });

      it('should return initial array if no AIE is added', () => {
        const aIECollection: IAIE[] = [sampleWithRequiredData];
        expectedResult = service.addAIEToCollectionIfMissing(aIECollection, undefined, null);
        expect(expectedResult).toEqual(aIECollection);
      });
    });

    describe('compareAIE', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAIE(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareAIE(entity1, entity2);
        const compareResult2 = service.compareAIE(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'CBA' };

        const compareResult1 = service.compareAIE(entity1, entity2);
        const compareResult2 = service.compareAIE(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'ABC' };

        const compareResult1 = service.compareAIE(entity1, entity2);
        const compareResult2 = service.compareAIE(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
