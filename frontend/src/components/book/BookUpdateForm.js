import React from 'react'
import { Form, Input, Label, Modal } from 'semantic-ui-react'


const BookUpdateForm = (showUpdateForm, setShowUpdateForm) => {

    return (
        <Modal
            onClose={() => setShowUpdateForm(false)}
            onOpen={() => setShowUpdateForm(true)}
            open={showUpdateForm}>
            <Modal.Header>Update book</Modal.Header>
            <Modal.Content>
                <Form>
                    <Form.Field>
                        <Label>
                            <Input placeholder='book name'></Input>
                        </Label>
                    </Form.Field>
                </Form>
            </Modal.Content>
        </Modal>
    )
}

export default BookUpdateForm