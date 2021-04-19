import React, { useEffect, useState } from 'react'
import { useCookies } from "react-cookie"
import { Container } from "semantic-ui-react"
import { findBy, sellBook } from "../api/services/books"
import BookSearchForm from '../components/book/BookSearchForm'
import BookTable from "../components/book/BookTable"

const BookStorePage = () => {

    const [cookies] = useCookies(["user"])
    const [books, setBooks] = useState([])
    const [isBusy, setBusy] = useState(true)
    const [author, setAuthor] = useState("")
    const [genre, setGenre] = useState("")
    const [title, setTitle] = useState("")


    const updateBookList = () => {
        findBy(cookies.user, title, author, genre).then((response) => {
            setBooks(response.data)
        })
    }

    useEffect(() => {
        setBusy(true)
        updateBookList()
        setBusy(false)
    }, [author, genre, title])

    const onSellClick = (book) => {
        sellBook(cookies.user, book.id).then((response) => {
            updateBookList()
        }).catch((reason) => {
            alert(reason)
        })
    }

    return (
        <Container>
            <BookSearchForm
                setAuthor={setAuthor}
                setGenre={setGenre}
                setTitle={setTitle} />
            { isBusy ? undefined : <BookTable
                propsBooks={books}
                buttonText={"Sell"}
                buttonFunc={onSellClick} />}
        </Container>
    )
}

export default BookStorePage