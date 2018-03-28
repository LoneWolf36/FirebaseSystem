const functions = require('firebase-functions');
//import admin module
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);


// Listens for new messages added to messages/:pushId
exports.emergencyalert = functions.database.ref('{city_name}/Alert/{pushId}').onWrite( event => {

    console.log('Push notification of alert event triggered');
    //  Grab the current value of what was written to the Realtime Database.
    var valueObject = event.data.val();
    var city_name =valueObject.city_name;
  // Create a notification
    const payload = {
        notification: {
            title:valueObject.dam_name,
            body: valueObject.text,
            sound: "default"
        },
    };
console.log('Payload made');
  //Create an options object that contains the time to live for the notification and the priority
    const options = {
        priority: "high",
        timeToLive: 60 * 60 * 24
    };

	console.log('payload going to be sent');
    return admin.messaging().sendToTopic(city_name, payload, options);
});


exports.notify = functions.database.ref('{city_name}/Notify/{pushId}').onWrite( event => {
    console.log('Push notification of notify event triggered');
    //  Grab the current value of what was written to the Realtime Database.
    var valueObject = event.data.val();
    var city_name =valueObject.city_name;
  // Create a notification
    const payload = {
        notification: {
            title:valueObject.date,
            body: valueObject.time,
            sound: "default"
        },
    };
console.log('Payload made');
  //Create an options object that contains the time to live for the notification and the priority
    const options = {
        priority: "high",
        timeToLive: 60 * 60 * 24
    };

	console.log('payload going to be sent');
    return admin.messaging().sendToTopic(city_name, payload, options);
});


exports.timely_cron = functions.https.onRequest((req, res) => {
	console.log('FIRECRON');
	const time =new Date().getTime();
	const cRef = admin.database().ref("cities");
	console.log(time);
    //console.log(admin.database.ServerValue.TIMESTAMP);
    return cRef.toString();
});
