//import firebase functions modules
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
	//console.log(admin.database.ServerValue.TIMESTAMP);
//	return;

	/*return Promise.all([
	
	const time =new Date().getTime();
	const cRef = admin.database().ref("cities");
	console.log(time);
	
]).then(() => {
  res.status(200).send('ok');
}).catch(err => {
  console.log(err.stack);
  res.status(500).send('error');
});*/


	console.log('FIRECRON4');
	const time =new Date().getTime();
	const cRef = admin.database().ref("cities");
	console.log(time);
	//const citylist[];
	var cur_city;
	var in_city;
	var cur_dam;
	var in_dam;
	var cur_sh;
	var in_sh;
return new Promise(function(resolve, reject) {

//console.log(cRef+"   ->Cref");
return cRef.once('value').then(snapshot => {
      snapshot.forEach(function(child) {	
		//Each City
			//console.log(child.val());
			//For each city
			cur_city=child.val(); //namecity 
			in_city=admin.database().ref(cur_city+"/Dams") //ref maker
			console.log(in_city+"  in_city var"); 				
				in_city.once('value').then(snapshot1 => {
				snapshot1.forEach(function(child1) {
					//console.log("Here123");
						//EACH DAM
						console.log("MY DAM VALUE :"+child1.val().key);
						console.log("MY DAM KEY :"+child1.key);
						
							/*		cur_dam=child1.key;
									//in_dam=admin.database().ref(cur_city+"/"+cur_dam)
									in_dam=  in_city+"/"+cur_dam;
									console.log(in_dam+"  in_dam var");
									
									in_dam.once('value').then(snapshot2 => {
									//console.log("HereXXX0");
									snapshot2.forEach(function(child2) {
									console.log("HereABS23");
									console.log(child2.val());
									});
									
							return; //res.status(200).send('ok');
							}).catch(error => {this.errorMessage = 'Error - ' + error.message})	
						*/
						
						//EACH DAM
					});
				return; //res.status(200).send('ok');
				}).catch(error => {this.errorMessage = 'Error - ' + error.message})	
			//For each city
			//return;
     
      });
     return res.status(200).send('ok');
  });
//}

});
});

