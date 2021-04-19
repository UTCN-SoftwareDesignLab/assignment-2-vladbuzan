import { Button, Table } from 'semantic-ui-react'

export const bookToRow = (book, buttonText, buttonFunc) => {

    return (
        <Table.Row key={book.id}>
            <Table.Cell>
                <Button positive onClick={() => buttonFunc(book)}>
                    {buttonText}
                </Button>
            </Table.Cell>
            <Table.Cell>
                {book.name}
            </Table.Cell>
            <Table.Cell>
                {book.author}
            </Table.Cell>
            <Table.Cell>
                {book.genre}
            </Table.Cell>
            <Table.Cell>
                {book.quantity}
            </Table.Cell>
            <Table.Cell>
                {book.price}
            </Table.Cell>
        </Table.Row>
    )
}