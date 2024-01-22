import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { UserSubjectDTO } from "../defs/user-subject-dto-defs";

interface DaySlot {
  name: string;
  event: { name: string; teacher: string; room: string } | null;
}

@Component({
  selector: 'app-timetable',
  templateUrl: './timetable.component.html',
  styleUrls: ['./timetable.component.css']
})
export class TimetableComponent implements OnInit {
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

  constructor(private httpClient: HttpClient) {
  }

  ngOnInit() {
    this.getAllSubjects();

  }


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
    console.log(timeSlot)
    const daySlot = timeSlot?.days.find(d => d.name === day);
    const [timeStart, timeEnd] = hour.split('-')
    const timeStartPadded = timeStart.split(':').map(part => part.padStart(2, '0')).join(':');
    const timeEndPadded = timeEnd.split(':').map(part => part.padStart(2, '0')).join(':');
    const headers = new HttpHeaders({
      'Authorization': 'Bearer ' + sessionStorage.getItem('token')
    });


    if (daySlot) {
      const request = {
        subjectName: this.eventName,
        teacher: this.teacherName,
        room: this.room,
        dayOfWeek: this.parseDayOfWeek(daySlot.name),
        timeStart: timeStartPadded,
        timeEnd: timeEndPadded
      }
      console.log(request)

      this.httpClient.post('http://localhost:8080/user-subject/add', request, { headers: headers }).subscribe(response => {
        console.log(response)
      })



      daySlot.event = { name: this.eventName, teacher: this.teacherName, room: this.room };
      this.cancelAddEvent();
    }
  }


  addSubject(hour: string, day: string, userSubjectDto: UserSubjectDTO) {
    console.log(hour)
    const timeSlot = this.timetable.find(time => time.hour === hour);
    console.log(timeSlot)
    const daySlot = timeSlot?.days.find(d => d.name === day);

    if (daySlot) {
      daySlot.event = { name: userSubjectDto.subjectName, teacher: userSubjectDto.teacher, room: userSubjectDto.room };
    }
  }



  cancelAddEvent() {
    this.showAddEventForm = false;
  }

  parseDayOfWeek(dayOfWeek: string): string {
    switch (dayOfWeek) {
      case 'pon':
        return 'MONDAY';
      case 'wt':
        return 'TUESDAY';
      case 'sr':
        return 'WEDNESDAY';
      case 'czw':
        return 'THURSDAY';
      case 'pt':
        return 'FRIDAY';
      case 'MONDAY':
        return 'pon';
      case 'TUESDAY':
        return 'wt';
      case 'WEDNESDAY':
        return 'sr';
      case 'THURSDAY':
        return 'czw';
      case 'FRIDAY':
        return 'pt';
      default:
        throw new Error('Wrong day');
    }
  }


  getAllSubjects() {
    const headers = new HttpHeaders({
      'Authorization': 'Bearer ' + sessionStorage.getItem('token')
    });

    this.httpClient.get<UserSubjectDTO[]>('http://localhost:8080/user-subject/all', {headers: headers}).subscribe((response: UserSubjectDTO[]) => {
     response.forEach((subject: UserSubjectDTO) => {
       const timeStart = this.formatTime(subject.timeStart)
       const timeEnd = this.formatTime(subject.timeEnd)
       const hour = `${timeStart}-${timeEnd}`
       const day = this.parseDayOfWeek(subject.dayOfWeek)
       this.addSubject(hour, day, subject)
     })
    })

  }

  formatTime(time: string) {
    const [hours, minutes] = time.split(":").map(part => parseInt(part, 10));
    const formattedHours = hours < 10 ? `${hours}` : `${hours}`;
    const formattedMinutes = minutes < 10 ? `0${minutes}` : `${minutes}`;
    return `${formattedHours}:${formattedMinutes}`;
  }
}

