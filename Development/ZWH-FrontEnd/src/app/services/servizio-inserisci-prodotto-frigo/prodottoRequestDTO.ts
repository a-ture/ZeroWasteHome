export interface ProdottoReq {
  codiceBarre: string;
  nomeProdotto: string;
  dataScadenza: string;
  categoria: Categoria[];
  img: string;
  quantità: number;
}

export type Categoria = 'VEGANO' | 'VEGETARIANO' | 'SENZA_GLUTINE';
