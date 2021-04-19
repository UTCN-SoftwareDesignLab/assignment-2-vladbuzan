import React, { useState, useEffect } from 'react'
import { useCookies } from 'react-cookie'
import fileDownload from 'js-file-download'
import { Button, Container, Modal } from 'semantic-ui-react'
import { addBook, CSVReport, deleteBook, findBy, PDFReport, updateBook } from "../api/services/books"
import BookTable from '../components/book/BookTable'
import BookSearchForm from '../components/book/BookSearchForm'
import { encodeParams } from '../api/http'
import BookForm from '../components/book/BookForm'

const AdminBooksView = () => {

    const [isBusy, setBusy] = useState(true)
    const [author, setAuthor] = useState("")
    const [genre, setGenre] = useState("")
    const [title, setTitle] = useState("")
    const [cookies] = useCookies(["user"])
    const [books, setBooks] = useState([])
    const [showUpdateForm, setShowUpdateForm] = useState(false)
    const [showAddForm, setShowAddForm] = useState(false)
    const [selectedBook, setSelectedBook] = useState({})

    const updateBookList = () => {
        findBy(cookies.user, title, author, genre).then((response) => {
            setBooks(response.data)
        })
    }

    const onUpdateClick = (book) => {
        setSelectedBook(book)
        setShowUpdateForm(true)
    }

    const onFormUpdateClick = (book) => {
        let data = (encodeParams(book))
        updateBook(cookies.user, selectedBook.id, data).then((response) => {
            alert("book updated successfully")
        })
    }

    const onCSVReportClick = () => {
        CSVReport(cookies.user).then((response) => {
            fileDownload(response.data, "report.csv");
        })
    }

    const onPDFReportClick = () => {
        PDFReport(cookies.user).then((response) => {
            fileDownload(response.data, "report.pdf");
        })
    }

    const onDeleteClick = () => {
        deleteBook(cookies.user, selectedBook.id).then((response) => {
            alert("book removed succesfully")
        }).catch((reason) => console.log(reason))
        setShowUpdateForm(false)
    }

    const onAddBookClick = (book) => {
        addBook(cookies.user, encodeParams(book)).then((response) => {
            alert("book saved successfully")
        })
    }

    useEffect(() => {
        setBusy(true)
        updateBookList()
        setBusy(false)
    }, [author, genre, title])

    useEffect(() => {
        updateBookList()
    }, [books])


    return (
        <Container>
            <BookSearchForm
                setAuthor={setAuthor}
                setGenre={setGenre}
                setTitle={setTitle} />
            { isBusy ? undefined : <BookTable
                propsBooks={books}
                buttonText={"Update"}
                buttonFunc={onUpdateClick} />}

            <Modal
                onClose={() => setShowUpdateForm(false)}
                onOpen={() => setShowUpdateForm(true)}
                open={showUpdateForm}>
                <Modal.Header>Update book</Modal.Header>
                <Modal.Content>
                    <BookForm
                        selectedBook={selectedBook}
                        onSubmitClick={onFormUpdateClick} />
                </Modal.Content>
                <Modal.Header>Delete book</Modal.Header>
                <Modal.Content>
                    <Button color='red' onClick={onDeleteClick}>
                        Delete
                    </Button>
                </Modal.Content>
            </Modal>

            <Modal
                onClose={() => setShowAddForm(false)}
                onOpen={() => setShowAddForm(true)}
                open={showAddForm}>
                <Modal.Header>Add book</Modal.Header>
                <Modal.Content>
                    <BookForm selectedBook={selectedBook} onSubmitClick={onAddBookClick} />
                </Modal.Content>
            </Modal>

            <Button color='violet' onClick={() => setShowAddForm(true)} >
                Add book
            </Button>
            <Button color='olive' onClick={onCSVReportClick} >
                CSV Report
            </Button>
            <Button color='teal' onClick={onPDFReportClick} >
                PDF Report
            </Button>
        </Container>
    )
}


export default AdminBooksView