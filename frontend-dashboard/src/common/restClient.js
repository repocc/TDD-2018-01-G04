import {
  COUNT,
  GET_LIST,
  GET_ONE,
  GET_MANY,
  CREATE,
  UPDATE,
  DELETE,
  fetchUtils,
} from 'admin-on-rest';

const API_URL = process.env.REACT_APP_API_URL; 

/**
* @param {String} type One of the constants appearing at the top if this file, e.g. 'UPDATE'
* @param {String} resource Name of the resource to fetch, e.g. 'posts'
* @param {Object} params The REST request params, depending on the type
* @returns {Object} { url, options } The HTTP request parameters
*/
const convertRESTRequestToHTTP = (type, resource, params) => {
  let url = '';
  const options = {};
  switch (type) {
  case COUNT: {
      url = `${API_URL}/${resource}/count`;
      break;
  }
  case GET_LIST: {
      url = `${API_URL}/${resource}`;
      break;
  }
  case GET_MANY: {
    url = `${API_URL}/${resource}`;
    break;
  }
  case GET_ONE:
      url = `${API_URL}/${resource}/${params.id}`;
      break;
  case UPDATE:
      url = `${API_URL}/${resource}/${params.id}`;
      options.method = 'PUT';
      options.body = JSON.stringify(params.data);
      break;
  case CREATE:
      url = `${API_URL}/${resource}`;
      options.method = 'POST';
      options.body = JSON.stringify(params.data);
      break;
  case DELETE:
      url = `${API_URL}/${resource}/${params.id}`;
      options.method = 'DELETE';
      break;
  default:
      throw new Error(`Unsupported fetch action type ${type}`);
  }
  return { url, options };
};
/**
* @param {Object} response HTTP response from fetch()
* @param {String} type One of the constants appearing at the top if this file, e.g. 'UPDATE'
* @param {String} resource Name of the resource to fetch, e.g. 'posts'
* @param {Object} params The REST request params, depending on the type
* @param {Object} count Rest response count.
* @returns {Object} REST response
*/
const convertHTTPResponseToREST = (response, type, resource, params, count) => {
  const { json } = response;
  switch (type) {
  case COUNT:
      return json.count;
  case GET_LIST:
      return { data: json.map(x => x), total: count };
  case CREATE:
      return { data: { ...params.data, id: json.id } };
  default:
      return { data: json };
  }
};

/**
* @param {string} type Request type, e.g GET_LIST
* @param {string} resource Resource name, e.g. "posts"
* @param {Object} payload Request parameters. Depends on the request type
* @param {Object} count Rest response count.
* @returns {Promise} the Promise for a REST response
*/
const executeRequest = (type, resource, params, count) => {
  const { fetchJson } = fetchUtils;
  const { url, options } = convertRESTRequestToHTTP(type, resource, params);
  return fetchJson(url, options)
  .then(response => { 
      return convertHTTPResponseToREST(response, type, resource, params, count)
  });
}

/**
* @param {string} type Request type, e.g GET_LIST
* @param {string} resource Resource name, e.g. "posts"
* @param {Object} payload Request parameters. Depends on the request type
* @returns {Promise} the Promise for a REST response
*/
export default (type, resource, params) => {
  const { fetchJson } = fetchUtils;
  
  switch(type){
      case GET_LIST: {
          //We first need to retrieve count, then find elements
          const { url, options } = convertRESTRequestToHTTP(COUNT, resource, params);
          return fetchJson(url, options)
          .then(response => {
              var count = convertHTTPResponseToREST(response, COUNT, resource, params, null)
              return executeRequest(type, resource, params, count);
          });
      }
      default: {
          return executeRequest(type, resource, params, null);
      }       
  }
};