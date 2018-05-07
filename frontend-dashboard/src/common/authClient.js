import { AUTH_LOGIN, AUTH_LOGOUT, AUTH_ERROR, AUTH_CHECK, AUTH_GET_PERMISSIONS } from 'admin-on-rest';

const API_URL = process.env.REACT_APP_API_URL; 
const AUTH_ERROR_MESSAGE = "El usuario no es válido. Intente nuevamente."

export default (type, params) => {
    if (type === AUTH_LOGIN) {
        const { username } = params;
        const request = new Request(API_URL + '/auth', {
            method: 'POST',
            body: JSON.stringify({ username }),
            headers: new Headers({ 'Content-Type': 'application/json' }),
        })
        return fetch(request)
            .then(response => {
                if (response.status < 200 || response.status >= 300) {
                    throw new Error(AUTH_ERROR_MESSAGE);
                }
                return response.json();
            })
            .then((user) => {
                localStorage.setItem('username', user.username);
                localStorage.setItem('role', user.role);
            });
    }
    if (type === AUTH_LOGOUT) {
        localStorage.removeItem('username');
        localStorage.removeItem('role');
        return Promise.resolve();
    }
    if (type === AUTH_ERROR) {
        return Promise.resolve();
    }
    if (type === AUTH_CHECK) {
        return localStorage.getItem('username')
            ? Promise.resolve()
            : Promise.reject();
    }
    if (type === AUTH_GET_PERMISSIONS) {
        const role = localStorage.getItem('role');
        return Promise.resolve(role);
    }
    return Promise.reject('Unkown method');
};