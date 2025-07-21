export interface OrderItem {
  name: string;
  quantity: number;
  unitPrice: number;
}

export interface Order {
  id: number;
  orderDate: string;
  status: string;
  items: OrderItem[];
}