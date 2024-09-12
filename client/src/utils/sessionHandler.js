import Cookies from 'js-cookie';

const app = 'kenneth-app';

// Save session in browser cookie
export const saveSession = (username, userToken) => {
    console.log('Token: ' + userToken);
    Cookies.set(app, userToken)
}

// Get session cookie
export const getSession = () => {
    const data = Cookies.get(app);
    return data;
}

// Delete session cookie
export const deleteSession = () => {
    Cookies.remove(app);
}