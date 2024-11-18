import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../aie.test-samples';

import { AIEFormService } from './aie-form.service';

describe('AIE Form Service', () => {
  let service: AIEFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AIEFormService);
  });

  describe('Service methods', () => {
    describe('createAIEFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAIEFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            type: expect.any(Object),
            description: expect.any(Object),
            createdAt: expect.any(Object),
            createdBy: expect.any(Object),
            icon: expect.any(Object),
            version: expect.any(Object),
            category: expect.any(Object),
            rate: expect.any(Object),
            aieMetadata: expect.any(Object),
            userID: expect.any(Object),
            isPublic: expect.any(Object),
            organizationName: expect.any(Object),
            tenantID: expect.any(Object),
          }),
        );
      });

      it('passing IAIE should create a new form with FormGroup', () => {
        const formGroup = service.createAIEFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            type: expect.any(Object),
            description: expect.any(Object),
            createdAt: expect.any(Object),
            createdBy: expect.any(Object),
            icon: expect.any(Object),
            version: expect.any(Object),
            category: expect.any(Object),
            rate: expect.any(Object),
            aieMetadata: expect.any(Object),
            userID: expect.any(Object),
            isPublic: expect.any(Object),
            organizationName: expect.any(Object),
            tenantID: expect.any(Object),
          }),
        );
      });
    });

    describe('getAIE', () => {
      it('should return NewAIE for default AIE initial value', () => {
        const formGroup = service.createAIEFormGroup(sampleWithNewData);

        const aIE = service.getAIE(formGroup) as any;

        expect(aIE).toMatchObject(sampleWithNewData);
      });

      it('should return NewAIE for empty AIE initial value', () => {
        const formGroup = service.createAIEFormGroup();

        const aIE = service.getAIE(formGroup) as any;

        expect(aIE).toMatchObject({});
      });

      it('should return IAIE', () => {
        const formGroup = service.createAIEFormGroup(sampleWithRequiredData);

        const aIE = service.getAIE(formGroup) as any;

        expect(aIE).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAIE should not enable id FormControl', () => {
        const formGroup = service.createAIEFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAIE should disable id FormControl', () => {
        const formGroup = service.createAIEFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
