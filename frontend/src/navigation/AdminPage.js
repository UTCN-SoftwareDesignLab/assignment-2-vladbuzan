import React, { useState } from 'react'
import { Container, Menu } from "semantic-ui-react"
import AdminBooksView from '../views/AdminBooksView'
import AdminUsersView from '../views/AdminUsersView'

const AdminPage = () => {

    const [activeItem, setActiveItem] = useState('Users')

    return (
        <Container>
            <Menu vertical>
                <Menu.Item
                    name='Users'
                    active={activeItem === 'Users'}
                    onClick={() => setActiveItem('Users')}
                >
                </Menu.Item>
                <Menu.Item
                    name='Books'
                    active={activeItem === 'Books'}
                    onClick={() => setActiveItem('Books')}
                >
                </Menu.Item>
            </Menu>
            <Container>
                {activeItem === 'Books' ? <AdminBooksView /> : undefined}
                {activeItem === 'Users' ? <AdminUsersView /> : undefined}
            </Container>
        </Container>
    )
}

export default AdminPage