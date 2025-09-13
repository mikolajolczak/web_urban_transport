export interface Address {
  id: number;
  street: string;
  city: string;
  buildingNumber: string;
  apartmentNumber?: string;
  postalCode: string;
}
