import React, { useEffect, useState } from 'react'
import { useCookies } from "react-cookie"
import { Button, Container, Modal } from 'semantic-ui-react'
import { encodeParams } from '../api/http'
import { findAllUsers, removeUser, updateUser } from '../api/services/users'
import UserForm from '../components/user/UserForm'
import UserTable from '../components/user/UserTable'

const AdminUsersView = () => {

    const [cookies] = useCookies(["user"])
    const [users, setUsers] = useState([])
    const [isBusy, setBusy] = useState(true)
    const [showModifyForm, setShowModifyForm] = useState(false)
    const [currentUser, setCurrentUser] = useState({})

    const onModifyClick = (user) => {
        setCurrentUser(user)
        setShowModifyForm(true)
    }

    const onUpdateClick = (user) => {
        let data = {
            username: user.name,
            email: user.email,
            role: user.role
        }
        updateUser(cookies.user, currentUser.id, encodeParams(data)).then((response) => {
            alert('User updated successfully')
        }).catch((reason) => {
            alert(reason)
        })
    }

    const onDeleteClick = () => {
        removeUser(cookies.user, currentUser.id).then((response) => {
            alert('User removed successfully')
        }).catch((reason) => {
            alert(reason)
        })
    }
    const updateUserList = () => {
        findAllUsers(cookies.user).then((response) => {
            setUsers(response.data)
        })
    }

    useEffect(() => {
        setBusy(true)
        updateUserList()
        setBusy(false)
    }, [users])


    return (
        <Container>
            { isBusy ? undefined : <UserTable
                users={users}
                buttonText={"Modify"}
                buttonFunc={onModifyClick} />}
            <Modal
                onClose={() => setShowModifyForm(false)}
                onOpen={() => setShowModifyForm(true)}
                open={showModifyForm}>
                <Modal.Header>Update user</Modal.Header>
                <Modal.Content>
                    <UserForm
                        selectedUser={currentUser}
                        onSubmitClick={onUpdateClick} />
                </Modal.Content>
                <Modal.Header>Remove user</Modal.Header>
                <Modal.Content>
                    <Button
                        color='red'
                        onClick={onDeleteClick}>
                        Remove user
                </Button>
                </Modal.Content>
            </Modal>
        </Container>
    )
}

export default AdminUsersView