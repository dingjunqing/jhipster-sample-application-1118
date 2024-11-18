import dayjs from 'dayjs/esm';

export interface IAIE {
  id: string;
  name?: string | null;
  type?: string | null;
  description?: string | null;
  createdAt?: dayjs.Dayjs | null;
  createdBy?: string | null;
  icon?: string | null;
  version?: string | null;
  category?: string | null;
  rate?: number | null;
  aieMetadata?: string | null;
  userID?: string | null;
  isPublic?: boolean | null;
  organizationName?: string | null;
  tenantID?: string | null;
}

export type NewAIE = Omit<IAIE, 'id'> & { id: null };
