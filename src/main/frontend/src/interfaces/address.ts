export interface Address {
  addressId: number;
  street: string;
  city: string;
  buildingNumber: string;
  apartmentNumber?: string;
  postalCode: string;
}
