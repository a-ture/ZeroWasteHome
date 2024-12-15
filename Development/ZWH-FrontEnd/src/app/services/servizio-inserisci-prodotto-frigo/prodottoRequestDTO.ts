export interface ProdottoReq {
  codiceBarre: string;
  nomeProdotto: string;
  dataScadenza: string;
  categoria: Categoria[];
  quantità: number;
}

export type Categoria = 'VEGANO' | 'VEGETARIANO' | 'GLUTENFREE';
