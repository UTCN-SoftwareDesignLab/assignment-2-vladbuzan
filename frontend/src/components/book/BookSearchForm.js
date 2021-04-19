import React from 'react'
import { Form } from 'semantic-ui-react'

const BookSearchForm = ({ setTitle, setAuthor, setGenre }) => {
  
  return (
    <Form>
      <Form.Group widths='equal'>
        <Form.Input fluid label='Title' placeholder='Title' onChange={(event) =>
          setTitle(event.target.value)} />
        <Form.Input fluid label='Author' placeholder='Author' onChange={(event) =>
          setAuthor(event.target.value)} />
        <Form.Input fluid label='Genre' placeholder='Genre' onChange={(event) =>
          setGenre(event.target.value)} />
      </Form.Group>
    </Form>
  )
}

export default BookSearchForm