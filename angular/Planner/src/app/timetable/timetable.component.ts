import { Component } from '@angular/core';

interface DaySlot {
  name: string;
  event: { name: string; teacher: string; room: string } | null;
}

@Component({
  selector: 'app-timetable',
  templateUrl: './timetable.component.html',
  styleUrls: ['./timetable.component.css']
})
export class TimetableComponent {
  timetable: { hour: string; days: DaySlot[] }[] = [
    { hour: '8:00-9:30', days: [{ name: '', event: null }, { name: 'pon', event: null }, { name: 'wt', event: null }, { name: 'sr', event: null }, { name: 'czw', event: null }, { name: 'pt', event: null }] },
    { hour: '10:00-11:30', days: [{ name: '', event: null }, { name: 'pon', event: null }, { name: 'wt', event: null }, { name: 'sr', event: null }, { name: 'czw', event: null }, { name: 'pt', event: null }] },
    { hour: '12:00-13:30', days: [{ name: '', event: null }, { name: 'pon', event: null }, { name: 'wt', event: null }, { name: 'sr', event: null }, { name: 'czw', event: null }, { name: 'pt', event: null }] },
    { hour: '14:00-15:30', days: [{ name: '', event: null }, { name: 'pon', event: null }, { name: 'wt', event: null }, { name: 'sr', event: null }, { name: 'czw', event: null }, { name: 'pt', event: null }] },
    { hour: '16:00-17:30', days: [{ name: '', event: null }, { name: 'pon', event: null }, { name: 'wt', event: null }, { name: 'sr', event: null }, { name: 'czw', event: null }, { name: 'pt', event: null }] },
    { hour: '18:00-19:30', days: [{ name: '', event: null }, { name: 'pon', event: null }, { name: 'wt', event: null }, { name: 'sr', event: null }, { name: 'czw', event: null }, { name: 'pt', event: null }] },
    { hour: '20:00-21:30', days: [{ name: '', event: null }, { name: 'pon', event: null }, { name: 'wt', event: null }, { name: 'sr', event: null }, { name: 'czw', event: null }, { name: 'pt', event: null }] },
  ];

  showAddEventForm = false;
  eventName: string = '';
  teacherName: string = '';
  room: string = '';
  selectedHour: string = '';
  selectedDay: string = '';


  showAddEventDialog(hour: string, day: string) {
    this.showAddEventForm = true;
    this.eventName = '';
    this.teacherName = '';
    this.room = '';
    this.selectedHour = hour;
    this.selectedDay = day;
  }

  addEvent(hour: string, day: string) {
    const timeSlot = this.timetable.find(time => time.hour === hour);
    const daySlot = timeSlot?.days.find(d => d.name === day);
  
    if (daySlot) {
      daySlot.event = { name: this.eventName, teacher: this.teacherName, room: this.room };
      this.cancelAddEvent();
    }
  }

  cancelAddEvent() {
    this.showAddEventForm = false;
  }
}