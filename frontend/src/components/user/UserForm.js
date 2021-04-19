import React, { useState } from 'react'
import { Button, Container, Dropdown, Form, Input, Label } from 'semantic-ui-react'

const UserForm = ({ selectedUser, onSubmitClick }) => {

    const [username, setUsername] = useState('')
    const [email, setEmail] = useState('')
    const [role, setRole] = useState('REGULAR')

    const roleOptions = [{
        key: 1,
        text: 'Regular',
        value: 'REGULAR'
    },
    {
        key: 2,
        text: 'Admin',
        value: 'ADMIN'
    }]

    const getUserObject = () => {
        return {
            name: username,
            email: email,
            role: role
        }
    }

    return (
        <Container>
            <Form>
                <Form.Field>
                    <Label>Username</Label>
                    <Input
                        placeholder={selectedUser.name}
                        value={username}
                        onChange={(e) => setUsername(e.target.value)} />
                </Form.Field>
                <Form.Field>
                    <Label>Emial</Label>
                    <Input
                        type='email'
                        placeholder={selectedUser.email}
                        value={email}
                        onChange={(e) => setEmail(e.target.value)} />
                </Form.Field>
                <Form.Field>
                    <Dropdown
                        placeholder='Role'
                        search selection options={roleOptions}
                        onChange={(_, value) => setRole(value.value)} />
                </Form.Field>
                <Button color='black' onClick={() => onSubmitClick(getUserObject())}>
                    Submit
                </Button>
            </Form>
        </Container>
    )
}

export default UserForm