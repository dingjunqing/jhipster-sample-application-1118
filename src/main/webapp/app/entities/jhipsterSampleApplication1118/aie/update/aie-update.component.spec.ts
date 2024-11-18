import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { AIEService } from '../service/aie.service';
import { IAIE } from '../aie.model';
import { AIEFormService } from './aie-form.service';

import { AIEUpdateComponent } from './aie-update.component';

describe('AIE Management Update Component', () => {
  let comp: AIEUpdateComponent;
  let fixture: ComponentFixture<AIEUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let aIEFormService: AIEFormService;
  let aIEService: AIEService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AIEUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(AIEUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AIEUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    aIEFormService = TestBed.inject(AIEFormService);
    aIEService = TestBed.inject(AIEService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const aIE: IAIE = { id: 'CBA' };

      activatedRoute.data = of({ aIE });
      comp.ngOnInit();

      expect(comp.aIE).toEqual(aIE);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAIE>>();
      const aIE = { id: 'ABC' };
      jest.spyOn(aIEFormService, 'getAIE').mockReturnValue(aIE);
      jest.spyOn(aIEService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ aIE });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: aIE }));
      saveSubject.complete();

      // THEN
      expect(aIEFormService.getAIE).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(aIEService.update).toHaveBeenCalledWith(expect.objectContaining(aIE));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAIE>>();
      const aIE = { id: 'ABC' };
      jest.spyOn(aIEFormService, 'getAIE').mockReturnValue({ id: null });
      jest.spyOn(aIEService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ aIE: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: aIE }));
      saveSubject.complete();

      // THEN
      expect(aIEFormService.getAIE).toHaveBeenCalled();
      expect(aIEService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAIE>>();
      const aIE = { id: 'ABC' };
      jest.spyOn(aIEService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ aIE });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(aIEService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
