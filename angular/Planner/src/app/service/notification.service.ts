import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root',
})
export class NotificationService {
  constructor(private snackBar: MatSnackBar) {}

  showSnackBar(message: string, closeLabel: string = 'Close', callback?: () => void) {
    const snackBarRef = this.snackBar.open(message, closeLabel,  {
      duration: 3000,
      horizontalPosition: 'center',
      verticalPosition: 'top',
      panelClass: ['snackbar'],
    });
    if(callback) {
      snackBarRef.onAction().subscribe(() => {
        callback();
      })
    }
  }
}
