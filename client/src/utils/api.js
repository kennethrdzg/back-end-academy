import config from "../config";

export const getPosts = async () => {
    return await requestAPI('posts', 'GET');
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