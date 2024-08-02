
document.addEventListener('DOMContentLoaded', () => {
    const addButton = document.getElementById('addTodoButton');
    const inputField = document.getElementById('todoInput');
    const todoList = document.getElementById('todoList');

    addButton.addEventListener('click', () => {
        const todoText = inputField.value.trim();

        if (todoText === '') {
            alert('할 일을 입력해주세염');
            return;
        }

        const li = document.createElement('li');

        // Create checkbox
        const checkbox = document.createElement('input');
        checkbox.type = 'checkbox';
        checkbox.addEventListener('change', () => {
            if (checkbox.checked) {
                li.classList.add('checked');
            } else {
                li.classList.remove('checked');
            }
        });

        // Create text node
        const textNode = document.createTextNode(todoText);

        // Create remove button
        const removeButton = document.createElement('button');
        removeButton.textContent = 'x';
        removeButton.classList.add('remove-btn');
        removeButton.addEventListener('click', () => {
            todoList.removeChild(li);
        });

        li.appendChild(checkbox);
        li.appendChild(textNode);
        li.appendChild(removeButton);
        todoList.appendChild(li);

        inputField.value = '';
    });

    inputField.addEventListener('keypress', (event) => {
        if (event.key === 'Enter') {
            addButton.click();
        }
    });
});
