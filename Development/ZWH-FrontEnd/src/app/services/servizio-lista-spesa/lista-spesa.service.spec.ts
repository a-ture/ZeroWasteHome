import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ShoppingListService } from './lista-spesa.service';

describe('ShoppingListService', () => {
  let service: ShoppingListService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule], // Importa il modulo di testing HTTP
      providers: [ShoppingListService], // Fornisci il servizio da testare
    });
    service = TestBed.inject(ShoppingListService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify(); // Verifica che non ci siano richieste HTTP pendenti
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should send a POST request to generate a shopping list', () => {
    const mockResponse = { prodotti: [{ nomeProdotto: 'Pane', quantita: 1 }] };
    const email = 'utente@example.com';

    service.generateShoppingList(email).subscribe(response => {
      expect(response).toEqual(mockResponse);
    });

    const req = httpMock.expectOne('http://localhost:8080/api/lista-spesa/generate');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toBe(email);

    req.flush(mockResponse); // Risponde con il mock
  });
});
