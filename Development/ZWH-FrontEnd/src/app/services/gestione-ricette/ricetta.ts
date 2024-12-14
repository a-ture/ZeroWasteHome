export interface Ricetta {
  id?: number; // Facoltativo, in quanto il backend lo genera
  nome: string;
  quantitaPerPersona: number;
  categoria: CategoriaRicetta; // Se hai un enum in backend, puoi creare un tipo TypeScript corrispondente
  ingredienti: string[]; // Array di stringhe per rappresentare gli ingredienti
  istruzioni: string;
  img?: string; // Campo opzionale per il nome dell'immagine
}
export type CategoriaRicetta = 'ANTIPASTO' | 'PRIMO' | 'SECONDO' | 'DOLCE' | 'CONTORNO';
