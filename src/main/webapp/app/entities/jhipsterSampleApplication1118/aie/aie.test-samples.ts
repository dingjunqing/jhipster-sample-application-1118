import dayjs from 'dayjs/esm';

import { IAIE, NewAIE } from './aie.model';

export const sampleWithRequiredData: IAIE = {
  id: '0e15929d-01aa-4018-8889-936a516013be',
  name: 'juicy',
  type: 'cross',
  createdAt: dayjs('2024-11-18T00:31'),
  createdBy: 'reorient lightly',
  aieMetadata: 'smug',
  userID: 'freezing',
  isPublic: true,
};

export const sampleWithPartialData: IAIE = {
  id: '8d349433-4fd3-4b6a-976e-d89c0d30fc81',
  name: 'lift',
  type: 'pacemaker graduate uncover',
  description: 'beneath cheerfully typeface',
  createdAt: dayjs('2024-11-17T23:12'),
  createdBy: 'graft',
  icon: 'unless',
  version: 'contradict',
  aieMetadata: 'colligate westernize instantly',
  userID: 'some busily',
  isPublic: false,
  organizationName: 'indeed',
};

export const sampleWithFullData: IAIE = {
  id: 'ef0d0ed0-0495-4fbd-bd95-1ac3de4756e1',
  name: 'overvalue',
  type: 'hm',
  description: 'till boo more',
  createdAt: dayjs('2024-11-17T22:42'),
  createdBy: 'valiantly gah which',
  icon: 'overtrain',
  version: 'hype plumber',
  category: 'politely what nor',
  rate: 16286.47,
  aieMetadata: 'yippee yawningly',
  userID: 'shameful obnoxiously napkin',
  isPublic: true,
  organizationName: 'testimonial',
  tenantID: 'unfortunately unripe dredger',
};

export const sampleWithNewData: NewAIE = {
  name: 'embalm',
  type: 'dispose next if',
  createdAt: dayjs('2024-11-17T17:08'),
  createdBy: 'until ack',
  aieMetadata: 'er',
  userID: 'aha singe pave',
  isPublic: false,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
