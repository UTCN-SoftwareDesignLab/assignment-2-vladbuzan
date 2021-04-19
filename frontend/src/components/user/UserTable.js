import React, { useEffect } from 'react'
import { Table } from 'semantic-ui-react'
import { userToRow } from '../mapper/UserToTableRow'

const UserTable = ({ users, buttonText, buttonFunc }) => {

    useEffect(() => {
    }, [users])

    return (
        <Table celled selectable>
            <Table.Header>
                <Table.Row>
                    <Table.HeaderCell />
                    <Table.HeaderCell>UserName</Table.HeaderCell>
                    <Table.HeaderCell>Email</Table.HeaderCell>
                    <Table.HeaderCell>Role</Table.HeaderCell>
                </Table.Row>
            </Table.Header>

            <Table.Body>
                {users.map((elem) => {
                    return (userToRow(elem, buttonText, buttonFunc))
                })}
            </Table.Body>
        </Table>
    )
}

export default UserTable