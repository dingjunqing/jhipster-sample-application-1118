import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IAIE, NewAIE } from '../aie.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAIE for edit and NewAIEFormGroupInput for create.
 */
type AIEFormGroupInput = IAIE | PartialWithRequiredKeyOf<NewAIE>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IAIE | NewAIE> = Omit<T, 'createdAt'> & {
  createdAt?: string | null;
};

type AIEFormRawValue = FormValueOf<IAIE>;

type NewAIEFormRawValue = FormValueOf<NewAIE>;

type AIEFormDefaults = Pick<NewAIE, 'id' | 'createdAt' | 'isPublic'>;

type AIEFormGroupContent = {
  id: FormControl<AIEFormRawValue['id'] | NewAIE['id']>;
  name: FormControl<AIEFormRawValue['name']>;
  type: FormControl<AIEFormRawValue['type']>;
  description: FormControl<AIEFormRawValue['description']>;
  createdAt: FormControl<AIEFormRawValue['createdAt']>;
  createdBy: FormControl<AIEFormRawValue['createdBy']>;
  icon: FormControl<AIEFormRawValue['icon']>;
  version: FormControl<AIEFormRawValue['version']>;
  category: FormControl<AIEFormRawValue['category']>;
  rate: FormControl<AIEFormRawValue['rate']>;
  aieMetadata: FormControl<AIEFormRawValue['aieMetadata']>;
  userID: FormControl<AIEFormRawValue['userID']>;
  isPublic: FormControl<AIEFormRawValue['isPublic']>;
  organizationName: FormControl<AIEFormRawValue['organizationName']>;
  tenantID: FormControl<AIEFormRawValue['tenantID']>;
};

export type AIEFormGroup = FormGroup<AIEFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AIEFormService {
  createAIEFormGroup(aIE: AIEFormGroupInput = { id: null }): AIEFormGroup {
    const aIERawValue = this.convertAIEToAIERawValue({
      ...this.getFormDefaults(),
      ...aIE,
    });
    return new FormGroup<AIEFormGroupContent>({
      id: new FormControl(
        { value: aIERawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      name: new FormControl(aIERawValue.name, {
        validators: [Validators.required],
      }),
      type: new FormControl(aIERawValue.type, {
        validators: [Validators.required],
      }),
      description: new FormControl(aIERawValue.description),
      createdAt: new FormControl(aIERawValue.createdAt, {
        validators: [Validators.required],
      }),
      createdBy: new FormControl(aIERawValue.createdBy, {
        validators: [Validators.required],
      }),
      icon: new FormControl(aIERawValue.icon),
      version: new FormControl(aIERawValue.version),
      category: new FormControl(aIERawValue.category),
      rate: new FormControl(aIERawValue.rate),
      aieMetadata: new FormControl(aIERawValue.aieMetadata, {
        validators: [Validators.required],
      }),
      userID: new FormControl(aIERawValue.userID, {
        validators: [Validators.required],
      }),
      isPublic: new FormControl(aIERawValue.isPublic, {
        validators: [Validators.required],
      }),
      organizationName: new FormControl(aIERawValue.organizationName),
      tenantID: new FormControl(aIERawValue.tenantID),
    });
  }

  getAIE(form: AIEFormGroup): IAIE | NewAIE {
    return this.convertAIERawValueToAIE(form.getRawValue() as AIEFormRawValue | NewAIEFormRawValue);
  }

  resetForm(form: AIEFormGroup, aIE: AIEFormGroupInput): void {
    const aIERawValue = this.convertAIEToAIERawValue({ ...this.getFormDefaults(), ...aIE });
    form.reset(
      {
        ...aIERawValue,
        id: { value: aIERawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): AIEFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      createdAt: currentTime,
      isPublic: false,
    };
  }

  private convertAIERawValueToAIE(rawAIE: AIEFormRawValue | NewAIEFormRawValue): IAIE | NewAIE {
    return {
      ...rawAIE,
      createdAt: dayjs(rawAIE.createdAt, DATE_TIME_FORMAT),
    };
  }

  private convertAIEToAIERawValue(
    aIE: IAIE | (Partial<NewAIE> & AIEFormDefaults),
  ): AIEFormRawValue | PartialWithRequiredKeyOf<NewAIEFormRawValue> {
    return {
      ...aIE,
      createdAt: aIE.createdAt ? aIE.createdAt.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
