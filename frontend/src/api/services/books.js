import authHeader, { BASE_URL, encodeParams, HTTP } from "../http"


export const findAllBooks = (user) => {
    return HTTP.get(BASE_URL + "/book", { headers: authHeader(user) })
}

export const deleteBook = (user, id) => {
    return HTTP.delete(BASE_URL + `/book/${id}`, { headers: authHeader(user) })
}
export const findBy = (user, title, author, genre) => {
    let query = `/search?title=${title}&author=${author}&genre=${genre}`
    return HTTP.get(BASE_URL + "/book" + query, { headers: authHeader(user) })
}

export const sellBook = (user, id) => {
    let query = `/sell/${id}`
    return HTTP.patch(BASE_URL + "/book" + query, undefined, { headers: authHeader(user) })
}

export const updateBook = (user, id, data) => {
    return HTTP.put(BASE_URL + `/book/${id}`, data, { headers: authHeader(user) })
}

export const addBook = (user, data) => {
    return HTTP.post(BASE_URL + '/book', data, { headers: authHeader(user) })
}

export const CSVReport = (user) => {
    return HTTP.get(BASE_URL + '/book/export/CSV', { headers: authHeader(user) })
}

export const PDFReport = (user) => {
    return HTTP.get(BASE_URL + '/book/export/PDF', { headers: authHeader(user) })
}