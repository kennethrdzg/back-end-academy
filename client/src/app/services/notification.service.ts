import { Injectable } from '@angular/core';
import { RxStomp } from '@stomp/rx-stomp';

// const amqplib = require('amqplib');

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  constructor() {}

  subsciption(): RxStomp{
    const rxStomp = new RxStomp();
    rxStomp.configure({
      brokerURL: 'ws://localhost:15674/ws',
    });
    rxStomp.activate();
    return rxStomp;
  }

}
