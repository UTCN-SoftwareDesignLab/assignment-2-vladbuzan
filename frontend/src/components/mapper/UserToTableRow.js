import { Button, Table } from 'semantic-ui-react'

export const userToRow = (user, buttonText, buttonFunc) => {

    return (
        <Table.Row key={user.id}>
            <Table.Cell>
                <Button positive onClick={() => buttonFunc(user)}>
                    {buttonText}
                </Button>
            </Table.Cell>
            <Table.Cell>
                {user.name}
            </Table.Cell>
            <Table.Cell>
                {user.email}
            </Table.Cell>
            <Table.Cell>
                {user.role}
            </Table.Cell>
        </Table.Row>
    )
}