import React, { useEffect } from 'react'
import { Table } from 'semantic-ui-react'
import { bookToRow } from '../mapper/BookToTableRow'

const BookTable = ({propsBooks, buttonText, buttonFunc}) => {

    useEffect(() => {   
    }, [propsBooks])
    
    return (
        <Table celled selectable>
            <Table.Header>
                <Table.Row>
                    <Table.HeaderCell />
                    <Table.HeaderCell>Name</Table.HeaderCell>
                    <Table.HeaderCell>Author</Table.HeaderCell>
                    <Table.HeaderCell>Genre</Table.HeaderCell>
                    <Table.HeaderCell>Quantity</Table.HeaderCell>
                    <Table.HeaderCell>Price</Table.HeaderCell>
                </Table.Row>
            </Table.Header>

            <Table.Body>
                { propsBooks.map((elem) => {
                    return (bookToRow(elem, buttonText, buttonFunc))
                })}
            </Table.Body>
        </Table>
    )
}

export default BookTable