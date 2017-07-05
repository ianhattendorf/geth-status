import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GethStatusComponent } from './geth-status.component';

describe('GethStatusComponent', () => {
  let component: GethStatusComponent;
  let fixture: ComponentFixture<GethStatusComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GethStatusComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GethStatusComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });

  it('should render title in a h1 tag', async(() => {
    const compiled = fixture.debugElement.nativeElement;
    expect(compiled.querySelector('h1').textContent).toContain('Geth Node Status');
  }));
});
