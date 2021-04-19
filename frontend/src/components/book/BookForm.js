import React, { useState } from 'react'
import { Button, Form, Input, Label } from 'semantic-ui-react'

const BookForm = ({ selectedBook, onSubmitClick }) => {

    const [bookName, setBookName] = useState('')
    const [bookAuthor, setBookAuthor] = useState('')
    const [bookGenre, setBookGenre] = useState('')
    const [bookQuantity, setBookQuantity] = useState(selectedBook.quantity)
    const [bookPrice, setBookPrice] = useState(selectedBook.price)

    const getBookObject = () => {
        return {
            name: bookName,
            author: bookAuthor,
            genre: bookGenre,
            quantity: bookQuantity,
            price: bookPrice
        }
    }

    return (
        <Form>
            <Form.Field>
                <Label> Title </Label>
                <Input placeholder={selectedBook.name} value={bookName}
                    onChange={(e) => setBookName(e.target.value)} />
            </Form.Field>
            <Form.Field>
                <Label> Author </Label>
                <Input placeholder={selectedBook.author} value={bookAuthor}
                    onChange={(e) => setBookAuthor(e.target.value)} />
            </Form.Field>
            <Form.Field>
                <Label> Genre </Label>
                <Input placeholder={selectedBook.genre} value={bookGenre}
                    onChange={(e) => setBookGenre(e.target.value)} />
            </Form.Field>
            <Form.Field>
                <Label> Quantity </Label>
                <Input placeholder={selectedBook.quantity} value={bookQuantity}
                    type='number'
                    onChange={(e) => setBookQuantity(e.target.value)} />
            </Form.Field>
            <Form.Field>
                <Label> Price </Label>
                <Input placeholder={selectedBook.price} value={bookPrice}
                    type='number'
                    onChange={(e) => setBookPrice(e.target.value)} />
            </Form.Field>
            <Form.Field>
                <Button color='black' onClick={() => onSubmitClick(getBookObject())}>
                    Submit
                    </Button>
            </Form.Field>
        </Form>
    )
}

export default BookForm