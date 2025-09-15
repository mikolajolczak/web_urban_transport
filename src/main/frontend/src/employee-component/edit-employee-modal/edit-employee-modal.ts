import {Component, EventEmitter, Input, Output} from '@angular/core';
import {faChevronRight, faFloppyDisk} from '@fortawesome/free-solid-svg-icons';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {FormsModule} from '@angular/forms';
import {Employee} from '../../entities/employee';
import {Office} from '../../entities/office';
import {Position} from '../../entities/position';
import {Salary} from '../../entities/salary';

@Component({
  selector: 'app-edit-employee-modal',
  imports: [
    FontAwesomeModule, FormsModule
  ],
  templateUrl: './edit-employee-modal.html',
  styleUrl: './edit-employee-modal.scss'
})
export class EditEmployeeModal {
  @Input() employee!: Employee;
  @Output() close = new EventEmitter<void>();
  @Output() saveEmployee = new EventEmitter<Employee>();


  faChevronRight = faChevronRight;
  faFloppyDisk = faFloppyDisk;

  offices: Office[] = [
    {
      id: 1, officeName: 'Warszawa',
      address: undefined
    },
    {id: 2, officeName: 'Kraków'},
    {id: 3, officeName: 'Gdańsk'},
  ];

  positions:Position[]=[
    {
      id: 1,
      positionName: "asd"
    }
  ]

  salaries:Salary[]=[{
    id: 1,
    amount: 100
  }]

  save() {
    this.saveEmployee.emit(this.employee);
    this.close.emit();
  }


}
