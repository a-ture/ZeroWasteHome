import { ComponentFixture, TestBed } from '@angular/core/testing';
import { BreadcrumbComponent } from './breadcrumb.component';
import { ActivatedRoute } from '@angular/router';
import { BreadcrumbModule } from 'primeng/breadcrumb';

describe('BreadcrumbComponent', () => {
  let component: BreadcrumbComponent;
  let fixture: ComponentFixture<BreadcrumbComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BreadcrumbModule],
      declarations: [BreadcrumbComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: { data: { breadcrumb: 'Test' } },
            root: { children: [] },
          },
        },
      ],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BreadcrumbComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should render breadcrumb items', () => {
    component.items = [
      { label: 'Home', routerLink: '/' },
      { label: 'Test', routerLink: '/test' },
    ];
    fixture.detectChanges();
    const compiled = fixture.nativeElement;
    expect(compiled.querySelectorAll('.p-breadcrumb a').length).toBe(2);
  });
});
