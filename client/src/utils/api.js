import config from "../config";

import {randomBytes, createHash} from 'crypto-browserify';

export const userRegister = async(username, password) => {
    const salt = randomBytes(16).toString('hex');
    console.log("SALT: " + salt);
    const passwordHash = createHash('sha256').update(salt + password).digest('hex');
    console.log(passwordHash)
    return await requestAPI('/register', 'POST', {username, salt, passwordHash});
}

export const requestAPI = async (
    path = '', 
    method = 'GET', 
    data = null, 
    headers = {}
  ) => {
  
    const url = `${config.domains.api}/${path}`
    console.log("URL: " + url)
    // Set headers
  
    // Default options are marked with *
    const response = await fetch(url, {
      method: method.toUpperCase(),
      mode: 'cors',
      //cache: 'no-cache',
    })
  
    if (response.status < 200 || response.status >= 300) {
      const error = await response.json()
      throw new Error(error.error)
    }
  
    return await response.json()
}
/*
export const requestAPI = async (
    path = '', 
    method = 'GET', 
    data = null, 
    headers = {}
) => {
    const url = `${config.domains.api}/${path}`
    console.log("URL: " + url);

    headers = Object.assign(
        { 'Content-Type': 'application/json'}, headers
    );

    const response = await fetch(
        url, {
            method: method, 
            mode: 'no-cors', 
            cache: 'no-cache',
            headers, 
            body: data ? JSON.stringify(data): null
        }
    );

    return await response.json();
}
*/