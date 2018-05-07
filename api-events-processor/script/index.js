const BASE_URL = 'http://localhost:3000/api'
const axios = require('axios');

var events = [
  {"important": true},
  {"spam": true},
  {"important": false},
  {"important": false}
]

function postEvent(event){
  return axios.post(BASE_URL + '/event', {event: event})
}

let postEventPromises = events.map(e => postEvent(e))

Promise.all(postEventPromises)
.then(function (response) {
  console.log(response.map(r => r.status));
})
.catch(function (error) {
  console.log(error);
});
