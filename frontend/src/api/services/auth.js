import { BASE_URL, HTTP } from "../http"

export const logIn = (data) => {
    return HTTP.post(BASE_URL + "/auth/sign-in", data)
}

export const register = (data)=> {
    console.log(data)
    return HTTP.post(BASE_URL + "/auth/sign-up", data)
}

