import { Component, OnInit } from '@angular/core';
import { HeaderComponent } from '../../components/header/header.component';
import { ShoppingListComponent } from '../../components/shopping-list/shopping-list.component';
import { FooterComponent } from '../../components/footer/footer.component';
import { userBtnComponent } from '../../components/userBtn/userBtn.component';
import { AccordionBasicDemo } from '../../components/accordion-faq/accordion-faq.component';
import { UtilityBarComponent } from '../../components/utility-bar/utility-bar.component';
import { BreadcrumbComponent } from '../../components/breadcrumb/breadcrumb.component';
import { ShoppingListService } from '../../services/servizio-lista-spesa/lista-spesa.service';

@Component({
  selector: 'app-genera-lista',
  standalone: true,
  imports: [
    HeaderComponent,
    ShoppingListComponent,
    FooterComponent,
    userBtnComponent,
    AccordionBasicDemo,
    UtilityBarComponent,
    BreadcrumbComponent,
  ],
  templateUrl: './genera-lista.component.html',
  styleUrls: ['./genera-lista.component.css'],
})
export class GeneraListaComponent implements OnInit {
  tableTitle: string = 'Lista della Spesa';
  buttons = [{ label: 'Modifica Lista' }];
  items: any[] = []; // La lista dinamica che verrà popolata dal backend

  constructor(private shoppingListService: ShoppingListService) {}

  ngOnInit(): void {
    const email = 'test1@example.com'; // Email dinamica, eventualmente sostituire
    this.shoppingListService.generateShoppingList(email).subscribe({
      next: data => {
        console.log('Dati ricevuti dal backend:', data); // Log dei dati

        // Mappatura dei dati dal backend alla struttura necessaria per il componente
        this.items = data.products.map((product: any) => ({
          name: product.nome || product.name, // Usa `nome` o `name` in base alla disponibilità
          quantita: 1, // Aggiungi quantità predefinita (non presente nell'oggetto originale)
          checked: false, // Imposta `false` di default
        }));

        console.log('Items elaborati:', this.items); // Log degli items elaborati
      },
      error: err => console.error('Errore durante il recupero della lista:', err),
    });
  }
}
